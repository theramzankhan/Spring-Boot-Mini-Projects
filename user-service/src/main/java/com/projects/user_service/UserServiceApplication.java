package com.projects.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
		
	}
		
	@Bean
	@LoadBalanced //So if there are multiple services are available and connected to service registry, then it will load balanced our requests
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
