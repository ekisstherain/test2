package com.gdie.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdie.common.SpringUtil;
import com.gdie.entity.Account;
import com.gdie.entity.AccountDetailKey;
import com.gdie.entity.History;
import com.gdie.entity.Role;
import com.gdie.entity.Statistics;
import com.gdie.entity.User;
import com.gdie.service.IAccountDetailService;
import com.gdie.service.IAccountService;
import com.gdie.service.IHistoryService;
import com.gdie.service.IRoleService;
import com.gdie.service.IStatisticsService;
import com.gdie.service.IUserService;
import com.gdie.service.impl.AccountDetailService;
import com.gdie.service.impl.AccountService;
import com.gdie.service.impl.HistoryService;
import com.gdie.service.impl.RoleService;
import com.gdie.service.impl.StatisticsService;
import com.gdie.service.impl.UserService;
import com.gdie.vo.Order;

public class TableBackUp {

	private IUserService userService = SpringUtil.getBean(UserService.class);

	private IRoleService roleService = SpringUtil.getBean(RoleService.class);;

	private IAccountService accountService = SpringUtil.getBean(AccountService.class);;

	private IAccountDetailService accountDetailService = SpringUtil.getBean(AccountDetailService.class);;

	private IHistoryService historyService = SpringUtil.getBean(HistoryService.class);;

	private IStatisticsService statisticsService = SpringUtil.getBean(StatisticsService.class);;

	/**
	 * 获取备份的数据库表语句
	 * 
	 * @return
	 */
	public static String getTableBackUpSql() {
		TableBackUp tb = new TableBackUp();
		StringBuffer sb = new StringBuffer();
		sb.append(tb.getRoleData()).append("\r\n");
		sb.append(tb.getUserInsertData()).append("\r\n");
		sb.append(tb.getAccountData()).append("\r\n");
		sb.append(tb.getAccountDetailData()).append("\r\n");
		sb.append(tb.getHistoryData()).append("\r\n");
		sb.append(tb.getStatisticsData()).append("\r\n");
		return sb.toString();
	}

	/**
	 * 角色数据
	 * 
	 * @return
	 */
	public String getRoleData() {
		String insert = "INSERT INTO t_role (id, name) VALUES ('%s', '%s');\r\n";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Role> list = roleService.query(map);
		StringBuffer buffer = new StringBuffer();
		buffer.append("--角色表数据\r\n");
		for (Role o : list) {
			buffer.append(String.format(insert, o.getId(), o.getName()));
		}
		return buffer.toString();
	}

	/**
	 * 备份用户表
	 * 
	 * @return
	 */
	public String getUserInsertData() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", Order.desc("createdate"));
		List<User> list = userService.query(map);
		String insert = "INSERT INTO t_user (id, createdate, age, name, password, createuser, lastmodifydate, lastmodifyuser, account, role) VALUES ('%s', '%s', NULL, '%s', '%s', NULL, '%s', NULL, '%s', '%s');\r\n";
		StringBuffer buffer = new StringBuffer();
		buffer.append("--用户表数据\r\n");
		for (User u : list) {
			buffer.append(String.format(insert, u.getId(), u.getCreatedate(), u.getName(), u.getPassword(), u.getLastmodifydate(), u.getAccount(), u.getRole()));
		}

		return buffer.toString();
	}

	/**
	 * 账单数据
	 * 
	 * @return
	 */
	public String getAccountData() {
		String insert = "INSERT INTO t_account (id, pay, use_type, use_date, payor, status, remark) VALUES ('%s', %s, '%s', '%s', '%s', '%s', '%s');\r\n";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Account> list = accountService.query(map);
		StringBuffer buffer = new StringBuffer();
		buffer.append("--账单表数据\r\n");
		for (Account o : list) {
			buffer.append(String.format(insert, o.getId(), o.getPay(), o.getUseType(), o.getUseDate(), o.getPayor(), o.getStatus(), o.getRemark()));
		}
		return buffer.toString();
	}

	/**
	 * 账单明细表数据
	 * 
	 * @return
	 */
	public String getAccountDetailData() {
		String insert = "INSERT INTO t_account_detail (account_id, customer_id) VALUES ('%s', '%s');\r\n";
		Map<String, Object> map = new HashMap<String, Object>();
		List<AccountDetailKey> list = accountDetailService.findAll();
		StringBuffer buffer = new StringBuffer();
		buffer.append("--账单明细表数据\r\n");
		for (AccountDetailKey o : list) {
			buffer.append(String.format(insert, o.getAccountId(), o.getCustomerId()));
		}
		return buffer.toString();
	}

	/**
	 * 历史记录表数据
	 * 
	 * @return
	 */
	public String getHistoryData() {
		String insert = "INSERT INTO t_history (id, operate_time, operate_user, detail, object) VALUES ('%s', '%s', '%s', '%s', '%s');\r\n";
		Map<String, Object> map = new HashMap<String, Object>();
		List<History> list = historyService.query(map);
		StringBuffer buffer = new StringBuffer();
		buffer.append("--历史记录表数据\r\n");
		for (History o : list) {
			buffer.append(String.format(insert, o.getId(), o.getOperateTime(), o.getOperateUser(), o.getDetail(), o.getObject()));
		}
		return buffer.toString();
	}

	/**
	 * 结算表数据
	 * 
	 * @return
	 */
	public String getStatisticsData() {
		String insert = "INSERT INTO t_statistics (id, customer, pay, consume, balance, balanced_date) VALUES ('%s', '%s', %s, %s, %s, '%s');\r\n";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Statistics> list = statisticsService.query(map);
		StringBuffer buffer = new StringBuffer();
		buffer.append("--结算表数据\r\n");
		for (Statistics o : list) {
			buffer.append(String.format(insert, o.getId(), o.getCustomer(), o.getPay(), o.getConsume(), o.getBalance(), o.getBalancedDate()));
		}
		return buffer.toString();
	}

}
