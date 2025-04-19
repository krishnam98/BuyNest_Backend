package com.BuyNest.backend;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		Dotenv dotenv= Dotenv.load();
		System.setProperty("Db_username",dotenv.get("Db_Username"));
		System.setProperty("Db_password",dotenv.get("Db_Password"));
		SpringApplication.run(BackendApplication.class, args);
	}

}
