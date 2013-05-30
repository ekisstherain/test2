package com.gdie.dao;

import java.util.List;

import com.gdie.entity.User;

public interface UserMapper extends BaseMapper<User, String> {
	public List<User> findAll();

	public User findByAccount(User user);
}