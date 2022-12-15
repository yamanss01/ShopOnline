package com.shoponline.service;

import java.util.ArrayList;
import java.util.HashSet;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shoponline.dao.UserRepository;
import com.shoponline.model.Role;
import com.shoponline.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void initRoleAndUser() {
		logger.info("initiallizing role and user");
		Role adminRole = roleService.getRoleById(1);
		
        User adminUser = new User();
        
        
        try {
            adminUser.setUserEmail("admin123@gmail.com");
            adminUser.setUserPassword(getEncodedPassword("admin@pass"));
            adminUser.setUserFirstName("admin");
            adminUser.setUserLastName("admin");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminUser.setRole(adminRoles);
            saveUser(adminUser); 
            
        } catch (Exception ex) {
			logger.error("Exception occured while initiallizing user and roles from DB, Error : " + ex.getMessage());
		}

    }
    
	public User registerNewUser(User user) {
		logger.info("registering new user, userName : "+user.getUserEmail());
		Role role = roleService.getRoleById(2);
		Set<Role> userRoles = new HashSet<>();
		User users = new User();
		
		try {
			userRoles.add(role);
			user.setRole(userRoles);
			user.setUserPassword(getEncodedPassword(user.getUserPassword()));
			users = saveUser(user);
			
		} catch (Exception ex) {
			logger.error("Exception occured while registering new user, Error : " + ex.getMessage());
		}
				
		return users;
	}
	
	public String getEncodedPassword(String password) {
		logger.info("getting password encoded");
		String pass = null;
		try {
			pass = passwordEncoder.encode(password);
			
		} catch (Exception ex) {
			logger.error("Exception occured while encoding password, Error : " + ex.getMessage());
		}
        return pass;
    }
	
	public User saveUser(User user) {
		logger.info("saving new user in DB, userName : "+user.getUserFirstName()+" "+user.getUserLastName());
		User users = new User();
		try {
			users = userRepository.save(user);
			
		} catch (Exception ex) {
			logger.error("Exception occurred while saving new user in DB, Error : " + ex.getMessage());
		}
		return users;
	}

	public List<User> getAllUser() {
		logger.info("getting users from DB");
		List<User> user = new ArrayList<User>();
		try {
			user = userRepository.findAll();
		} catch (Exception ex) {
			logger.error("Exception occurred while getting users from DB, Error : " + ex.getMessage());
		}
		return user;
	}
	
	public User findUserByUserName(String userName) {
		logger.info("getting user by username from DB, userName : "+userName);
		User user = null;
		try {
			user = userRepository.findByUserEmail(userName).get();
			
		} catch (Exception ex) {
			logger.error("Exception occurred while getting user by username from DB, Error : " + ex.getMessage());
		}
		
		return user;
	}

}
