package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.ProductsDao;
import com.poly.entity.Products;
import com.poly.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductsDao pdao;

    @Override
    public List<Products> findAll() {
        // Lấy danh sách tất cả sản phẩm từ cơ sở dữ liệu
        return pdao.findAll();
    }

    @Override
    public Products findById(Integer id) {
        // Tìm kiếm sản phẩm dựa trên ID và trả về kết quả
        return pdao.findById(id).orElse(null);
    }

    @Override
    public List<Products> findByCategoryId(Integer cid) {
        // Tìm kiếm sản phẩm dựa trên ID danh mục và trả về danh sách kết quả
        return pdao.findByCategoryId(cid);
    }

    @Override
    public List<Products> findAllDesc() {
        // Lấy danh sách tất cả sản phẩm theo thứ tự giảm dần từ cơ sở dữ liệu
        return pdao.findAllDesc();
    }

    @Override
    public Products save(Products products) {
        // Lưu sản phẩm vào cơ sở dữ liệu
        return pdao.save(products);
    }

    @Override
    public void deleteById(Integer id) {
        // Xóa sản phẩm dựa trên ID từ cơ sở dữ liệu
        pdao.deleteByRelatedId(id);
        pdao.deleteById(id);
    }

    @Override
    public Page<Products> findProductNameLike(String keywords, Pageable pageable) {
        // Tìm kiếm sản phẩm dựa trên tên sản phẩm chứa từ khóa và trả về kết quả phân trang
        return pdao.findByProductNameLike(keywords, pageable);
    }

    @Override
    public Page<Products> SortByProductName(Pageable pageable) {
        // Lấy trang sản phẩm được sắp xếp theo tên từ cơ sở dữ liệu và trả về kết quả phân trang
        return pdao.SortByProductName(pageable);
    }

    @Override
    public Page<Products> findAllByPage(Pageable pageable) {
        // Lấy một trang danh sách tất cả sản phẩm từ cơ sở dữ liệu và trả về kết quả phân trang
        return pdao.findAll(pageable);
    }

    @Override
    public boolean existsById(Integer id) {
        // Kiểm tra xem sản phẩm có tồn tại dựa trên ID không
        return pdao.existsById(id);
    }

    @Override
    public List<Products> getProductByCategoryId(Integer id) {
        // Lấy danh sách sản phẩm dựa trên ID danh mục từ cơ sở dữ liệu
        return pdao.getProductByCategoryId(id);
    }

    @Override
    public List<Products> findByCategory(Optional<Integer> cid) {
        // Lấy danh sách sản phẩm dựa trên ID danh mục từ cơ sở dữ liệu
        return pdao.finByCategory(cid);
    }

    @Override
    public List<Products> findProductByKeywords(String keyword) {
        // Tìm kiếm sản phẩm dựa trên từ khóa và trả về danh sách kết quả
        return pdao.findProductByKeywords(keyword);
    }

    @Override
    public Products getOne(Integer id) {
        // Lấy một sản phẩm dựa trên ID và trả về kết quả
        return pdao.getOne(id);
    }

    @Override
    public Page<Products> findAll(Pageable pageable) {
        // Lấy một trang danh sách tất cả sản phẩm từ cơ sở dữ liệu và trả về kết quả phân trang
        return pdao.findAll(pageable);
    }

	
	
}
