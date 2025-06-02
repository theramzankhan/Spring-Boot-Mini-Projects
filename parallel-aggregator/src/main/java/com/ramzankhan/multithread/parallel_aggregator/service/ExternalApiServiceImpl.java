package com.ramzankhan.multithread.parallel_aggregator.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
	
	@Value("${weather.api.key}")
	private String weatherApiKey;
	
	@Value("${stock.api.key}")
	private String stockApiKey;
	
	@Value("${news.api.key}")
	private String newsApiKey;

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public CompletableFuture<String> getWeather() {
		// TODO Auto-generated method stub
		String url = String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=London", weatherApiKey);
		String result = restTemplate.getForObject(url, String.class);
		return CompletableFuture.completedFuture("Weather: " + result);
	}

	@Override
	public CompletableFuture<String> getStock() {
		// TODO Auto-generated method stub
		String url = String.format("https://finnhub.io/api/v1/quote?symbol=AAPL&token=%s", stockApiKey);
		String result = restTemplate.getForObject(url, String.class);
		return CompletableFuture.completedFuture("Stock: " + result);
	}

	@Override
	public CompletableFuture<String> getNews() {
		// TODO Auto-generated method stub
		String url = String.format("https://newsapi.org/v2/top-headlines?country=us&apiKey=%s", newsApiKey);
		String result = restTemplate.getForObject(url, String.class);
		return CompletableFuture.completedFuture("News: " + result);
	}
	
	
}
