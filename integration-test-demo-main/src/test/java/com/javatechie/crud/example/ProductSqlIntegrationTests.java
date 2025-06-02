package com.javatechie.crud.example;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.javatechie.crud.example.entity.Product;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductSqlIntegrationTests {  //Using SQL-based data setup using @Sql

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllProducts_WithSqlData() {
        System.out.println("================== Starting testGetAllProducts_WithSqlData ==================");

        ResponseEntity<Product[]> response = restTemplate.getForEntity("/products", Product[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Fetched Products: " + Arrays.toString(response.getBody()));
        assertEquals(3, response.getBody().length);

        System.out.println("================== Finished testGetAllProducts_WithSqlData ==================");
    }
    
}
