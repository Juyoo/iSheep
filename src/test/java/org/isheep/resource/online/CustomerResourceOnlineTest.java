package org.isheep.resource.online;

import org.isheep.entity.Customer;
import org.isheep.entity.CustomerHibernateValidatorTest;
import org.isheep.repository.CustomerRepository;
import org.isheep.resource.CustomerResource;
import org.isheep.resource.exceptionmapper.ErrorMessage;
import org.isheep.testutils.WebIntegrationTest;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;

/**
 * Created by raymo on 27/11/2016.
 */
public class CustomerResourceOnlineTest extends WebIntegrationTest {

    private final String CUSTOMER_BASE_URL = CustomerResource.BASE_URL;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void shouldNotCreateIfIDIsAlreadyDefined() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setId(46L);

        final ResponseEntity<ErrorMessage> response = this.restTemplate.postForEntity(CUSTOMER_BASE_URL, customer, ErrorMessage.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).containsIgnoringCase("id");
    }

    @Test
    public void shouldNotCreateIfTokenIsAlreadyDefined() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setToken("abcd");

        final ResponseEntity<ErrorMessage> response = this.restTemplate.postForEntity(CUSTOMER_BASE_URL, customer, ErrorMessage.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).containsIgnoringCase("token");
    }

    @Test
    public void shouldCreateCustomer() {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setToken(null);

        final ResponseEntity<Customer> response = this.restTemplate.postForEntity(CUSTOMER_BASE_URL, customer, Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        then(customerRepository).should(times(1)).save(any(Customer.class));
    }

}
