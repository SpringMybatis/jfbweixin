package com.weixin.zjjfb.util;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.weixin.zjjfb.model.message.WeiXinImageMessage;
import com.weixin.zjjfb.model.message.WeiXinMusicMessage;
import com.weixin.zjjfb.model.message.WeiXinNewsMessage;
import com.weixin.zjjfb.model.message.WeiXinTextMessage;
import com.weixin.zjjfb.model.news.Article;
import com.weixin.zjjfb.model.news.Image;
import com.weixin.zjjfb.model.news.Music;

public class XmlUtil {

	/**
	 * 解析xml成map
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> paseToXml(HttpServletRequest request)
			throws Exception {
		// reader
		SAXReader saxReader = new SAXReader();
		// 获取document对象
		Document doc = saxReader.read(request.getInputStream());
		// Document doc = saxReader.read(MessageXmlUtil.class.getClassLoader().getResourceAsStream("a.xml"));
		// 根元素
		Element root = doc.getRootElement();
		// 获取节点
		List<Element> elementList = root.elements();
		if (null != elementList && !elementList.isEmpty()) {
			Map<String, String> elementMap = new HashMap<String, String>();
			for (Element e : elementList) {
				elementMap.put(e.getName(), e.getText());
			}
			return elementMap;
		}
		return null;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(WeiXinTextMessage weiXinTextMessage) {
		xstream.alias("xml", weiXinTextMessage.getClass());
		return xstream.toXML(weiXinTextMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(WeiXinMusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		xstream.alias("Music", new Music().getClass());
		return xstream.toXML(musicMessage);
	}
	
	
	public static String imageMessageToxml(WeiXinImageMessage imageMessage){
		xstream.alias("xml", imageMessage.getClass());
		xstream.alias("Image", new Image().getClass());
		return xstream.toXML(imageMessage);
	}
	
	

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(WeiXinNewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	
	
}
