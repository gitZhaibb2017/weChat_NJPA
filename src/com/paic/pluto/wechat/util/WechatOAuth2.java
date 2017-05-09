package com.paic.pluto.wechat.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.paic.pluto.wechat.enums.EnumMethod;

public class WechatOAuth2 {
	
	private static final Logger LOGGER = Logger.getLogger(WechatOAuth2.class);
	
	
	private static final String get_oauth2_url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";

	/**
	 * 根据code获取成员信息
	 * 
	 * @param token
	 * @param code
	 * @param AgentID
	 * @return
	 */
	public static JsonObject getUserByCode(String token, String code, int AgentID) {
		String menuUrl = get_oauth2_url.replace("ACCESS_TOKEN", token).replace("CODE", code).replace("AGENTID", AgentID + "");
		JsonObject jo = HttpRequestUtil.httpRequest(menuUrl, EnumMethod.GET.name(), null);
		System.out.println("jo=" + jo);
		return jo;
	}
	
	
	/**
	 * 构造带员工身份信息的URL
	 *
	 * @param corpid
	 *            企业id
	 * @param redirect_uri
	 *            授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param state
	 *            重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值
	 * @return
	 */
	public static String oAuth2Url(String corpid, String redirect_uri) {
		try {
			redirect_uri = java.net.URLEncoder.encode(redirect_uri, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转码失败", e);
		}
		String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + corpid + "&redirect_uri="
				+ redirect_uri + "&response_type=code&scope=snsapi_base&state=PAWJ#wechat_redirect";
		LOGGER.info("静默授权,oauth2Url: " + oauth2Url);
		return oauth2Url;
	}

}
