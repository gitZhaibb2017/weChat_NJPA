package com.paic.pluto.wechat.service;

import com.paic.pluto.wechat.pojo.Signature;
import com.paic.pluto.wechat.pojo.WeChatBase;

public interface WeChatService {

	
	WeChatBase getWeChatBase(String appid);
	
	void updateCertificate(String appId,String appsecret);
	
	int insertSignature(Signature signature,String appsecret,String jsapiTicket);
	
	Signature getSignature(String appid,String url);
	
	
	
}
