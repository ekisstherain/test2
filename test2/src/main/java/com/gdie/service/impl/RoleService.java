package com.gdie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdie.dao.RoleMapper;
import com.gdie.entity.Role;
import com.gdie.service.IRoleService;

@Service
public class RoleService extends BaseService<Role, String> implements IRoleService {
	private RoleMapper roleMapper;

	@Autowired
	public void setUserMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
		this.mapper = roleMapper;
	}
}
