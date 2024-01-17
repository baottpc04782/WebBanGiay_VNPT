package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.poly.dao.SizeDao;
import com.poly.entity.Size;
import com.poly.services.SizeServies;



@Service
public class SizeServiceImpl implements SizeServies{
	@Autowired
	SizeDao sizeDao;
	
	 @Override
	    public List<Size> findAll() {
	        // Lấy danh sách tất cả các kích thước từ cơ sở dữ liệu
	        return sizeDao.findAll();
	    }

	    @Override
	    public Page<Size> findAll(Pageable pageable) {
	        // Lấy một trang danh sách tất cả các kích thước từ cơ sở dữ liệu và trả về kết quả phân trang
	        return sizeDao.findAll(pageable);
	    }

	    @Override
	    public Optional<Size> findById(Integer sizeId) {
	        // Tìm kiếm kích thước dựa trên ID và trả về kết quả
	        return sizeDao.findById(sizeId);
	    }

	    @Override
	    public Size create(Size size) {
	        // Lưu kích thước vào cơ sở dữ liệu
	        return sizeDao.save(size);
	    }

	    @Override
	    public Size update(Size size) {
	        // Cập nhật thông tin của kích thước trong cơ sở dữ liệu
	        return sizeDao.save(size);
	    }

	    @Override
	    public void delete(Size size) {
	        // Xóa kích thước khỏi cơ sở dữ liệu
	        sizeDao.delete(size);
	    }

	    @Override
	    public Page<Size> search(Integer name, Pageable pageable) {
	        // Tìm kiếm kích thước dựa trên tên và trả về kết quả phân trang
	        return sizeDao.findByName(name, pageable);
	    }

	
	

}
