package org.isheep.config.database;

import org.isheep.config.conditional.DevProfileCondition;
import org.isheep.entity.Customer;
import org.isheep.entity.Parcel;
import org.isheep.entity.Shipping;
import org.isheep.entity.embeddable.Address;
import org.isheep.entity.embeddable.CreditCard;
import org.isheep.entity.embeddable.Name;
import org.isheep.repository.CustomerRepository;
import org.isheep.repository.ShippingRepository;
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
    private final ShippingRepository shippingRepository;

    @Inject
    public PopulateEmbeddedDatabase(final CustomerRepository customerRepository, ShippingRepository shippingRepository) {
        this.customerRepository = customerRepository;
        this.shippingRepository = shippingRepository;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void contextRefreshedEvent() {
        this.populateDatabase();
    }

    private void populateDatabase() {
        final CreditCard creditCard = new CreditCard("Amazon", "5105105105105100", "563", 5, 2019);
        final Address address = new Address("6 Bis", "Rue du mouton", "63000", "Clermont-Ferrand");
        Customer customer = new Customer("Amazon", address, creditCard, "superSecretToken");

        customer = this.customerRepository.save(customer);

        final Name recipientName = new Name("Anthony", "Raymond");
        final Address recipentAddress = new Address("9", "Rue geoge clémenceau", "63000", "Clermont-Ferrand");
        final Parcel parcel = new Parcel(12.3f, 15.9f, 3.0f, 352.94f);

        Shipping shipping = new Shipping(customer, recipientName, recipentAddress, parcel, 3.2f);
        shipping = shippingRepository.save(shipping);
    }

}
