package org.isheep.config.security;

import org.isheep.entity.Customer;
import org.isheep.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by raymo on 21/11/2016.
 */
@Component
public class ISheepAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CustomerRepository repository;

    @Inject
    public ISheepAuthenticationProvider(final CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String token = (String) authentication.getCredentials();
        if (logger.isDebugEnabled()) {
            logger.debug("Trying to identify user with token '" + token + "'.");
        }

        final Customer customer = repository.findByToken(token);
        if (customer == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No user found with token '" + token + "'.");
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Successfully identified user '" + customer.getToken() + "' for token '" + token + "'.");
        }
        return new ISheepAuthentication(customer);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }

    private static final class ISheepAuthentication implements Authentication {

        private static final long serialVersionUID = -2481572349825611474L;
        private final Customer principal;
        private boolean isAuthenticated;

        private ISheepAuthentication(final Customer principal) {
            this.principal = principal;
            this.isAuthenticated= false;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            // TODO: if roles are included, implement this function properly
            return Collections.emptyList();
        }

        @Override
        public Object getCredentials() {
            return principal.getToken();
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        @Override
        public boolean isAuthenticated() {
            return isAuthenticated;
        }

        @Override
        public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
            this.isAuthenticated = isAuthenticated;
        }

        @Override
        public String getName() {
            return principal.getName();
        }
    }
}
