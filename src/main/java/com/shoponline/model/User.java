package com.shoponline.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * this entity class will hold the User details
 * @author yaman
 *
 */
@Entity
public class User implements Serializable{
	
	private static final long serialVersionUID = -3465813074586302847L;
	
	/**
	 * this variable will hold the unique User Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	/**
	 * this variable will hold the User first name
	 */
	private String userFirstName;
	
	/**
	 * this variable will hold the User last name
	 */
	private String userLastName;
	
	/**
	 * this variable will hold the unique User email
	 */
	@Column(unique = true)
	private String userEmail;
	
	/**
	 * this variable will hold the User encrypted password
	 */
	private String userPassword;
	
	/**
	 * this variable will hold the User address
	 */
	@Column(length = 700)
	private String userAddress;
	
	/**
	 * this variable will hold the User Roles
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "USER_ROLE",
	           joinColumns = {
	        		   @JoinColumn(name = "USER_ID")
	           },
	           inverseJoinColumns = {
	        		   @JoinColumn(name = "ROLE_ID")
	           }
	)
	private Set<Role> role;

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
}
