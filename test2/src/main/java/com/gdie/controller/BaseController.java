package com.gdie.controller;

import org.apache.log4j.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 控制器父类
 * 
 * @author HHP
 * 
 */
public abstract class BaseController {
	/**
	 * Logger for this class
	 */
	protected final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 请求返回的具体地址的父类,比如请求users/listUser.do, 导航到的地址是/users/listUser.jsp文件
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String formsPage(@PathVariable String name) {
		String redirectUrl = "/" + name; // 默认导航到根目录
		RequestMapping requestMapping = (RequestMapping) this.getClass().getAnnotation(RequestMapping.class);
		if (requestMapping != null) {
			String[] values = requestMapping.value();
			if (values != null && values.length > 0 && !"/".equals(values[0])) {
				redirectUrl = values[0] + "/" + name;
			}
		}
		logger.debug("redirect location: " + redirectUrl);
		return redirectUrl;
	}
}
