package com.example.getwayserver.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfigResource {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/api/security/**", "/actuator/**").permitAll()
                        .pathMatchers("/api/users").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .oauth2ResourceServer(resource -> {
                    resource.jwt(Customizer.withDefaults());
                })
        ;
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
