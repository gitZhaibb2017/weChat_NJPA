package com.paic.pluto.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.paic.pawj.wechat.pojo.AccessToken;
import com.paic.pluto.wechat.enums.EnumMethod;

/**
 * 公众平台通用接口工具类
 * 
 */
public class WechatAccessToken {
	// 获取微信公众号：access_token的接口地址（GET） 限2000（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 获取企业号access_token
	public final static String company_access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret, int type) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		if (type == 1) {
			requestUrl = company_access_token_url.replace("CORPID", appid).replace("CORPSECRET", appsecret);
			System.err.println(requestUrl);
		}
		JsonObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		if(jsonObject==null){
			jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		}
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.get("access_token").getAsString());
				accessToken.setExpiresIn(jsonObject.get("expires_in").getAsInt());
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
			}
		}
		return accessToken;
	}
	
	/**
	 * 获取jsapi_ticket
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getTicket(AccessToken accessToken) {
	    String ticket = null;
	    String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ accessToken.getToken() +"&type=jsapi";//这个url链接和参数不能变
	    JsonObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
	    ticket = jsonObject.get("ticket").getAsString();
	    return ticket;
	}
	

	/**
	 * 
	 * @param decript
	 * @return
	 */
	public static String SHA1(String decript) {
	    try {
	        MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
	        digest.update(decript.getBytes());
	        byte messageDigest[] = digest.digest();
	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        // 字节数组转换为 十六进制 数
	            for (int i = 0; i < messageDigest.length; i++) {
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
	                if (shaHex.length() < 2) {
	                    hexString.append(0);
	                }
	                hexString.append(shaHex);
	            }
	            return hexString.toString();
	 
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return "";
	}
	
	
	public static void main(String[] args) {
		
		AccessToken acToken = getAccessToken("wx3bab3576f7554012","13559ac5875dd465814cb9350460da5e",0);
		//1、获取AccessToken
		//String accessToken = acToken.getToken();
		
		//System.out.println(accessToken);
		
//         String accessToken ="wUQkbZ-XsppPbgclqOIIEoUST4mVIJ2K-kQPJ41KjRUBn1pxYQGh2wbsf3KNsjM5YKUixuo1GnJ38mFJvyZToIvxcXyKXT-R1qtKIjwAEcx-zwtAYnkgXXfMWVQrKsStIWGgAEAZNX";
		
//         System.out.println(accessToken.length()); //138
         
		//2、获取Ticket
		String jsapi_ticket = getTicket(acToken);
		
		System.out.println("kgt8ON7yVITDhtdwci0qeXfJv7aRyDDSI8D010qwuFft6kCD0oM3yjC-byWzZedfSh1jPb1rBKfIVfgW5-wNSg".length());  //86
		
		//3、时间戳和随机字符串
	//	String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
	//    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
	    
	//    System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);
	    
	    //4、获取url
	//    String url="http://www.luiyang.com/add.html";
	    /*根据JSSDK上面的规则进行计算，这里比较简单，我就手动写啦
	    String[] ArrTmp = {"jsapi_ticket","timestamp","nonce","url"};
	    Arrays.sort(ArrTmp);
	    StringBuffer sf = new StringBuffer();
	    for(int i=0;i<ArrTmp.length;i++){
	    	sf.append(ArrTmp[i]);
	    }
	    */
	    
	    //5、将参数排序并拼接字符串
	 //   String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
	   
	    //6、将字符串进行sha1加密
	 //   String signature =SHA1(str);
	  //  System.out.println("参数："+str+"\n签名："+signature);
	}

	
	


}