package com.paic.pluto.dao;

import java.util.List;

import com.paic.pluto.pojo.ImageUpload;

public interface ImageUploadDao {

	int insertUploadRecord(ImageUpload imageUpload);
	
	List<ImageUpload> getMyImg(String openId,String type);
	
	int deleteUpload(String openId, String uploadId);
}
