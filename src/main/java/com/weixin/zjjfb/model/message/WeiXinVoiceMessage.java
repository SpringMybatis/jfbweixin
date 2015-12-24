package com.weixin.zjjfb.model.message;

public class WeiXinVoiceMessage extends WeiXinBaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

}
