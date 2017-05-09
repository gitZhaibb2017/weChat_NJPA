package com.paic.pluto.dao;

import java.util.List;

import com.paic.pluto.pojo.ImageBase;

public interface ImageBaseDao {
	
	/**
	 * 插入图片记录
	 * 
	 * @param imageBase
	 * @return
	 */
	int insertImgBase(ImageBase imageBase);
	
	/**
	 * 获取微信用户在指定图片类型（活动）中上传的图片总数
	 * 
	 * @param openId 微信用户openId
	 * @param type 图片类型，这里指活动
	 * @return
	 */
	Integer countMyImg(String openId,String type);
	
	/**
	 * 查询需要从微信下载到本地的图片MeidaId
	 * 
	 * @return
	 */
	List<ImageBase> queryNeedDownloadMeidaId(); 
	
	/**
	 * 查询需要从上传到CEPH发本地图片
	 * 
	 * @return
	 */
	List<ImageBase> queryNeedUploadPic();
	
	/**
	 * 
	 * 
	 * @param openId
	 * @param startNum
	 * @return
	 */
	List<ImageBase> queryImageInfoList(String openId,Integer startNum); 
	
	/**
	 * 从微信下载后更新ImageBase的imgUri
	 * 
	 * @param imgBase
	 * @return
	 */
	int updateImgBaseForWeChat(ImageBase imgBase);
	
	/**
	 * 上传图片到ceph后更新ImageBase的imgUri
	 * 
	 * @param imgBase
	 * @return
	 */
	int updateImgBaseForCeph(ImageBase imgBase);
	
	/**
	 * 根据主键查询图片
	 * 
	 * @param dbid
	 * @return
	 */
	ImageBase queryImageBaseByPk(String dbid);
	
}
