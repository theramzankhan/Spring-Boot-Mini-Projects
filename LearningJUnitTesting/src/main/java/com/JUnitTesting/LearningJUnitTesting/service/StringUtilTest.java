package com.JUnitTesting.LearningJUnitTesting.service;




import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

//Example 3: Testing Edge Cases
public class StringUtilTest {
	
	StringUtil util = new StringUtil();
	
	@Test
	void testNullString() {
		assertTrue(util.isNullOrEmpty(null));
	}
	
	@Test
	void testEmptyString() {
		assertTrue(util.isNullOrEmpty(""));
	}

	@Test
	void testNonEmptyString() {
		assertFalse(util.isNullOrEmpty("Hello"));
	}
}
