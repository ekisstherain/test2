package com.gdie.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdie.common.ConfigUtil;
import com.gdie.common.SessionInfo;
import com.gdie.entity.History;
import com.gdie.entity.User;
import com.gdie.service.IHistoryService;

/**
 * 手机端控制器
 * 
 * @author HHP
 * 
 */
@Controller
@RequestMapping("/phone")
public class PhoneController extends BaseController {

	@Autowired
	private IHistoryService historyService;

	@RequestMapping(value = "/editAccount", method = RequestMethod.GET)
	public void editAccount(Model model, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getProperty("sessionInfo"));
		if (sessionInfo != null && sessionInfo.getUser() != null) { // 有会话信息
			model.addAttribute("user", sessionInfo.getUser());
		} else {
			model.addAttribute("user", new User());
		}
	}

	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
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

		response.sendRedirect(request.getContextPath() + "/phoneLogin.jsp");
	}
}
