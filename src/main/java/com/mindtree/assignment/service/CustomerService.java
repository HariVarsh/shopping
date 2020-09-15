package com.mindtree.assignment.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mindtree.assignment.entity.Customer;
import com.mindtree.assignment.exception.MyApplicationException;

@Transactional
public interface CustomerService {

	void register(Customer customer) throws MyApplicationException;

	Customer login(String email, String password) throws MyApplicationException;

	List<Customer> search(String name)throws MyApplicationException;

}
