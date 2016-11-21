package org.isheep.config.security;

/**
 * Created by raymo on 21/11/2016.
 */

import com.google.common.base.Strings;
import org.isheep.entity.Customer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class ISheepAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String xAuth = request.getHeader("X-Authorization");

        // validate the value in xAuth
        if(!isValid(xAuth)){
            throw new SecurityException("'X-Authorization' header is required.");
        }

        final Authentication authenticationToken = new AuthenticationToken(Collections.emptyList(), xAuth);

        // Create our Authentication and let Spring know about it
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean isValid(final String xAuthHeader) {
        return !Strings.isNullOrEmpty(xAuthHeader);
    }

}