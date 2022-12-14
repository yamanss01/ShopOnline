package com.shoponline.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * this Entity class will hold the User Role details
 * @author yaman
 *
 */
@Entity
public class Role implements Serializable{
	
	private static final long serialVersionUID = -3465813074586302847L;
	
	/**
	 * this variable will hold the unique Role Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int roleId;
	
	/**
	 * this variable will hold the Role name
	 */
	private String roleName;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
