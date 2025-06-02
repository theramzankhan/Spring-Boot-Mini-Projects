package com.example.BootBatchEx3.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import com.example.BootBatchEx3.entity.Customer;

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
