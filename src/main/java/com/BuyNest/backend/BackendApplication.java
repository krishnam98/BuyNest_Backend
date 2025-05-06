package com.BuyNest.backend;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
//
//		System.setProperty("Db_username", System.getenv("Db_Username"));
//		System.setProperty("Db_password", System.getenv("Db_Password"));
		SpringApplication.run(BackendApplication.class, args);
	}

}
