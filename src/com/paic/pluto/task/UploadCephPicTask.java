package com.paic.pluto.task;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.paic.pluto.pojo.ImageBase;
import com.paic.pluto.service.ImgBaseService;

/**
 * 定时上传Pic到Ceph
 * 
 * @author zhaibb
 *
 */
@Component
public class UploadCephPicTask {
	
	private static final Logger LOGGER = Logger.getLogger(UploadCephPicTask.class);
	
	@Autowired
	private ImgBaseService imgBaseService;

	
	@Scheduled(cron = "0/10 * * * * ?")//每天凌晨两点执行
    public void doSomethingWith() throws IOException{
		LOGGER.info("定时上传图片任务开始......");
        long begin = System.currentTimeMillis();
    
        LOGGER.info("task start");
        
        //---------------------start-------------------
        
        //获取需要上传的图片
        List<ImageBase> imgBases = imgBaseService.queryNeedUploadPic();
        
        for(ImageBase o : imgBases){
        	imgBaseService.uploadCephPic(o);
        }
        
        //---------------------end-------------------
        
        LOGGER.info("task end");
        
        long end = System.currentTimeMillis();
        LOGGER.info("定时上传图片任务结束，共耗时：[" + (end-begin) / 1000 + "]秒");
	}
}
