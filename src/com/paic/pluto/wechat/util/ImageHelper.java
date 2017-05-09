package com.paic.pluto.wechat.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageHelper {

	
	private static String BASE_IMG_DIR = "/opt/soft/images/";
//	private static String BASE_IMG_DIR = "E:\\images\\";
	
	/**
	 * 获取媒体文件
	 * 
	 * @param accessToken 接口访问凭证
	 * @param mediaId 媒体文件id
	 * @return
	 * @throws IOException 
	 */
	public static HttpURLConnection getHttpURLConnection(String accessToken,String mediaId) throws IOException{
		
//		String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		System.out.println(requestUrl);
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setDefaultUseCaches(true);
		conn.setRequestMethod("GET");
		
		return conn;
		
		//return conn.getInputStream();
		
	}
	
	/**
	 * 根据accessToken和mediaId从微信平台下载图片到固定的位置
	 * 
	 * @param accessToken
	 * @param mediaId
	 * @return
	 * @throws IOException 
	 */
	public static String downloadWeChatPic(String accessToken,String mediaId) throws IOException{
		
		String imgUri = BASE_IMG_DIR + mediaId+".jpg";
		
		HttpURLConnection conn = getHttpURLConnection(accessToken, mediaId);
		
		InputStream inputStream = conn.getInputStream();
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		
		fileOutputStream = new FileOutputStream(imgUri);
		while ((len = inputStream.read(data)) != -1) {
			fileOutputStream.write(data, 0, len);
		}
		
		fileOutputStream.close();
		inputStream.close();
		conn.disconnect();
		return imgUri;
	}
	
	
	
//	public static void main(String[] args) throws Exception {
//		
//		String accessToken ="01wS9yVKW5LJW0_OTEpUY6Dvu-2idzTJwypcpeoDC665Ssli8FVjwXi-6kyrgLVlzn6y-04zpa_C10xvkhyhSoDW9HIxd6coKADFp-Tv66ULGCdAHAFAW";
//		
//		String mediaId  ="X9eLoFUCZUdLlAg-FaNfyQpdXnTgZoZCVAqner7zmG8fQUqrSeiRkrHtNo0xDkVw";
//		
//			
//		HttpURLConnection conn = getHttpURLConnection(accessToken,mediaId);
//		
//		InputStream inputStream = conn.getInputStream();
//		byte[] data = new byte[1024];
//		int len = 0;
//		FileOutputStream fileOutputStream = null;
//		
//		fileOutputStream = new FileOutputStream("E:\\test1.jpg");
//		while ((len = inputStream.read(data)) != -1) {
//			fileOutputStream.write(data, 0, len);
//		}
//
//	}
	
	
}
