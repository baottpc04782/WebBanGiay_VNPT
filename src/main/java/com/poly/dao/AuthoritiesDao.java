package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Authorities;

public interface AuthoritiesDao extends JpaRepository<Authorities, Integer> {

}
