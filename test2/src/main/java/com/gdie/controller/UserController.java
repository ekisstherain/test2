package com.gdie.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdie.entity.Role;
import com.gdie.entity.User;
import com.gdie.service.IRoleService;
import com.gdie.service.IUserService;
import com.gdie.vo.Order;
import com.gdie.vo.PageData;
import com.gdie.vo.ResultMsg;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@RequestMapping("/getUserList")
	@ResponseBody
	public PageData<User> getUserList(String pageIndex) {
		int index = 1;
		if (!StringUtils.isEmpty(pageIndex)) {
			try {
				index = Integer.parseInt(pageIndex);
			} catch (Exception ex) {
				index = 1;
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", Order.desc("createdate"));
		PageData<User> pageData = userService.query(map, index);
		return pageData;
	}

	@RequestMapping("/addUsers")
	@ResponseBody
	public ResultMsg addUsers(User user) {
		ResultMsg resultMsg = new ResultMsg();
		String res = userService.add(user);
		if (StringUtils.isEmpty(res)) {
			resultMsg.setSuccess(true);
		} else {
			resultMsg.setMessage(res);
		}
		return resultMsg;
	}

	@RequestMapping("/getUserById")
	@ResponseBody
	public User getUserById(String id) {
		return userService.findById(id);
	}

	@RequestMapping("/modifyUser")
	@ResponseBody
	public Boolean modifyUser(User user) {
		User oldUser = userService.findById(user.getId());
		oldUser.setName(user.getName());
		oldUser.setRole(user.getRole() == null ? "" : user.getRole());
		oldUser.setLastmodifydate(new Date().toLocaleString());
		userService.updateByPrimaryKeySelective(oldUser);
		return true;
	}

	@RequestMapping("/getRoleList")
	@ResponseBody
	public List<Role> getRoleList() {
		Map<String, Object> map = new HashMap<String, Object>();
		return roleService.query(map);
	}
}
