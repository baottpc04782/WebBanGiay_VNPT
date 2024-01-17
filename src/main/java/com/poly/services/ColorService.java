package com.poly.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.entity.Color;


public interface ColorService {
	/**
	 * Lấy danh sách tất cả các màu sắc.
	 */
	public List<Color> findAll();

	/**
	 * Lấy danh sách tất cả các màu sắc theo trang.
	 */
	public Page<Color> findAll(Pageable pageable);

	/**
	 * Tìm một màu sắc theo ID.
	 */
	public Optional<Color> findById(Integer colorId);

	/**
	 * Tạo một màu sắc mới.
	 */
	public Color create(Color color);

	/**
	 * Cập nhật thông tin của một màu sắc.
	 */
	public Color update(Color color);

	/**
	 * Xóa một màu sắc.
	 */
	public void delete(Color color);

	/**
	 * Tìm kiếm màu sắc theo tên và trang.
	 */
	public Page<Color> search(String name, Pageable pageable);
}
