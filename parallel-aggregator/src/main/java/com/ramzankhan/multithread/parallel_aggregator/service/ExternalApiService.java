package com.ramzankhan.multithread.parallel_aggregator.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

public interface ExternalApiService {
	
	@Async("taskExecutor")
	CompletableFuture<String> getWeather();
	
	@Async("taskExecutor")
	CompletableFuture<String> getStock();
	
	@Async("taskExecutor")
	CompletableFuture<String> getNews();
}
