package org.isheep.service;

import org.isheep.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Created by raymo on 28/11/2016.
 */
@Service
public class CustomerApiKeyGenerator {

    private final CustomerRepository customerRepository;

    @Inject
    public CustomerApiKeyGenerator(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String generateUniqueApiKey() {
        String apiKey;
        do {
            apiKey = UUID.randomUUID().toString();
        } while (this.tokenAlreadyExists(apiKey));

        return apiKey;
    }

    Boolean tokenAlreadyExists(final String apiKey) {
        return customerRepository.findByToken(apiKey) != null;
    }

}
