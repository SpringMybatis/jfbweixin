package com.weixin.zjjfb.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.weixin.zjjfb.key.InterfaceKey;
import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.util.HttpUtil;
import com.weixin.zjjfb.util.WeixinUtil;

public class IpSearchServiceImpl implements BaseService {

	public String getServiceMsg(String content) throws Exception {
		String processContent = WeixinUtil.processContent(content);
		if (StringUtils.isNotBlank(processContent)) {
			String url = InterfaceKey.BAIDU_IP_HOME_URL.getValue();
			String httpUrl = url.replaceAll("IP_HOME", processContent);
			JSONObject json = HttpUtil.doHttpGet(httpUrl,InterfaceKey.APIKEY.getValue());
			if("success".equals(json.getString("errMsg"))){
				JSONObject result = json.getJSONObject("retData");
				return content+"\n"+result.get("country")+"-"+result.get("province");
			}else{
				return "对不起,您需要查询的IP不存在,请仔细核对IP！";
			}
		}
		return "对不起,您需要查询的IP不存在！";
	}

}
