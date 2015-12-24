package com.weixin.zjjfb.key;

public enum InterfaceKey {

	KEY("879a6cb3afb84dbf4fc84a1df2ab7319"),

	APIKEY("628019e572860bb7dac86973d01e0fe5"),
	
	APPKEY("1307ee261de8bbcf83830de89caae73f"), 
	
	FACEPLUSPLUS_APIKEY("6ee34ef6484002565e08fb6b6cce4bb9"),
	
	FACEPLUSPLUS_APISECRET("5dEs9QE5YXy4w2wHXIriZ9Jrzpv_VB-P"),
	
	FACEPLUSPLUS_DETECH_URL("http://apicn.faceplusplus.com/v2/detection/detect?api_key=API_KEY&api_secret=API_SECRET&url=URL"),

	BAIDU_TULING_URL("http://www.tuling123.com/openapi/api?key=KEY&info=INFO"),
	
	BAIDU_EXPRESS_URL("http://apis.baidu.com/ppsuda/waybillnoquery/waybillnotrace?expresscode=EXPRESSCODE&billno=BILLNO"),
	
	BAIDU_PHONE_HOME_URL("http://apis.baidu.com/apistore/mobilenumber/mobilenumber?phone=PHONE"),
	
	BAIDU_IP_HOME_URL("http://apis.baidu.com/apistore/iplookupservice/iplookup?ip=IP_HOME"),

	BAIDU_TRANSLATE_URL("http://apis.baidu.com/apistore/tranlateservice/translate?query=QUERY&from=FROM&to=TO"),
	
	BAIDU_TODAYHISTORY_URL("http://apis.baidu.com/netpopo/todayhistory/todayhistory?month=MONTH&day=DAY&appkey=APPKEY"),
	
	BAIDU_WEATHER_URL("http://apis.baidu.com/apistore/weatherservice/cityname?cityname=CITYNAME"),
	
	BAIDU_VOICE_URL("http://apis.baidu.com/apistore/vop/baiduvopjson");

	private InterfaceKey(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
