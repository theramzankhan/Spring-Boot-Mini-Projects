package com.ramzankhan.multithread.parallel_aggregator.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramzankhan.multithread.parallel_aggregator.service.AggregatorService;

@RestController
@RequestMapping("/aggregate")
public class AggregatorController {

	@Autowired
	private AggregatorService aggregatorService;
	
	@GetMapping
	public ResponseEntity<Map<String, String>> getAggregatedData() throws Exception {
		return ResponseEntity.ok(aggregatorService.aggregateData());
	}
}
