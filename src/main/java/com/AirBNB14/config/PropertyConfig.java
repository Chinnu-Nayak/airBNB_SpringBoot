package com.AirBNB14.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class PropertyConfig {
    private JwtFIlterChain jwtFIlterChain;

    public PropertyConfig(JwtFIlterChain jwtFIlterChain) {
        this.jwtFIlterChain = jwtFIlterChain;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtFIlterChain, AuthorizationFilter.class);
        http.authorizeHttpRequests().
                requestMatchers("/api/v1/users/login","/api/v1/users/register")
                .permitAll().anyRequest().authenticated();
        return http.build();

    }
}
