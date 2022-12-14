package com.shoponline.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * this Entity class will hold the Category details
 * @author yaman
 *
 */
@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {
	
	private static final long serialVersionUID = -3465813074586302847L;

	/**
	 * this variable will hold the unique Category Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer id;
	
	/**
	 * this variable will hold the Category name
	 */
	private String name;
	
	/**
	 * this variable will hold the Category image
	 */
	private String imageName;
	
	
	/**
	 * this variable will hold the reference of menu linked to Category
	 */
	@ManyToOne
	@JoinColumn(name = "menuId", nullable = false)
	@JsonBackReference
	private Menu menu;

	/**
	 * this variable will hold the products linked to Category
	 */
	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Category() {	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
