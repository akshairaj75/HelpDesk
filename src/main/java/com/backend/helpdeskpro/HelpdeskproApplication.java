package com.backend.helpdeskpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HelpdeskproApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskproApplication.class, args);
	}

}
