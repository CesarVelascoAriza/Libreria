package com.cava.examples.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerAutorizationApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ServerAutorizationApplication.class, args);
	}

}
