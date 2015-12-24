package com.weixin.zjjfb.model.message;

import com.weixin.zjjfb.model.news.Music;

public class WeiXinMusicMessage extends WeiXinBaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}

}
