package com.frontpagenews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CustomnewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomnewsApplication.class, args);
	}
}
