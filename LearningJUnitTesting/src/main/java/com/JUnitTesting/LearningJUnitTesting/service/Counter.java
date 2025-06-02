package com.JUnitTesting.LearningJUnitTesting.service;

//Example 2: Using @BeforeEach and @AfterEach
public class Counter {
	int value = 0;
	
	public void incrementValue() {
		value++;
	}
	
	public int getValue() {
		return value;
	}

}
