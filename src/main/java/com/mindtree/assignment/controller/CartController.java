package com.mindtree.assignment.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.assignment.config.ApiResponse;
import com.mindtree.assignment.entity.Cart;
import com.mindtree.assignment.exception.MyApplicationException;
import com.mindtree.assignment.service.CartService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CartController {
	
	@Autowired
	CartService cartService;

	private static Logger log = LogManager.getLogger();
	
	// add products to the cart
	@PostMapping("/cart")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody Cart cart) throws MyApplicationException {
		log.info("adding products to cart");
		try {
			return new ResponseEntity<ApiResponse>(new ApiResponse("add to cart", false,  cartService.addToCart(cart), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}
	
	// delete a product from cart
	@DeleteMapping("/cart/{email}/{prodId}")
	public ResponseEntity<ApiResponse> deleteOneItemFromCart(@PathVariable String email,@PathVariable int prodId) throws MyApplicationException {
		log.info("delete a product from cart");
		try {
			return new ResponseEntity<ApiResponse>(new ApiResponse("delete one item from cart", false,  cartService.deleteOneItemFromCart(email,prodId), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}
	
	// delete all products from cart
	@DeleteMapping("/cart/{email}")
	public ResponseEntity<ApiResponse> deleteAll(@PathVariable String email) throws MyApplicationException {
		try {
			log.info("delete all products from cart");
			return new ResponseEntity<ApiResponse>(new ApiResponse("delete entire cart", false,  cartService.deleteAll(email), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}
	
	// get all products in a cart
	@GetMapping("/cart/{email}")
	public ResponseEntity<ApiResponse> displayCart(@PathVariable String email) throws MyApplicationException {
		try {
			log.info("get all products from cart");
			return new ResponseEntity<ApiResponse>(new ApiResponse("display cart", false,  cartService.displayCart(email), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}
	
//update cart count
	@PutMapping("/cart/{prodId}/{count}/{email}")
	public ResponseEntity<ApiResponse> updateCount(@PathVariable int count, @PathVariable int prodId ,@PathVariable String email) throws MyApplicationException {
		try {
			log.info("upate cart count");
			return new ResponseEntity<ApiResponse>(new ApiResponse("increment cart count", false,  cartService.updateCount(prodId,count,email), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}	
	
	
	
	
	
	
	
	
	// get all products
	@GetMapping("/product")
	public ResponseEntity<ApiResponse> getAllProducts() throws MyApplicationException {
		try {
			return new ResponseEntity<ApiResponse>(new ApiResponse("get all products", false,  cartService.getAllProducts(), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}
	
	// get products by prodId
	@GetMapping("/productId/{id}")	
	public ResponseEntity<ApiResponse> getProductsById(@PathVariable int id ) throws MyApplicationException {
		try {
			return new ResponseEntity<ApiResponse>(new ApiResponse("get products by id", false,  cartService.getProductsById(id), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}
	
	// get products by prodName
	@GetMapping("/productName/{name}")
	public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name ) throws MyApplicationException {
		try {
			return new ResponseEntity<ApiResponse>(new ApiResponse("get product By product name", false,  cartService.getProductsByName(name), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}
	
	// get products by prodCategory
	@GetMapping("/productCategory/{catName}")
	public ResponseEntity<ApiResponse> getProductsByCat(@PathVariable String catName ) throws MyApplicationException {
		try {
			return new ResponseEntity<ApiResponse>(new ApiResponse("get product By product name", false,  cartService.getProductsByCat(catName), HttpStatus.OK),
					HttpStatus.OK);
		} catch (MyApplicationException e) {
			log.error(e.getMessage());
			throw new MyApplicationException(e.getMessage());
		}

	}
}
