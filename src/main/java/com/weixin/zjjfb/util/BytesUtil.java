package com.weixin.zjjfb.util;

import java.io.UnsupportedEncodingException;

public class BytesUtil {

	/**
	 * 计算采用utf-8编码方式时字符串所占字节数
	 * 
	 * @param content
	 * @return
	 */
	public static int getByteSize(String content) {
		int size = 0;
		try {
			if (null != content) {
				// 汉字采用utf-8编码时占3个字节
				size = content.getBytes("utf-8").length;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 判断字节大小
	 * 
	 * @param content
	 * @return
	 */
	public static boolean checkBytes(String content, int limit) {
		int size = 0;
		try {
			if (null != content) {
				// 汉字采用utf-8编码时占3个字节
				size = content.getBytes("utf-8").length;
				if (size < limit) {
					return true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

}
