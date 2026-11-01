package com.cava.example.gateway.gateway.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

   /*  @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){

        return httpSecurity
                .csrf(crf-> crf.disable())
                .authorizeExchange(exchage->
                        exchage.pathMatchers("/actuator/**").permitAll()
                                .anyExchange().authenticated()
                        )
                .oauth2ResourceServer(oauth2-> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/actuator/**").permitAll()
                        .anyExchange().authenticated()
                )
                // Configuración explícita para asegurar el comportamiento reactivo de WebFlux
                .oauth2Login(oauth2 -> oauth2
                        .authenticationMatcher(org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.anyExchange())
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }*/
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                // 1. Deshabilitar CSRF para pruebas locales
                .csrf(csrf -> csrf.disable())
                
                // 2. Control de acceso a las rutas
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/actuator/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/webjars/**",
                                "/v3/api-docs/**", "/api/*/v3/api-docs/**",
                                "/api/auth/login").permitAll()
                        .anyExchange().authenticated()
                )
                
                // 3. Configuración del Resource Server
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .build();
    }
}
