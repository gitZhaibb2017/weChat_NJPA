package com.paic.pluto.wechat.interceptor;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class OAuth2Interceptor implements HandlerInterceptor {

	private static final Logger LOGGER = Logger
			.getLogger(HandlerInterceptor.class);
	
	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		// 先判断是否有注解
		Object objUid = session.getAttribute(SessionUserInfo.SESSION_ID);
		if (objUid == null) {
			String resultUrl = request.getRequestURL().toString();
			LOGGER.info("[OAuth2Interceptor]resultUrl: " + resultUrl);
			String param = request.getQueryString();
			if (param != null) {
				resultUrl += "?" + param;
			}
			try {
				resultUrl = java.net.URLEncoder.encode(resultUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("转码失败", e);
			}
			// 请求的路径
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/wechat/oauth2.do");
			return false;
		}
		return true;
	}

}