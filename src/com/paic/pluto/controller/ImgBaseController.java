/*
 * 
 */
package com.paic.pluto.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paic.pluto.pojo.ImageBase;
import com.paic.pluto.pojo.ImageUpload;
import com.paic.pluto.pojo.UserInfo;
import com.paic.pluto.service.ImgBaseService;

/**
 * 
 * @author zhaibb
 *
 */
@Controller
public class ImgBaseController {
	
	@Autowired
	private ImgBaseService imgBaseService;
	
	
	/**
	 * 获取指定图片类型（活动）下微信用户上传的图片
	 * 
	 * @param type 图片类型，这里指活动
	 * @return
	 */
	@RequestMapping(value="/getMyImg/{openId}/{type}",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public List<ImageUpload> getMyImg(@PathVariable(value="openId")String openId,@PathVariable(value="type")String type,HttpServletRequest request){
		
		String wxOpenId = request.getParameter("code");
		
		if(null != wxOpenId && !"".equals(wxOpenId) ){
			List<ImageUpload> list = new ArrayList<ImageUpload>();
			list.add(new ImageUpload(wxOpenId));
			return list;
		}
		
		return imgBaseService.getMyImg(openId, type);
	}
	
	/**
	 * 记录上传的微信图片
	 * 
	 * @param openId
	 * @param imageUpload
	 * @param userInfo
	 * @param mediaIds
	 * @param localIds
	 * @return
	 */
	@RequestMapping(value="/imgUploadByWeChat/{openId}",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public Map<String,String> uploadImagesByWeChat(@PathVariable(value="openId")String openId,@ModelAttribute ImageUpload imageUpload,
			@ModelAttribute UserInfo userInfo,@RequestParam String mediaIds,@RequestParam String localIds){
		imageUpload.setOpenId(openId);
		Map<String,String> maps = new HashMap<String,String>();
		maps.put("imageUpload", imgBaseService.uploadImagesByWeChat(imageUpload,userInfo,mediaIds,localIds));
		return maps;
	}
	
	/**
	 * 获取评选图片列表
	 * @return 
	 */
	@RequestMapping(value="/imgList/{openId}",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public List<ImageBase> getImageInfoList(@PathVariable(value="openId") String openId,@RequestParam Integer pageNum){
		return imgBaseService.queryImageInfoList(openId,pageNum);
	}
	
	
	/**
	 * 获取评选图片对象
	 * @throws IOException 
	 */
	@RequestMapping("/img/{imgId}")
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public ResponseEntity<byte[]> getImageObject(@PathVariable(value="imgId")String imgId) throws IOException{
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		byte[] imageData = imgBaseService.queryS3ImageBaseByPk(imgId);
//		ImageBase imgBase = imgBaseService.queryImageBaseByPk(imgId);
//		byte[] imageData = null;
//		if(imgBase != null && imgBase.getImgUri() != null){
//			imageData = image2byte(imgBase.getImgUri());
//		}		
		return new ResponseEntity<byte[]>(imageData,headers,HttpStatus.OK);
		
	}
	
	/**
	 * 根据登录用户和上传记录dbid删除记录
	 * 
	 * @param openId
	 * @param uploadId
	 * @return
	 */
	@RequestMapping(value="/delUpload/{openId}/{uploadId}",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	@CrossOrigin(origins="http://pingan.tonghc.cn")
	public int deleteUpload(@PathVariable(value="openId")String openId,@PathVariable(value="uploadId")String uploadId){
		return imgBaseService.deleteUpload(openId, uploadId);
	}
	
	/**
	 * 把图片转换为byte[]
	 * 
	 * @return
	 */
	private byte[] image2byte(String path){
		
		byte[] data = null;
		
		FileImageInputStream input = null;
		
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream  outPut = new ByteArrayOutputStream();
			byte[] buf  =new byte[1024];
			int num = 0;
			
			while( (num = input.read(buf) )!= -1 ){
				outPut.write(buf,0,num);
			}
			data = outPut.toByteArray();
			outPut.close();
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	
}
