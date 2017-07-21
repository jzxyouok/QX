package com.cn.qx.web.dao.impl;

import java.sql.Types;

import com.cn.qx.web.dao.LoginDao;
import com.syit.dboperate.PreParam;
import com.syit.dboperate.dbtable.DBTableObject;
import com.syit.dboperate.dbtable.DBTableOper;
import com.syit.util.ObjFactory;

public class LoginDaoImpl implements LoginDao{
	
	private DBTableOper dbTableOper = (DBTableOper)ObjFactory.getInstance().getObjectByKey("DBTableOper");
	/**
	 * 用户查询
	 * @param userName
	 * @param password
	 * @return DBTableObject
	 */
	public DBTableObject getQxUser(String userName, String password){
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT * FROM T_QX_USER WHERE USERNAME = ? AND PASSWORD = ? ");
		PreParam[] preParams = new PreParam[2];
		preParams[0] = new PreParam(userName, Types.VARCHAR);
		preParams[1] = new PreParam(password, Types.VARCHAR);
		return dbTableOper.getTableObjectByPreSQL(sqlBuf.toString(), preParams);
	}
}
