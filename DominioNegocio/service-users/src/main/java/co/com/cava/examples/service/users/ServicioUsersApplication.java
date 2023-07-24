package co.com.cava.examples.service.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServicioUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioUsersApplication.class, args);
	}

}
