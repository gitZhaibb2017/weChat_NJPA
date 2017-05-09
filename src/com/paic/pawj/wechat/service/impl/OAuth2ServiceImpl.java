package com.paic.pawj.wechat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.paic.pawj.wechat.dao.WechatUserMapper;
import com.paic.pawj.wechat.service.OAuth2Service;
import com.paic.pluto.wechat.interceptor.SessionUserInfo;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {

	private static final String WX_OPENID_PIX = "wechat.openid.";
	
//	@Resource
//	private WechatUserMapper wechatUserDAO;
	
	
	@Override
	public SessionUserInfo getUserInfoByOpenid(String id) {
		SessionUserInfo userInfo = null;
		return userInfo;
	}

}
