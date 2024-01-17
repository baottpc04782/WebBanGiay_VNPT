package com.poly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Discount;

public interface DiscountDao extends JpaRepository<Discount, Integer> {

    @Query("SELECT p FROM Discount p WHERE p.products.productId IN ?1 and ?2 >= p.startDay and ?2 <= p.endDay and p.status = 1")
    List<Discount> findDiscountByProductIdAnđate(Integer[] productId, Date dateToday);

}
