package com.technocratsid.config;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SparkConfig {

    @PostConstruct
    public void runSparkJob() {
        SparkSession spark = SparkSession
                .builder()
                .appName("Spark User Demo")
                .master("local[*]")
                .getOrCreate();

        String dataFile = getClass().getClassLoader().getResource("users.csv").getPath();

        Dataset<Row> df = spark.read().format("csv")
                .option("header", "true")
                .option("inferSchema", "true")
                .load(dataFile);

        df.createOrReplaceTempView("users");

        Dataset<Row> cities = spark.sql("SELECT DISTINCT city FROM users");

        for (Row row : cities.collectAsList()) {
            String city = row.getString(0);
            Dataset<Row> usersInCity = spark.sql("SELECT * FROM users WHERE city = '" + city + "'");
            long count = usersInCity.count();
            System.out.println(city + ": " + count);
        }

        spark.stop();
    }
}
