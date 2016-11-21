package org.isheep.resource;

import org.isheep.config.security.CurrentCustomer;
import org.isheep.entity.Customer;
import org.isheep.entity.Shipping;
import org.isheep.exception.ResourceNotFoundException;
import org.isheep.repository.ShippingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymo on 21/11/2016.
 */
@RequestMapping("/shipping")
@RestController
public class ShippingResource {

    private final ShippingRepository shippingRepository;

    @Inject
    public ShippingResource(final ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public final List<Shipping> myShippings(@CurrentCustomer final Customer customer) {
        return shippingRepository.findBySender(customer);
    }

}
