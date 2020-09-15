package com.mindtree.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtree.assignment.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	
}
