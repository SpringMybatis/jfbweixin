package com.weixin.zjjfb.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.zjjfb.key.InterfaceKey;
import com.weixin.zjjfb.model.service.Face;
import com.weixin.zjjfb.service.BaseService;
import com.weixin.zjjfb.util.HttpUtil;

public class FaceDetectServiceImpl implements BaseService {

	public String getServiceMsg(String content) throws Exception {
		try {
			// 拼接Face++人脸检测的请求地址  
            String queryUrl = InterfaceKey.FACEPLUSPLUS_DETECH_URL.getValue();
            // 对URL进行编码  
            queryUrl = queryUrl.replace("URL", java.net.URLEncoder.encode(content, "UTF-8"));  
            queryUrl = queryUrl.replace("API_KEY", InterfaceKey.FACEPLUSPLUS_APIKEY.getValue());  
            queryUrl = queryUrl.replace("API_SECRET", InterfaceKey.FACEPLUSPLUS_APISECRET.getValue());  
            // 调用人脸检测接口  
            String json = HttpUtil.httpRequest(queryUrl);  
            // 解析返回json中的Face列表  
            JSONArray jsonArray = JSONObject.fromObject(json).getJSONArray("face");  
            List<Face> faceList = new ArrayList<Face>();  
            // 遍历检测到的人脸  
            for (int i = 0; i < jsonArray.size(); i++) {  
                // face  
                JSONObject faceObject = (JSONObject) jsonArray.get(i);  
                // attribute  
                JSONObject attrObject = faceObject.getJSONObject("attribute");  
                // position  
                JSONObject posObject = faceObject.getJSONObject("position");  
                Face face = new Face();  
                face.setFaceId(faceObject.getString("face_id"));  
                face.setAgeValue(attrObject.getJSONObject("age").getInt("value"));  
                face.setAgeRange(attrObject.getJSONObject("age").getInt("range"));  
                face.setGenderValue(genderConvert(attrObject.getJSONObject("gender").getString("value")));  
                face.setGenderConfidence(attrObject.getJSONObject("gender").getDouble("confidence"));  
                face.setRaceValue(raceConvert(attrObject.getJSONObject("race").getString("value")));  
                face.setRaceConfidence(attrObject.getJSONObject("race").getDouble("confidence"));  
                face.setSmilingValue(attrObject.getJSONObject("smiling").getDouble("value"));  
                face.setCenterX(posObject.getJSONObject("center").getDouble("x"));  
                face.setCenterY(posObject.getJSONObject("center").getDouble("y"));  
                faceList.add(face);  
            }  
            // 将检测出的Face按从左至右的顺序排序  
            Collections.sort(faceList);
            // 返回信息
            return makeMessage(faceList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/** 
     * 性别转换（英文->中文） 
     *  
     * @param gender 
     * @return 
     */  
    public String genderConvert(String gender) {  
        String result = "男性";  
        if ("Male".equals(gender))  
            result = "男性";  
        else if ("Female".equals(gender))  
            result = "女性";  
  
        return result;  
    }  
	
	
	 /** 
     * 人种转换（英文->中文） 
     *  
     * @param race 
     * @return 
     */  
    public String raceConvert(String race) {  
        String result = "黄色";  
        if ("Asian".equals(race))  
            result = "黄色";  
        else if ("White".equals(race))  
            result = "白色";  
        else if ("Black".equals(race))  
            result = "黑色";  
        return result;  
    }  
  
    /** 
     * 根据人脸识别结果组装消息 
     *  
     * @param faceList 人脸列表 
     * @return 
     */  
    public String makeMessage(List<Face> faceList) {  
        StringBuffer buffer = new StringBuffer();  
        // 检测到1张脸  
        if (1 == faceList.size()) {  
            buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸").append("\n");  
            for (Face face : faceList) {  
                buffer.append(face.getRaceValue()).append("人种,");  
                buffer.append(face.getGenderValue()).append(",");  
                buffer.append(face.getAgeValue()).append("岁左右").append("\n");  
            }  
        }  
        // 检测到2-10张脸  
        else if (faceList.size() > 1 && faceList.size() <= 10) {  
            buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸，按脸部中心位置从左至右依次为：").append("\n");  
            for (Face face : faceList) {  
                buffer.append(face.getRaceValue()).append("人种,");  
                buffer.append(face.getGenderValue()).append(",");  
                buffer.append(face.getAgeValue()).append("岁左右").append("\n");  
            }  
        }  
        // 检测到10张脸以上  
        else if (faceList.size() > 10) {  
            buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸").append("\n");  
            // 统计各人种、性别的人数  
            int asiaMale = 0;  
            int asiaFemale = 0;  
            int whiteMale = 0;  
            int whiteFemale = 0;  
            int blackMale = 0;  
            int blackFemale = 0;  
            for (Face face : faceList) {  
                if ("黄色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        asiaMale++;  
                    else  
                        asiaFemale++;  
                else if ("白色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        whiteMale++;  
                    else  
                        whiteFemale++;  
                else if ("黑色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        blackMale++;  
                    else  
                        blackFemale++;  
            }  
            if (0 != asiaMale || 0 != asiaFemale)  
                buffer.append("黄色人种：").append(asiaMale).append("男").append(asiaFemale).append("女").append("\n");  
            if (0 != whiteMale || 0 != whiteFemale)  
                buffer.append("白色人种：").append(whiteMale).append("男").append(whiteFemale).append("女").append("\n");  
            if (0 != blackMale || 0 != blackFemale)  
                buffer.append("黑色人种：").append(blackMale).append("男").append(blackFemale).append("女").append("\n");  
        }  
        // 移除末尾空格  
        buffer = new StringBuffer(buffer.substring(0, buffer.lastIndexOf("\n")));  
        return buffer.toString();  
    }  
	
    
    public static void main(String[] args) throws Exception {
    	BaseService baseService = new FaceDetectServiceImpl();
    	String s = baseService.getServiceMsg("http://bigtu.eastday.com/img/201108/16/66/16435948291730954290.jpg");
    	System.out.println(s);
	}
    
}
