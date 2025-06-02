//package com.technocratsid;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SQLContext;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import scala.Tuple2;
//
//import java.util.Map;
//import java.util.Properties;
//
//@SpringBootApplication
//public class SpringSparkWordCountApplication implements CommandLineRunner {
////	CommandLineRunner lets this class run code when the app starts
//
//    public static void main(String[] args) {
//        SpringApplication.run(SpringSparkWordCountApplication.class, args);
//    }
//
//    @Override
//    public void run(String... args) {
//        SparkConf conf = new SparkConf().setAppName("UserCityCountFromMySQL").setMaster("local");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        SQLContext sqlContext = new SQLContext(sc);
////        SparkConf sets up Spark — here it runs locally (on your computer).
////        JavaSparkContext is the Spark engine for Java.
////        SQLContext helps Spark understand SQL/database tables.
//
//        // JDBC configuration
//        String jdbcUrl = "jdbc:mysql://localhost:3306/customer_db";
//        String table = "customer_info"; // Must have a 'city' column
//        Properties props = new Properties();
//        props.put("user", "root");
//        props.put("password", "Admin@123");
//        props.put("driver", "com.mysql.cj.jdbc.Driver");
//
//        // Read from MySQL using SQLContext
//        Dataset<Row> df = sqlContext.read().jdbc(jdbcUrl, table, props); //Dataset<Row>	A table in Spark  //Reads the whole customer_info table into Spark as a table-like structure (called a Dataset).
//        df.show();
//          
//        // Convert to RDD and count cities
//        JavaRDD<Row> rows = df.javaRDD(); //Converts the table into a list of rows to be processed with normal code. //RDD	Like a list of data in Spark
//
//        JavaPairRDD<String, Integer> genderCounts = rows
//                .map(row -> row.getAs("gender").toString())
//                .mapToPair(gender -> new Tuple2<>(gender.trim(), 1)) //.mapToPair	Prepares data as key-value (like "Male", 1)
//                .reduceByKey(Integer::sum);  //.reduceByKey	    Adds up values by key
//
//        // Print results
//        Map<String, Integer> result = genderCounts.collectAsMap();
//        result.forEach((gender, count) ->
//                System.out.println(gender + ": " + count));
//        
//     // Dataset 2: Indian Users	
//        String queryNY = "(SELECT firstName, country from customer_info where country = 'India') AS in_users";
//        Dataset<Row> in_users = sqlContext.read().jdbc(jdbcUrl, queryNY, props);
//        in_users.show();
//
//        sc.close();  //Closes Spark when done.
//    }
//}



package com.technocratsid;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import scala.Tuple2;

import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class SpringSparkWordCountApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringSparkWordCountApplication.class, args);
    }

    @Override
    public void run(String... args) {
        long startTime = System.currentTimeMillis(); // Start timing

        SparkConf conf = new SparkConf().setAppName("Spark + Spring Boot").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        // JDBC configuration
        String jdbcUrl = "jdbc:mysql://localhost:3306/customer_db";
        String table = "customer_info"; // Must have a 'city' column
        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "Admin@123");
        props.put("driver", "com.mysql.cj.jdbc.Driver");

        // Read from MySQL using SQLContext
        Dataset<Row> df = sqlContext.read().jdbc(jdbcUrl, table, props);
        df.show();

        // Convert to RDD and count gender
        JavaRDD<Row> rows = df.javaRDD();
        JavaPairRDD<String, Integer> genderCounts = rows
                .map(row -> row.getAs("gender").toString())
                .mapToPair(gender -> new Tuple2<>(gender.trim(), 1))
                .reduceByKey(Integer::sum);

        // Print results
        Map<String, Integer> result = genderCounts.collectAsMap();
        result.forEach((gender, count) ->
                System.out.println(gender + ": " + count));

        // Dataset 2: Indian Users
        String queryIN = "(SELECT firstName, country from customer_info where country = 'India') AS in_users";
        Dataset<Row> in_users = sqlContext.read().jdbc(jdbcUrl, queryIN, props);
        in_users.show();

        sc.close();

        long endTime = System.currentTimeMillis(); // End timing
        System.out.println("Spark job completed in " + (endTime - startTime) + " ms");
    }
}

