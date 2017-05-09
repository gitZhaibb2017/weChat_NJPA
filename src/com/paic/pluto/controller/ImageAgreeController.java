package com.paic.pluto.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paic.pluto.service.ImageAgreeService;

@Controller
public class ImageAgreeController {

	@Autowired
	public ImageAgreeService imageAgreeService;
	
	/**
	 * 插入点赞数据
	 * 
	 * @param imageAgree
	 * @return
	 */
	@RequestMapping(value="/updateAgree",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public int insertAgree(@RequestParam String openId,@RequestParam String imgId){
		return imageAgreeService.updateAgree(openId,imgId);
	}
	
	
	/**
	 * 获取前N条数据
	 * 
	 */
	@RequestMapping(value="/getOrder/{limit}",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public List<Map<String,String>> getTheTop10(@PathVariable(value="limit")Integer limit){
		return imageAgreeService.getTheTop10(limit);
	}
	
	
}
