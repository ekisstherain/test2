package com.gdie.dao;

import java.util.List;

import com.gdie.entity.AccountDetailKey;

public interface AccountDetailMapper {
	int deleteByPrimaryKey(AccountDetailKey key);

	int insert(AccountDetailKey record);

	int insertSelective(AccountDetailKey record);

	public List<AccountDetailKey> findByAccount(String id);

	public void deleteByAccount(String accountId);

	public List<AccountDetailKey> findAll();
}