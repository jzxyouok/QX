package com.cn.qx.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import antlr.Utils;

import com.cn.qx.util.Constants;
import com.cn.qx.util.DateUtil;
import com.cn.qx.util.JsonUtil;
import com.cn.qx.util.StringUtil;
import com.cn.qx.web.dao.ChatDao;
import com.cn.qx.web.service.ChatService;
import com.mysql.jdbc.Util;
import com.syit.dboperate.dbtable.DBTableObject;

public class ChatServiceImpl implements ChatService{
	
	@Autowired
	ChatDao chatDao;
	/**
	 * 查询新近联系人列表
	 * @param userId
	 * @return JSONArray
	 * @throws JSONException 
	 */
	public JSONArray getRecentUserList(String userId) throws JSONException{
		DBTableObject recentUserObj = chatDao.getRecentUserList(userId);
		return JsonUtil.myGetJsonArrayByDBTab(recentUserObj);
	}
	
	/**
	 * 查询联系簿列表
	 * @param userId
	 * @return JSONArray
	 * @throws JSONException 
	 */
	public JSONArray getDirectoryList(String userId) throws JSONException{
		DBTableObject recentUserObj = chatDao.getRecentUserList(userId);
		return JsonUtil.myGetJsonArrayByDBTab(recentUserObj);
	}
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
	public JSONObject sendMessage(String userId, String name, String receiveId, int receiveType, String message) throws Exception{
		JSONObject resultObj = new JSONObject();
		String sendTime = DateUtil.formatDateTime();
		//0：单点发送
		if(receiveType==0){
			DBTableObject sendUserObj = chatDao.getSendUserByReceiveUserId(userId, receiveId);
			if(sendUserObj.next()){
				//用户在白名单，发送消息
				if(StringUtil.availableStr(sendUserObj.getFieldValue("WHITE_USER_ID"))){
					Event event = Event.createDataEvent(Constants.EVENT_SENDMESSAGE);
					event.setField("message", URLEncoder.encode(message, "ISO-8859-1"));//发送的消息
					event.setField("sendTime", sendTime.split(" ")[1]);//发送的时间
					event.setField("name", URLEncoder.encode(name, "ISO-8859-1"));//发送人姓名
					event.setField("sendFlag", sendUserObj.getFieldInt("REJECT_FLAG"));//接收人对于发送人消息是否免打扰
					event.setField("username", sendUserObj.getFieldValue("USERNAME"));//发送人用户名
					event.setField("sex", sendUserObj.getFieldValue("SEX"));//发送人性别
					event.setField("bz", sendUserObj.getFieldValue("BZ"));//发送人备注
					event.setField("userId", sendUserObj.getFieldValue("WHITE_USER_ID"));//发送人ID
					Dispatcher.getInstance().unicast(event, receiveId);
					//Dispatcher.getInstance().broadcast(anEvent);
					//消息发送并提醒
					resultObj.put("sendFlag", Constants.SEND_SUCCESS);
					resultObj.put("msg", "消息已推送");
				}else if(StringUtil.availableStr(sendUserObj.getFieldValue("BLACK_USER_ID"))){
					//消息发送并提醒
					resultObj.put("sendFlag", Constants.SEND_FAILED);
					resultObj.put("msg", "消息发送失败");
				}else{
					//消息发送并提醒
					resultObj.put("sendFlag", Constants.SEND_FAILED);
					resultObj.put("msg", "消息发送失败,你们不是好友");
				}
			}
			//记录日志
		}else if(receiveType==1){
			DBTableObject sendGroupObj = chatDao.getGroupUserByGroupId(userId, receiveId);
			Event event = Event.createDataEvent(Constants.EVENT_SENDMESSAGE + "_" + receiveId);
			event.setField("message", URLEncoder.encode(message, "ISO-8859-1"));//发送的消息
			event.setField("sendTime", sendTime.split(" ")[1]);//发送的时间
			event.setField("name", URLEncoder.encode(name, "ISO-8859-1"));//发送人姓名
			event.setField("groupId", receiveId);
			while(sendGroupObj.next()){
				event.setField("sendFlag", sendGroupObj.getFieldInt("REJECT_FLAG"));
				Dispatcher.getInstance().unicast(event, sendGroupObj.getFieldValue("ID"));
			}
			//消息发送并提醒
			resultObj.put("sendFlag", Constants.SEND_SUCCESS);
			resultObj.put("msg", "消息已推送");
		}else{
			//其他消息
			//消息发送并提醒
			resultObj.put("sendFlag", Constants.SEND_SUCCESS);
			resultObj.put("msg", "消息已推送");
		}
		return resultObj;
	}

	/**
	 * 获取人员大头像
	 * @param userId 用户ID
	 * @return 头像数据
	 */
	public byte[] getBigPhotoByUserId(String userId) {
		DBTableObject userObj = chatDao.getUserById(userId);
		return userObj.getFieldBlobValue_Bytes("PHOTO_BIG");
	}
	/**
	 * 获取人员小头像
	 * @param userId 用户ID
	 * @return 头像数据
	 */
	public byte[] getSmallPhotoByUserId(String userId) {
		DBTableObject userObj = chatDao.getUserById(userId);
		return userObj.getFieldBlobValue_Bytes("PHOTO_SMALL");
	}
	
}
