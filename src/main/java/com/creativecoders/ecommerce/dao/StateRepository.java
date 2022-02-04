package com.creativecoders.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.creativecoders.ecommerce.entity.State;

@CrossOrigin("http://localhost:8082")
@RepositoryRestResource
public interface StateRepository extends JpaRepository<State, Integer> {
	
	// Retrieve states for given country code
	List<State> findByCountryCode(@Param("code") String code);
}