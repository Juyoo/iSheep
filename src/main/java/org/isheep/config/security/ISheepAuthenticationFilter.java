package org.isheep.config.security;

/**
 * Created by raymo on 21/11/2016.
 */

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {

        final String xAuth = request.getHeader("X-Authorization");

        // validate the value in xAuth
        if(!isValid(xAuth)){
            logger.debug("Request received without X-Authorization header, aborting authentication.");
            throw new SecurityException("'X-Authorization' header is required.");
        }

        final Authentication authenticationToken = new ISheepAuthenticationToken(Collections.emptyList(), xAuth);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean isValid(final String xAuthHeader) {
        return !Strings.isNullOrEmpty(xAuthHeader);
    }

}