package org.isheep.testconfig.security;

import org.isheep.config.CustomSpringProfiles;
import org.isheep.config.security.ISheepAuthenticationProvider;
import org.isheep.config.security.ISheepAuthenticationToken;
import org.isheep.entity.Customer;
import org.isheep.entity.CustomerHibernateValidatorTest;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by raymo on 28/11/2016.
 */
@Profile(CustomSpringProfiles.TEST_PROFILE)
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class MockedSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ROLE = "admin";
    private static final String NO_AUTH = "noauth";

    private final MockedProvider mockedProvider;

    @Inject
    public MockedSecurityConfig(final MockedSecurityConfig.MockedProvider mockedProvider) {
        this.mockedProvider = mockedProvider;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .anonymous().disable();

        http
                .addFilterBefore(new MockedFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(HttpMethod.POST, "/customer");
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.mockedProvider);
    }

    private static class MockedFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
            final String overrideRoleHeader = request.getHeader("mocked-role");

            final Authentication authenticationToken = new ISheepAuthenticationToken(Collections.emptyList(), overrideRoleHeader);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }

    }

    @Profile(CustomSpringProfiles.TEST_PROFILE)
    @Component
    private static final class MockedProvider extends ISheepAuthenticationProvider {

        public MockedProvider() {
            super(null);
        }

        @Override
        public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
            final String token = (String) authentication.getCredentials();

            switch (token) {
                case ADMIN_ROLE:
                    final Customer asAdmin = CustomerHibernateValidatorTest.createValid();
                    asAdmin.setId(42L);
                    asAdmin.setToken("asadmin");
                    return new ISheepAuthentication(asAdmin);
                case NO_AUTH:
                    // unauthenticated
                    return null;
                default:
                    final Customer asuser = CustomerHibernateValidatorTest.createValid();
                    asuser.setId(42L);
                    asuser.setToken("asuser");
                    return new ISheepAuthentication(asuser);
            }
        }

        @Override
        public boolean supports(final Class<?> authentication) {
            return true;
        }
    }

}
