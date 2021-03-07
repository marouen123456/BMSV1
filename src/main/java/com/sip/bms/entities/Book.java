package com.sip.bms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import com.sip.bms.entities.Category;

@Entity
public class Book {

	
	@Override
	public String toString() {
		return "Book [id_book=" + id_book + ", title=" + title + ", author=" + author + ", description=" + description
				+ ", price=" + price + ", isbn=" + isbn + ", status=" + status + ", cover=" + cover + ", category=" + category + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name= "id_book")
	private long id_book;
	
	@NotBlank(message = "Title is mandatory")
	@Column(name = "title")
	private String title;
	
	@NotBlank(message = "Author is mandatory")
	@Column(name = "author")
	private String author;
	
	private String description;
	
	private float price;
	
	private long isbn;
	
	private String status;
	
	private String cover;
	
	
	public Book(String title, String author, String description, float price, long isbn, String status, String cover) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.price = price;
		this.isbn = isbn;
		this.status=status;
		this.cover = cover;
	}

public Book() {}

	public long getId_book() {
		return id_book;
	}

	public void setId_book(long id_book) {
		this.id_book = id_book;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
	
/**** Many To One ****/
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_category", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

	public Category getCategory() {
	return category;
	}

	public void setCategory(Category category) {
	this.category = category;
	}
}
