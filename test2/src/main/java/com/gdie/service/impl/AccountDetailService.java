package com.gdie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdie.dao.AccountDetailMapper;
import com.gdie.entity.AccountDetailKey;
import com.gdie.service.IAccountDetailService;

@Service
public class AccountDetailService implements IAccountDetailService {

	@Autowired
	private AccountDetailMapper detailMapper;

	@Override
	public int deleteByPrimaryKey(AccountDetailKey key) {
		// TODO Auto-generated method stub
		return detailMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(AccountDetailKey record) {
		// TODO Auto-generated method stub
		return detailMapper.insert(record);
	}

	@Override
	public int insertSelective(AccountDetailKey record) {
		// TODO Auto-generated method stub
		return detailMapper.insertSelective(record);
	}

	@Override
	public List<AccountDetailKey> findByAccount(String id) {
		// TODO Auto-generated method stub
		return detailMapper.findByAccount(id);
	}

	@Override
	public void deleteByAccount(String accountId) {
		// TODO Auto-generated method stub
		detailMapper.deleteByAccount(accountId);
	}

	@Override
	public List<AccountDetailKey> findAll() {
		// TODO Auto-generated method stub
		return detailMapper.findAll();
	}
}
