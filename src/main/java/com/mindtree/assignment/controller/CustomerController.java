package com.mindtree.assignment.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.assignment.config.ApiResponse;
import com.mindtree.assignment.entity.Cart;
import com.mindtree.assignment.entity.Customer;
import com.mindtree.assignment.exception.MyApplicationException;
import com.mindtree.assignment.service.CustomerService;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	
	private static Logger log = LogManager.getLogger();

	/*
	 * adds a new customer to the database with password encrypted using bcrypt
	 * 
	 * @param customer object
	 * 
	 * @return response entity
	 */

	// sign up
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Customer customer) {
		log.info("Inside register user");
		HashMap<String, Object> result = new HashMap<>();
		try {
			customerService.register(customer);
			result.put("status", HttpStatus.OK);
			result.put("body", "success");
			return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(result);
		} catch (MyApplicationException e) {
			result.put("exception", e.getMessage());
			result.put("body", "exception");
			return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(result);
		}

	}

	/*
	 * verifies if the pwd matches the encypted pwd in db
	 * 
	 * @param emailId and password
	 * 
	 * @return response entity
	 */

	// login

	@GetMapping("/login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
		log.info("Inside login user");
		HashMap<String, Object> result = new HashMap<>();
		try {
			customerService.login(email, password);
			result.put("status", HttpStatus.OK);
			result.put("body", "success");
			return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(result);
		} catch (MyApplicationException e) {
			result.put("exception", e.getMessage());
			result.put("body", "exception");
			return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK)).body(result);
		}

	}
	/*
	 * retrives the employee details
	 * 
	 * @param emailId and password
	 * 
	 * @return response entity
	 */

	// search

	@GetMapping("/search/{name}")
	public ResponseEntity<ApiResponse> search(@PathVariable String name) throws MyApplicationException {
		log.info("Inside login user");
		try {
			List<Customer> cuslist = customerService.search(name);
			return new ResponseEntity<ApiResponse>(new ApiResponse("search User", false, cuslist, HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			throw new MyApplicationException(e.getMessage());
		}

	}


	
	

	

	

	








}