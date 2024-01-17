package com.poly.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.entity.Color;
import com.poly.entity.Size;



public interface SizeServies {
	/**
	 * Lấy danh sách tất cả các kích thước.
	 */
	public List<Size> findAll();

	/**
	 * Lấy danh sách tất cả các kích thước theo trang.
	 */
	public Page<Size> findAll(Pageable pageable);

	/**
	 * Tìm kích thước theo ID.
	 */
	public Optional<Size> findById(Integer sizeId);

	/**
	 * Tạo một kích thước mới.
	 */
	public Size create(Size size);

	/**
	 * Cập nhật thông tin của một kích thước.
	 */
	public Size update(Size size);

	/**
	 * Xóa một kích thước.
	 */
	public void delete(Size size);

	/**
	 * Tìm kiếm kích thước theo tên và trang.
	 */
	public Page<Size> search(Integer name, Pageable pageable);


}
