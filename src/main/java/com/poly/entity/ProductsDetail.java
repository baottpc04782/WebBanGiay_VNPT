package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "PRODUCTS_DETAIL")
public class ProductsDetail implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pdDetailId;
	private Integer quantity;

	@JsonIgnore
    @OneToMany(mappedBy = "productsDetail")
	List<OrdersDetail> ordersDetail;
	
	@ManyToOne
    @JoinColumn(name = "colorId")
	private Color color;
	
	@ManyToOne
    @JoinColumn(name = "sizeId")
	private Size size;
	
	@ManyToOne
    @JoinColumn(name = "productId")
	private Products products;
	
}
