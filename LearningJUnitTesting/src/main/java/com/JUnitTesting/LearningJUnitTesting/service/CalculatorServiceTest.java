package com.JUnitTesting.LearningJUnitTesting.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {


	CalculatorService calculatorService = new CalculatorService();
	
	@Test
	void testAdd() {
		int result = calculatorService.add(5, 5);
		assertEquals(10, result, "5 + 5 should equal 10");
	}
	
	@Test
	void testSubtract() {
		int result = calculatorService.subtract(5, 10);
		assertEquals(-5, result, "5  10 should equal -5");
	}
}
