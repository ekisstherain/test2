package com.gdie.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class StartupContextLoaderListener extends ContextLoaderListener implements ServletContextListener {
	protected static final Log logger = LogFactory.getLog(StartupContextLoaderListener.class);

	/*
	 * @author chenchunrong
	 * 
	 * @createTime 2012-9-7 下午03:41:05
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("initializing context...");
		}

		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		setupContext(context);
	}

	public static void setupContext(ServletContext context) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

		if (logger.isDebugEnabled()) {
			logger.debug("Initialization ...");
		}

		String vcapJson = java.lang.System.getenv("VCAP_SERVICES");
		// JSON.parse(text)
		logger.debug(vcapJson);

		//Vcap vcap = JSON.parseObject(input, clazz, config, featureValues, features)
		//JSONObject object = JSON.parse
		//logger.debug("vcap:" + JSON.toJSONString(vcap));
	}
}
