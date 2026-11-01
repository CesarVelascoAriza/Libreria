package com.cava.example.gateway.gateway.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * Valida el JWT y además consulta al servidor de autorización si fue revocado por logout.
 */
@Configuration
public class JwtDecoderConfig {

    @Bean
    ReactiveJwtDecoder reactiveJwtDecoder(
            @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri,
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri) {
        NimbusReactiveJwtDecoder decoder = NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuerUri));
        WebClient client = WebClient.create(issuerUri);

        return token -> decoder.decode(token).flatMap(jwt -> {
            if (jwt.getId() == null) {
                return Mono.just(jwt);
            }
            return client.get().uri("/auth/revoked/{jti}", jwt.getId())
                    .retrieve()
                    .bodyToMono(RevokedResponse.class)
                    .map(RevokedResponse::revoked)
                    .flatMap(revoked -> revoked
                            ? Mono.<org.springframework.security.oauth2.jwt.Jwt>error(new BadJwtException("Token revocado"))
                            : Mono.just(jwt));
        });
    }

    record RevokedResponse(boolean revoked) {
    }
}
