package com.creativecoders.ecommerce.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import com.creativecoders.ecommerce.entity.Product;

// Product => entity, Long => primary key
public interface ProductRepository extends JpaRepository<Product, Long>{

	/*
	 * Behind the scenes: It will execute
	 * 		SELECT *  FROM product WHERE category_id=?
	 * Spring Data REST automatically exposes endpoint
	 * 		http://localhost:8080/api/products/search/findByCategoryId?id=2
	 */
	Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable);	

	// select * from Product p where p.name LIKE CONCAT('%', :name, '%')
	Page<Product> findByNameContaining(@RequestParam("name") String name, Pageable pageable);
}
