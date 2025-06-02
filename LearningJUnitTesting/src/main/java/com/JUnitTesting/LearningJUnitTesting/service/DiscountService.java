package com.JUnitTesting.LearningJUnitTesting.service;

public class DiscountService {
	
	public double applyDiscount(double price) {
		if(price > 100) {
			return price * 0.9; //10% discount
		}
		return price;
	}

}
