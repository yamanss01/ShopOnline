package com.shoponline.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * this Entity class will hold the Menu details
 * @author yaman
 */
@Entity
@Table(name="MENU")
public class Menu implements Serializable{
	
	private static final long serialVersionUID = -3465813074586302847L;
	
	/**
	 * this variable will hold the unique menu Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int menuId;
	
	/**
	 * this variable will hold the menu name
	 */
	private String menuName;
	
	/**
	 * this variable will hold the menu status i.e active or not by admin
	 */
	private boolean status;
	
	/**
	 * this variable will hold the details of categories linked to that Menu
	 */
	@OneToMany(mappedBy = "menu")
	private List<Category> categories;
	
	public Menu() {	}
	
	public boolean isStatus() {
		return status;
	}
	
	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
