package com.gdie.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdie.common.ConfigUtil;
import com.gdie.common.SessionInfo;
import com.gdie.entity.History;
import com.gdie.entity.User;
import com.gdie.service.IHistoryService;
import com.gdie.service.IUserService;
import com.gdie.vo.ResultMsg;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IHistoryService historyService;

	@RequestMapping("/login")
	@ResponseBody
	public ResultMsg login(User user, HttpSession session) {
		logger.debug("name:" + user.getAccount() + ", password:" + user.getPassword());
		ResultMsg msg = new ResultMsg();
		User user2 = userService.login(user);
		History history = History.getInstance();
		if (user2 != null) {
			// 保存登录信息
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setUser(user2);
			session.setAttribute(ConfigUtil.getProperty("sessionInfo"), sessionInfo);
			msg.setSuccess(true);
			history.setDetail("成功登录系统");
			history.setOperateUser(user2.getId());
			history.setOperateUserName(user2.getName());
		} else {
			msg.setSuccess(false);
			msg.setMessage("用户名或者密码错误!");
			history.setDetail("失败登录系统:用户名或者密码错误!");
			history.setOperateUser(user.getId());
			history.setOperateUserName(user.getName());
		}
		historyService.insert(history); // 保存历史
		return msg;
	}

	/**
	 * 获取登录信息
	 * 
	 * @author hhp
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/getLoginInfo")
	@ResponseBody
	public ResultMsg getLoginInfo(HttpSession session) {
		ResultMsg msg = new ResultMsg();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getProperty("sessionInfo"));
		if (sessionInfo != null && sessionInfo.getUser() != null) { // 有会话信息
			logger.debug("使用session保持登录!");
			msg.setSuccess(true);
		}
		return msg;
	}

	@RequestMapping("/logout")
	public void logout(HttpServletResponse response, HttpSession session) throws IOException {
		if (session != null) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getProperty("sessionInfo"));
			User user = sessionInfo.getUser();
			History history = History.getInstance();
			history.setDetail("退出系统");
			history.setOperateUser(user.getId());
			history.setOperateUserName(user.getName());
			historyService.insert(history); // 保存历史
			session.invalidate(); // 让会话失效
		}

		response.sendRedirect("login.jsp");
	}

	/**
	 * 获取登录用户信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public User getUserInfo(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getProperty("sessionInfo"));
		if (sessionInfo != null) {
			return sessionInfo.getUser();
		}
		return null;
	}
}
