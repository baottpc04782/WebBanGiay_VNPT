package com.poly.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.entity.Categories;

public interface CategoryService {
	 // Trả về danh sách tất cả các danh mục
    List<Categories> findAll();

    // Trả về một trang danh sách các danh mục dựa trên phân trang
    Page<Categories> findAll(Pageable pageable);

    // Tìm kiếm danh mục theo ID và trả về kết quả dưới dạng Optional
    Optional<Categories> findById(Integer categoryId);

    // Tạo mới một danh mục và trả về nó
    Categories create(Categories categories);

    // Cập nhật thông tin của một danh mục và trả về nó
    Categories update(Categories categories);

    // Xóa một danh mục khỏi hệ thống
    void delete(Categories categories);

    // Tìm kiếm các danh mục dựa trên tên và phân trang kết quả
    Page<Categories> search(String name, Pageable pageable);
    
}
