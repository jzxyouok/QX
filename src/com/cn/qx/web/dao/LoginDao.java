package com.cn.qx.web.dao;

import com.syit.dboperate.dbtable.DBTableObject;

public interface LoginDao {
	
	/**
	 * 用户查询
	 * @param userName
	 * @param password
	 * @return DBTableObject
	 */
	public DBTableObject getQxUser(String userName, String password);
	
}
