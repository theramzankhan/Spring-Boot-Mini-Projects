package com.project;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
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
                    try (JavaSparkContext sc = new JavaSparkContext(conf)) {
                        JavaRDD<String> data = sc.parallelize(Arrays.asList("Hello", "from", "Spark!"));
                        data.collect().forEach(System.out::println);
                    }

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
