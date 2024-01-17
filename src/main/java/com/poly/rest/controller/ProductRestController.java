package com.poly.rest.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.DiscountDao;
import com.poly.dao.ProductsDetailDao;
import com.poly.entity.Discount;
import com.poly.entity.Products;
import com.poly.entity.ProductsDetail;
import com.poly.services.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	
    @Autowired
    ProductService productService;
    
    @Autowired
    ProductsDetailDao productsDetailDao;
    
    @Autowired
    DiscountDao discountDao;

    @GetMapping("{id}")
    public Products getOne(@PathVariable("id") Integer id){
        return productService.findById(id);
    }
    
    @GetMapping("/detail/{id}")
    public List<ProductsDetail> getDetailById(@PathVariable("id") Integer productId){
        return productsDetailDao.findProductsDetailByProductId(productId);
    }
    
    @GetMapping("/discount/{productId}")
    public List<Discount> getdiscountByproductId(@PathVariable("productId") Integer[] productId){
    	Date dateToday = new Date();
    	List<Discount> lstDiscount = discountDao.findDiscountByProductIdAnđate(productId, dateToday);
    	return lstDiscount;
    }
}
