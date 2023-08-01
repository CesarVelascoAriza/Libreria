package com.cava.examples.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserManagementConfing {

	@Bean
	@Order(1)
	public SecurityFilterChain userManamentSecurityfilterChain(HttpSecurity http)throws Exception{
		
		http.authorizeHttpRequests(
				authz-> authz
				.requestMatchers(HttpMethod.GET,"/actuator/**").permitAll()
				.anyRequest().authenticated()
		)
		.formLogin(Customizer.withDefaults())
		;
		
		
		
		return http.build();

	}
}
