package org.isheep.resource;

import org.isheep.entity.Customer;
import org.isheep.entity.CustomerHibernateValidatorTest;
import org.isheep.exception.ResourceNotFoundException;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by anthony on 14/11/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerResourceOfflineTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerResource customerResource;


    @Test
    public void shouldRejectCreateIfIdAlreadyDefined() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setId(42L);

        try {
            customerResource.create(customer);
            fail("Shoul fail if ID is already defined.");
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cannot persist an entity if ID is already defined");
        }
    }

    @Test
    public void shouldCreate() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        doReturn(customer).when(customerRepository).save(customer);

        final Customer created = customerResource.create(customer);

        assertThat(created).isEqualToIgnoringGivenFields(customer, "id");
        verify(customerRepository, times(1)).save(customer);
    }

}
