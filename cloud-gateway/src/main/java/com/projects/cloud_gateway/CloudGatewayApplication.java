package com.projects.cloud_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
//@EnableEurekaClient is no longer needed in Spring Boot 3.x + Spring Cloud 2024
@EnableDiscoveryClient
public class CloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}
	
	@PostConstruct //This method will run right after the bean is initialized and Spring context is ready.
	public void init() {
		System.out.println(">>> API Gateway started on port 9191");
	}

}
