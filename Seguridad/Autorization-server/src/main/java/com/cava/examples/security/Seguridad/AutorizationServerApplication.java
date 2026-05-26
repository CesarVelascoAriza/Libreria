package com.cava.examples.security.Seguridad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AutorizationServerApplication implements CommandLineRunner {

/* 	@Autowired
	private BCryptPasswordEncoder passwordEncoder; */
	public static void main(String[] args) {
		SpringApplication.run(AutorizationServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password ="secret";
		/* String passwordEncode = passwordEncoder.encode(password);
		System.out.println(passwordEncode);*/
	}
}
