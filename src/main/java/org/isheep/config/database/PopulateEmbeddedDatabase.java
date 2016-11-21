package org.isheep.config.database;

import org.isheep.config.conditional.DevProfileCondition;
import org.isheep.entity.Customer;
import org.isheep.entity.embeddable.Address;
import org.isheep.entity.embeddable.CreditCard;
import org.isheep.repository.CustomerRepository;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by raymo on 21/11/2016.
 */
@Component
@Conditional(value = {DevProfileCondition.class})
@SuppressWarnings("unused")
public class PopulateEmbeddedDatabase {

    private final CustomerRepository customerRepository;

    @Inject
    public PopulateEmbeddedDatabase(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void contextRefreshedEvent() {
        this.populateDatabase();
    }

    private void populateDatabase() {
        final CreditCard creditCard = new CreditCard("Amazon", "5105105105105100", "563", 5, 2019);
        final Address address = new Address("6 Bis", "Rue du mouton", "63000", "Clermont-Ferrand");
        final Customer customer = new Customer("Amazon", address, creditCard, "superSecretToken");

        this.customerRepository.save(customer);
    }

}
