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
@Table(name = "PRODUCTS")
public class Products implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	private String description;
	private String image;
	private String name;
	private float price;

	@JsonIgnore
	@OneToMany(mappedBy = "products")
	List<ProductsDetail> productsDetail;

	@JsonIgnore
	@OneToMany(mappedBy = "products")
	List<Discount> discount;

	@ManyToOne
	@JoinColumn(name = "manufactorId")
	private Manufactor manufactor;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Categories categories;
	
	public Products() {}

    public Products(Integer productId) {
    	this.productId = productId;
    }

}
