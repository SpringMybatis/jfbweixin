package com.weixin.zjjfb.servlet;

import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.weixin.zjjfb.task.TokenTask;

@SuppressWarnings("serial")
public class InitTokenServlet extends HttpServlet {
	
	private Logger log =  Logger.getLogger(InitTokenServlet.class);

	@Override
	public void init() throws ServletException {
		String appid = getInitParameter("appid");
		String appsecret = getInitParameter("appsecret");
		 // 未配置appid、appsecret时给出提示  
        if (StringUtils.isBlank(appid)||StringUtils.isBlank(appsecret)) {  
            log.error("appid and appsecret configuration error, please check carefully.");  
        } else {  
            // 启动定时Timer获取access_token
        	Timer timer = new Timer();   
        	// 执行定时任务
        	TokenTask ttTask = new TokenTask(appid, appsecret);
        	timer.schedule(ttTask, 5*1000, 7000*1000);
        }
	}
	
}
