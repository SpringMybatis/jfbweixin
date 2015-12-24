package com.weixin.zjjfb.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.weixin.zjjfb.key.InterfaceKey;
import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.util.HttpUtil;
import com.weixin.zjjfb.util.QQFaceUtil;
import com.weixin.zjjfb.util.WeixinUtil;

public class RobotServiceImpl implements BaseService {

	public String getServiceMsg(String content) throws Exception {
		if(QQFaceUtil.isQqFace(content)){
			return content;
		}
		// 组装请求url
		String processContent = WeixinUtil.processContent(content);
		if (StringUtils.isNotBlank(processContent)) {
			String url = InterfaceKey.BAIDU_TULING_URL.getValue();
			String httpUrl = url.replaceAll("KEY", InterfaceKey.KEY.getValue()).replaceAll("INFO", processContent);
			// http-get调用接口
			JSONObject result = HttpUtil.doHttpGet(httpUrl, null);
			// 组装返回信息
			return result.getString("text");
		}
		return "暂时无法提供服务......";
	}

}
