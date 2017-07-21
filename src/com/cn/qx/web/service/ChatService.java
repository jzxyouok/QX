package com.cn.qx.web.service;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.syit.dboperate.dbtable.DBTableObject;

public interface ChatService {
	/**
	 * 查询新近联系人列表
	 * @param userId
	 * @return JSONArray
	 * @throws JSONException 
	 */
	public JSONArray getRecentUserList(String userId) throws JSONException;
	/**
	 * 查询联系簿列表
	 * @param userId
	 * @return JSONArray
	 * @throws JSONException 
	 */
	public JSONArray getDirectoryList(String userId) throws JSONException;
	/**
	 * 发送消息
	 * @param userId 发送人ID
	 * @param name 发送人姓名
	 * @param receiveId 接收人/群ID
	 * @param receiveType  接收人/群
	 * @param message 消息
	 * @throws UnsupportedEncodingException 
	 * @throws JSONException 
	 */
	public JSONObject sendMessage(String userId, String name, String receiveId, int receiveType, String message) throws Exception;
	/**
	 * 获取人员大头像
	 * @param userId 用户ID
	 * @return 头像数据
	 */
	public byte[] getBigPhotoByUserId(String userId);
	/**
	 * 获取人员小头像
	 * @param userId 用户ID
	 * @return 头像数据
	 */
	public byte[] getSmallPhotoByUserId(String userId);
}
