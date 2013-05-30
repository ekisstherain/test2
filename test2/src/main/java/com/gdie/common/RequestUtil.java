package com.gdie.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 请求对象工具类
 * 
 * @author hhp
 * 
 */
public class RequestUtil {

	private final static Log logger = LogFactory.getLog(RequestUtil.class);

	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		if (request.getQueryString() != null) {
			requestPath += "?" + request.getQueryString();
		}
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length());// 去掉项目路径
		return requestPath;
	}

	/**
	 * 获取cookie
	 * 
	 * @author hhp
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) { // 有cookie
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {// 找到匹配的cookie
					return cookie;
				}
			}
		}
		return null;
	}
}
