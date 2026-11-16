package com.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication combines three annotations:
//
// 1. @Configuration: Allows this class to define Spring beans.
// 2. @EnableAutoConfiguration: Configures Spring Boot automatically.
// 3. @ComponentScan: Scans this package and its subpackages
//    for components such as @Controller and @Service.
//
// IMPORTANT:
// Keep this class in the parent package so Spring can discover
// all controllers inside p01_spring_mvc_basics.

@SpringBootApplication
public class SpringMvcApplication {

	public static void main(String[] args) {

		// Starts the Spring Boot application.
		// Embedded Tomcat starts automatically.
		// DispatcherServlet is configured automatically.
		SpringApplication.run(SpringMvcApplication.class, args);
	}
}