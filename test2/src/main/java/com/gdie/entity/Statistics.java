package com.gdie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计对象
 * 
 * @author HHP
 * 
 */
public class Statistics implements Serializable{

	private String id;

	private String customer;

	private Double pay;

	private Double consume;

	private Double balance;

	private Date balancedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public Double getConsume() {
		return consume;
	}

	public void setConsume(Double consume) {
		this.consume = consume;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getBalancedDate() {
		return balancedDate;
	}

	public void setBalancedDate(Date balancedDate) {
		this.balancedDate = balancedDate;
	}
}
