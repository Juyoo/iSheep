package org.isheep.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.*;
import org.isheep.entity.Customer;
import org.isheep.entity.Parcel;
import org.isheep.entity.Shipping;
import org.isheep.exception.NoRouteFoundBetweenAddresses;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by raymo on 21/11/2016.
 */
@Service
public class ParcelPriceCalculator {

    private final GeoApiContext contextGoogle = new GeoApiContext().setApiKey("AIzaSyAEuEb-h7ziOnoyIGzkZRFWIvNdFJOtgdk");

    ParcelPriceCalculator() {
    }

    private Float calculatePrice(final Parcel parcel) {
        final Float cubeDimension = parcel.getHeight() * parcel.getWidth() * parcel.getDepth();
        return (cubeDimension / 100) * 0.01f + parcel.getWeight() * 0.002f;
    }

    public Float calculateShippingPrice(final Shipping shipping){
        final float parcelPrice = calculatePrice(shipping.getParcel());
        final float distance;

        final String[] origins = {"Clermont-Ferrand 63000 France"};
        final String[] destinations = { shipping.getRecipientAddress().asStringAddress() };

        DistanceMatrix results = null;
        try {
            results = DistanceMatrixApi
                    .getDistanceMatrix(contextGoogle, origins, destinations)
                    .units(Unit.METRIC)
                    .mode(TravelMode.DRIVING)
                    .await();
        } catch (final Exception e) {
            throw new IllegalArgumentException("Problem when trying to find distance between warehouse and shipping address.", e);
        }

        try {
            distance = results.rows[0].elements[0].distance.inMeters;
        } catch (final Throwable t) {
            throw new NoRouteFoundBetweenAddresses(shipping.getRecipientAddress());
        }

        final float distanceFactor = distance/200000+1;
        return parcelPrice*distanceFactor;
    }

}
