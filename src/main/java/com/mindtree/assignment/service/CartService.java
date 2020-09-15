package com.mindtree.assignment.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mindtree.assignment.entity.Cart;
import com.mindtree.assignment.entity.Product;
import com.mindtree.assignment.exception.MyApplicationException;

@Transactional
public interface CartService {

	 

	Cart addToCart(Cart cart) throws MyApplicationException;

	Cart deleteOneItemFromCart(String email, int prodId)  throws MyApplicationException;

	Object deleteAll(String email) throws MyApplicationException;

	Object displayCart(String email) throws MyApplicationException;
	
	Object updateCount(int prodId, int count, String email) throws MyApplicationException;
	
	

	List<Product> getAllProducts() throws MyApplicationException;

	Product getProductsById(int id) throws MyApplicationException;

	List<Product> getProductsByName(String name) throws MyApplicationException;

	Object getProductsByCat(String catName) throws MyApplicationException;




	

}
