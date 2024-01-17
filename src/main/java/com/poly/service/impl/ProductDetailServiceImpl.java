package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.ProductsDetailDao;
import com.poly.entity.ProductsDetail;
import com.poly.services.ProductDetailService;




@Service
public class ProductDetailServiceImpl implements ProductDetailService {
	@Autowired
	ProductsDetailDao productsDetailDAO;

	 @Override
	    public ProductsDetail save(ProductsDetail detail) {
	        // Lưu chi tiết sản phẩm vào cơ sở dữ liệu
	        return productsDetailDAO.save(detail);
	    }

	    @Override
	    public List<ProductsDetail> findAll() {
	        // Lấy danh sách tất cả chi tiết sản phẩm từ cơ sở dữ liệu
	        return productsDetailDAO.findAll();
	    }

	    @Override
	    public Page<ProductsDetail> findByProductIdAndPage(Integer productId, Pageable pageable) {
	        // Lấy một trang danh sách chi tiết sản phẩm dựa trên ID sản phẩm và phân trang từ cơ sở dữ liệu
	        return productsDetailDAO.findByProductIdAndPage(productId, pageable);
	    }

	    @Override
	    public void deleteById(Integer id) {
	        // Xóa chi tiết sản phẩm dựa trên ID từ cơ sở dữ liệu
	        productsDetailDAO.deleteById(id);
	    }

	    @Override
	    public ProductsDetail findById(Integer id) {
	        // Tìm kiếm chi tiết sản phẩm dựa trên ID và trả về kết quả
	        return productsDetailDAO.getOne(id);
	    }
}
