package com.BuyNest.backend;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {

		System.setProperty("Db_username", System.getenv("Db_username"));
		System.setProperty("Db_password", System.getenv("Db_password"));
		SpringApplication.run(BackendApplication.class, args);
	}

}
