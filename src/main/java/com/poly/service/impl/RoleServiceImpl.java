package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.RolesDao;
import com.poly.entity.Roles;
import com.poly.services.RoleService;


@Service
public class RoleServiceImpl implements RoleService{

	@Autowired																																																																																																																																																						
	RolesDao  rolesDao;

    @Override
    public List<Roles> findAll() {
        // Lấy danh sách tất cả các vai trò từ cơ sở dữ liệu
        return rolesDao.findAll();
    }

    @Override
    public Page<Roles> findAll(Pageable pageable) {
        // Lấy một trang danh sách tất cả các vai trò từ cơ sở dữ liệu và trả về kết quả phân trang
        return rolesDao.findAll(pageable);
    }

    @Override
    public Optional<Roles> findById(String id) {
        // Tìm kiếm vai trò dựa trên ID và trả về kết quả
        return rolesDao.findById(id);
    }

    @Override
    public Roles create(Roles roles) {
        // Lưu vai trò vào cơ sở dữ liệu
        return rolesDao.save(roles);
    }

    @Override
    public Roles update(Roles roles) {
        // Cập nhật thông tin của vai trò trong cơ sở dữ liệu
        return rolesDao.save(roles);
    }

    @Override
    public void delete(Roles roles) {
        // Xóa vai trò khỏi cơ sở dữ liệu
        rolesDao.delete(roles);
    }

	/*
	 * @Override public Page<Roles> search(String name, Pageable pageable) { // TODO
	 * Auto-generated method stub return rolesDao.; }
	 */

	

	
}
