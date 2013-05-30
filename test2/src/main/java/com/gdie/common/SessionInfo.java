package com.gdie.common;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.gdie.entity.User;

/**
 * session信息模型,保存用户登录信息
 * 
 * @author hhp
 * 
 */
public class SessionInfo implements Serializable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SessionInfo.class);

	private static final long serialVersionUID = 4210305903038776827L;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 从会话中获取登录用户对象
	 * 
	 * @param session
	 * @return
	 */
	public static User getLoginUser(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getProperty("sessionInfo"));
		if (sessionInfo != null && sessionInfo.getUser() != null) { // 有会话信息
			return sessionInfo.getUser();
		}
		logger.warn("注意：由于session中的user对象为空或者session为空，无法获取用户信息，返回的是新建的对象!");
		return new User();
	}
}
