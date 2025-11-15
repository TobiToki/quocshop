package com.quoctruong.projectjavademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectjavademoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectjavademoApplication.class, args);
		DatabaseConnection.main(new String[]{});
	}

}
