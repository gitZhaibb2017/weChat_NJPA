package com.paic.pluto.dao;

import java.util.List;

import com.paic.pluto.pojo.ImageAgree;

public interface ImageAgreeDao {

	ImageAgree queryAgree(String openId,String imgId);
	
	int insertAgree(ImageAgree imageAgree);
	
	int deleteAgree(String openId,String imgId);
	
	List<String> getTheTop10(Integer limit);
}
