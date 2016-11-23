package org.isheep.config;

import org.isheep.config.security.ISheepAuthenticationFilter;
import org.isheep.config.security.ISheepAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.inject.Inject;

/**
 * Created by raymo on 21/11/2016.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ISheepAuthenticationProvider authenticationProvider;

    @Inject
    public SecurityConfig(final ISheepAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
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
                .addFilterBefore(new ISheepAuthenticationFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(HttpMethod.POST, "/customer");
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider);
    }

}
