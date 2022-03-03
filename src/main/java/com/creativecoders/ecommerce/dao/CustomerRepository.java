package com.creativecoders.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creativecoders.ecommerce.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	// 	behind the scene: 
	//	select * from customer c where c.email = theEmail;
	// 	return null if not found
	Customer findByEmail(String theEmail);
}
