package com.paic.pluto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paic.pluto.pojo.UserInfo;
import com.paic.pluto.service.UserInfoService;

@Controller
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	
	@RequestMapping(value="/getUserInfoByOpenId",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public UserInfo getUserInfoByOpenId(@RequestParam String openId){
		
		UserInfo userInfo = userInfoService.getUserInfoByOpenId(openId);
		if(null ==userInfo){
			userInfo = new UserInfo(-1);
		}
		return userInfo;
	}
}
