package com.weixin.zjjfb.key;

public enum TokenKey {

	/**
	 * 微信APPID
	 */
	WX_APPID("appid","wxb1f7b298411daf9b"),
	
	/**
	 * 微信APPSECRET
	 */
	WX_APPSECRET("appsecret","80d21721c889f4efa303e379e1639c9b"),
	
	/**
	 * 微信测试APPID
	 */
	WX_TEST_APPID("appid","wx33f33656b6aab17c"),
	
	/**
	 * 微信测试APPSECRET
	 */
	WX_TEST_APPSECRET("appsecret","d4624c36b6795d1d99dcf0547af5443d"),
	
	/**
	 * 微信token
	 */
	WX_TOKEN("jfb", "jfb");

	private TokenKey(String key, String value) {
		this.key = key;
		this.value = value;
	}

	private String key;

	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
