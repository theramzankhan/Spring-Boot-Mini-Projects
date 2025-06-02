package com.project;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import scala.Tuple2;

@SpringBootApplication
@EnableBatchProcessing
public class SparkBatchApp1 {

    public static void main(String[] args) {
    	System.out.println("Spring Batch Version: " + org.springframework.batch.core.Job.class.getPackage().getImplementationVersion());
        SpringApplication.run(SparkBatchApp1.class, args);
    }

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .start(helloStep())
                .build();
    }

    @Bean
    public Step helloStep() {
        return stepBuilderFactory.get("helloStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Hello from Spring Batch");

                    SparkConf conf = new SparkConf().setAppName("HelloSpark").setMaster("local");
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
                 
                    in_users.write()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://localhost:3306/cust_db3")
                    .option("dbtable", "output_1")
                    .option("user", "root")
                    .option("password", "Admin@123")
                    .option("driver", "com.mysql.cj.jdbc.Driver")
                    .mode("overwrite")  // or "append"
                    .save();

                    sc.close();
                    
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
