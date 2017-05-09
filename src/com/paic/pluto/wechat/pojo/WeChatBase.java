package com.paic.pluto.wechat.pojo;

/**
 * 保存accessToken 和 jsapiTicket
 * 
 * @author admin
 *
 */
public class WeChatBase {

	private String appid;
	
	private String secret;
	
	private String accessToken;
	
	private String jsapiTicket;
	
	

	public WeChatBase() {
		super();
	}

	public WeChatBase(String appid, String secret, String accessToken, String jsapiTicket) {
		super();
		this.appid = appid;
		this.secret = secret;
		this.accessToken = accessToken;
		this.jsapiTicket = jsapiTicket;
	}



	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}
	
}
