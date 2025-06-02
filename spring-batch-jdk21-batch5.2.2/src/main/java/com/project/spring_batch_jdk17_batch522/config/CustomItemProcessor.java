package com.project.spring_batch_jdk17_batch522.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import com.project.spring_batch_jdk17_batch522.entity.Customer;


public class CustomItemProcessor implements ItemProcessor<Customer, Customer> {
	
	@Override
	public Customer process(Customer customer) throws Exception {
		return customer;

//	@Override
//	public Customer process(Customer customer) throws Exception {
//		if(customer.getCountry().equals("United States")) {
//			return customer;
//		} else {
//			return null;
//		}
	}
	
}
