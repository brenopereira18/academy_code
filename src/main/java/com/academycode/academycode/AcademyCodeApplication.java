package com.academycode.academycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.academycode.academycode")
public class AcademyCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademyCodeApplication.class, args);
	}

}
