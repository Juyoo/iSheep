package org.isheep.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by raymo on 21/11/2016.
 */
public class ISheepAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -2588333892230675868L;
    private final String token;

    public ISheepAuthenticationToken(final Collection<? extends GrantedAuthority> authorities, final String token) {
        super(authorities);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
