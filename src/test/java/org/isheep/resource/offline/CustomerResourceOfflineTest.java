package org.isheep.resource.offline;

import org.isheep.entity.Customer;
import org.isheep.entity.jpa.CustomerHibernateValidatorTest;
import org.isheep.repository.CustomerRepository;
import org.isheep.resource.CustomerResource;
import org.isheep.service.CustomerApiKeyGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by anthony on 14/11/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerResourceOfflineTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerApiKeyGenerator keyGenerator;

    @InjectMocks
    private CustomerResource customerResource;

    @Before
    public void setUp() {
        doReturn(UUID.randomUUID().toString()).when(keyGenerator).generateUniqueApiKey();
    }


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
    public void shouldFailCreateIfTokenIsAlreadyDefined() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setToken("qsdsqds");

        try {
            customerResource.create(customer);
            fail("Shoul fail if token is already defined.");
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Cannot persist an entity if Token is already defined");
        }
    }

    @Test
    public void shouldCreate() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setToken(null);
        doReturn(customer).when(customerRepository).save(customer);

        final Customer created = customerResource.create(customer);

        assertThat(created).isEqualToIgnoringGivenFields(customer, "id");
        verify(customerRepository, times(1)).save(customer);
    }

}
