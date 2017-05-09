package com.paic.pluto.service;

import java.io.IOException;
import java.util.List;

import com.paic.pluto.pojo.ImageBase;
import com.paic.pluto.pojo.ImageUpload;
import com.paic.pluto.pojo.UserInfo;
import com.paic.pluto.wechat.pojo.WeChatBase;

public interface ImgBaseService {

	String uploadImagesByWeChat(ImageUpload imageUpload,UserInfo userInfo,String mediaIds,String localIds);
	
	/**
	 * 获取微信用户在指定图片类型（活动）中上传的图片
	 * 
	 * @param openId 微信用户openId
	 * @param type 图片类型，这里指活动
	 * @return
	 */
	List<ImageUpload> getMyImg(String openId,String type);
	
	List<ImageBase> queryNeedDownloadMeidaId();
	
	List<ImageBase> queryNeedUploadPic();
	
	List<ImageBase> queryImageInfoList(String openId,Integer pageNum);
	
	int deleteUpload(String openId,String uploadId);
	
	int downWeChatPic(ImageBase imageBase,WeChatBase weChatBase) throws IOException;
	
	int uploadCephPic(ImageBase imageBase);
	
	ImageBase queryImageBaseByPk(String dbid);
	
	byte[] queryS3ImageBaseByPk(String dbid) throws IOException;
	
	int transferWeChatPic2S3(ImageBase imgBase,WeChatBase weChatBase);
}
