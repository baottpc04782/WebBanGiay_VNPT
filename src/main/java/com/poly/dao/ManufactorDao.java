package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Manufactor;

public interface ManufactorDao extends JpaRepository<Manufactor, Integer> {
	@Query("SELECT m FROM Manufactor m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Manufactor> findByNameContaining(@Param("name") String name, Pageable pageable);
}
