package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Categories;
import com.poly.entity.Color;

public interface CategoriesDao extends JpaRepository<Categories, Integer> {
	@Query("SELECT c FROM Categories c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Categories> findByNameContaining(@Param("name") String name, Pageable pageable);
}
