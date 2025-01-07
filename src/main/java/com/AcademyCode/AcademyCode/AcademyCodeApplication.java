package com.AcademyCode.AcademyCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.AcademyCode.AcademyCode")
public class AcademyCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademyCodeApplication.class, args);
	}

}
