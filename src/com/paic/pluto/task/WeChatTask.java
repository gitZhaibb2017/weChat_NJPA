package com.paic.pluto.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.paic.pawj.wechat.controller.OAuth2Controller;
import com.paic.pluto.wechat.service.WeChatService;

@Component
public class WeChatTask {
	
	private static final Logger LOGGER = Logger.getLogger(OAuth2Controller.class);
	
	private static String appId= "wx3bab3576f7554012";
	
	private static String appsecret= "13559ac5875dd465814cb9350460da5e";
	
	@Autowired
	private WeChatService weChatService;

	
	@Scheduled(cron = "0 0 0/1 * * ?")//每天凌晨两点执行
//	@Scheduled(cron = "0 0/1 * * * ?")//每天凌晨两点执行
    public void doSomethingWith(){
		LOGGER.info("定时任务开始......");
        long begin = System.currentTimeMillis();
    
        System.out.println("task start");
        
        weChatService.updateCertificate(appId, appsecret);
        
        System.out.println("task end");
        
        long end = System.currentTimeMillis();
        LOGGER.info("定时任务结束，共耗时：[" + (end-begin) / 1000 + "]秒");
	}
}
