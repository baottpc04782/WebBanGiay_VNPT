package com.poly.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.AccountAddressDao;
import com.poly.dao.AccountsDao;
import com.poly.entity.AccountAddress;
import com.poly.entity.Accounts;
import com.poly.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountsDao accountsDAO;

    @Autowired
    AccountAddressDao accountAddressDao;

    @Override
    public Accounts findById(String username) {
        // Tìm kiếm tài khoản dựa trên tên đăng nhập và trả về kết quả
        return accountsDAO.findById(username).orElse(null);
    }

    @Override
    public Accounts saveProfile(Accounts entity, Accounts dto) {
        // Cập nhật thông tin hồ sơ tài khoản
        entity.setFullname(dto.getFullname());
        entity.setEmail(dto.getEmail());
        entity.setPhonenumber(dto.getPhonenumber());
        entity.setPhoto("image");

        // Lưu thông tin hồ sơ vào cơ sở dữ liệu
        accountsDAO.save(entity);

        // Xác định địa chỉ của tài khoản
        AccountAddress entityAccAdd = accountAddressDao.findAccountAddressByUsername(entity.getUsername());
        AccountAddress dtoAccAdd = dto.getAccountAddress();
        if (entityAccAdd == null)
            entityAccAdd = new AccountAddress();

        entityAccAdd.setAddressId(entity.getUsername());
        entityAccAdd.setWards(dtoAccAdd.getWards());
        entityAccAdd.setDistrict(dtoAccAdd.getDistrict());
        entityAccAdd.setCity(dtoAccAdd.getCity());
        entityAccAdd.setAccounts(entity);

        // Lưu thông tin địa chỉ vào cơ sở dữ liệu
        accountAddressDao.save(entityAccAdd);

        return entity;
    }

    @Override
    public Page<Accounts> findAll(Pageable pageable) {
        // Lấy một trang danh sách tất cả các tài khoản dựa trên phân trang từ dao và trả về
        return accountsDAO.findAll(pageable);
    }

    @Override
    public Accounts create(Accounts account) {
        // Tạo mới một tài khoản thông qua dao và trả về
        return accountsDAO.save(account);
    }

    @Override
    public void delete(Accounts account) {
        // Xóa một tài khoản thông qua dao
        accountsDAO.delete(account);
    }

    @Override
    public Accounts update(Accounts account) {
        // Cập nhật thông tin của một tài khoản thông qua dao và trả về
        return accountsDAO.save(account);
    }

    @Override
    public Accounts findByUsername(String username) {
        // Tìm kiếm tài khoản dựa trên tên đăng nhập và trả về kết quả
        return accountsDAO.findByUsername(username);
    }

}
