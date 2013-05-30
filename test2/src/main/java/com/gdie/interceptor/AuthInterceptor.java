package com.gdie.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gdie.common.ConfigUtil;
import com.gdie.common.RequestUtil;
import com.gdie.common.SessionInfo;
import com.gdie.service.IUserService;
import com.gdie.vo.ResultMsg;

/**
 * 权限拦截器
 * 
 * @author hhp
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

	@Autowired
	private IUserService userService;

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 读取配置的模式，根据模式设置是否拦截地址
		if ("develop".equals(ConfigUtil.getProperty("currentRumtimeMode"))) {
			logger.warn("注意：当前权限拦截运行在 develop 模式下!!!");
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getProperty("sessionInfo"));
			if (sessionInfo == null) {
				sessionInfo = new SessionInfo();
				sessionInfo.setUser(userService.findById("1"));
				request.getSession().setAttribute(ConfigUtil.getProperty("sessionInfo"), sessionInfo);
			}
		} else { // 发布模式
			String requestPath = RequestUtil.getRequestPath(request);// 用户访问的资源地址
			if ("/login.do".equals(requestPath)) { // 登录验证地址不过滤
				// 验证是否已经登录了
				SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getProperty("sessionInfo"));
				if (sessionInfo == null) {
					return true;
				} else {
					response.sendRedirect(request.getContextPath() + "/index.do");
				}
				return false;
			}
			if ("/getLoginInfo.do".equals(requestPath)) { // 获取登录信息不拦截
				return true;
			}

			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getProperty("sessionInfo"));
			if (sessionInfo == null) {// 没有登录系统，或登录超时
				logger.debug("权限拦截地址：" + requestPath);
				ResultMsg msg = new ResultMsg();
				msg.setSuccess(false);
				msg.setMessage("您没有登录或登录超时，请重新登录！");
				request.setAttribute("loginMsg", msg);
				forward(request, response);
				return false;
			}
		}
		return true; // 其他页面都放行
	}

	/**
	 * 页面跳转
	 * 
	 * @author hhp
	 * 
	 * @param msg
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestPath = RequestUtil.getRequestPath(request);// 用户访问的资源地址
		if (requestPath.contains("/phone")) { // 手机端登录
			response.sendRedirect(request.getContextPath() + "/phoneLogin.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

}
