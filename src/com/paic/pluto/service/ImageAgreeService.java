/*
 * 
 */
package com.paic.pluto.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author admin
 *
 */
public interface ImageAgreeService {
	
	int updateAgree(String openId,String imgId);
	
	List<Map<String,String>> getTheTop10(Integer limit);
}
