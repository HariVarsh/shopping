package com.mindtree.assignment.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "Category")
public class Product implements Comparable<Product> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;

	private String productName;

	private float price;

	@Column(insertable = false, updatable = false)
	private String Category;
	


	public String getCategory() {
		return Category;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}



	public Product(int productId, String productName, float price, String category) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		Category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Category == null) ? 0 : Category.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + productId;
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
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
		Product other = (Product) obj;
		if (Category == null) {
			if (other.Category != null)
				return false;
		} else if (!Category.equals(other.Category))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (productId != other.productId)
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		return true;
	}

	public Product() {
		super();
	}

	@Override
	public int compareTo(Product o) {
		if(o.equals(this)){
			return 1;
		}
		return 0;
	}



}
