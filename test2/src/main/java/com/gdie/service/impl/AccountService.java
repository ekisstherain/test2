package com.gdie.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdie.common.ConfigUtil;
import com.gdie.common.DBUtil;
import com.gdie.common.JsonUtil;
import com.gdie.dao.AccountDetailMapper;
import com.gdie.dao.AccountMapper;
import com.gdie.dao.HistoryMapper;
import com.gdie.dao.StatisticsMapper;
import com.gdie.entity.Account;
import com.gdie.entity.AccountDetailKey;
import com.gdie.entity.History;
import com.gdie.entity.Statistics;
import com.gdie.service.IAccountService;
import com.gdie.vo.DateScope;
import com.gdie.vo.PageData;
import com.gdie.vo.QueryResult;
import com.gdie.vo.SqlParam;

@Service
public class AccountService extends BaseService<Account, String> implements IAccountService {
	private AccountMapper accountMapper;

	@Autowired
	private AccountDetailMapper accountDetailMapper;

	@Autowired
	private HistoryMapper historyMapper;

	@Autowired
	private StatisticsMapper statisticsMapper;

	@Autowired
	public void setUserMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
		this.mapper = accountMapper;
	}

	@Transactional
	public String addAccount(Account account, List<String> customers) {
		if (customers == null || customers.size() == 0) {
			return "账单缺少消费者!";
		}
		if (account.getPay() == null) {
			return "账单缺少付费金额";
		}
		if (StringUtils.isEmpty(account.getPayor())) {
			return "账单缺少付费人";
		}
		if (account.getUseDate() == null) {
			return "账单缺少使用日期";
		}

		account.init(); // 初始化其他参数
		account.setStatus("unbalanced"); // 设置状态
		accountMapper.insert(account); // 创建账单

		// 创建账单明细
		for (String customer : customers) {
			accountDetailMapper.insert(new AccountDetailKey(account.getId(), customer));
		}

		// 创建历史记录
		History history = new History(account.getId(), account.getPayor(), "创建账单:" + JsonUtil.toJSONString2(account));
		historyMapper.insert(history);

		return "";
	}

	@Override
	@Transactional
	public String modify(Account account, List<String> customers) {
		Account oldAccount = findById(account.getId());
		oldAccount.setPay(account.getPay());
		oldAccount.setUseType(account.getUseType());
		oldAccount.setUseDate(account.getUseDate());
		oldAccount.setRemark(account.getRemark());

		if (customers == null || customers.size() == 0) {
			return "账单缺少消费者!";
		}
		if (oldAccount.getPay() == null) {
			return "账单缺少付费金额";
		}
		if (StringUtils.isEmpty(oldAccount.getPayor())) {
			return "账单缺少付费人";
		}
		if (oldAccount.getUseDate() == null) {
			return "账单缺少使用日期";
		}

		updateByPrimaryKeySelective(oldAccount);

		// 删除明细
		accountDetailMapper.deleteByAccount(oldAccount.getId());
		// 创建账单明细
		for (String customer : customers) {
			accountDetailMapper.insert(new AccountDetailKey(oldAccount.getId(), customer));
		}

		// 创建历史记录
		History history = new History(oldAccount.getId(), oldAccount.getPayor(), "修改账单:" + JsonUtil.toJSONString2(oldAccount));
		historyMapper.insert(history);

		return "";
	}

	@Override
	public List<Account> selectAccountView(SqlParam sqlParam) {
		// TODO Auto-generated method stub
		return accountMapper.selectAccountView(sqlParam);
	}

	@Override
	public PageData<Account> selectAccountView(Map<String, Object> condition, int page) {
		SqlParam sqlParam = SqlParam.getInstance(DBUtil.buildWhere(condition, page, new Integer(ConfigUtil.getProperty("DEFAULT_PAGE_SIZE"))));
		QueryResult<Account> result = new QueryResult<Account>();
		result.setData(accountMapper.selectAccountView(sqlParam));

		// 去除分页信息 语句
		String sql = sqlParam.getWhere();
		int startIndex = sql.indexOf("limit");
		if (startIndex > -1) {
			sql = sql.substring(0, startIndex);
		}

		// 去除排序
		startIndex = sql.indexOf("order");
		if (startIndex > -1) {
			sql = sql.substring(0, startIndex);
		}

		sqlParam.setWhere(sql);
		result.setTotal(count(sqlParam));

		PageData<Account> pageData = new PageData<Account>(page);
		pageData.initPageData(result); // 设置页面属性
		return pageData;
	}

	@Override
	public List<Statistics> statistics(DateScope dateScope) {
		// TODO Auto-generated method stub
		return accountMapper.statistics(dateScope);
	}

	@Override
	public PageData<Statistics> getStat(DateScope dateScope) {
		PageData<Statistics> pageData = new PageData<Statistics>(1);
		pageData.setData(accountMapper.statistics(dateScope));
		return pageData;
	}

	@Override
	@Transactional
	public void balanced(DateScope dateScope) {
		// 修改状态
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ge:use_date", dateScope.getStartDate());
		map.put("le:use_date", dateScope.getEndDate());
		List<Account> accounts = query(map);
		for (Account account : accounts) {
			account.setStatus("balanced"); // 结算状态
			updateByPrimaryKeySelective(account);
		}

		// 插入结算单
		List<Statistics> statistics = accountMapper.statistics(dateScope);
		for (Statistics statistics2 : statistics) {
			statistics2.setId(UUID.randomUUID().toString());
			statistics2.setBalancedDate(new Date());
			statisticsMapper.insert(statistics2);
		}
	}
}
