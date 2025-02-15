package com.example.getwayserver.securityconfig;

import java.util.Collection;
import java.util.stream.Collectors;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfigResource {
    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/security/**", "/actuator/**").permitAll()
                        .requestMatchers( "/api/users/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .cors( crf -> crf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                    jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, AbstractAuthenticationToken>() {

                        @Override
                        public AbstractAuthenticationToken convert(Jwt source) {
                            Collection<String> roles = source.getClaimAsStringList("roles");
                            Collection<GrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());

                            return new JwtAuthenticationToken(source, authorities);
                        }
                    })))
        ;
        

        return http.build();
    }
}
