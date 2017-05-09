package com.paic.pluto.pojo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ImageUpload {

	private String dbid;
	
	private String openId;
	
	private String photoDate;
	
	private Date createDate;
	
	private String description;
	
	private String isDelete;

	private List<ImageBase> imageBases;
	
	
	public ImageUpload() {
		super();
	}

	public ImageUpload(String dbid) {
		super();
		this.dbid = dbid;
	}

	public String getDbid() {
		return dbid;
	}

	public void setDbid(String dbid) {
		this.dbid = dbid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhotoDate() {
		return photoDate;
	}

	public void setPhotoDate(String photoDate) {
		this.photoDate = photoDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public List<ImageBase> getImageBases() {
		return imageBases;
	}

	public void setImageBases(List<ImageBase> imageBases) {
		this.imageBases = imageBases;
	}
	
}
