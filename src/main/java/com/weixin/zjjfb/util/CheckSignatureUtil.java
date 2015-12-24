package com.weixin.zjjfb.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.weixin.zjjfb.key.TokenKey;

public class CheckSignatureUtil {

	private static final String TOKEN = TokenKey.WX_TOKEN.getKey();
	
	/**
	 * 检查微信加密签名
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkSignature(HttpServletRequest request) {
		// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 构造数组
		String[] arr = new String[]{TOKEN,timestamp,nonce}; 
		// 排序
		Arrays.sort(arr);
		// 拼成字符串
		StringBuffer sb = new StringBuffer();
		for(String s:arr){
			sb.append(s);
		}
		// 经过sha1加密后的字符串
		String sha1 = PasswordUtil.SHA1(sb.toString());
		if(signature.equals(sha1)){
			return true;
		}
		return false;
	}

}
