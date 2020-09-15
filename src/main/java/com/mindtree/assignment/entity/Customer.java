package com.mindtree.assignment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c") 
public class Customer implements Comparable<Customer> {

	private String customer_name;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int customerId;
	private String customer_email;
	
	private String password;




	public String getPassword() {
		return password;
	}

	public Customer() {
		super();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public Customer( String customer_name, String customer_email, String password) {
		super();
		this.customer_name = customer_name;
		this.customer_email = customer_email;
		this.password = password;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer_email == null) ? 0 : customer_email.hashCode());
		result = prime * result + ((customer_name == null) ? 0 : customer_name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customer_email == null) {
			if (other.customer_email != null)
				return false;
		} else if (!customer_email.equals(other.customer_email))
			return false;
		if (customer_name == null) {
			if (other.customer_name != null)
				return false;
		} else if (!customer_name.equals(other.customer_name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public int compareTo(Customer o) {
		if(o.equals(this)){
			return 1;
		}
		return 0;	}
	
	

}
