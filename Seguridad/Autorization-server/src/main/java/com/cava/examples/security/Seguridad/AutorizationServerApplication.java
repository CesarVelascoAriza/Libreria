package com.cava.examples.security.Seguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AutorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutorizationServerApplication.class, args);
	}

}
