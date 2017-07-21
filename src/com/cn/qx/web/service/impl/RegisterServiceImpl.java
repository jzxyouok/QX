package com.cn.qx.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;

import org.codehaus.xfire.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import antlr.Utils;

import com.cn.qx.util.Constants;
import com.cn.qx.util.DateUtil;
import com.cn.qx.util.JsonUtil;
import com.cn.qx.util.StringUtil;
import com.cn.qx.util.UUIDUtil;
import com.cn.qx.web.dao.ChatDao;
import com.cn.qx.web.dao.RegisterDao;
import com.cn.qx.web.service.ChatService;
import com.cn.qx.web.service.RegisterService;
import com.mysql.jdbc.Util;
import com.syit.dboperate.dbtable.DBTableObject;

public class RegisterServiceImpl implements RegisterService{
	
	@Autowired
	RegisterDao registerDao;
	/**
	 * 注册
	 */
	public boolean register(Map<String, String> params){
		String fileName = UUIDUtil.getUUID() + DateUtil.formatDateTime("yyyyMMddHHmmss");
		params.put("PHOTO_FILENAME", fileName);
		if(StringUtil.availableStr(params.get("BIGFILE"))){
			params.put("BIGFILE", params.get("BIGFILE").split(",")[1]);
		}
		if(StringUtil.availableStr(params.get("SMALLFILE"))){
			params.put("SMALLFILE", params.get("SMALLFILE").split(",")[1]);
		}
		byte[] photo_big = Base64.decode(params.get("BIGFILE"));
		byte[] photo_small = Base64.decode(params.get("SMALLFILE"));
		return registerDao.register(params, photo_big, photo_small);
	}
	
}
