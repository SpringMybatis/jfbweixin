package com.weixin.zjjfb.key;

public enum MessageKey {

	/**
	 * 返回消息类型：文本
	 */
	MESSAGE_TYPE_TEXT("text"),

	/**
	 * 返回消息类型：音乐
	 */
	MESSAGE_TYPE_MUSIC("music"),

	/**
	 * 返回消息类型：图文
	 */
	MESSAGE_TYPE_NEWS("news"),

	/**
	 * 请求消息类型：图片
	 */
	MESSAGE_TYPE_IMAGE("image"),

	/**
	 * 请求消息类型：链接
	 */
	MESSAGE_TYPE_LINK("link"),

	/**
	 * 请求消息类型：地理位置
	 */
	MESSAGE_TYPE_LOCATION("location"),

	/**
	 * 请求消息类型：音频
	 */
	MESSAGE_TYPE_VOICE("voice"),

	/**
	 * 请求消息类型：推送
	 */
	MESSAGE_TYPE_EVENT("event"),

	/**
	 * 事件类型：subscribe(订阅)
	 */
	EVENT_TYPE_SUBSCRIBE("subscribe"),

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	EVENT_TYPE_UNSUBSCRIBE("unsubscribe"),

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	EVENT_TYPE_CLICK("CLICK");

	private MessageKey(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
