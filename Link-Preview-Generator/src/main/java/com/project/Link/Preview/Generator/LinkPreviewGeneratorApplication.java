package com.project.Link.Preview.Generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LinkPreviewGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkPreviewGeneratorApplication.class, args);
	}

}
