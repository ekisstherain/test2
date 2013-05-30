package com.gdie.service;

import com.gdie.dao.UserMapper;
import com.gdie.entity.User;

public interface IUserService extends IBaseService<User, String>, UserMapper {
	public User getUserById(String id);

	public User login(User user);
	
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public String add(User user);
}
