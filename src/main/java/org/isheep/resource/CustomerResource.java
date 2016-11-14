package org.isheep.controller;

import org.isheep.entity.Customer;
import org.isheep.exception.ResourceNotFoundException;
import org.isheep.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by anthony on 08/11/16.
 */
@RequestMapping("/customer")
@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Inject
    public CustomerController(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public final void delete(@PathVariable("id") final Long id) {
        final Customer customer = customerRepository.findOne(id);
        if (customer == null) {
            throw new ResourceNotFoundException("No customer found for ID: " + id);
        }

        customerRepository.delete(customer);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public final List<Customer> findAll() {
        return customerRepository.findAll();
    }



}
