package com.puzzlix.solid_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SolidTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolidTaskApplication.class, args);
	}

}
