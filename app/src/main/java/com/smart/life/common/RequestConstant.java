package com.smart.life.common;

import java.util.Map;

import com.smart.life.utils.HttpRequests.HttpRequestType;

import android.content.Context;

public class RequestConstant {

	public static Context context;
	public static String requestUrl;
	public static Map<String, Object> map;

	private HttpRequestType type;

	public HttpRequestType getType() {
		return type;
	}

	public void setType(HttpRequestType type) {
		this.type = type;
	}
	
	public static void setMap(Map map){
		RequestConstant.map = map;
	}

	

}
