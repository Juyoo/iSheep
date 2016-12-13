package org.isheep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ISheepApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ISheepApplication.class, args);
	}
}
