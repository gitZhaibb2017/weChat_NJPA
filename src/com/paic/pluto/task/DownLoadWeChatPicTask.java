package com.paic.pluto.task;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.paic.pluto.pojo.ImageBase;
import com.paic.pluto.service.ImgBaseService;
import com.paic.pluto.wechat.pojo.WeChatBase;
import com.paic.pluto.wechat.service.WeChatService;

@Component
public class DownLoadWeChatPicTask {
	
	private static final Logger LOGGER = Logger.getLogger(DownLoadWeChatPicTask.class);
	
	private static String appid= "wx3bab3576f7554012";
	
	@Autowired
	private WeChatService weChatService;
	
	@Autowired
	private ImgBaseService imgBaseService;

	
	@Scheduled(cron = "0/10 * * * * ?")//每天凌晨两点执行
    public void doSomethingWith() throws IOException{
		LOGGER.info("定时下载图片任务开始......");
        long begin = System.currentTimeMillis();
        //TODO:建立定长线程池，根据线程池是否有空闲线程决定是否执行下面的操作
        //---------------------start-------------------
        
        //根据appid 获取appid已经生成的access_token
        WeChatBase weChatBase = weChatService.getWeChatBase(appid);
        
        if(null != weChatBase){
        
        //查询要下载的mediaIds
        	List<ImageBase> imageBases =  imgBaseService.queryNeedDownloadMeidaId();
            for(ImageBase o : imageBases){
            	imgBaseService.downWeChatPic(o, weChatBase);
            }
        	
        }else{
        	LOGGER.info("定时下载图片任务未获取到可用的AccessToken");
        }
        //---------------------end-------------------
        
        long end = System.currentTimeMillis();
        LOGGER.info("定时下载图片任务结束，共耗时：[" + (end-begin) / 1000 + "]秒");
	}
}
