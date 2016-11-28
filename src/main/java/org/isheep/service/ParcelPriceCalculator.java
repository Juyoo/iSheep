package org.isheep.service;

import org.isheep.entity.Parcel;
import org.springframework.stereotype.Service;

/**
 * Created by raymo on 21/11/2016.
 */
@Service
public class ParcelPriceCalculator {

    ParcelPriceCalculator() {
    }

    public Float calculatePrice(final Parcel parcel) {
        final Float cubeDimension = parcel.getHeight() * parcel.getWidth() * parcel.getDepth();
        // TODO : Add distance factor (gmap api?)
        return (cubeDimension / 100) * 0.01f + parcel.getWeight() * 0.002f;
    }

}
