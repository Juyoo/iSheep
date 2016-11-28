package org.isheep.service;

import org.isheep.entity.jpa.CustomerHibernateValidatorTest;
import org.isheep.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by raymo on 28/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerApiKeyGeneratorTest {

    @Mock
    private CustomerRepository customerRepository;

    @Spy
    @InjectMocks
    private CustomerApiKeyGenerator keyGenerator;

    @Test
    public void shouldGenerateUniqueKey() {
        doReturn(CustomerHibernateValidatorTest.createValid())
                .doReturn(CustomerHibernateValidatorTest.createValid())
                .doReturn(null)
                .when(customerRepository).findByToken(any(String.class));

        keyGenerator.generateUniqueApiKey();

        verify(keyGenerator, times(3)).tokenAlreadyExists(any(String.class));
        verify(customerRepository, times(3)).findByToken(any(String.class));
    }

}
