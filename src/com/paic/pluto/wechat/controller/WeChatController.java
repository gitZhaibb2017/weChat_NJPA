package com.paic.pluto.wechat.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paic.pawj.wechat.pojo.AccessToken;
import com.paic.pluto.wechat.pojo.Signature;
import com.paic.pluto.wechat.service.WeChatService;
import com.paic.pluto.wechat.util.WechatAccessToken;

@Controller
public class WeChatController {

	private static final Logger LOGGER = Logger
			.getLogger(WeChatController.class);
	
	@Autowired
	private WeChatService weChatService;
	
//	@RequestMapping("/wechat/index.do")
//	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//	{
//		LOGGER.info(request.getSession().getAttribute(SessionUserInfo.SESSION_ID));
//		return "wechat/index";
//		//request.getRequestDispatcher("/pawj/lottery/index.html").forward(request, response);
//	}
//	@RequestMapping("/getAccessToken")
//	@ResponseBody
//	public AccessToken getAccessToken(@RequestParam String appid,@RequestParam String appsecret){
//		return WechatAccessToken.getAccessToken(appid, appsecret, 0);
//	}
	
//	@RequestMapping("/getTicket")
//	@ResponseBody
//	public String getTicket(@ModelAttribute AccessToken accessToken){
//		return WechatAccessToken.getTicket(accessToken);
//	}
	
	
	//获取签名
	@RequestMapping(value="/getSignature",method={RequestMethod.POST,RequestMethod.GET}) //,produces = "application/json; charset=utf-8"
	@ResponseBody
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public Signature getSignature(@RequestParam String appid,@RequestParam String url){
		
		appid="wx3bab3576f7554012";
		url = "http://pingan.tonghc.cn/index.html";
		
		return weChatService.getSignature(appid,url);
	}
	
	
	
	
}
