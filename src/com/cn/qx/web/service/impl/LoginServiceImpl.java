package com.cn.qx.web.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.qx.util.JsonUtil;
import com.cn.qx.web.dao.LoginDao;
import com.cn.qx.web.service.LoginService;
import com.syit.dboperate.dbtable.DBTableObject;


public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;
	/**
	 * 获得登录对象
	 * @param userName
	 * @param password
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getQxLoginInfo(String userName, String password) throws JSONException{
		DBTableObject loginObj = loginDao.getQxUser(userName, password);
		if(loginObj.next()){
			return JsonUtil.getJsonObjectByDBRow(loginObj);
		}else{
			return null;
		}
	}
}
