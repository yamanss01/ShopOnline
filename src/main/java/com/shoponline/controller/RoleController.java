package com.shoponline.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoponline.model.Role;
import com.shoponline.service.RoleService;

/**
 * this controller handles operation for adding Roles to User
 * @author yaman
 *
 */
@RestController
public class RoleController {

	private Logger logger = LoggerFactory.getLogger(RoleController.class.getName());

	/**
	 * this service class handles operations linked to Roles
	 */
	@Autowired
    private RoleService roleService;

	/**
     * This api when called creates a new role.
     * @param role
     * @return
     */
    @PostMapping({"/createNewRole"})
    public Role createNewRole(@RequestBody Role role) {
    	logger.info("Execution of createNewRole api started");
        return roleService.createNewRole(role);
    }
}
