package com.poly.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.entity.ProductsDetail;

public interface ProductDetailService {

	/**
	 * Lưu thông tin chi tiết sản phẩm.
	 */
	ProductsDetail save(ProductsDetail detail);

	/**
	 * Lấy danh sách tất cả các chi tiết sản phẩm.
	 */
	List<ProductsDetail> findAll();

	/**
	 * Lấy danh sách các chi tiết sản phẩm dựa trên ID sản phẩm và trang.
	 */
	Page<ProductsDetail> findByProductIdAndPage(Integer productId, Pageable pageable);

	/**
	 * Xóa chi tiết sản phẩm dựa trên ID.
	 */
	void deleteById(Integer id);

	/**
	 * Tìm chi tiết sản phẩm dựa trên ID.
	 */
	ProductsDetail findById(Integer id);

 
}
