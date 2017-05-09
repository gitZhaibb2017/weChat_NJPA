package com.paic.pawj.wechat.util;

import com.google.gson.JsonObject;
import com.paic.pawj.wechat.enums.EnumMethod;

public class WechatOAuth2 {
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

}
