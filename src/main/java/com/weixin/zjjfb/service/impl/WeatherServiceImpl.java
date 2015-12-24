package com.weixin.zjjfb.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.weixin.zjjfb.key.InterfaceKey;
import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.util.HttpUtil;
import com.weixin.zjjfb.util.WeixinUtil;

public class WeatherServiceImpl implements BaseService {

	public String getServiceMsg(String content) throws Exception {
		String processContent = WeixinUtil.processContent(content);
		if (StringUtils.isNotBlank(processContent)) {
			String url = InterfaceKey.BAIDU_WEATHER_URL.getValue().replaceAll("CITYNAME", processContent);
			JSONObject json = HttpUtil.doHttpGet(url, InterfaceKey.APIKEY.getValue());
			if (-1 == json.getInt("errNum")) {
				return json.getString("errMsg");
			} else {
				JSONObject result = json.getJSONObject("retData");
				return "城市:" + result.get("city") + "\r\n" + "日期:"
						+ result.get("date") + " " + result.get("time")
						+ "\r\n" + "温度:" + result.get("temp") + "'C," + "最低温度"
						+ result.get("l_tmp") + "'C,最高温度" + result.get("h_tmp")
						+ " 'C\r\n" + "风向:" + result.get("WD")
						+ result.get("WS");
			}
		}
		return "天气城市不能为空!";
	}

}
