package com.poly.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "ORDERS_DETAIL")
public class OrdersDetail implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ordersDetailId;
	private Integer quantity;
	private float price;
	
	@ManyToOne
    @JoinColumn(name = "orderId")
	private Orders orderId;
	
	@ManyToOne
    @JoinColumn(name = "pdDetailId")
	private ProductsDetail productsDetail;

}
