package com.shoponline.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * this Entity class will hold the Product details
 * @author yaman
 *
 */
@Entity
@Table(name="PRODUCT")
public class Product implements Serializable{
	
	private static final long serialVersionUID = -3465813074586302847L;

	/**
	 * this variable will hold the unique Product Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
	 * this variable will hold the Product name
	 */
	@Column(nullable = false)
	private String name;
	
	/**
	 * this variable will hold the Product description
	 */
	@Column(length = 600)
	private String description;
	
	/**
	 * this variable will hold the Product available stock
	 */
	private int stock;
	
	/**
	 * this variable will hold the Product price
	 */
	@Column(nullable = false)
	private double price;
	
	/**
	 * this variable will hold the Product size
	 */
	private String size;
	
	/**
	 * this variable will hold the Product color
	 */
	private String color;
	
	/**
	 * this variable will hold the Product rating
	 */
	private double rating;
	
	/**
	 * this variable will hold the Product reviews
	 */
	private String reviews;
	
	/**
	 * this variable will hold the Product image name
	 */
	private String imageName;
	
	/**
	 * this variable will hold the reference of Product linked with Category
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
	@JsonBackReference
	private Category category;
	
	public Product() { }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getReviews() {
		return reviews;
	}
	public void setReviews(String reviews) {
		this.reviews = reviews;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}


}
