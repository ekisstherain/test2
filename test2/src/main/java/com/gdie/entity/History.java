package com.gdie.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class History implements Serializable{
	private String id;

	private Date operateTime;

	private String operateUser;

	private String operateUserName;

	private String detail;

	private String object;

	public void init() {
		this.id = UUID.randomUUID().toString();
		this.operateTime = new Date();
	}

	public History() {
		// TODO Auto-generated constructor stub
	}

	public History(String object, String operateUser, String detail) {
		init();
		this.object = object;
		this.operateUser = operateUser;
		this.detail = detail;
	}

	public static History getInstance() {
		History history = new History();
		history.setId(UUID.randomUUID().toString());
		history.setOperateTime(new Date());
		return history;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser == null ? null : operateUser.trim();
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail == null ? null : detail.trim();
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object == null ? null : object.trim();
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
}