package com.shoponline.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoponline.dao.RoleRepository;
import com.shoponline.model.Role;

@Service
public class RoleServiceImpl implements RoleService {

	private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class.getName());
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role createNewRole(Role role) {
		logger.info("creating new role, Role : "+role.getRoleName());
		Role roles = null;
		try {
			roles = roleRepository.save(role);
			
		} catch (Exception ex) {
			logger.error("Exception occured while creating new role in DB, Error : " + ex.getMessage());
		}
		return roles;
	}
	
	public Role saveRole(Role role) {
		logger.info("saving role");
		Role roles = null;
		try {
			roles = roleRepository.save(role);
		}  catch (Exception ex) {
			logger.error("Exception occured while saving role in DB, Error : " + ex.getMessage());
		}
		return roles;
	}

	public Role getRoleById(int id) {
		logger.info("getting role by Id : "+id);
		Role role = null;
		try {
			role = roleRepository.findById(id).get();
			
		} catch (Exception ex) {
			logger.error("Exception occured while getting role by id from DB, Error : " + ex.getMessage());
		}
		return role;
	}

	public List<Role> getAllRole() {
		logger.info("getting roles");
		List<Role> role = new ArrayList<Role>();
		try {
			role = roleRepository.findAll();
			
		} catch (Exception ex) {
			logger.error("Exception occured while getting roles from DB, Error : " + ex.getMessage());
		}
		return role;
	}
}
