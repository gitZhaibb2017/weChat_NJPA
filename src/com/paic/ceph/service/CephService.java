package com.paic.ceph.service;

public interface CephService {
	
	/**
	 * 获取图片数据
	 * 
	 * @return
	 */
	byte[] getImageData();
	
	/**
	 * 上传图片信息到ceph
	 * 
	 * @return
	 */
	boolean transferImageData();
}
