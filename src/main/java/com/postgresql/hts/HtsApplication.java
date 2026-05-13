package com.postgresql.hts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.postgresql.hts")
public class HtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtsApplication.class, args);
	}
}