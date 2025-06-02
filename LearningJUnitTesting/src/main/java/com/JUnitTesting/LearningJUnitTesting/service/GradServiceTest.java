package com.JUnitTesting.LearningJUnitTesting.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

//Test with Multiple Scenarios
public class GradServiceTest {
	
	GradService gradService = new GradService();
	
	@Test
	void testGradeA() {
		assertEquals("A", gradService.getGrade(95));
	}
	
	@Test
	void testGradeB() {
		assertEquals("B", gradService.getGrade(75));
	}
	
	@Test
	void testGradeC() {
		assertEquals("C", gradService.getGrade(74));
	}

	@Test
	void testGradeF() {
		assertEquals("F", gradService.getGrade(10));
	}
	
	@Test
	void testGradeF_Failure() {
		assertEquals("F", gradService.getGrade(-10));
	}
}
