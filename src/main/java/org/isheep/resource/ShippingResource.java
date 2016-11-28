package org.isheep.resource;

import org.isheep.config.security.CurrentCustomer;
import org.isheep.entity.Customer;
import org.isheep.entity.Shipping;
import org.isheep.repository.ShippingRepository;
import org.isheep.service.ParcelPriceCalculator;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by raymo on 21/11/2016.
 */
@RequestMapping("/shipping")
@RestController
public class ShippingResource {

    private final ShippingRepository shippingRepository;
    private final ParcelPriceCalculator calculator;

    @Inject
    public ShippingResource(final ShippingRepository shippingRepository, final ParcelPriceCalculator calculator) {
        this.shippingRepository = shippingRepository;
        this.calculator = calculator;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public final List<Shipping> myShippings(@CurrentCustomer final Customer customer) {
        return shippingRepository.findBySender(customer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public final Shipping create(@Validated() final Shipping shipping, @CurrentCustomer final Customer customer) {
        shipping.setSender(customer);
        shipping.setPrice(calculator.calculatePrice(shipping.getParcel()));

        return shippingRepository.save(shipping);
    }

}
