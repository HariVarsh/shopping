package com.mindtree.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtree.assignment.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
