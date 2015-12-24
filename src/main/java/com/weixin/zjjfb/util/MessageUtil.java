package com.weixin.zjjfb.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.weixin.zjjfb.key.MessageKey;
import com.weixin.zjjfb.model.message.WeiXinMusicMessage;
import com.weixin.zjjfb.model.message.WeiXinNewsMessage;
import com.weixin.zjjfb.model.news.Article;
import com.weixin.zjjfb.model.news.Music;

public class MessageUtil {

	
	/**
	 * 图文消息
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String newsMessage(String fromUserName,String toUserName){
		WeiXinNewsMessage newsMessage = new WeiXinNewsMessage();  
        newsMessage.setToUserName(fromUserName);  
        newsMessage.setFromUserName(toUserName);  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageKey.MESSAGE_TYPE_NEWS.getValue());  
		
        List<Article> articleList = new ArrayList<Article>();  
		Article article1 = new Article();  
        article1.setTitle("微信公众平台官网");  
        article1.setDescription("");  
        article1.setPicUrl("http://xiaozhong.ngrok.cc/jfbweixin/image/test.jpg");  
        article1.setUrl("https://mp.weixin.qq.com/cgi-bin/home?t=home/index&lang=zh_CN");  

        Article article2 = new Article();  
        article2.setTitle("用户总数");  
        article2.setDescription("");  
        article2.setPicUrl("http://xiaozhong.ngrok.cc/jfbweixin/image/xiaotu.jpg");  
        article2.setUrl("http://www.hao123.com/?tn=90056402_hao_pg");  

        Article article3 = new Article();  
        article3.setTitle("新增人数");  
        article3.setDescription("");  
        article3.setPicUrl("http://xiaozhong.ngrok.cc/jfbweixin/image/xiaotu.jpg");  
        article3.setUrl("http://www.hao123.com/?tn=90056402_hao_pg");  

        articleList.add(article1);  
        articleList.add(article2);  
        articleList.add(article3);  
        newsMessage.setArticleCount(articleList.size());  
        newsMessage.setArticles(articleList);  
        
        return XmlUtil.newsMessageToXml(newsMessage);
		
	}
	
	
	public static String musicMessage(String fromUserName,String toUserName){
		WeiXinMusicMessage  musicMessage = new WeiXinMusicMessage();
		musicMessage.setToUserName(fromUserName);  
		musicMessage.setFromUserName(toUserName);  
		musicMessage.setCreateTime(new Date().getTime());  
		musicMessage.setMsgType(MessageKey.MESSAGE_TYPE_MUSIC.getValue());  
		
		Music music = new Music();
		music.setDescription("刘德华");
		music.setTitle("男人哭吧哭吧不是罪");
		music.setMusicUrl("http://xiaozhong.ngrok.cc/jfbweixin/music/100610417280064.mp3");
		music.setHQMusicUrl("http://xiaozhong.ngrok.cc/jfbweixin/music/100610417280064.mp3");
		music.setThumbMediaId("sWeGE27Jkn32xsHaPgUQjHdq8MMhNHP1ZoZbUqrLHOPVSKzWEYkQah8s7lH0VXMP");
		
		musicMessage.setMusic(music);
		
		return XmlUtil.musicMessageToXml(musicMessage);
	}
	
	
}
