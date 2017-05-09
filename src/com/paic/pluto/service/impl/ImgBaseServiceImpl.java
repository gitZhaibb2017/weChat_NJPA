package com.paic.pluto.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import org.omg.CORBA_2_3.portable.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paic.ceph.service.CephUtil;
import com.paic.pluto.dao.ImageBaseDao;
import com.paic.pluto.dao.ImageUploadDao;
import com.paic.pluto.pojo.ImageBase;
import com.paic.pluto.pojo.ImageUpload;
import com.paic.pluto.pojo.UserInfo;
import com.paic.pluto.service.ImgBaseService;
import com.paic.pluto.service.UserInfoService;
import com.paic.pluto.wechat.pojo.WeChatBase;
import com.paic.pluto.wechat.service.WeChatService;
import com.paic.pluto.wechat.util.ImageHelper;

@Service
public class ImgBaseServiceImpl implements ImgBaseService {
	
	@Autowired
	private WeChatService weChatService;
	
	@Autowired
	private ImageUploadDao imageUploadDao;
	
	@Autowired
	private ImageBaseDao imageBaseDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String uploadImagesByWeChat(ImageUpload imageUpload,UserInfo userInfo,String mediaIds,String localIds) {
		
		if( null == mediaIds || "".equals(mediaIds.trim()) ||null == localIds || "".equals(localIds.trim())){
			return "fail";
		}else{
			//查询是否需要插入用户信息
			
			if (null == userInfoService.getUserInfoByOpenId(userInfo.getOpenId())){
				userInfoService.insertUserInfo(userInfo);
			}else{
				userInfoService.updateUserInfo(userInfo);
			}
			
			//插入上传信息表
			int i = imageUploadDao.insertUploadRecord(imageUpload);
			String[] mediaIdList = mediaIds.split(",");
//			String[] localIdList = localIds.split(",");
			
			
			WeChatBase weChatBase = weChatService.getWeChatBase("wx3bab3576f7554012");
			
//			for(String mediaId : mediaIdList){
			for(int j= 0 ;j< mediaIdList.length ;j++){
				ImageBase obj = new ImageBase();
				obj.setUploadId(imageUpload.getDbid());
				obj.setMediaId(mediaIdList[j]);
//				obj.setLocalId(localIdList[j]);
				//TODO：生成下载的URL
				obj.setLocalId(createURL(weChatBase.getAccessToken(),mediaIdList[j]));
				obj.setOpenId(imageUpload.getOpenId());
				imageBaseDao.insertImgBase(obj);
			}
			return "success";
		}
		
		
				
		
	}

	
	private static String createURL(String accessToken,String mediaId){
		//http://file.api.weixin.qq.com/cgi-bin/media/get?
		String requestUrl = "access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		return requestUrl;
	}
	

	@Override
	public List<ImageBase> queryNeedDownloadMeidaId(){
		return imageBaseDao.queryNeedDownloadMeidaId();
	}


	
	
	@Override
	public List<ImageBase> queryNeedUploadPic() {
		return imageBaseDao.queryNeedUploadPic();
	}


	/**
	 * 获取微信用户在指定图片类型（活动）中上传的图片总数
	 * 
	 * @param openId 微信用户openId
	 * @param type 图片类型，这里指活动
	 * @return
	 */
	@Override
	public List<ImageUpload> getMyImg(String openId,String type) {
		return imageUploadDao.getMyImg(openId,type);
	}




	/**
	 * 查询图片描述，点赞信息，当前用户是否点赞
	 * 
	 */
	@Override
	public List<ImageBase> queryImageInfoList(String openId,Integer pageNum){
		
		Integer startNum =  6 * (pageNum-1);
		
		return imageBaseDao.queryImageInfoList(openId,startNum);
	}




	@Override
	public int deleteUpload(String openId, String uploadId) {
		return imageUploadDao.deleteUpload(openId, uploadId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int transferWeChatPic2S3(ImageBase imgBase,WeChatBase weChatBase){
		
//		String imgUri = ImageHelper.downloadWeChatPic(weChatBase.getAccessToken(), imgBase.getMediaId());
//        
//		HttpURLConnection conn = ImageHelper.getHttpURLConnection(weChatBase.getAccessToken(), imgBase.getMediaId());
//		
//		InputStream input = conn.getInputStream();
//		
//		input.close();
//		
//		imgBase.setImgUri(imgUri);
//		
//		return imageBaseDao.updateImgBaseForWeChat(imgBase);
		return 0;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int downWeChatPic(ImageBase imgBase,WeChatBase weChatBase) throws IOException {
		
		String imgUri = ImageHelper.downloadWeChatPic(weChatBase.getAccessToken(), imgBase.getMediaId());
        
		imgBase.setImgUri(imgUri);
		
		return imageBaseDao.updateImgBaseForWeChat(imgBase);
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int uploadCephPic(ImageBase imageBase) {
		String s3ImgUri = CephUtil.uploadCephPic(imageBase.getImgUri());
		imageBase.setS3ImgUri(s3ImgUri);
		return imageBaseDao.updateImgBaseForCeph(imageBase);
	}


	public ImageBase queryImageBaseByPk(String dbid){
		return imageBaseDao.queryImageBaseByPk(dbid);
	}
	
	public byte[] queryS3ImageBaseByPk(String dbid) throws IOException{
		ImageBase  imgBase = imageBaseDao.queryImageBaseByPk(dbid);
		if(imgBase != null && imgBase.getS3ImgUri() != null){
			String[] str = imgBase.getS3ImgUri().split(CephUtil.FILE_SPLIT);
			return CephUtil.getContentFromS3(str[0], str[1]);
		}	
		return null;
	}
	
}
