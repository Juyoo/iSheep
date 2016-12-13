package org.isheep.service;

import org.assertj.core.data.Percentage;
import org.isheep.entity.Shipping;
import org.isheep.entity.jpa.ShippingHibernateValidatorTest;
import org.isheep.testutils.WebIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by Maxime on 07/12/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class ParcelPriceCalculatorTest extends WebIntegrationTest {


    @Test
    public void shouldCalculateShippingDistance(){
        Shipping shipping = ShippingHibernateValidatorTest.createValid();
        ParcelPriceCalculator priceCalculator = new ParcelPriceCalculator();
        float shippingPrice = priceCalculator.calculateShippingPrice(shipping);
        assertThat(shippingPrice).isCloseTo(2.42f, Percentage.withPercentage(5d));
    }


}
