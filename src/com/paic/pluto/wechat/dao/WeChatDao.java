package com.paic.pluto.wechat.dao;

import java.util.List;

import com.paic.pluto.wechat.pojo.Signature;
import com.paic.pluto.wechat.pojo.WeChatBase;

public interface WeChatDao {
	
	
	int deleteAccessTokenAndTicketByAppId(String appid);
	
	WeChatBase getAccessTokenAndTicket(String appid);
	
	int insertAccessTokenAndTicket(WeChatBase weChatBase);
	
	int deleteSignatureByAppId(String appId);
	
	List<Signature> queryUrlForSignature(String appid);
	
	int insertSignature(Signature signature);
	
	Signature getSignature(String appid,String url);
}
