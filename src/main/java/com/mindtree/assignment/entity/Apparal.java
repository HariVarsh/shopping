package com.mindtree.assignment.entity;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("apparal")
public class Apparal extends Product {
	
	
	private String type;
	private String brand;
	private String design;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
	public Apparal(String type, String brand, String design) {
		super();
		this.type = type;
		this.brand = brand;
		this.design = design;
	}
	public Apparal() {
		super();
	}
	
	
	

}
