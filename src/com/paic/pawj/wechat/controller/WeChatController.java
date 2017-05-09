package com.paic.pawj.wechat.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paic.pawj.wechat.interceptor.SessionUserInfo;

//@Controller
public class WeChatController {

	private static final Logger LOGGER = Logger
			.getLogger(WeChatController.class);
	
	
	@RequestMapping("/wechat/index.do")
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		LOGGER.info(request.getSession().getAttribute(SessionUserInfo.SESSION_ID));
		return "wechat/index";
		//request.getRequestDispatcher("/pawj/lottery/index.html").forward(request, response);
	}
}
