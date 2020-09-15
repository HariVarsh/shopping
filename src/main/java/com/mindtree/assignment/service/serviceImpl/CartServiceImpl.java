package com.mindtree.assignment.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.assignment.entity.Cart;
import com.mindtree.assignment.entity.Customer;
import com.mindtree.assignment.entity.Product;
import com.mindtree.assignment.exception.BadInputException;
import com.mindtree.assignment.exception.MyApplicationException;
import com.mindtree.assignment.exception.RecordNotFoundException;
import com.mindtree.assignment.repository.CartRepository;
import com.mindtree.assignment.repository.CustomerRepo;
import com.mindtree.assignment.repository.ProductRepository;
import com.mindtree.assignment.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CustomerRepo customerRepository;

	@Autowired
	ProductRepository productRepository;

	Logger log = LogManager.getLogger(CartServiceImpl.class);

	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public Cart addToCart(Cart cart) throws MyApplicationException {
		log.info("inside add to cart method");

		int count = 0, cartId = 0;

		try {
			// check if customer exists in db
			if (customerRepository.findAll().stream().filter(i -> i.getCustomer_email().equals(cart.getCustomerEmail()))
					.collect(Collectors.toList()).size() == 0) {
				log.error("This customer doesnot exists in db");
				throw new RecordNotFoundException("This customer doesnot exists");
			}
			// check if prodId exists in product table
			else if (!productRepository.findAll().stream()
					.anyMatch(i -> i.getProductId() == cart.getProduct().getProductId())) {
				log.error("This product doesnot exists in db");
				throw new RecordNotFoundException("This product doesnot exists");
			}

			else {
				// checking if the product id is already present in the cart
				if (cartRepository.findAll().stream().anyMatch(i -> i.getCustomerEmail().equals(cart.getCustomerEmail())
						&& i.getProduct().getProductId() == cart.getProduct().getProductId())) {
					List<Cart> cartList = cartRepository.findAll().stream()
							.filter(i -> i.getCustomerEmail().equals(cart.getCustomerEmail())
									&& i.getProduct().getProductId() == cart.getProduct().getProductId())
							.collect(Collectors.toList());
					log.info("this product is already in the cart, thus just incrementing the cart count");
					count = cartList.get(0).getCount();
					cartId = cartList.get(0).getCartId();
					count++;
					cartRepository.update(cartId, count);
					return cartRepository.findAll().stream()
							.filter(i -> i.getCustomerEmail().equals(cart.getCustomerEmail())
									&& i.getProduct().getProductId() == cart.getProduct().getProductId())
							.collect(Collectors.toList()).get(0);

				} else {
					log.info("This is a new product to the cart, thus adding a new row in the cart table");
					cart.setCount(1);
					cartRepository.save(cart);
					return cart;

				}

			}

		} catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		}

	}

	@Override
	public Cart deleteOneItemFromCart(String email, int prodId) throws MyApplicationException {
		// to delete an items from cart, first we check the count of the item in the
		// cart, if it is more than one we decrement the count, else we delete the
		// entire row.

		try {
			// check if customer exists in db
			if (customerRepository.findAll().stream().filter(i -> i.getCustomer_email().equals(email))
					.collect(Collectors.toList()).size() == 0) {
				log.error("This customer doesnot exists in db");
				throw new RecordNotFoundException("This customer doesnot exists");
			}

			// check if prodId exists in product table and cart table
			else if (!productRepository.findAll().stream().anyMatch(i -> i.getProductId() == prodId)
					|| !cartRepository.findAll().stream().anyMatch(i -> i.getProduct().getProductId() == prodId)) {
				log.error("This product doesnot exists in db");
				throw new RecordNotFoundException("This product doesnot exists");
			}

			// check if the prod Id exists in cart
			else if (!cartRepository.findAll().stream().anyMatch(i -> i.getCustomerEmail().equals(email))) {
				log.error("NO items in cart");
				throw new RecordNotFoundException("No items in cart");
			} else {
				int count = 0, cartId = 0;
				// getting the row of the prodId which is equal to the newly coming prodId to be
				// deleted
				List<Cart> cartList = cartRepository.findAll().stream()
						.filter(i -> i.getCustomerEmail().equals(email) && i.getProduct().getProductId() == prodId)
						.collect(Collectors.toList());

				count = cartList.get(0).getCount();
				cartId = cartList.get(0).getCartId();
				log.info("getting the count of the product in cart");
				if (count != 1) {
					log.info("The count is more than one, which means the product is present "
							+ "more than once in the cart," + " thus we decrement the count by one "
							+ "and update the row");
					count--;
					int c = cartId;
					cartRepository.update(cartId, count);

					return cartRepository.findAll().stream().filter(i -> i.getCartId() == c)
							.collect(Collectors.toList()).get(0);
				}

				else {
					log.info("The count is one, which means the product is present only once in the cart,"
							+ " thus we delete the entire row");
					cartRepository.deleteThatRow(cartId, count);
					return null;
				}

			}
		}

		catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		}

	}

	@Override
	public Object deleteAll(String email) throws MyApplicationException {
		try {
			// check if customer exists in db
			List<Customer> clist = customerRepository.findAll().stream()
					.filter(i -> i.getCustomer_email().equals(email)).collect(Collectors.toList());

			if (clist.size() == 0) {
				log.error("This customer doesnot exists in db");
				throw new RecordNotFoundException("This customer doesnot exists");
			} else if (!cartRepository.findAll().stream().anyMatch(i -> i.getCustomerEmail().equals(email))) {
				log.error("no items in customer's cart");
				throw new RecordNotFoundException("No items in cart");

			} else {
				log.info("deleted the entire cart of custommer");
				cartRepository.deleteFullCart(email);
				return null;
			}

		} catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		}
	}

	@Override
	public List<Cart> displayCart(String email) throws MyApplicationException {

		List<Cart> cartList = cartRepository.findAll().stream().filter(i -> i.getCustomerEmail().equals(email))
				.collect(Collectors.toList());
		try {

			if (customerRepository.findAll().stream().filter(i -> i.getCustomer_email().equals(email))
					.collect(Collectors.toList()).size() == 0) {
				log.error("This customer doesnot exists in db");
				throw new RecordNotFoundException("This customer doesnot exists");
			} else if (cartList.size() == 0) {
				log.error("no items in customer's cart");
				throw new RecordNotFoundException("No items in the cart");
			} else {
				log.info("displaying customer's cart");
				return cartList;
			}
		} catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		}

	}

	@Override
	public Object updateCount(int prodId, int count, String email) throws MyApplicationException {
		List<Cart> cList = cartRepository.findAll().stream().filter(i -> i.getCustomerEmail().equals(email))
				.collect(Collectors.toList());

		try {

			if (customerRepository.findAll().stream().filter(i -> i.getCustomer_email().equals(email))
					.collect(Collectors.toList()).size() == 0) {
				log.error("This customer doesn't exists");
				throw new RecordNotFoundException("This customer doesnot exists");
			} else if (cList.size() == 0) {
				log.error("no items in customer's cart");
				throw new RecordNotFoundException("No items in the cart");
			} else {
				List<Cart> cartList = cartRepository.findAll().stream()
						.filter(i -> i.getProduct().getProductId() == prodId).collect(Collectors.toList());

				int cartId = cartList.get(0).getCartId();
				if (count > 0)
					cartRepository.update(cartId, count);

				else if (count == 0)
					cartRepository.deleteThatRow(cartId, count + 1);
				else
					throw new BadInputException("Negative Numbers Not allowed");
				return cartRepository.findAll().stream().filter(i -> i.getProduct().getProductId() == prodId)
						.collect(Collectors.toList());
			}
		} catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		} catch (BadInputException e) {
			throw new MyApplicationException(e.getMessage());
		}

	}

	@Override
	public List<Product> getAllProducts() throws MyApplicationException {
		try {
			if (productRepository.findAll().size() != 0)
				return productRepository.findAll();
			else
				throw new RecordNotFoundException("No products available");
		} catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		}
	}

	@Override
	public Product getProductsById(int id) throws MyApplicationException {
		try {
			if (productRepository.findAll().stream().anyMatch(i -> i.getProductId() == id)) {
				List<Product> pList = productRepository.findAll().stream().filter(i -> i.getProductId() == id)
						.collect(Collectors.toList());
				return pList.get(0);
			} else
				throw new RecordNotFoundException("This product doesnot exists");
		} catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		}
	}

	@Override
	public List<Product> getProductsByName(String name) throws MyApplicationException {
		try {
			if (productRepository.findAll().stream().anyMatch(i -> i.getProductName().equalsIgnoreCase(name))) {
				return productRepository.findAll().stream().filter(i -> i.getProductName().equals(name))
						.collect(Collectors.toList());
			} else
				throw new RecordNotFoundException("This product doesnot exists");
		} catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		}
	}

	@Override
	public Object getProductsByCat(String catName) throws MyApplicationException {
		try {
			if (productRepository.findAll().stream().anyMatch(i -> i.getCategory().equals(catName))) {
				return productRepository.findAll().stream().filter(i -> i.getCategory().equals(catName))
						.collect(Collectors.toList());
			} else
				throw new RecordNotFoundException("This Category doesnot exists");
		} catch (RecordNotFoundException e) {
			throw new MyApplicationException(e.getMessage());
		}
	}

}
