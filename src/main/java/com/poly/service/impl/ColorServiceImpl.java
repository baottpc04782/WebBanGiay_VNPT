package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.ColorDao;
import com.poly.entity.Color;
import com.poly.services.ColorService;


@Service
public class ColorServiceImpl implements ColorService {
      @Autowired
      ColorDao colorDao;

      @Override
      public List<Color> findAll() {
          // Lấy danh sách tất cả các màu sắc từ dao và trả về
          return colorDao.findAll();
      }

      @Override
      public Page<Color> findAll(Pageable pageable) {
          // Lấy một trang danh sách màu sắc dựa trên phân trang từ dao và trả về
          return colorDao.findAll(pageable);
      }

      @Override
      public Optional<Color> findById(Integer colorId) {
          // Tìm kiếm một màu sắc theo ID từ dao và trả về kết quả dưới dạng Optional
          return colorDao.findById(colorId);
      }

      @Override
      public Color create(Color color) {
          // Tạo mới một màu sắc thông qua dao và trả về
          return colorDao.save(color);
      }

      @Override
      public Color update(Color color) {
          // Cập nhật thông tin của một màu sắc thông qua dao và trả về
          return colorDao.save(color);
      }

      @Override
      public void delete(Color color) {
          // Xóa một màu sắc thông qua dao
          colorDao.delete(color);
      }

      @Override
      public Page<Color> search(String name, Pageable pageable) {
          // Tìm kiếm màu sắc dựa trên tên với hỗ trợ phân trang từ dao và trả về
          return colorDao.findByNameContaining(name, pageable);
      }
 
      
}
