package com.poly.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "DISCOUNT")
public class Discount implements Serializable {
	
	@Id
	private Integer categoryId;
	private Date startDay;
	private Date endDay;
	private Integer quantity;
	
	private String status;
	
	@ManyToOne
    @JoinColumn(name = "productId")
	private Products products;

}
