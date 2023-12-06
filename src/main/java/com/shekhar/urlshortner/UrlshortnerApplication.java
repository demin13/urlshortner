package com.shekhar.urlshortner;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = {
		"com.shekhar.urlshortner.littleurl.Entities",
		"com.shekhar.urlshortner.Auth.Models"
})
@EnableScheduling
public class UrlshortnerApplication {
	public static void main(String[] args) {

		SpringApplication.run(UrlshortnerApplication.class, args);
	}
}

