package org.isheep.resource.online;

import org.hamcrest.Matchers;
import org.isheep.entity.Customer;
import org.isheep.entity.jpa.CustomerHibernateValidatorTest;
import org.isheep.repository.CustomerRepository;
import org.isheep.resource.CustomerResource;
import org.isheep.service.CustomerApiKeyGenerator;
import org.isheep.testutils.WebIntegrationTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isA;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by raymo on 27/11/2016.
 */
@WebMvcTest(CustomerResource.class)
public class CustomerResourceOnlineTest extends WebIntegrationTest {

    private final static String CUSTOMER_BASE_URL = CustomerResource.BASE_URL;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerApiKeyGenerator apiKeyGenerator;

    @Test
    public void shouldNotCreateIfIDIsAlreadyDefined() throws Exception {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setId(46L);

        performWithNoAuthentication(post(CUSTOMER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(customer))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ID is already defined")));
    }

    @Test
    public void shouldNotCreateIfTokenIsAlreadyDefined() throws Exception {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setToken("abcd");

        performWithNoAuthentication(post(CUSTOMER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(customer))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Token is already defined")));
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        final Customer customer = CustomerHibernateValidatorTest.createValid();
        customer.setId(null);
        customer.setToken(null);

        performWithNoAuthentication(post(CUSTOMER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(customer))
        )
                .andExpect(status().isCreated());

        then(customerRepository).should(times(1)).save(any(Customer.class));
        then(apiKeyGenerator).should(times(1)).generateUniqueApiKey();
    }

    @Test
    public void shouldFindMe() throws Exception {
        perform(get(CUSTOMER_BASE_URL + "/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

}
