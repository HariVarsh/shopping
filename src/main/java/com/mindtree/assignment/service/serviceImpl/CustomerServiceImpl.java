package com.mindtree.assignment.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mindtree.assignment.entity.Customer;
import com.mindtree.assignment.exception.MyApplicationException;
import com.mindtree.assignment.exception.RecordAlreadyExistsException;
import com.mindtree.assignment.exception.RecordNotFoundException;
import com.mindtree.assignment.repository.CustomerRepo;
import com.mindtree.assignment.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepo customerRepo;
	public final static Logger log = LogManager.getLogger();

	@Override
	public void register(Customer customer) throws MyApplicationException {
		try {
			if (customerRepo.findAll().stream()
					.anyMatch(i -> i.getCustomer_email().equals(customer.getCustomer_email()))) {
				log.error("This Email is already registered");
				throw new RecordAlreadyExistsException("This Email is already registered");
			} else {
				log.info("New user added to DB");
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				customer.setPassword(passwordEncoder.encode(customer.getPassword()));
				customerRepo.save(customer);
			}

		} catch (RecordAlreadyExistsException e) {
			throw new MyApplicationException("This Email is already registered");
		}

	}

	@Override
	public Customer login(String email, String password) throws MyApplicationException {

		try {
			if (customerRepo.findAll().stream().anyMatch(i -> i.getCustomer_email().equals(email))) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				List<Customer> emailMatchList = customerRepo.findAll().stream()
						.filter(i -> i.getCustomer_email().equals(email)).collect(Collectors.toList());
				boolean isPasswordMatch = passwordEncoder.matches(password, emailMatchList.get(0).getPassword());

				if (isPasswordMatch) {
					log.info("password matched");
					return emailMatchList.get(0);
				} else {
					log.error("This Password is not valid");
					throw new RecordNotFoundException("This Password is not valid");
				}

			} else {
				log.error("This Email is not registered");
				throw new RecordNotFoundException("This Email is not registered");
			}

		} catch (RecordNotFoundException e) {
			throw new MyApplicationException("email or pwd is invalid");
		}

	}

	@Override
	public List<Customer> search(String name) throws MyApplicationException {
		// TODO Auto-generated method stub
		List<Customer> newCusList = new ArrayList<Customer>();
		if (customerRepo.findAll().stream().map(i -> i.getCustomer_email().contains(name)) != null) {
			List<Customer> cusList = customerRepo.findAll();
			for (Customer c : cusList) {
				if (c.getCustomer_name().contains(name)) {
					newCusList.add(c);
				}
			}
		}
		try {
		if (newCusList.size() > 0)
			return newCusList;
		else
			throw new RecordNotFoundException("No Employees");
		}
		catch (RecordNotFoundException e) {
			throw new MyApplicationException("No Employees");
		}
		
	}

}
