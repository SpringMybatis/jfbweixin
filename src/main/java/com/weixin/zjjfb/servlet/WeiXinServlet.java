package com.weixin.zjjfb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.zjjfb.key.MessageKey;
import com.weixin.zjjfb.model.message.WeiXinTextMessage;
import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.service.impl.BaiduTranslateServiceImpl;
import com.weixin.zjjfb.service.impl.ExpressServiceImpl;
import com.weixin.zjjfb.service.impl.FaceDetectServiceImpl;
import com.weixin.zjjfb.service.impl.IpSearchServiceImpl;
import com.weixin.zjjfb.service.impl.PhoneNumServiceImpl;
import com.weixin.zjjfb.service.impl.RobotServiceImpl;
import com.weixin.zjjfb.service.impl.WeatherServiceImpl;
import com.weixin.zjjfb.util.CheckSignatureUtil;
import com.weixin.zjjfb.util.MenuUtil;
import com.weixin.zjjfb.util.WeixinUtil;
import com.weixin.zjjfb.util.XmlUtil;

@SuppressWarnings("serial")
public class WeiXinServlet extends HttpServlet {

	private static BaseService translateService = new BaiduTranslateServiceImpl();
	
	private static BaseService expressService = new ExpressServiceImpl();
	
	private static BaseService ipService = new IpSearchServiceImpl();
	
	private static BaseService phoneNumService = new PhoneNumServiceImpl();
	
	private static BaseService weatherService = new WeatherServiceImpl();
	
	private static BaseService robotService = new RobotServiceImpl();
	
	private static BaseService faceService = new FaceDetectServiceImpl();

	private static Map<String,BaseService> setMap = new HashMap<String,BaseService>();
	
	private static Map<String,String> keyMap = new HashMap<String,String>();
	
	static {
		setMap.put("TQ", weatherService);
		setMap.put("SJ", phoneNumService);
		setMap.put("IP", ipService);
		setMap.put("KD", expressService);
		setMap.put("FY", translateService);
		setMap.put("RJ", robotService);
		
		keyMap.put("11", "1");
		keyMap.put("12", "none");
		keyMap.put("13", "none");
		keyMap.put("21", "2");
		keyMap.put("22", "3");
		keyMap.put("23", "4");
		keyMap.put("24", "6");
		keyMap.put("25", "5");
		
		
	}
	
	/**
	 * 机器人交流接口
	 * 
	 */
	
	
	
	/**
	 * 验证是否是微信服务器发过来的消息
	 * 
	 * */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
			if (CheckSignatureUtil.checkSignature(request)) {
				// 随机字符串
				String echostr = request.getParameter("echostr");
				out.print(echostr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}

	/**
	 * 处理微信服务器发过来的消息
	 * 
	 * */
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = null;
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
		try {
			out = response.getWriter();
			// 处理request参数
			Map<String, String> msgMap = XmlUtil.paseToXml(request);
			
			System.out.println(msgMap.toString());
			
			String toUserName = msgMap.get("ToUserName");
			String fromUserName = msgMap.get("FromUserName");
			String msgType = msgMap.get("MsgType");
			String content = msgMap.get("Content");
			int result = WeixinUtil.checkContent(content);
			String repMsg = "";
			System.out.println(msgType);
			// 文本消息
			if (msgType.equals(MessageKey.MESSAGE_TYPE_TEXT.getValue())) {
				// 响应消息
				WeiXinTextMessage textMessage = new WeiXinTextMessage();
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setFromUserName(toUserName);
				textMessage.setMsgType(MessageKey.MESSAGE_TYPE_TEXT.getValue());
				textMessage.setToUserName(fromUserName);
				String responseContent = "";
				if("?".equals(content)||"？".equals(content)){
					responseContent = MenuUtil.getMainMenu();
				// 1-5 菜单回复
				}else if(result>0){
					responseContent = MenuUtil.getSubMenu(content);
				// 五个功能点
				}else if(WeixinUtil.containsKey(content)){
					String[] array1 = content.split(":");
					BaseService baseService1 = setMap.get(array1[0].toUpperCase());
					if(null!=baseService1){
						responseContent = baseService1.getServiceMsg(content);
					}else{
						String[] array2 = content.split("：");
						System.out.println(array2);
						BaseService baseService2 = setMap.get(array2[0].toUpperCase());
						responseContent = baseService2.getServiceMsg(content);
					}
				// 机器人交流	
				}else{
					BaseService robotService = setMap.get("RJ");
					responseContent = robotService.getServiceMsg(content);
				}
				textMessage.setContent(responseContent);
				repMsg = XmlUtil.textMessageToXml(textMessage);
			// 图片消息
			}else if(msgType.equals(MessageKey.MESSAGE_TYPE_IMAGE.getValue())){
				/*WeiXinImageMessage imageMessage = new WeiXinImageMessage();
				imageMessage.setCreateTime(new Date().getTime());
				imageMessage.setFromUserName(toUserName);
				imageMessage.setMsgType(msgType);
				imageMessage.setToUserName(fromUserName);
				// 图片消息
				Image image = new Image();
				image.setMediaId("8cqbasTRcu_cJ_ARv9KA8T7wIRzXWjjNtvfuzCbI_5GKhJb1ssKO-AzYVID5SNdU");
				imageMessage.setImage(image);
				repMsg = XmlUtil.imageMessageToxml(imageMessage);*/
				// 响应消息
				WeiXinTextMessage textMessage = new WeiXinTextMessage();
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setFromUserName(toUserName);
				textMessage.setMsgType(MessageKey.MESSAGE_TYPE_TEXT.getValue());
				textMessage.setToUserName(fromUserName);
				String picUrl = msgMap.get("PicUrl");  
				String responseContent = faceService.getServiceMsg(picUrl);
				textMessage.setContent(responseContent);
				repMsg = XmlUtil.textMessageToXml(textMessage);
			// 地理位置息
			}else if(msgType.equals(MessageKey.MESSAGE_TYPE_LOCATION.getValue())){	
				System.out.println("location");
				// 回复音乐消息
				// repMsg = MessageUtil.musicMessage(fromUserName, toUserName);
			// 连接消息
			}else if(msgType.equals(MessageKey.MESSAGE_TYPE_LINK.getValue())){	
				// 回复图文消息
				System.out.println("link");
				// repMsg = MessageUtil.newsMessage(fromUserName, toUserName);
			// 事件推送
			}else if (msgType.equals(MessageKey.MESSAGE_TYPE_EVENT.getValue())) {
				// 事件类型
				String eventType = msgMap.get("Event");
				System.out.println(eventType);
				
				// 订阅
				if (eventType.equals(MessageKey.EVENT_TYPE_SUBSCRIBE.getValue())) {
					WeiXinTextMessage textMessage = new WeiXinTextMessage();
					String responseContent = MenuUtil.getMainMenu();
					textMessage.setContent(responseContent);
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setFromUserName(toUserName);
					textMessage.setMsgType(MessageKey.MESSAGE_TYPE_TEXT.getValue());
					textMessage.setToUserName(fromUserName);
					repMsg = XmlUtil.textMessageToXml(textMessage);
				// 自定义菜单点击事件
				}else if (eventType.equals(MessageKey.EVENT_TYPE_CLICK.getValue())) {
					String eventKey = msgMap.get("EventKey");
					String eventNum = keyMap.get(eventKey);
					// 响应消息
					WeiXinTextMessage textMessage = new WeiXinTextMessage();
					textMessage.setCreateTime(new Date().getTime());
					textMessage.setFromUserName(toUserName);
					textMessage.setMsgType(MessageKey.MESSAGE_TYPE_TEXT.getValue());
					textMessage.setToUserName(fromUserName);
					if("none".equals(eventNum)){
						textMessage.setContent(MenuUtil.getSubMenu("9"));
					}else{
						textMessage.setContent(MenuUtil.getSubMenu(eventNum));
					}
					repMsg = XmlUtil.textMessageToXml(textMessage);
				}
			}
			System.out.println(repMsg);
			out.print(repMsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}

}
