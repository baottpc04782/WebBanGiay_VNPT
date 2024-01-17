package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.dao.AccountsDao;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "ACCOUNTS")
public class Accounts implements Serializable {

	@Id
	private String username;
	private String password;
	private String email;
	private String fullname;
	private String phonenumber;
	private String photo;
	private String verifyCode;
	private boolean activated;

	@JsonIgnore
	@OneToOne(mappedBy = "accounts")
	AccountAddress accountAddress;

	@JsonIgnore
	@OneToMany(mappedBy = "accounts")
	List<Orders> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "accounts", fetch = FetchType.EAGER)
	List<Authorities> authorities;

	

   

	


	
}
