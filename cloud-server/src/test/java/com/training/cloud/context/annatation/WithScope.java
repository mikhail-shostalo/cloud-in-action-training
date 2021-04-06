package com.training.cloud.context.annatation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.training.cloud.context.WithMockOAuth2ScopeSecurityContextFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockOAuth2ScopeSecurityContextFactory.class)
public @interface WithScope {
    String value() default StringUtils.EMPTY;
}