package com.weixin.zjjfb.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.weixin.zjjfb.key.InterfaceKey;
import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.util.HttpUtil;
import com.weixin.zjjfb.util.WeixinUtil;

public class PhoneNumServiceImpl implements BaseService {

	public String getServiceMsg(String content) throws Exception {
		String processContent = WeixinUtil.processContent(content);
		if (StringUtils.isNotBlank(processContent)) {
			String url = InterfaceKey.BAIDU_PHONE_HOME_URL.getValue().replaceAll("PHONE", processContent);
			JSONObject json = HttpUtil.doHttpGet(url,InterfaceKey.APIKEY.getValue());
			if("success".equals(json.getString("retMsg"))){
				JSONObject result = json.getJSONObject("retData");
				return result.get("phone")+"\n"+result.getString("city")+"-"+result.get("supplier");
			}else{
				return "手机号码不存在!";
			}
		}
		return "手机号码不能为空";
	}

}
