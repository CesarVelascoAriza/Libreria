package com.cava.examples.seguridad.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.cava.examples.seguridad.service.UserService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import com.cava.examples.seguridad.service.Impl.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class ServerAuthorizationConfig {


    // 1. Inyecta tu implementación al inicio de la clase
    @Autowired
    private UserServiceImp userServiceImp;

    // 2. Agrega este Bean para que Spring Security lo asocie globalmente
    @Bean
    public UserDetailsService userDetailsService() {
        return userServiceImp;
    }

    @Bean
@Order(1)
public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
            new OAuth2AuthorizationServerConfigurer();

    authorizationServerConfigurer
            .oidc(Customizer.withDefaults()); // Habilita OpenID Connect 1.0

    http
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer, Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated()
            )
            // 🛠️ FIX 1: Habilitar HTTP Basic para que el Gateway pueda intercambiar el token por detrás
            .httpBasic(Customizer.withDefaults())
            // 🛠️ FIX 2: El formulario de login debe estar amarrado al contenedor de OAuth2
            .formLogin(Customizer.withDefaults());

    http.exceptionHandling(exceptions -> exceptions
            .defaultAuthenticationEntryPointFor(
                    new LoginUrlAuthenticationEntryPoint("/login"),
                    new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
            )
    );

    return http.build();
}
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 🔓 Mantenemos CSRF deshabilitado provisionalmente para pruebas locales
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/actuator/**", "/login").permitAll()    
                    .anyRequest().authenticated()
                )
                // 💻 Habilitamos el login por formulario en el filtro por defecto
                .formLogin(Customizer.withDefaults()); 

        return http.build();
    }

@Bean
public DaoAuthenticationProvider authenticationProvider(
        UserDetailsService userDetailsService, 
        PasswordEncoder passwordEncoder) {
    
    // 👤 Pasamos el servicio directamente en el constructor requerido
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService); 
    
    // 🔐 Asignamos el encriptador de contraseñas
    provider.setPasswordEncoder(passwordEncoder);       
    
    return provider;
}
    /*@Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        authorizationServerConfigurer
                .oidc(Customizer.withDefaults());

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                // 🛠️ FIX CRÍTICO: Habilitamos HTTP Basic y FormLogin en este filtro 
                // para que sepa procesar las credenciales enviadas a /oauth2/authorize
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        http.exceptionHandling(exceptions -> exceptions
                .defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint("/login"),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                )
        );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/actuator/**", "/login").permitAll()    
                    .anyRequest().authenticated()
                        
                )
                .formLogin(Customizer.withDefaults());
                //.sessionManagement(session -> session
                //        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //);

        return http.build();
    }*/

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient gatewayClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gateway-app")
                .clientSecret(passwordEncoder.encode("gateway-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 🔄 Flujos anteriores
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) 
                .authorizationGrantType(new AuthorizationGrantType("password"))
                .redirectUri("http://localhost:8762/login/oauth2/code/gateway-app")
                .scope("read")
                .scope("write")
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(false) 
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(gatewayClient);
    }
    /*@Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient gatewayClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gateway-app")
                .clientSecret(passwordEncoder.encode("gateway-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 🔄 Mantenemos tus flujos existentes
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                // 🔑 ¡AGREGADO!: Habilitamos el flujo de credenciales de cliente para pruebas directas
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) 
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .redirectUri("http://servidor-gateway:8762/login/oauth2/code/gateway-app")
                .scope("read")
                .scope("write")
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(false) 
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(gatewayClient);
    }
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient gatewayClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gateway-app")
                .clientSecret(passwordEncoder.encode("gateway-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .redirectUri("http://servidor-gateway:8762/login/oauth2/code/gateway-app")
                .scope("read")
                .scope("write")
                // 🛠️ CORREGIDO: Sintaxis oficial usando el Builder de ClientSettings
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(false) // Fuerza el uso seguro de PKCE
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(gatewayClient);
    }*/

    // 🛠️ AGREGO: Customizer para inyectar los roles reales de la Base de Datos en el Token JWT
    /*@Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            if (context.getTokenType().getValue().equals("access_token")) {
                // Extraemos las autoridades cargadas previamente por tu UsuarioServiceImp
                Set<String> roles = context.getPrincipal().getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());

                // Mapeamos los roles dentro del Claim "roles" en el JWT
                context.getClaims().claim("roles", roles);
            }
        };
    }*/

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // 🛠️ Reemplaza este Bean en tu ServerAuthorizationConfig

    @Bean
public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
    return context -> {
        if (context.getTokenType().getValue().equals("access_token")) {
            // 👤 Extraemos el principal del contexto del token
            Object principal = context.getPrincipal();
            
            // 🔄 Si el principal es una instancia de Authentication, extraemos sus roles
            if (principal instanceof org.springframework.security.core.Authentication authentication) {
                Set<String> roles = authentication.getAuthorities().stream()
                        .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                        .map(authority -> authority.startsWith("ROLE_") ? authority : "ROLE_" + authority)
                        .collect(Collectors.toSet());

                // 🔑 Guardamos los roles en el Claim del JWT
                context.getClaims().claim("roles", roles);
            }
        }
    };
}
}
