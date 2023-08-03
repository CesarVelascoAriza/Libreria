package com.cava.examples.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;


@Configuration
public class IdTokenCustomizerConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        return context -> {

            Authentication principal =context.getPrincipal();
            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                context.getClaims().claim("usename",context.getPrincipal().getName());
                //context.getClaims().claim("scope",context.getPrincipal().getAuthorities());
            }
            if (context.getTokenType().getValue().equals(OAuth2TokenType.ACCESS_TOKEN)){
                context.getClaims().claim("usename",context.getPrincipal().getName());
                //context.getClaims().claim("scope",context.getPrincipal().getAuthorities());
            }
            if (context.getTokenType().getValue().equals(OAuth2TokenType.REFRESH_TOKEN)) {
                context.getClaims().claim("usename",context.getPrincipal().getName());
                //context.getClaims().claim("scope",context.getPrincipal().getAuthorities());
            }
        };
    }
}
