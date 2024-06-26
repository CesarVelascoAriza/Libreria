package com.cava.examples.services.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({
		"com.cava.examples.common.entitis"
})
public class SpringApplicationBook {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SpringApplicationBook.class, args);
	}

}
