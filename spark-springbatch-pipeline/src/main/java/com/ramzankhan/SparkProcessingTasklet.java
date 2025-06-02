package com.ramzankhan;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import scala.Tuple2;

import java.util.Map;
import java.util.Properties;

public class SparkProcessingTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	long startTime = System.currentTimeMillis(); // Start timing
        SparkConf conf = new SparkConf().setAppName("HelloSpark").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        String jdbcUrl = "jdbc:mysql://localhost:3306/customer_db";
        String outputUrl = "jdbc:mysql://localhost:3306/cust_db3";

        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "Admin@123");
        props.put("driver", "com.mysql.cj.jdbc.Driver");

        Dataset<Row> df = sqlContext.read().jdbc(jdbcUrl, "customer_info", props);

        JavaRDD<Row> rows = df.javaRDD();
        JavaPairRDD<String, Integer> genderCounts = rows
                .map(row -> row.getAs("gender").toString())
                .mapToPair(gender -> new Tuple2<>(gender.trim(), 1))
                .reduceByKey(Integer::sum);

        Map<String, Integer> result = genderCounts.collectAsMap();
        result.forEach((gender, count) ->
                System.out.println(gender + ": " + count));

        Dataset<Row> in_users = sqlContext.read().jdbc(jdbcUrl,
                "(SELECT firstName, country FROM customer_info WHERE country = 'India') AS in_users", props);
        in_users.show();

        in_users.write()
	        .format("jdbc")
	        .option("url", "jdbc:mysql://localhost:3306/spark_batch")
	        .option("dbtable", "in_users")
	        .option("user", "root")
	        .option("password", "Admin@123")
	        .option("driver", "com.mysql.cj.jdbc.Driver")
	        .mode("overwrite")  // or "append"
	        .save();

        sc.close();
        long endTime = System.currentTimeMillis(); // End timing
        System.out.println("Spark job completed in " + (endTime - startTime) + " ms");
        return RepeatStatus.FINISHED;
    }
}
