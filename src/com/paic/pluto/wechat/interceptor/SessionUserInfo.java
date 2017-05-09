package com.paic.pluto.wechat.interceptor;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SessionUserInfo {
	public static final String SESSION_ID = "userInfo";
	public static final String OPENID = "openid";

	private String userId;

	private String userName;

}
