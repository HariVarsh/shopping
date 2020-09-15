package com.mindtree.assignment.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("book")
public class Book extends Product {
	
	
	private String genre;
	private String author;
	private String publication;
	
	
	
	
	
	public Book() {
		super();
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public Book(String genre, String author, String publication) {
		super();
		this.genre = genre;
		this.author = author;
		this.publication = publication;
	}
	
	

}
