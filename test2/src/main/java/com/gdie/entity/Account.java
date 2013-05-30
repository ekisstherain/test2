package com.gdie.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Account implements Serializable{
	private String id;

	private Double pay;

	private String useType;

	private Date useDate;

	private String payor;

	private String status;

	private String remark;

	// view 的属性
	private String payorName;

	private String customersName;
	
	private List<AccountDetailKey> detailKeys;

	public void init() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType == null ? null : useType.trim();
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getPayor() {
		return payor;
	}

	public void setPayor(String payor) {
		this.payor = payor == null ? null : payor.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayorName() {
		return payorName;
	}

	public void setPayorName(String payorName) {
		this.payorName = payorName;
	}

	public String getCustomersName() {
		return customersName;
	}

	public void setCustomersName(String customersName) {
		this.customersName = customersName;
	}

	public List<AccountDetailKey> getDetailKeys() {
		return detailKeys;
	}

	public void setDetailKeys(List<AccountDetailKey> detailKeys) {
		this.detailKeys = detailKeys;
	}

}