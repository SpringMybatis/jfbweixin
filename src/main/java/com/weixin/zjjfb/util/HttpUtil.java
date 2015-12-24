package com.weixin.zjjfb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public class HttpUtil {

	private static Logger log = Logger.getLogger(HttpUtil.class);
	
	/**
	 * 执行http-get请求
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject doHttpGet(String url,String apikey){
		JSONObject json = null;
		try {
			// 构造httpClient
			HttpClient client = new DefaultHttpClient();
			// get请求
			HttpGet get = new HttpGet(url);
			if(!StringUtils.isBlank(apikey)){
				get.setHeader("apikey", apikey);
			}
			// 执行get请求，并返回
			HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				// 处理返回结果
				String result = EntityUtils.toString(response.getEntity());
				json = JSONObject.fromObject(result);
				return json;
			}
		} catch (Exception e) {
			System.out.println("doHttpGet异常！");
			log.error("HttpUtil doHttpGet Exception:"+e);
		}
		return null;
	}
	
	/**
	 * 执行https-get请求
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject doHttpsGet(String url,String apikey){
		JSONObject json = null;
		try {
			// 构造httpClient
			HttpClient client = new DefaultHttpClient();
			processHttpsClient(client);
			// get请求
			HttpGet get = new HttpGet(url);
			if(!StringUtils.isBlank(apikey)){
				get.setHeader("apikey", apikey);
			}
			// 执行get请求，并返回
			HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				// 处理返回结果
				String result = EntityUtils.toString(response.getEntity());
				json = JSONObject.fromObject(result);
				return json;
			}
		} catch (Exception e) {
			System.out.println("doHttpGet异常！");
			log.error("HttpUtil doHttpGet Exception:"+e);
		}
		return null;
	}
	
	public static JSONObject doHttpsPost(String url,String outPut){
		
		JSONObject json = null;
		try {
			// 构造httpClient
			HttpClient client = new DefaultHttpClient();
			processHttpsClient(client);
			// post请求
			HttpPost post = new HttpPost(url);
			// post请求参数
			StringEntity se = new StringEntity(outPut);
			post.setEntity(se);
			// 执行post请求，并返回
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				// 处理返回结果
				String result = EntityUtils.toString(response.getEntity());
				json = JSONObject.fromObject(result);
				return json;
			}
		} catch (Exception e) {
			System.out.println("doHttpGet异常！");
			log.error("HttpUtil doHttpGet Exception:"+e);
		}
		return null;
		
		
	}
	
	
	/**
	 * 执行http-post请求
	 * 
	 * @param url
	 * @param outPut
	 * @return
	 */
	public static JSONObject doHttpPost(String url,String outPut){
		JSONObject json = null;
		try {
			// 构造httpClient
			HttpClient client = new DefaultHttpClient();
			// post请求
			HttpPost post = new HttpPost(url);
			// post请求参数
			StringEntity se = new StringEntity(outPut);
			post.setEntity(se);
			// 执行post请求，并返回
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				// 处理返回结果
				String result = EntityUtils.toString(response.getEntity());
				json = JSONObject.fromObject(result);
				return json;
			}
		} catch (Exception e) {
			System.out.println("doHttpPost异常！");
			log.error("HttpUtil doHttpPost Exception:"+e);
		}
		return null;
	}
	
	
	/**
	 * 添加https协议
	 * 
	 */
	public static void processHttpsClient(HttpClient client) throws Exception{
		X509TrustManager x509TrustManager = new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			
			public void checkServerTrusted(X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
			
			public void checkClientTrusted(X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
		};
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, new TrustManager[]{x509TrustManager}, new java.security.SecureRandom());
		SSLSocketFactory ssf = new SSLSocketFactory(sslContext); 
		ClientConnectionManager ccm = client.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();  
        sr.register(new Scheme("https", 443, ssf));
	}
	
	
	/**
	 * 语音识别信息
	 * 
	 * @param httpUrl
	 * @param httpArg
	 * @return
	 */
	public static String voiceHttpRequest(String httpUrl, String filePath, String type,String apikey) {
		BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
		try {
			// 参数格式转换
			FileInputStream fis = new FileInputStream(new File(filePath));
			byte[] byteAudio = IOUtils.toByteArray(fis);
			byte[] base64AudioByte = Base64.encodeBase64(byteAudio);
			String base64AudioStr = new String(base64AudioByte, "utf-8");
			// 【】base64后urlencode
			String base64AudioStrUrlenocde = URLEncoder.encode(base64AudioStr);
			String postParams = "audioBase64=" + base64AudioStrUrlenocde+ "&format=" + type + "&rate=8000&channel=1&lan=zh";
			// 参数
			System.out.println(postParams);
			// 创建请求连接
			HttpURLConnection connection = (HttpURLConnection) new URL(httpUrl).openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", apikey);
			connection.setDoOutput(true);
			connection.getOutputStream().write(postParams.getBytes("UTF-8"));
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sbf.append(line);
			}
			String s = sbf.toString();
			reader.close();
			result = s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	 /** 
     * 发送http请求 
     *  
     * @param requestUrl 请求地址 
     * @return String 
     */  
    public static String httpRequest(String requestUrl) {  
        StringBuffer buffer = new StringBuffer();  
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buffer.toString();  
    } 
}
