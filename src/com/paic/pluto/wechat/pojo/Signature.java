package com.paic.pluto.wechat.pojo;

/**
 * 微信签名的相关信息
 * 
 * @author admin
 *
 */
public class Signature {

	
	private String appid;
	
	private String timestamps;
	
	private String nonceStr;
	
	private String signature;
	
	private String url;
	
	public Signature() {
		// TODO Auto-generated constructor stub
	}

	public Signature(String appid) {
		this.appid = appid;
	}



	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(String timestamps) {
		this.timestamps = timestamps;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
