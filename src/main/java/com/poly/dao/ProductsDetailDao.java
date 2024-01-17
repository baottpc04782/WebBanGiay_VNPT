package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.ProductsDetail;

public interface ProductsDetailDao extends JpaRepository<ProductsDetail, Integer> {
	
	@Query("SELECT p FROM ProductsDetail p WHERE p.products.id=?1")
    List<ProductsDetail> findProductsDetailByProductId(Integer cid);
	
	@Query("SELECT p FROM ProductsDetail p WHERE p.products.productId=?1")
	Page<ProductsDetail> findByProductIdAndPage(Integer productId, Pageable pageable);

}
