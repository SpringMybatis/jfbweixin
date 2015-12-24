package com.weixin.zjjfb.service.impl;

import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.util.HttpUtil;

public class VoiceDetechServiceImpl implements BaseService {

	@Override
	public String getServiceMsg(String content) throws Exception {
		
		
		return HttpUtil.voiceHttpRequest("", "", "", "");
	}

}
