package com.ungdungso.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="account")
public class Account {
	@Id
	@Column( columnDefinition = "NVARCHAR(20)", name="username")
	private String userName;
	
	@Column( columnDefinition = "NVARCHAR(255) NOT NULL",name="fullname")	
	private String fullName;
	
	@Column( columnDefinition = "NVARCHAR(10) NOT NULL",name="phone")
	private String phone;
	
	@Column( columnDefinition = "NVARCHAR(40) NOT NULL",name="mail")
	private String mail;
	
	@Column( columnDefinition = "NVARCHAR(255) NOT NULL", name="pass")
	private String pass;
	
	@Column( columnDefinition = "NVARCHAR(40) NOT NULL",name="permission")
	private String permission;
	
	@Column( columnDefinition = "NVARCHAR(10) NOT NULL", name="available")
	private String available;
	
	public Account() {
		super();
	}
	public Account(String userName, String fullName, String phone, String mail, String pass, String permission,
			String available) {
		super();
		this.userName = userName;
		this.fullName = fullName;
		this.phone = phone;
		this.mail = mail;
		this.pass = pass;
		this.permission = permission;
		this.available = available;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	
	
	
	

}
