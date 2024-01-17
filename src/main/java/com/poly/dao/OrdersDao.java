package com.poly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Orders;

public interface OrdersDao extends JpaRepository<Orders, Integer> {

      // Phương thức lấy danh sách đơn hàng dựa trên tên người dùng
    @Query("SELECT o FROM Orders o WHERE o.accounts.username = :username")
    List<Orders> finByUsername(@Param("username") String username);

    // Phương thức lấy danh sách đơn hàng dựa trên ngày tạo đơn hàng
    @Query("SELECT o FROM Orders o WHERE EXTRACT(YEAR FROM o.createDate) = EXTRACT(YEAR FROM :dateToday) " +
           "AND EXTRACT(MONTH FROM o.createDate) = EXTRACT(MONTH FROM :dateToday) " +
           "AND EXTRACT(DAY FROM o.createDate) = EXTRACT(DAY FROM :dateToday)")
    List<Orders> findByCreateDate(@Param("dateToday") Date dateToday);

    // Phương thức lấy danh sách đơn hàng dựa trên tháng tạo đơn hàng
    @Query("SELECT o FROM Orders o WHERE EXTRACT(YEAR FROM o.createDate) = EXTRACT(YEAR FROM :dateToday) " +
           "AND EXTRACT(MONTH FROM o.createDate) = EXTRACT(MONTH FROM :dateToday)")
    List<Orders> findByCreateDateMonth(@Param("dateToday") Date dateToday);

    // Phương thức lấy danh sách đơn hàng dựa trên năm tạo đơn hàng
    @Query("SELECT o FROM Orders o WHERE EXTRACT(YEAR FROM o.createDate) = EXTRACT(YEAR FROM :dateToday)")
    List<Orders> findByCreateDateYear(@Param("dateToday") Date dateToday);
    
	/*
	 * @Query("SELECT o FROM Orders o WHERE YEAR(o.createDate) = :year")
	 * List<Orders> findByYear(@Param("year") int year);
	 * 
	 * @Query("SELECT SUM(od.Total) FROM Orders o JOIN o.ordersDetail od WHERE YEAR(o.createDate) = :year"
	 * ) Float getTotalRevenueByYear(@Param("year") int year);
	 */
}
