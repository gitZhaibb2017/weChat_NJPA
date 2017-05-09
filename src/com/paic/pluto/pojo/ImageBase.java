package com.paic.pluto.pojo;

import java.util.Date;

public class ImageBase {

	private String dbid;
	
	private String uploadId;
	
	private String mediaId;
	
	private String imgUri;
	
	private String s3ImgUri;

	private String createBy;
	
	private Date createDate;
	
	private String updateBy;
	
	private Date updateDate;

	private String openId;
	
	private String picMd5;
	
	private String localId;

	public String getDbid() {
		return dbid;
	}

	public void setDbid(String dbid) {
		this.dbid = dbid;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getImgUri() {
		return imgUri;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}

	public String getS3ImgUri() {
		return s3ImgUri;
	}

	public void setS3ImgUri(String s3ImgUri) {
		this.s3ImgUri = s3ImgUri;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPicMd5() {
		return picMd5;
	}

	public void setPicMd5(String picMd5) {
		this.picMd5 = picMd5;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}
	
}
