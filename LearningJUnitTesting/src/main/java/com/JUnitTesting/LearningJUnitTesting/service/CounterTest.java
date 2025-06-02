package com.JUnitTesting.LearningJUnitTesting.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CounterTest {
	
	Counter counter;
	
	@BeforeEach
	void setUp() {
		counter = new Counter();
	}
	
	@AfterEach
	void tearDown() {
		System.out.println("Test finished!");
	}
	
	@Test
	void testIncrementOnce() {
		counter.incrementValue();
		assertEquals(1, counter.getValue());
	}
	@Test
	void testIncrementTwice() {
		counter.incrementValue();
		counter.incrementValue();
		assertEquals(2, counter.getValue());
	}

}
