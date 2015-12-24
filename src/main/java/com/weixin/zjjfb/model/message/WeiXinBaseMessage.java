package com.weixin.zjjfb.model.message;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WeiXinBaseMessage implements Serializable {

	/* 文本消息 */
	private String ToUserName;

	private String FromUserName;

	private long CreateTime;

	private String MsgType;

	// private long MsgId;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}