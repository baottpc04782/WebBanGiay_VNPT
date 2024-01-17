package com.poly.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.entity.Roles;



public interface RoleService {

	public List<Roles> findAll() ; //in ra lưu vào danh sách
	
	public Page<Roles> findAll(Pageable pageable) ;  //phân trang
	
	public Optional<Roles> findById(String id) ;    //tìm kiếm theo id

	public Roles create(Roles roles) ;     //thêm danh sách

	public Roles update(Roles roles) ;   //sửa danh sách

	public void delete(Roles roles) ;
	
	/* Page<Roles> search(String name, Pageable pageable); */
	
}
