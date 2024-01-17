package com.poly.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "ACCOUNT_ADDRESS")
public class AccountAddress implements Serializable {

	@Id
	private String addressId;
	private String wards;
	private String district;
	private String city;

	@OneToOne
	@JoinColumn(name = "username")
	private Accounts accounts;

}
