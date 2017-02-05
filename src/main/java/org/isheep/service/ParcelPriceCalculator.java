package org.isheep.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.*;
import org.isheep.entity.Parcel;
import org.isheep.entity.Shipping;
import org.springframework.stereotype.Service;

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
        // TODO : Add distance factor (gmap api?)
        return (cubeDimension / 100) * 0.01f + parcel.getWeight() * 0.002f;
    }

    public Float calculateShippingPrice(final Shipping shipping){
        float parcelPrice = calculatePrice(shipping.getParcel());
        float distance = 0f;

        String[] origins = {"Clermont-Ferrand 63000 France"};
        String[] destinations = {
            shipping.getRecipientAddress().getStreetNumber()
            + " " + shipping.getRecipientAddress().getStreet()
            + " " + shipping.getRecipientAddress().getZip()
            + " " + shipping.getRecipientAddress().getCity()
        };

        DistanceMatrix results = null;
        try {
            results = DistanceMatrixApi
                    .getDistanceMatrix(contextGoogle, origins, destinations)
                    .units(Unit.METRIC)
                    .mode(TravelMode.DRIVING)
                    .await();
        } catch (Exception e) {
            throw new IllegalArgumentException("Problem when trying to find distance between warehouse and shipping address.", e);
        }

        if (results != null){
            distance = results.rows[0].elements[0].distance.inMeters;
        }

        float distanceFactor = distance/200000+1;
        return parcelPrice*distanceFactor;
    }

}
