package com.weixin.zjjfb.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import com.weixin.zjjfb.key.TokenKey;
import com.weixin.zjjfb.model.token.AccessToken;

public class WeixinUtil {

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 菜单创建（POST） 限100（次/天）
	public static String MENU_CREAT_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 上传临时素材
	public static String UPLOAD_TYPE_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 获取accessToken
	 * 
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject json = HttpUtil.doHttpsGet(url, null);
		accessToken.setToken(json.getString("access_token"));
		accessToken.setExpiresIn(json.getInt("expires_in"));
		return accessToken;
	}

	/**
	 * 上传素材
	 * 
	 * @param uploadMediaUrl
	 * @param mediaFileUrl
	 * @return
	 * @throws Exception
	 */
	public static JSONObject upLoadFile(String uploadMediaUrl,
			String mediaFileUrl) throws Exception {
		// 创建url
		URL url = new URL(uploadMediaUrl);
		File file = new File(mediaFileUrl);
		if (!file.exists() || !file.isFile()) {
			System.out.println("上传的文件不存在");
		}
		// 建立lianjie
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb
				.append("Content-Disposition: form-data;name=\"uploadfile\";filename=\""
						+ file.getName() + "\" \r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分,把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		// 定义BufferedReader输入流来读取URL的响应
		reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String s = buffer.toString();
		return JSONObject.fromObject(s);
	}

	
	/**
	 * 创建菜单
	 * 
	 * 
	 * @param action
	 * @param menu
	 * @throws Exception
	 */
	public static void createMenu(String action,String menu) throws Exception {
		URL url = new URL(action);
		HttpURLConnection http = (HttpURLConnection) url.openConnection(); 
		http.setRequestMethod("POST"); 
		http.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); 
		http.setDoOutput(true); 
		http.setDoInput(true);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
		http.connect();
		OutputStream os= http.getOutputStream(); 
		os.write(menu.getBytes("UTF-8"));//传入参数 
		os.flush();
		os.close();
		InputStream is =http.getInputStream();
		int size =is.available();
		byte[] jsonBytes =new byte[size];
		is.read(jsonBytes);
		String message=new String(jsonBytes,"UTF-8");
		System.out.println(message);
	} 
	
	
	public static void main(String[] args) throws Exception {
		String filePath = "C:/Users/Administrator/Desktop/weixin/1288167474024bql4bie6wq_medium.jpg";
		String type = "thumb";
		AccessToken token = getAccessToken(TokenKey.WX_TEST_APPID.getValue(),TokenKey.WX_TEST_APPSECRET.getValue());
		String url = UPLOAD_TYPE_URL.replaceAll("TYPE", type).replaceAll("ACCESS_TOKEN", token.getToken());
		JSONObject jsonObject = upLoadFile(url, filePath);
		System.out.println(jsonObject.toString());
		
		// System.out.println(jsonObject.get("media_id"));
		/*AccessToken token = getAccessToken(TokenKey.WX_TEST_APPID.getValue(),TokenKey.WX_TEST_APPSECRET.getValue());
		String url = MENU_CREAT_URL.replaceAll("ACCESS_TOKEN", token.getToken());
		
		Menu menu = MenuUtil.getMenu();
		String menuText = JSONObject.fromObject(menu).toString();
		
		createMenu(url, menuText);*/
		
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}

	/**
	 * utf编码
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * String转int
	 * 
	 * @param content
	 * @return
	 */
	public static int checkContent(String content){
		int test = 0;
		try {
			test = Integer.parseInt(content);
		} catch (Exception e) {
			test = 0;
		}
		return test;
	}
	
	
	/**
	 * 处理字符串
	 * 
	 * @param content
	 * @return
	 */
	public static String processContent(String content) {
		String returnStr =  content.toUpperCase();
		if (returnStr.startsWith("翻译：") || returnStr.startsWith("RJ：")
		  ||returnStr.startsWith("SJ：")||returnStr.startsWith("IP：")
		  ||returnStr.startsWith("TQ：")||returnStr.startsWith("KD：")
		  ||returnStr.startsWith("FY：")) {
			content = content.substring(content.indexOf("：") + 1, content.length());
		}
		if (returnStr.startsWith("翻译:") || returnStr.startsWith("RJ:")
				  ||returnStr.startsWith("SJ:")||returnStr.startsWith("IP:")
				  ||returnStr.startsWith("TQ:")||returnStr.startsWith("KD:")
				  ||returnStr.startsWith("FY:")) {
			content = content.substring(content.indexOf(":") + 1, content.length());
		}
		return content;
	}
	
	
	/**
	 * 验证是不是包含关键字
	 * 
	 * @param content
	 * @return
	 */
	public static boolean containsKey(String content) {
		String returnStr =  content.toUpperCase();
		if (returnStr.startsWith("RJ：")||returnStr.startsWith("FY：")
		  ||returnStr.startsWith("SJ：")||returnStr.startsWith("IP：")
		  ||returnStr.startsWith("TQ：")||returnStr.startsWith("KD：") 
		  ||returnStr.startsWith("RJ:") ||returnStr.startsWith("FY:")
		  ||returnStr.startsWith("SJ:")||returnStr.startsWith("IP:")
		  ||returnStr.startsWith("TQ:")||returnStr.startsWith("KD:")) {
			return true;
		}
		return false;
	}
}
