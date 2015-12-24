package com.weixin.zjjfb.util;

import com.weixin.zjjfb.model.menu.Button;
import com.weixin.zjjfb.model.menu.CommonButton;
import com.weixin.zjjfb.model.menu.ComplexButton;
import com.weixin.zjjfb.model.menu.Menu;
import com.weixin.zjjfb.model.menu.ViewButton;

public class MenuUtil {
	
	// private static final String WRAPSEPARATED = "\n\n";

	private static final String SEPARATED = "\n";

	/**
	 * 主菜单设置
	 * 
	 * @return
	 */
	public static String getMainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，我是小钟，请回复数字选择服务：").append(SEPARATED);
		buffer.append("1、人机交流").append(SEPARATED);
		buffer.append("2、手机归属查询").append(SEPARATED);
		buffer.append("3、IP地址查询").append(SEPARATED);
		buffer.append("4、天气查询").append(SEPARATED);
		buffer.append("5、小钟翻译").append(SEPARATED);
		buffer.append("6、快递查询").append(SEPARATED);
		buffer.append("7、人脸识别").append(SEPARATED);
		buffer.append("8、语音识别").append(SEPARATED);
		buffer.append("输入其他则默认人机交流\n输入“?”显示此帮助菜单");
		return buffer.toString();
	}

	/**
	 * 二级菜单
	 * 
	 * @param number
	 * @return
	 */
	public static String getSubMenu(String number){
		String content = "";
		if("1".equals(number)){
			content = "欢迎和小钟机器人交流";
		}else if("2".equals(number)){
			content = "请输入SJ:您查询的手机号码，比如(SJ:15221106455)";
		}else if("3".equals(number)){
			content = "请输入IP:您查询的IP地址，比如(IP:15221106455)";
		}else if("4".equals(number)){
			content = "请输入TQ:您查询的城市，比如(TQ:上海)";
		}else if("5".equals(number)){
			content = "请输入FY:您想翻译的语句，比如(FY:你好)";
		}else if("6".equals(number)){
			content = "支持一下快递查询:\n申通(ST)\n圆通(YT)\n中通(ZT)\n邮政EMS(YZEMS)\n天天(TT)\n优速(YS)\n快捷(KJ)\n全峰(QF)\n增益(ZY)\n请输入:\nKD:物流公司名称(YT)+您查询的订单号\n比如(KD:ST200093247451)";
		}else if("7".equals(number)){
			content = "对不起该功能暂未实现！";
		}else if("8".equals(number)){
			content = "对不起该功能暂未实现！";
		}else if("9".equals(number)){
			content = "对不起该功能暂未实现！";
		}
		return content;
	}
	
	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	public static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("人机交流");
		btn11.setType("click");
		btn11.setKey("11");

		CommonButton btn12 = new CommonButton();
		btn12.setName("人脸识别");
		btn12.setType("click");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("语音识别");
		btn13.setType("click");
		btn13.setKey("13");

		CommonButton btn21 = new CommonButton();
		btn21.setName("手机归属查询");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("IP地址查询");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("天气查询");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("快递查询");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn25 = new CommonButton();
		btn25.setName("百度翻译");
		btn25.setType("click");
		btn25.setKey("25");

		ViewButton btn31 = new ViewButton();
		btn31.setName("百度一下");
		btn31.setType("view");
		btn31.setUrl("https://www.baidu.com/");

		ViewButton btn32 = new ViewButton();
		btn32.setName("小钟官网");
		btn32.setType("view");
		btn32.setUrl("http://xiaozhong.ngrok.cc/jfbweixin/index.jsp");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("互动交流");
		mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("便捷查询");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24,
				btn25 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("关于我们");
		mainBtn3.setSub_button(new ViewButton[] { btn31, btn32 });

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
