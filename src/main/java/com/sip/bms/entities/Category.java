package com.sip.bms.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table (name = "mycategory")
public class Category {

	@Override
	public String toString() {
		return "Category [id_category=" + id_category + ", name=" + name + ", books=" + books + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_category;
	
	@NotBlank(message = "Category name is mandatory")
	private String name;

	public int getId_category() {
		return id_category;
	}

	public void setId_category(int id_category) {
		this.id_category = id_category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category(String name) {
		this.name = name;
	}
	public Category() {}
	
	/********* One To Many ********/
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="category")
	private List<Book> books;

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
}
