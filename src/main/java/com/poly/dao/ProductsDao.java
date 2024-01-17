package com.poly.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Products;

public interface ProductsDao extends JpaRepository<Products, Integer> {

	/*
	 * @Query("SELECT p FROM Products p WHERE p.categories.id=?1") List<Products>
	 * findByCategoryId(Integer cid);
	 */
    
    @Query("SELECT p FROM Products p WHERE p.categories.categoryId=?1")
    List<Products> findByCategoryId(Integer cid);
	
    @Query("SELECT p FROM Products p WHERE p.name LIKE :keywords")
    Page<Products> findByProductNameLike(@Param("keywords") String keywords, Pageable pageable);

    @Query("select p from Products p order by p.name asc")
    Page<Products> SortByProductName(Pageable pageable);
    
    @Query("select p from Products p where p.categories.categoryId = :id")
	List<Products> getProductByCategoryId(@Param("id") Integer id);

    @Query("select p from Products p where p.categories.categoryId = ?1")
	List<Products> finByCategory(Optional<Integer> cid);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductsDetail pd WHERE pd.products.productId IN (SELECT p.id FROM Products p WHERE p.productId = :productId)")
	void deleteByRelatedId(@Param("productId") Integer id);

    @Query("select a from Products a order by a.productId desc")
	List<Products> findAllDesc();

    @Query("SELECT p FROM Products p WHERE p.name LIKE %:keywords%")
	List<Products> findProductByKeywords(@Param("keywords")String keyword);

}
