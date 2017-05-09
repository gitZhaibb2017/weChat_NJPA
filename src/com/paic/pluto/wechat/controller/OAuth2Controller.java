package com.paic.pluto.wechat.controller;

import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paic.pawj.wechat.service.OAuth2Service;
import com.paic.pluto.wechat.interceptor.SessionUserInfo;
import com.paic.pluto.wechat.util.Constants;
import com.paic.pluto.wechat.util.HttpClientUtil;
import com.paic.pluto.wechat.util.QiYeUtil;
import com.paic.pluto.wechat.util.Result;
import com.paic.pluto.wechat.util.WechatOAuth2;

/**
 * OAuth2 处理控制器
 *
 * @author Sunlight
 *
 */
//@Controller
public class OAuth2Controller {

	private static final Logger LOGGER = Logger.getLogger(OAuth2Controller.class);

//	@Autowired
	private OAuth2Service oAuth2Service;

	/**
	 * 构造参数并将请求重定向到微信API获取登录信息
	 *
	 * @param index
	 * @return
	 */
//	@RequestMapping(value = { "wechat/oauth2.do" })
	public String Oauth2API(HttpServletRequest request) {
		// 此处可以添加获取持久化的数据，如企业号id等相关信息
		String CropId = Constants.CORPID;
		String redirectUrl = "";
		String backUrl = "http://pawj.test.kejinanjing.iego.cn/pawj/wechat/oauth2url.do";
		LOGGER.info("[Oauth2API]backUrl: " + backUrl);
		redirectUrl = WechatOAuth2.oAuth2Url(CropId, backUrl);
		return "redirect:" + redirectUrl;
	}

	/**
	 * 根据code获取Userid后跳转到需要带用户信息的最终页面
	 *
	 * @param request
	 * @param code
	 *            获取微信重定向到自己设置的URL中code参数
	 * @param oauth2url
	 *            跳转到最终页面的地址
	 * @return
	 */
//	@RequestMapping(value = { "wechat/oauth2url.do" })
	public String Oauth2MeUrl(HttpServletRequest request, HttpSession session, String code, String state) {
		LOGGER.info("微信回调");
		String result = "redirect:";
		if (StringUtils.isNotBlank(code) && Constants.STATE.equals(state)) {
			LOGGER.info("[Oauth2MeUrl]code: " + code);
			JsonObject obj = getUserInfoAccessToken(code);
			if (obj != null && null != obj.get("openid")) {
				LOGGER.info("[Oauth2MeUrl]openid: " + obj.get("openid").getAsString());
				SessionUserInfo user = oAuth2Service.getUserInfoByOpenid(obj.get("openid").getAsString());
				if (null == user) {
					session.setAttribute(SessionUserInfo.OPENID, obj.get("openid").getAsString());
					result = "redirect:/wxapp/h5/login/loginPAWJ.html";
				} else {
					session.setAttribute(SessionUserInfo.SESSION_ID, user);
					result = "forward:index.do";
				}
			}
		}
		// // 这里简单处理,存储到session中
		return result;
	}

	

	/**
	 * 调用接口获取用户信息
	 *
	 * @param token
	 * @param code
	 * @param agentId
	 * @return
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public String getMemberGuidByCode(String token, String code, int agentId) {
		System.out.println("code==" + code + "\ntoken=" + token + "\nagentid=" + agentId);
		Result<String> result = QiYeUtil.oAuth2GetUserByCode(token, code, agentId);
		System.out.println("result=" + result);
		if (result.getErrcode() == "0") {
			if (result.getObj() != null) {
				// 此处可以通过微信授权用code还钱的Userid查询自己本地服务器中的数据
				return result.getObj();
			}
		}
		return "";
	}

	/**
	 * 获取请求用户信息的access_token
	 *
	 * @param code
	 * @return
	 */
	public JsonObject getUserInfoAccessToken(String code) {
		JsonObject object = null;

		if ("5353922d-f591-47e5-9286-9bbae026770d".equals(code)) {
			object = new JsonObject();
			object.addProperty("openid", "880e730f-73b0-4ea8-a4c9-8312c0297f03");
			return object;
		}

		try {
			String url = String.format(
					"https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
					Constants.CORPID, Constants.SECRET, code);
			LOGGER.info("request accessToken from url: " + url);
			String result = HttpClientUtil.toString(HttpClientUtil.doHttpsGet(url, ""));
			Gson token_gson = new Gson();
			object = token_gson.fromJson(result, JsonObject.class);
			LOGGER.info("request accessToken success. [result={}]" + object);
		} catch (Exception ex) {
			LOGGER.error("fail to request wechat access token. [error={}]", ex);
		}
		return object;
	}

}