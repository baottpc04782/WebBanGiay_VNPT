package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Accounts;

public interface AccountsDao extends JpaRepository<Accounts, String> {
    @Query("SELECT p FROM Accounts p WHERE p.username=?1")
    Accounts findByUsername(String username);
}
