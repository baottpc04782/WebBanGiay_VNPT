package com.poly.services;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Orders;

public interface OrderService {
	/**
	 * Tạo đơn đặt hàng mới từ dữ liệu JSON.
	 *
	 * @param orderData Dữ liệu JSON chứa thông tin đơn đặt hàng.
	 * @return Đối tượng đơn đặt hàng đã được tạo.
	 */
	Orders create(JsonNode orderData);

	/**
	 * Tìm đơn đặt hàng theo ID.
	 *
	 * @param id ID của đơn đặt hàng cần tìm.
	 * @return Đối tượng đơn đặt hàng tương ứng với ID hoặc null nếu không tìm thấy.
	 */
	Orders findById(Integer id);

	/**
	 * Tìm danh sách đơn đặt hàng dựa trên tên người dùng.
	 *
	 * @param username Tên người dùng để tìm kiếm đơn đặt hàng.
	 * @return Danh sách đơn đặt hàng của người dùng cần tìm.
	 */
	List<Orders> findByUsername(String username);

}
