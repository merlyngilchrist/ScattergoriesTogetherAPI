package com.example.ScattergoriesTogetherAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.example.ScattergoriesTogetherAPI.repository") 
@EntityScan(basePackages = "com.example.ScattergoriesTogetherAPI.model") 
@SpringBootApplication
public class ScattergoriesTogetherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScattergoriesTogetherApiApplication.class, args);
	}

}
