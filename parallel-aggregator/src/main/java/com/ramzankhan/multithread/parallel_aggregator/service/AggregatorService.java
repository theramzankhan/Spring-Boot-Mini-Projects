package com.ramzankhan.multithread.parallel_aggregator.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AggregatorService {
	
	@Autowired
	private ExternalApiService externalApiService;

	public Map<String, String> aggregateData() throws Exception {
		CompletableFuture<String> weather = externalApiService.getWeather();
		CompletableFuture<String> stock = externalApiService.getStock();
		CompletableFuture<String> news = externalApiService.getNews();
		
		// Wait for all
		CompletableFuture.allOf(weather, stock, news).join();
		
		Map<String, String> result = new HashMap<>();
		result.put("weather", weather.get());
		result.put("stock", stock.get());
		result.put("news", news.get());	
		
		return result;
	}
	
	

}
