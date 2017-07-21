package com.cn.qx.web.dao;

import com.syit.dboperate.dbtable.DBTableObject;

public interface ChatDao {
	/**
	 * 查询新近联系人列表
	 * @param userId
	 * @return getRecentUserList
	 */
	public DBTableObject getRecentUserList(String userId);
	/**
	 * 查询联系薄列表
	 * @param userId
	 * @return getRecentUserList
	 */
	public DBTableObject getDirectoryList(String userId);
	/**
	 * 根据接收消息人查询发送人及其状态
	 * @param userId 发送人ID
	 * @param receiveId 接收人ID
	 * @return DBTableObject
	 */
	public DBTableObject getSendUserByReceiveUserId(String userId, String receiveId);
	/**
	 * 根据分组ID查询消息发送接收人列表
	 * @param userId 消息发送人ID
	 * @param groupId 消息接收分组ID
	 * @return DBTableObject
	 */
	public DBTableObject getGroupUserByGroupId(String userId, String groupId);
	/**
	 * 获取人员信息
	 * @param userId 用户ID
	 * @return 头像数据
	 */
	public DBTableObject getUserById(String userId);
}
