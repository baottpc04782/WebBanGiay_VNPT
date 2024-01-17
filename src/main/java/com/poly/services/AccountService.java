package com.poly.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.poly.entity.Accounts;

public interface AccountService {
	/**
	 * Tìm tài khoản bằng ID.
	 *
	 * @param username Tên đăng nhập của tài khoản cần tìm.
	 * @return Tài khoản tương ứng với ID hoặc null nếu không tìm thấy.
	 */
	public Accounts findById(String username);

	/**
	 * Tìm tài khoản bằng tên đăng nhập.
	 *
	 * @param username Tên đăng nhập của tài khoản cần tìm.
	 * @return Tài khoản tương ứng với tên đăng nhập hoặc null nếu không tìm thấy.
	 */
	public Accounts findByUsername(String username);

	/**
	 * Lưu thông tin hồ sơ của tài khoản.
	 *
	 * @param entity Đối tượng tài khoản chứa thông tin cần lưu.
	 * @param dto    Đối tượng tài khoản chứa thông tin cập nhật.
	 * @return Đối tượng tài khoản đã được cập nhật.
	 */
	public Accounts saveProfile(Accounts entity, Accounts dto);

	/**
	 * Lấy danh sách tất cả các tài khoản theo trang.
	 *
	 * @param pageable Thông tin về trang và kích thước trang.
	 * @return Trang dữ liệu chứa danh sách các tài khoản.
	 */
	public Page<Accounts> findAll(Pageable pageable);

	/**
	 * Tạo một tài khoản mới.
	 *
	 * @param account Đối tượng tài khoản cần tạo.
	 * @return Tài khoản đã được tạo.
	 */
	public Accounts create(Accounts account);

	/**
	 * Xóa một tài khoản.
	 *
	 * @param account Đối tượng tài khoản cần xóa.
	 */
	public void delete(Accounts account);

	/**
	 * Cập nhật thông tin của một tài khoản.
	 *
	 * @param account Đối tượng tài khoản chứa thông tin cập nhật.
	 * @return Tài khoản đã được cập nhật.
	 */
	public Accounts update(Accounts account);  

}
