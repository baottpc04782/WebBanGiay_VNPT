package com.poly.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.poly.entity.Manufactor;

public interface ManufactorService {

	/**
	 * Lấy danh sách tất cả các nhà sản xuất.
	 */
	public List<Manufactor> findAll();

	/**
	 * Lấy danh sách tất cả các nhà sản xuất theo trang.
	 */
	public Page<Manufactor> findAll(Pageable pageable);

	/**
	 * Tìm một nhà sản xuất theo ID.
	 */
	public Optional<Manufactor> findById(Integer manufactorId);

	/**
	 * Tạo một nhà sản xuất mới.
	 */
	public Manufactor create(Manufactor manufactor);

	/**
	 * Cập nhật thông tin của một nhà sản xuất.
	 */
	public Manufactor update(Manufactor manufactor);

	/**
	 * Xóa một nhà sản xuất.
	 */
	public void delete(Manufactor manufactor);

	/**
	 * Tìm kiếm nhà sản xuất theo tên và trang.
	 */
	public Page<Manufactor> search(String name, Pageable pageable);

	
}
