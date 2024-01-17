package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.AccountAddress;

public interface AccountAddressDao extends JpaRepository<AccountAddress, String> {
    @Query("SELECT p FROM AccountAddress p WHERE p.accounts.username=?1")
    public AccountAddress findAccountAddressByUsername(String username);
}
