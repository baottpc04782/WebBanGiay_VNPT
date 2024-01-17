package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.ManufactorDao;
import com.poly.entity.Manufactor;
import com.poly.services.ManufactorService;


@Service
public class ManufactorServiceImpl implements ManufactorService{
    @Autowired
    ManufactorDao manufactorDao;

    @Override
    public List<Manufactor> findAll() {
        // Lấy danh sách tất cả các nhà sản xuất từ dao và trả về
        return manufactorDao.findAll();
    }

    @Override
    public Page<Manufactor> findAll(Pageable pageable) {
        // Lấy một trang danh sách nhà sản xuất dựa trên phân trang từ dao và trả về
        return manufactorDao.findAll(pageable);
    }

    @Override
    public Optional<Manufactor> findById(Integer manufactorId) {
        // Tìm kiếm một nhà sản xuất theo ID từ dao và trả về kết quả dưới dạng Optional
        return manufactorDao.findById(manufactorId);
    }

    @Override
    public Manufactor create(Manufactor manufactor) {
        // Tạo mới một nhà sản xuất thông qua dao và trả về
        return manufactorDao.save(manufactor);
    }

    @Override
    public Manufactor update(Manufactor manufactor) {
        // Cập nhật thông tin của một nhà sản xuất thông qua dao và trả về
        return manufactorDao.save(manufactor);
    }

    @Override
    public void delete(Manufactor manufactor) {
        // Xóa một nhà sản xuất thông qua dao
        manufactorDao.delete(manufactor);
    }

    @Override
    public Page<Manufactor> search(String name, Pageable pageable) {
        // Tìm kiếm nhà sản xuất dựa trên tên với hỗ trợ phân trang từ dao và trả về
        return manufactorDao.findByNameContaining(name, pageable);
    }
	
	
}

