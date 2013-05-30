package com.gdie.entity;

import java.io.Serializable;

public class AccountDetailKey implements Serializable{
	private String accountId;

	private String customerId;

	public AccountDetailKey() {
		// TODO Auto-generated constructor stub
	}

	public AccountDetailKey(String accountId, String customerId) {
		super();
		this.accountId = accountId;
		this.customerId = customerId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId == null ? null : accountId.trim();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId == null ? null : customerId.trim();
	}
}