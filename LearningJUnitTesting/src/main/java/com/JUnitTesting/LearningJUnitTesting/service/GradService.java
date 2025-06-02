package com.JUnitTesting.LearningJUnitTesting.service;

//Test with Multiple Scenarios
public class GradService {
	
	public String getGrade(int grade) {
		if(grade >= 90) return "A";
		if(grade >= 75) return "B";
		if(grade >= 60) return "C";
		return "F";
	}

}
