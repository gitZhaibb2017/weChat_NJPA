package com.paic.pluto.dao;

import com.paic.pluto.pojo.UserInfo;

public interface UserInfoDao {
	
	int insertUserInfo(UserInfo userInfo);

	UserInfo getUserInfoByOpenId(String openId);
	
	int updateUserInfo(UserInfo userInfo);
}
