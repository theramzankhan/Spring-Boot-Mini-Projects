package com.example.BatchJobAtRuntime;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchJobAtRuntimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchJobAtRuntimeApplication.class, args);
	}

}
