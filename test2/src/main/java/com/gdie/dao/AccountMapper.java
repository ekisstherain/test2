package com.gdie.dao;

import java.util.List;

import com.gdie.entity.Account;
import com.gdie.entity.Statistics;
import com.gdie.vo.DateScope;
import com.gdie.vo.SqlParam;

public interface AccountMapper extends BaseMapper<Account, String> {

	public List<Account> selectAccountView(SqlParam sqlParam);

	public List<Statistics> statistics(DateScope dateScope);
}