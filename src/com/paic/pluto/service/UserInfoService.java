package com.paic.pluto.service;

import com.paic.pluto.pojo.UserInfo;

public interface UserInfoService {

	
	int insertUserInfo(UserInfo userInfo);
	
	UserInfo getUserInfoByOpenId(String openId); 
	
	int updateUserInfo(UserInfo userInfo);
}
