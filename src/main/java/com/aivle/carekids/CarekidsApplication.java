package com.aivle.carekids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarekidsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarekidsApplication.class, args);
	}

}
