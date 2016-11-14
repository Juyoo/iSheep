package org.isheep.controller;

import org.isheep.entity.Customer;
import org.isheep.entity.CustomerHibernateValidatorTest;
import org.isheep.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by anthony on 14/11/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerOfflineTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerController customerController;

    @Test
    public void shouldRejectCreateIfIdAlreadyDefined() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setId(42L);

        try {
            customerController.create(customer);
            fail("Shoul fail if ID is already defined.");
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cannot persist an entity if ID is already defined");
        }
    }

    @Test
    public void shouldCreate() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        Mockito.doReturn(customer).when(customerRepository).save(customer);

        final Customer created = customerController.create(customer);

        assertThat(created).isEqualToIgnoringGivenFields(customer, "id");
        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
    }

    @Test
    public void shouldRejectDeleteIfNotExists() {
        Mockito.doReturn(null).when(customerRepository).findOne(Matchers.eq(12L));


    }

}
