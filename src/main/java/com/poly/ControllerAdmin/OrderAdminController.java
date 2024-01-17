package com.poly.ControllerAdmin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.OrdersDao;
import com.poly.dao.OrdersDetailDao;
import com.poly.entity.Orders;

@Controller
@RequestMapping("/admin")
public class OrderAdminController {

	@Autowired
	OrdersDao ordersDAO;

	@Autowired
	OrdersDetailDao orderDetailDAO;

	// Hiển thị trang quản lý đơn hàng
	@GetMapping("/order")
	public String index(Model model) {
	    // Thêm danh sách trạng thái đơn hàng vào Model
	    model.addAttribute("lstStatus", listStatus());
	    
	    // Thêm danh sách đơn hàng vào Model
	    model.addAttribute("list", ordersDAO.findAll());
	    
	    return "/views/admin/order";
	}

	// Xử lý cập nhật trạng thái đơn hàng
	@RequestMapping("/order/saveStatus")
	public String saveStatusOrder(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
	    // Tìm đơn hàng theo ID
	    Optional<Orders> opOrder = ordersDAO.findById(id);
	    
	    // Nếu đơn hàng tồn tại
	    if (opOrder.isPresent()) {
	        Orders order = opOrder.get();
	        
	        // Cập nhật trạng thái đơn hàng
	        if (order != null) {
	            order.setOrderStatus(status);
	            ordersDAO.save(order);
	        }
	    }
	    
	    // Chuyển hướng về trang quản lý đơn hàng
	    return "redirect:/admin/order";
	}

	// Xem chi tiết đơn hàng
	@GetMapping("/orderSee/{id}")
	public String OrderSee(@PathVariable("id") Integer id, Model model) {
	    // Thêm danh sách đơn hàng vào Model
	    model.addAttribute("list", ordersDAO.findAll());
	    
	    // Thêm chi tiết đơn hàng vào Model
	    model.addAttribute("listdetail", ordersDAO.getOne(id));
	    
	    // Chuyển hướng về trang quản lý đơn hàng
	    return "/views/admin/order";
	}

	// Phương thức để trả về danh sách các trạng thái đơn hàng
	public Map<Integer, String> listStatus() {
	    Map<Integer, String> status = new HashMap<>();
	    status.put(1, "Đang chờ xác nhận");
	    status.put(2, "Huỷ hàng");
	    status.put(3, "Đã Xác nhận");
	    status.put(4, "Đang giao");
	    status.put(0, "Đã giao");
	    
	    return status;
	}

	/*
	 * @Autowired OrdersDao ordersDAO;
	 * 
	 * @Autowired OrdersDetailDao orderDetailDAO;
	 * 
	 * @GetMapping("/order") public String index(Model model) {
	 * model.addAttribute("lstStatus", listStatus()); model.addAttribute("list",
	 * ordersDAO.findAll()); return "/views/admin/order"; }
	 * 
	 * @RequestMapping("/order/saveStatus") public String
	 * saveStatusOrder(@RequestParam("id") Integer id, @RequestParam("status")
	 * Integer status) { Optional<Orders> opOrder = ordersDAO.findById(id); if
	 * (opOrder.isPresent()) { Orders order = opOrder.get(); if(order != null) {
	 * order.setOrderStatus(status); ordersDAO.save(order); } }
	 * 
	 * return "redirect:/admin/order"; }
	 * 
	 * @GetMapping("/orderSee/{id}") public String OrderSee(@PathVariable("id")
	 * Integer id,Model model){ // OrderDetail orderDetail = new OrderDetail();
	 * model.addAttribute("list", ordersDAO.findAll());
	 * 
	 * // if (order.getId().equals(orderDetail.getId())){ //
	 * System.out.println("order.getId()"); // } model.addAttribute("listdetail",
	 * ordersDAO.getOne(id));
	 * 
	 * // model.addAttribute("listorderdetail", orderDetailDAO.findByOrderById(id));
	 * 
	 * return "/views/admin/order"; }
	 * 
	 * public Map<Integer, String> listStatus () { Map<Integer, String> status = new
	 * HashMap<>(); status.put(1, "Đang chờ xác nhận"); status.put(2, "Huỷ hàng");
	 * status.put(3, "Đã Xác nhận"); status.put(4, "Đang giao"); status.put(0,
	 * "Đã giao");
	 * 
	 * return status; }
	 */

}
