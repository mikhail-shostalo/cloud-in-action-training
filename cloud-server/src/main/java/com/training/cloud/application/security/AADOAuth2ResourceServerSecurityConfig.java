package com.training.cloud.application.security;

import com.azure.spring.aad.webapi.AADJwtBearerTokenAuthenticationConverter;
import com.azure.spring.aad.webapi.AADResourceServerWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AADOAuth2ResourceServerSecurityConfig extends AADResourceServerWebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Value("${allowed.write.scope}")
    private String allowedWriteScope;

    @Value("${allowed.origins}")
    private String allowedOrigins;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests((requests) -> requests
                        .antMatchers(HttpMethod.GET, "/swagger-api-docs.html", "/swagger-ui/**", "/actuator/health", "/health-check")
                        .permitAll()
                        .antMatchers(HttpMethod.DELETE).hasAuthority(allowedWriteScope)
                        .antMatchers(HttpMethod.POST).hasAuthority(allowedWriteScope)
                        .antMatchers(HttpMethod.PUT).hasAuthority(allowedWriteScope)
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new AADJwtBearerTokenAuthenticationConverter());
        http.csrf().disable();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*");
    }
}