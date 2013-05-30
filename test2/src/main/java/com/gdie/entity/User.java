package com.gdie.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable{
	private String id;

	private String createdate;

	private Integer age;

	private String name;

	private String password;

	private String password2;

	private String createuser;

	private String lastmodifydate;

	private String lastmodifyuser;

	private String role;
	
	private String roleName;

	private String account;

	public void init() {
		this.id = UUID.randomUUID().toString();
		this.createdate = new Date().toLocaleString();
		this.lastmodifydate = this.createdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate == null ? null : createdate.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public String getLastmodifydate() {
		return lastmodifydate;
	}

	public void setLastmodifydate(String lastmodifydate) {
		this.lastmodifydate = lastmodifydate == null ? null : lastmodifydate.trim();
	}

	public String getLastmodifyuser() {
		return lastmodifyuser;
	}

	public void setLastmodifyuser(String lastmodifyuser) {
		this.lastmodifyuser = lastmodifyuser == null ? null : lastmodifyuser.trim();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}