package com.shoponline.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoponline.model.Menu;


public interface MenuRepository extends JpaRepository<Menu, Integer>{
	
}
