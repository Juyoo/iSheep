package org.isheep.resource;

import org.isheep.config.security.CurrentCustomer;
import org.isheep.entity.Customer;
import org.isheep.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by anthony on 08/11/16.
 */
@RequestMapping("/customer")
@RestController
public class CustomerResource {

    private final CustomerRepository customerRepository;

    @Inject
    public CustomerResource(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public final Customer me(@CurrentCustomer final Customer customer) {
        return customer;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public final List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public final Customer create(final Customer customer) {
        if (customer.getId() != null) {
            throw new IllegalArgumentException("Cannot persist an entity if ID is already defined");
        }

        return customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/me", method = RequestMethod.DELETE)
    public final void delete(@CurrentCustomer final Customer customer) {
        customerRepository.delete(customer);
    }


}
