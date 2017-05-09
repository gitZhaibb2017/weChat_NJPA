package com.paic.pluto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paic.pluto.dao.UserInfoDao;
import com.paic.pluto.pojo.UserInfo;
import com.paic.pluto.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public UserInfo getUserInfoByOpenId(String openId) {
		return userInfoDao.getUserInfoByOpenId(openId);
	}

	@Override
	public int insertUserInfo(UserInfo userInfo) {
		return userInfoDao.insertUserInfo(userInfo);
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		return userInfoDao.updateUserInfo(userInfo);
	}
	
}
