package com.javatechie.crud.example;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class SpringBootCrudExample2ApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        System.out.println("==================== [BeforeEach] Deleting all products from repository... ====================");
        productRepository.deleteAll();
        System.out.println("==================== [BeforeEach] Repository cleaned. ====================");
    }

    @Test
    void testCreateProduct() {
        System.out.println("==================== [Test] testCreateProduct started. ====================");

        Product product = new Product("Ramzan", 1, 99);
        System.out.println("==================== [Test] Saving product: " + product + " ====================");

        ResponseEntity<Product> response = restTemplate.postForEntity("/products", product, Product.class);
        System.out.println("==================== [Test] Response received: " + response.getStatusCode() + " ====================");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ramzan", response.getBody().getName());

        System.out.println("==================== [Test] testCreateProduct completed successfully. ====================");
    }

    @Test
    void testGetAllProducts() {
        System.out.println("==================== [Test] testGetAllProducts started. ====================");

        Product product = new Product("Suraj", 2, 199);
        productRepository.save(product);
        System.out.println("==================== [Test] Product saved: " + product + " ====================");

        ResponseEntity<Product[]> response = restTemplate.getForEntity("/products", Product[].class);

        System.out.println("==================== [Test] Response Status: " + response.getStatusCode() + " ====================");
        System.out.println("==================== [Test] Products received: " + Arrays.toString(response.getBody()) + " ====================");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Suraj", response.getBody()[0].getName());

        System.out.println("==================== [Test] testGetAllProducts completed successfully. ====================");
    }

    @Test
    void testFindProductById() {
        System.out.println("==================== [Test] testFindProductById started. ====================");

        Product saved = productRepository.save(new Product("Abdullah", 3, 799));
        System.out.println("==================== [Test] Product saved: " + saved + " ====================");

        ResponseEntity<Product> response = restTemplate.getForEntity("/products/" + saved.getId(), Product.class);
        System.out.println("==================== [Test] Response Status: " + response.getStatusCode() + " ====================");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Abdullah", response.getBody().getName());

        System.out.println("==================== [Test] testFindProductById completed successfully. ====================");
    }

    @Test
    void testDeleteProduct() {
        System.out.println("==================== [Test] testDeleteProduct started. ====================");

        Product saved = productRepository.save(new Product("Deepak", 4, 9999));
        System.out.println("==================== [Test] Product saved: " + saved + " ====================");

        restTemplate.delete("/products/delete/" + saved.getId());
        System.out.println("==================== [Test] Delete request sent for product ID: " + saved.getId() + " ====================");

        Optional<Product> deleted = productRepository.findById(saved.getId());
        System.out.println("==================== [Test] Checking if product is deleted: " + (deleted.isPresent() ? "Present" : "Not Present") + " ====================");

        assertFalse(deleted.isPresent(), "Product should be deleted");

        System.out.println("==================== [Test] testDeleteProduct completed successfully. ====================");
    }

    @Test
    void testFindProductById_NotFound() {
    	System.out.println("================== Starting testFindProductById_NotFound ==================");
        
        int nonExistingId = 99999;
        ResponseEntity<String> response = restTemplate.getForEntity("/products/" + nonExistingId, String.class);

        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("================== Finished testFindProductById_NotFound ==================");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void testCreateProduct_InvalidData_ShouldReturnBadRequest() {
    	System.out.println("================== Starting testCreateProduct_InvalidData ==================");
        
        Product invalidProduct = new Product("", -1, -100);  // Invalid: empty name, negative quantity & price

        ResponseEntity<String> response = restTemplate.postForEntity("/products", invalidProduct, String.class);

        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("================== Finished testCreateProduct_InvalidData ==================");
      
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    void testCreateProduct_ResponseValidation() {
    	System.out.println("================== Starting testCreateProduct_ResponseValidation ==================");

    	Product product = new Product("Keyboard", 10, 799.99);
    	
    	ResponseEntity<Product> response = restTemplate.postForEntity("/products", product, Product.class);
    	
    	// Basic status check
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	
    	// Content-Type header check
    	assertEquals("application/json", response.getHeaders().getContentType().toString());
    	
    	// Check response body fields
    	Product responseBody = response.getBody();
    	assertNotNull(responseBody);
    	assertNotNull(responseBody.getId());
    	assertEquals("keyboard", responseBody.getName());
    	assertEquals(10, responseBody.getPrice());
    	assertEquals(799.99, responseBody.getQuantity());
    	
    	// Example header check (you could add custom headers in your controller if needed)
    	assertTrue(response.getHeaders().containsKey("Content-Type"));
    	
    	System.out.println("Response Headers: " + response.getHeaders());
        System.out.println("Response Body: " + responseBody);
        System.out.println("================== Finished testCreateProduct_ResponseValidation ==================");
    }
}
