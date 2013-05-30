package com.gdie.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gdie.common.JsonUtil;
import com.gdie.entity.User;
import com.gdie.service.IAccountService;
import com.gdie.service.IUserService;
import com.gdie.vo.DateScope;
import com.gdie.vo.PageData;
import com.gdie.vo.QueryResult;
import com.gdie.vo.SqlParam;

import common.util.SpringTestBase;

public class UserServiceTest extends SpringTestBase{
	/**
	 * Logger for this class
	 *//*
	private static final Logger logger = Logger.getLogger(UserServiceTest.class);

	@Autowired
	private UserService userService;*/

	/*@Test
	public void testFindAll() {
		List<User> users = userService.findAll();
		logger.debug(JSON.toJSONStringWithDateFormat(users, "yyyy-MM-dd HH:mm:ss"));
	}*/

	/*@Test
	public void testFindByAccount() {
		User u = new User();
		u.setAccount("test");
		u.setPassword("test");
		User u2 = userService.findByAccount(u);

		Assert.assertNotNull(u2);
		u.setAccount("test");
		u.setPassword("");
		u2 = userService.findByAccount(u);
		logger.debug("--------------" + u2);
		Assert.assertNull(u2);
		u.setAccount("");
		u.setPassword("test");
		u2 = userService.findByAccount(u);
		Assert.assertNull(u2);
	}*/

	/*@Test
	public void testQuery() {
		List<User> list = userService.query(SqlParam.getInstance("order by id"));
		logger.debug(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void testQuery2() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("like_name", "test");
		List<User> list = userService.query(map);
		logger.debug(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void testQuery3() {
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("like_name", "test");
		QueryResult<User> res = userService.query(map, 1, 15);
		logger.debug(JSON.toJSONStringWithDateFormat(res, "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void testQuery4() {
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("like_name", "test");
		PageData<User> pageData = userService.query(map, 1);
		logger.debug(JSON.toJSONStringWithDateFormat(pageData, "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void testFindById() {
		logger.debug(JSON.toJSONStringWithDateFormat(userService.findById("1"), "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void testCount(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("like_name", "test");
		Long total = userService.count(map);
		System.out.println(total);
	}
	
	@Autowired
	private IAccountService accountService;
	
	@Test
	public void testStat(){
		DateScope dateScope = new DateScope();
		dateScope.setEndDate(new Date());
		dateScope.setStartDate(new Date());
		
		System.out.println(JsonUtil.toJSONString2(accountService.statistics(dateScope)));
	}*/
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IUserService userService;
	
	@Test
	public void testEhcache(){
		System.out.println(userService.findById("1").getName());
		System.out.println(userService.findById("1").getName());
		System.out.println(userService.findById("1").getName());
		System.out.println(userService.findById("1").getName());
		System.out.println(userService.findById("1").getName());
		System.out.println(userService.findById("1").getName());
	}
}
