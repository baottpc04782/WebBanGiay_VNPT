package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.CategoriesDao;
import com.poly.entity.Categories;
import com.poly.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoriesDao cdao;


    @Override
    public List<Categories> findAll() {
        // Lấy danh sách tất cả các danh mục từ dao và trả về
        return cdao.findAll();
    }

    @Override
    public Page<Categories> findAll(Pageable pageable) {
        // Lấy một trang danh sách danh mục dựa trên phân trang từ dao và trả về
        return cdao.findAll(pageable);
    }

    @Override
    public Optional<Categories> findById(Integer categoryId) {
        // Tìm kiếm một danh mục theo ID từ dao và trả về kết quả dưới dạng Optional
        return cdao.findById(categoryId);
    }

    @Override
    public Categories create(Categories categories) {
        // Tạo mới một danh mục thông qua dao và trả về
        return cdao.save(categories);
    }

    @Override
    public Categories update(Categories categories) {
        // Cập nhật thông tin của một danh mục thông qua dao và trả về
        return cdao.save(categories);
    }

    @Override
    public void delete(Categories categories) {
        // Xóa một danh mục thông qua dao
        cdao.delete(categories);
    }

    @Override
    public Page<Categories> search(String name, Pageable pageable) {
        // Tìm kiếm danh mục dựa trên tên với hỗ trợ phân trang từ dao và trả về
        return cdao.findByNameContaining(name, pageable);
    }
}
