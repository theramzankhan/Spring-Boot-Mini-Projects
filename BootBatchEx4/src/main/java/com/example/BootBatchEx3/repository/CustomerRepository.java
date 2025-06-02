package com.example.BootBatchEx3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BootBatchEx3.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}