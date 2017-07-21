package com.cn.qx.web.service;

import org.json.JSONException;
import org.json.JSONObject;

public interface LoginService {
	/**
	 * 获得登录对象
	 * @param userName
	 * @param password
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getQxLoginInfo(String userName, String password) throws JSONException;
}
