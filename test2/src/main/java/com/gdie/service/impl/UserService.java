package com.gdie.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.gdie.common.EncryptUtil;
import com.gdie.dao.UserMapper;
import com.gdie.entity.User;
import com.gdie.service.IUserService;

@Service
public class UserService extends BaseService<User, String> implements IUserService {

	private UserMapper userMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
		this.mapper = userMapper;
	}

	public List<User> findAll() {
		return userMapper.findAll();
	}

	@Override
	public User getUserById(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User findByAccount(User user) {
		return userMapper.findByAccount(user);
	}

	// 业务逻辑-----------------------------------------------------------------------------------------

	@Override
	public User login(User user) {
		if (user == null || StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())) {
			return null;
		}
		user.setPassword(EncryptUtil.sha(user.getPassword())); // 加密密码
		return findByAccount(user);
	}

	public String add(User user) {
		if (StringUtils.isEmpty(user.getName())) {
			return "姓名为空!";
		}
		if (StringUtils.isEmpty(user.getAccount())) {
			return "账号为空!";
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			return "密码为空!";
		}
		if (!user.getPassword().equals(user.getPassword2())) {
			return "两次密码不一致!";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", user.getName());
		if (query(map).size() > 0) { // 同名查询
			return "用户名: " + user.getName() + " 已经被使用!";
		}

		map.clear();
		map.put("account", user.getAccount());
		if (query(map).size() > 0) { // 帐号查询
			return "帐号： " + user.getAccount() + " 已经被使用!";
		}

		user.init();
		user.setPassword(EncryptUtil.sha(user.getPassword())); // 加密密码
		insert(user); // 添加用户
		return "";
	}
}
