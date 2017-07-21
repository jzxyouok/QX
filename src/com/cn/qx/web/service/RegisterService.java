package com.cn.qx.web.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface RegisterService {
	/**
	 * 注册
	 * @param params 参数
	 * @return  注册是否成功
	 */
	public boolean register(Map<String, String> params);
	
}
