package com.poly.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.poly.entity.Products;

public interface ProductService {

	/**
	 * Lấy danh sách tất cả sản phẩm.
	 */
	List<Products> findAll();

	/**
	 * Tìm sản phẩm dựa trên ID.
	 */
	Products findById(Integer id);

	/**
	 * Tìm danh sách sản phẩm dựa trên ID danh mục.
	 */
	List<Products> findByCategoryId(Integer cid);

	/**
	 * Lấy danh sách tất cả sản phẩm theo trang.
	 */
	Page<Products> findAll(Pageable pageable);

	/**
	 * Lưu thông tin sản phẩm.
	 */
	Products save(Products product);

	/**
	 * Xóa sản phẩm dựa trên ID.
	 */
	void deleteById(Integer id);

	/**
	 * Lấy thông tin sản phẩm theo ID.
	 */
	Products getOne(Integer id);

	/**
	 * Tìm sản phẩm theo tên giống với chuỗi đầu vào và trang.
	 */
	Page<Products> findProductNameLike(String string, Pageable pageable);

	/**
	 * Sắp xếp sản phẩm theo tên.
	 */
	Page<Products> SortByProductName(Pageable pageable);

	/**
	 * Lấy danh sách tất cả sản phẩm theo trang.
	 */
	Page<Products> findAllByPage(Pageable pageable);

	/**
	 * Kiểm tra sự tồn tại của sản phẩm dựa trên ID.
	 */
	boolean existsById(Integer id);

	/**
	 * Lấy danh sách sản phẩm dựa trên ID danh mục.
	 */
	List<Products> getProductByCategoryId(Integer id);

	/**
	 * Tìm sản phẩm dựa trên ID danh mục.
	 */
	List<Products> findByCategory(Optional<Integer> cid);

	/**
	 * Lấy danh sách tất cả sản phẩm theo thứ tự giảm dần.
	 */
	List<Products> findAllDesc();

	/**
	 * Tìm sản phẩm dựa trên từ khóa.
	 */
	List<Products> findProductByKeywords(String keyword);


    
}
