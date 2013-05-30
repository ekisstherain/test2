package com.gdie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdie.common.ConfigUtil;
import com.gdie.common.JsonUtil;
import com.gdie.common.SessionInfo;
import com.gdie.entity.Account;
import com.gdie.entity.AccountDetailKey;
import com.gdie.entity.Statistics;
import com.gdie.entity.User;
import com.gdie.service.IAccountDetailService;
import com.gdie.service.IAccountService;
import com.gdie.service.IStatisticsService;
import com.gdie.service.IUserService;
import com.gdie.vo.DateScope;
import com.gdie.vo.Order;
import com.gdie.vo.PageData;
import com.gdie.vo.ResultMsg;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {
	@Autowired
	private IAccountService accountService;

	@Autowired
	private IAccountDetailService accountDetailService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IStatisticsService statisticsService;

	@RequestMapping("/getAccountList")
	@ResponseBody
	public PageData<Account> getAccountList(String pageIndex, String conditions) {
		int index = 1;
		if (!StringUtils.isEmpty(pageIndex)) {
			try {
				index = Integer.parseInt(pageIndex);
			} catch (Exception ex) {
				index = 1;
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("order", Order.desc("use_date"));
		if(!StringUtils.isEmpty(conditions)){
			map.put("like:remark", conditions);
			//map.put("like:payorname", conditions);
		}
		PageData<Account> pageData = accountService.selectAccountView(map, index);
		return pageData;
	}

	@RequestMapping(value = "/editAccount", method = RequestMethod.GET)
	public void editAccount(Model model, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getProperty("sessionInfo"));
		if (sessionInfo != null && sessionInfo.getUser() != null) { // 有会话信息
			model.addAttribute("user", sessionInfo.getUser());
		} else {
			model.addAttribute("user", new User());
		}
	}

	@RequestMapping("/getCustomer")
	@ResponseBody
	public List<User> getCustomer() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role", "2"); // 账单管理员
		return userService.query(map);
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public ResultMsg add(Account account, @RequestParam(value = "customers[]") List<String> customers, HttpSession session) {
		 logger.debug(JsonUtil.toJSONString2(account));
		  logger.debug(customers);
		 
		// 设置付费人信息
		User user = SessionInfo.getLoginUser(session);
		account.setPayor(user.getId()); // 登录人就是付费人
		return ResultMsg.getInstance(accountService.addAccount(account, customers));
	}

	@RequestMapping(value = "/getAccountById")
	@ResponseBody
	public Account getAccountById(String id) {
		Account account = accountService.findById(id);
		List<AccountDetailKey> detailKeys = accountDetailService.findByAccount(id);
		account.setDetailKeys(detailKeys);
		User user = userService.findById(account.getPayor());
		account.setPayorName(user.getName());
		return account;
	}

	@RequestMapping(value = "/modify")
	@ResponseBody
	public ResultMsg modify(Account account, @RequestParam(value = "customers[]") List<String> customers) {
		return ResultMsg.getInstance(accountService.modify(account, customers));
	}

	// 统计代码
	// -----------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/stat")
	@ResponseBody
	public PageData<Statistics> stat(DateScope dateScope) {
		PageData<Statistics> pg = accountService.getStat(dateScope);
		logger.debug(JsonUtil.toJSONString2(pg));
		return pg;
	}

	@RequestMapping(value = "/getBalancedList")
	@ResponseBody
	public PageData<Statistics> getBalancedList(String pageIndex) {
		int index = 1;
		if (!StringUtils.isEmpty(pageIndex)) {
			try {
				index = Integer.parseInt(pageIndex);
			} catch (Exception ex) {
				index = 1;
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", Order.desc("balanced_date"));
		PageData<Statistics> pageData = statisticsService.query(map, index);
		return pageData;
	}

	@RequestMapping(value = "/balanced")
	@ResponseBody
	public ResultMsg balanced(DateScope dateScope, HttpSession session) {
		// 设置付费人信息
		User user = SessionInfo.getLoginUser(session);
		if ("admin".equals(user.getAccount())) {
			accountService.balanced(dateScope);
			return ResultMsg.getInstance("");
		} else {
			return ResultMsg.getInstance("对不起，你不是管理员，不可以结算账单！");
		}
	}
}
