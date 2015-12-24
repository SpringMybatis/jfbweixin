package com.weixin.zjjfb.model.message;

import com.weixin.zjjfb.model.news.Image;

public class WeiXinImageMessage extends WeiXinBaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String PicUrl;

	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

}
