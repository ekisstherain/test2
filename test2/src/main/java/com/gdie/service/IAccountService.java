package com.gdie.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.gdie.dao.AccountMapper;
import com.gdie.entity.Account;
import com.gdie.entity.Statistics;
import com.gdie.vo.DateScope;
import com.gdie.vo.PageData;

public interface IAccountService extends IBaseService<Account, String>, AccountMapper{

	/**
	 * 创建账单
	 * 
	 * @param account
	 * @param customers
	 * @return
	 */
	public String addAccount(Account account, List<String> customers);

	PageData<Account> selectAccountView(Map<String, Object> condition, int page);

	/**
	 * 修改账单
	 * 
	 * @param account
	 * @param customers
	 * @return
	 */
	String modify(Account account, List<String> customers);

	/**
	 * 获取统计数据
	 * @param dateScope
	 * @return
	 */
	PageData<Statistics> getStat(DateScope dateScope);

	/**
	 * 结算
	 * 
	 * @param dateScope
	 */
	void balanced(DateScope dateScope);

}
