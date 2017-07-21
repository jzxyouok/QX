package com.cn.qx.web.dao;

import java.util.Map;


public interface RegisterDao {
	/**
	 * 查询新近联系人列表
	 * @param params
	 * @param photo_big
	 * @param photo_small
	 * @return getRecentUserList
	 */
	public boolean register(Map<String, String> params, byte[] photo_big, byte[] photo_small);
}
