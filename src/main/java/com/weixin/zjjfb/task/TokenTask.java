package com.weixin.zjjfb.task;

import java.util.TimerTask;

import com.weixin.zjjfb.model.token.AccessToken;
import com.weixin.zjjfb.util.WeixinUtil;

public class TokenTask extends TimerTask {

	public String appid;

	public String appsecret;

	public static AccessToken accessToken = null;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public TokenTask() {

	}

	public TokenTask(String appid, String appsecret) {
		super();
		this.appid = appid;
		this.appsecret = appsecret;
	}

	@Override
	public void run() {
		accessToken = WeixinUtil.getAccessToken(appid, appsecret);
	}

}
