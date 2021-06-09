package com.lindazf.login.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApplication {

	private static final Logger log = LoggerFactory.getLogger(LoginApplication.class);

	public static void main(String[] args) {
		log.info("Spring Boot Project is started.");
		SpringApplication.run(LoginApplication.class, args);
		log.info("Spring Boot Project is finished.");
	}

}
