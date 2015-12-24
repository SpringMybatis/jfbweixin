package com.weixin.zjjfb.service.impl;

import net.sf.json.JSONObject;

import com.weixin.zjjfb.key.InterfaceKey;
import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.util.HttpUtil;
import com.weixin.zjjfb.util.WeixinUtil;

public class BaiduTranslateServiceImpl implements BaseService {
	
	public String getServiceMsg(String content) throws Exception {
		String processContent = WeixinUtil.processContent(content);
		String url = InterfaceKey.BAIDU_TRANSLATE_URL.getValue();
		String httpUrl = url.replaceAll("FROM", "auto").replaceAll("TO", "auto").replaceAll("QUERY", processContent);
		// http-get调用接口
		JSONObject result = HttpUtil.doHttpGet(httpUrl, InterfaceKey.APIKEY.getValue());
		String msg = result.getString("errMsg");
		if("success".equals(msg)){
			JSONObject retData = result.getJSONObject("retData");
			JSONObject resulte = retData.getJSONArray("trans_result").getJSONObject(0);
			return "原文:"+resulte.get("src")+"\n译文:"+resulte.get("dst");
		}
		return "不好意思,暂时无法翻译!";
	}
	
}
