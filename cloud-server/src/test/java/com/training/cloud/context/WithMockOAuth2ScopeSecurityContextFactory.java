package com.training.cloud.context;

import java.time.Instant;
import java.util.Collections;
import com.training.cloud.context.annatation.WithScope;
import org.assertj.core.util.Maps;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockOAuth2ScopeSecurityContextFactory implements WithSecurityContextFactory<WithScope> {

    @Override
    public SecurityContext createSecurityContext(WithScope annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Jwt jwt = new Jwt("tokenValue", Instant.MIN, Instant.MAX, Maps.newHashMap("key", "value"), Maps.newHashMap("key", "value"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(annotation.value());
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, Collections.singletonList(authority));
        context.setAuthentication(authentication);
        return context;
    }
}
