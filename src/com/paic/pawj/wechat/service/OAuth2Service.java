package com.paic.pawj.wechat.service;

import com.paic.pluto.wechat.interceptor.SessionUserInfo;

public interface OAuth2Service {
	
	public SessionUserInfo getUserInfoByOpenid(String id);

}
