package com.lindazf.login.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class LoginApplication {

	private static final Logger log = LoggerFactory.getLogger(LoginApplication.class);

	public static void main(String[] args) {
		log.info("Spring Boot Project is started.");
		SpringApplication.run(LoginApplication.class, args);
		log.info("Spring Boot Project is finished.");
	}

	@Bean
	public Docket swaggerConfiguration() {
		log.debug("Portal Application is started. ");
		// return a prepared Docket instance
		return new Docket(DocumentationType.SWAGGER_2).select() //
				.apis(RequestHandlerSelectors.basePackage("com")).paths(PathSelectors.regex("/api/.*"))//
				.build();
	}
}
