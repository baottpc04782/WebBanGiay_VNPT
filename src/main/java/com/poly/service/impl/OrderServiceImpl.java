package com.poly.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.OrdersDao;
import com.poly.dao.OrdersDetailDao;
import com.poly.entity.Orders;
import com.poly.entity.OrdersDetail;
import com.poly.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersDao dao;

    @Autowired
    OrdersDetailDao dtDao;

    @Override
    public Orders create(JsonNode orderData) {
        // Tạo một đối tượng ObjectMapper để chuyển đổi dữ liệu từ JsonNode sang đối tượng Orders
        ObjectMapper mapper = new ObjectMapper();

        // Chuyển đổi dữ liệu từ JsonNode sang đối tượng Orders và lưu vào cơ sở dữ liệu
        Orders order = mapper.convertValue(orderData, Orders.class);
        dao.save(order);

        // Chuyển đổi dữ liệu từ JsonNode sang danh sách OrdersDetail và lưu vào cơ sở dữ liệu
        TypeReference<List<OrdersDetail>> type = new TypeReference<List<OrdersDetail>>() {};
        List<OrdersDetail> details = mapper.convertValue(orderData.get("ordersDetail"), type)
                .stream().peek(d -> d.setOrderId(order)).collect(Collectors.toList());
        dtDao.saveAll(details);

        return order;
    }

    @Override
    public Orders findById(Integer id) {
        // Tìm kiếm đơn hàng dựa trên ID và trả về kết quả
        return dao.findById(id).orElse(null);
    }


	  @Override
	    public List<Orders> findByUsername(String username) {
		// Tìm kiếm danh sách đơn hàng dựa trên tên đăng nhập và trả về kết quả 
	        return dao.finByUsername(username);
	    } 

	/*
	 * @Override public List<Orders> findByUsername(String username) { // Tìm kiếm
	 * danh sách đơn hàng dựa trên tên đăng nhập và trả về kết quả return
	 * dao.findByUsername(username); }
	 */
}
