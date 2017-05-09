package com.paic.pluto.wechat.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paic.pawj.wechat.pojo.AccessToken;
import com.paic.pluto.wechat.dao.WeChatDao;
import com.paic.pluto.wechat.pojo.Signature;
import com.paic.pluto.wechat.pojo.WeChatBase;
import com.paic.pluto.wechat.service.WeChatService;
import com.paic.pluto.wechat.util.WechatAccessToken;

@Service
public class WeChatServiceImpl implements WeChatService{

	@Autowired
	private WeChatDao weChatDao;
	
	@Override
	public WeChatBase getWeChatBase(String appid) {
		// TODO Auto-generated method stub
		return weChatDao.getAccessTokenAndTicket(appid);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateCertificate(String appid,String appsecret) {
		
		//删除access_Token and jsapi_ticket
		weChatDao.deleteAccessTokenAndTicketByAppId(appid);
		
		
		//删除signature
		weChatDao.deleteSignatureByAppId(appid);
		
		
		//生成access_Token and jsapi_ticket
		WeChatBase weChatBase = createAccessTokenAndTicket(appid, appsecret);
		weChatDao.insertAccessTokenAndTicket(weChatBase);
		
		//按照appid下的url
		List<Signature>  signatures = weChatDao.queryUrlForSignature(appid);
		
		//按照url生成signature
		for(Signature obj : signatures){
			insertSignature(obj,appsecret,weChatBase.getJsapiTicket());
		}
	}

	private WeChatBase createAccessTokenAndTicket(String appid,String appsecret){
		
		AccessToken acToken = WechatAccessToken.getAccessToken(appid,appsecret,0);
		//1、获取AccessToken
		String accessToken = acToken.getToken();
		
		//2、获取Ticket
		String jsapi_ticket = WechatAccessToken.getTicket(acToken);
		
		//生成accessToken and jsapiTicket
		return new WeChatBase(appid,appsecret,accessToken,jsapi_ticket);

	}
	
     

	@Override
	public int insertSignature(Signature signature,String appsecret,String jsapiTicket) {
		
		String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
	    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
	    
	    signature.setNonceStr(noncestr);
	    signature.setTimestamps(timestamp);
	    
		
		String str = "jsapi_ticket="+jsapiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+signature.getUrl();
		String signatureStr =WechatAccessToken.SHA1(str);
		signature.setSignature(signatureStr);
		
		return weChatDao.insertSignature(signature);
	}




	@Override
	public Signature getSignature(String appid, String url) {
		Signature signature = weChatDao.getSignature(appid, url);
		if( signature == null ){
			signature = new Signature("-1");
		}
		return signature;
	}

}
