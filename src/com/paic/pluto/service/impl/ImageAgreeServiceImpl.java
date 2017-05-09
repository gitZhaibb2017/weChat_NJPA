package com.paic.pluto.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paic.pluto.dao.ImageAgreeDao;
import com.paic.pluto.pojo.ImageAgree;
import com.paic.pluto.service.ImageAgreeService;


@Service
public class ImageAgreeServiceImpl implements ImageAgreeService{

	
	@Autowired
	private ImageAgreeDao imageAgreeDao;
	
	
	/**
	 * 记录点赞数据
	 * 
	 * @param imageAgree
	 * @return 
	 * 
	 */
	@Override
	public int updateAgree(String openId,String imgId) {
		
		//获取是否存在
		ImageAgree imageAgree = imageAgreeDao.queryAgree(openId,imgId);
		if(null ==  imageAgree){
			imageAgree = new ImageAgree();
			imageAgree.setOpenId(openId);
			imageAgree.setImgId(imgId);
			return imageAgreeDao.insertAgree(imageAgree);
		}else{
			return imageAgreeDao.deleteAgree(openId,imgId);
		}
		
	}


	@Override
	public List<Map<String,String>> getTheTop10(Integer limit) {
		
	   //根据传入的limit获取记录数
	   //目前限定最大10条
		if( null == limit || limit >10){
			limit = 10;
		}
		
		List<Map<String,String>> names = new ArrayList<Map<String,String>>();
		
		
		List<String> nameList = imageAgreeDao.getTheTop10(limit);
		
		
		for(String name : nameList){
			Map<String, String> nameMap = new HashMap<String, String>();
			nameMap.put("name", name);
			names.add(nameMap);
		}
		
		return names;
	}
	
	
	
}
