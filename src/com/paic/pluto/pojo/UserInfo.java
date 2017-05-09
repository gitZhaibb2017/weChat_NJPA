package com.paic.pluto.pojo;

public class UserInfo {
	
	private Integer dbid;
	
	private String openId;
	
	private String phoneNo;
	
	private String showName;
	
	private String type;

	public UserInfo() {
	}

	public UserInfo(Integer dbid) {
		super();
		this.dbid = dbid;
	}
	
	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
