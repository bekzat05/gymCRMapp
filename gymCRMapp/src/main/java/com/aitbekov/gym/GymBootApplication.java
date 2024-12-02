package com.aitbekov.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GymBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymBootApplication.class, args);
	}

}
