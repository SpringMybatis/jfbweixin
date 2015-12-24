package com.weixin.zjjfb.service.impl;

import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.weixin.zjjfb.key.InterfaceKey;
import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.util.HttpUtil;
import com.weixin.zjjfb.util.WeixinUtil;

public class ExpressServiceImpl implements BaseService {
	
	private static Set<String> expressSet = new HashSet<String>();
	
	static {
		expressSet.add("YT");
		expressSet.add("ST");
		expressSet.add("ZT");
		expressSet.add("YZEMS");
		expressSet.add("TT");
		expressSet.add("YS");
		expressSet.add("KJ");
		expressSet.add("QF");
		expressSet.add("ZY");
	}
	
	public String getServiceMsg(String content) throws Exception {
		try {
			String processContent = WeixinUtil.processContent(content);
			
			if (StringUtils.isNotBlank(processContent)) {
				String code1 = processContent.substring(0,2);
				String bill1 = processContent.substring(2);
				String code2 = processContent.substring(0,5);
				String bill2 = processContent.substring(5);
				String expressCode = "";
				String billNo = "";
				if(expressSet.contains(code1.toUpperCase().trim())){
					expressCode = code1;
					billNo = bill1;
				}else if(expressSet.contains(code2.toUpperCase().trim())){
					expressCode = code2;
					billNo = bill2;
				}
				if(StringUtils.isNotBlank(expressCode)){
					String url = InterfaceKey.BAIDU_EXPRESS_URL.getValue();
					String httpUrl = url.replaceAll("EXPRESSCODE", expressCode).replaceAll("BILLNO", billNo);
					// http-get调用接口
					JSONObject result = HttpUtil.doHttpGet(httpUrl, InterfaceKey.APIKEY.getValue());
					JSONArray arr = result.getJSONArray("data");
					if(arr!=null && arr.size()>0){
						JSONObject data = arr.getJSONObject(0);
						String expressName = data.getString("expressName");
						JSONArray array = data.getJSONArray("wayBills");
						StringBuffer sb = new StringBuffer();
						sb.append(expressName).append("("+expressCode+")\n");
						for(int i=0;i<array.size();i++){
							JSONObject obj = array.getJSONObject(i);
							String time = DateFormatUtils.format(obj.getLong("datetime"), "yyyy-MM-dd HH:mm:ss");
							String info = obj.getString("processInfo");
							sb.append(time).append("  \n").append(info).append("\n");
						}
						return sb.toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "不好意思,查询不到运单号信息,请仔细核对运单号信息!";
	}
	
}
