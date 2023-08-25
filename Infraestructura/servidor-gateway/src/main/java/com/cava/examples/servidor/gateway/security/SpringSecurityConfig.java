package com.cava.examples.servidor.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain confugure (ServerHttpSecurity http)throws Exception{
        System.out.println("pasa por autenticacion ");
        http.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHORIZATION);
        http.authorizeExchange((auth)->{
           auth.pathMatchers("/api/users/**").permitAll();

        }).oauth2ResourceServer(Customizer.withDefaults());

        return http.build();
    }

}
