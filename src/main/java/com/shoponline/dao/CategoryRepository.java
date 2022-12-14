package com.shoponline.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoponline.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	
}
