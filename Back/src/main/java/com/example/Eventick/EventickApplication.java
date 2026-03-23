package com.example.Eventick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync; // Importado

@SpringBootApplication
@EnableAsync
public class EventickApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventickApplication.class, args);
	}

}