package com.JUnitTesting.LearningJUnitTesting.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiscountServiceTest {
	
	static int count = 0;
	DiscountService discountService;
	
	@BeforeEach
	void setUp() {
		count++;
		discountService = new DiscountService(); // Initialize before each test
		System.out.println("Setup for test: " + count);
	}
	
	@AfterEach
	void tearDown() {
		System.out.println("Test Finished! " + count);
	}
	
	@Test
	void shouldApplyDiscountForPriceAbove100() {
		double result = discountService.applyDiscount(200);
		assertEquals(180, result, 0.001);
	}
	
	@Test
	void shouldNotApplyDiscountForPriceAbove100( ) {
		double result = discountService.applyDiscount(80);
		assertEquals(80, result, 0.001);
	}

}
