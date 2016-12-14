package org.isheep.resource.online;

import org.isheep.entity.Parcel;
import org.isheep.entity.Shipping;
import org.isheep.entity.jpa.ShippingHibernateValidatorTest;
import org.isheep.repository.ShippingRepository;
import org.isheep.resource.ShippingResource;
import org.isheep.service.ParcelPriceCalculator;
import org.isheep.testutils.WebIntegrationTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by raymo on 28/11/2016.
 */
@WebMvcTest(ShippingResource.class)
public class ShippingResourceOnlineTest extends WebIntegrationTest {
    private static final String SHIPPING_BASE_URL = ShippingResource.BASE_URL;

    @MockBean
    private ShippingRepository shippingRepository;

    @MockBean
    private ParcelPriceCalculator calculator;

    @Test
    public void shouldFailCreateIfIdIsAlreadyDefined() throws Exception {
        final Shipping shipping = ShippingHibernateValidatorTest.createValid();
        shipping.setId(46L);

        perform(post(SHIPPING_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(shipping))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ID is already defined")));
    }

    @Test
    public void shouldCreateShipping() throws Exception {
        final Shipping shipping = ShippingHibernateValidatorTest.createValid();
        shipping.setId(null);

        perform(post(SHIPPING_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(shipping))
        )
                .andExpect(status().isCreated());

        then(shippingRepository).should(times(1)).save(any(Shipping.class));
        then(calculator).should(times(1)).calculateShippingPrice(any(Shipping.class));
    }

}
