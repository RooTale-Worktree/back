package com.Rootale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RootaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RootaleApplication.class, args);
	}

}
