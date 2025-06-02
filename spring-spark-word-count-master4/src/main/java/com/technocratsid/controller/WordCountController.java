package com.technocratsid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.spark.sql.*;
import org.apache.spark.sql.Row;

@RestController
public class WordCountController {

	@GetMapping("/count")
	public String getCsvRowCount() {
		SparkSession spark = SparkSession.builder()
				.appName("SparkCSV")
				.master("local[*]")
				.getOrCreate();
		
		Dataset<Row> df = spark.read()
				.option("header", "true")
				.csv("src/main/resources/users.csv");
		
		long count = df.count();
		spark.stop();
		return "CSV Row Count: + count";
	}

}
