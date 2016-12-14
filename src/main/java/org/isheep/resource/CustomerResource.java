package org.isheep.resource;

import com.google.common.base.Strings;
import org.isheep.config.security.CurrentCustomer;
import org.isheep.entity.Customer;
import org.isheep.repository.CustomerRepository;
import org.isheep.service.ApiKeyGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by anthony on 08/11/16.
 */
@RequestMapping(CustomerResource.BASE_URL)
@RestController
public class CustomerResource {

    public static final String BASE_URL = "/customer";
    private final CustomerRepository customerRepository;
    private final ApiKeyGenerator apiKeyGenerator;

    @Inject
    public CustomerResource(final CustomerRepository customerRepository, final ApiKeyGenerator apiKeyGenerator) {
        this.customerRepository = customerRepository;
        this.apiKeyGenerator = apiKeyGenerator;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public final Customer me(@CurrentCustomer final Customer customer) {
        return customer;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public final List<Customer> findAllCustomers(@CurrentCustomer final Customer customer) {
        return customerRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public final Customer register(@RequestBody @Valid final Customer customer) {
        if (customer.getId() != null) {
            throw new IllegalArgumentException("Cannot persist an entity if ID is already defined");
        }
        if (!Strings.isNullOrEmpty(customer.getToken()) && !customer.getToken().trim().isEmpty()) {
            throw new IllegalArgumentException("Cannot persist an entity if Token is already defined");
        }

        customer.setToken(apiKeyGenerator.generateUniqueApiKey());

        return customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/me", method = RequestMethod.DELETE)
    public final void delete(@CurrentCustomer final Customer customer) {
        customerRepository.delete(customer);
    }


}
