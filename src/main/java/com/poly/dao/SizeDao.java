package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Size;

public interface SizeDao extends JpaRepository<Size, Integer> {
	 @Query("SELECT s FROM Size s WHERE s.name like :keyword")
	 Page<Size> findByName(@Param("keyword") Integer name, Pageable pageable);
}
