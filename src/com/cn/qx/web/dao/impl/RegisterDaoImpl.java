package com.cn.qx.web.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.sql.RowSet;

import com.cn.qx.util.Constants;
import com.cn.qx.web.dao.RegisterDao;
import com.syit.dboperate.DBOper;
import com.syit.dboperate.PreParam;
import com.syit.dboperate.dbtable.DBTableObject;
import com.syit.dboperate.dbtable.DBTableOper;
import com.syit.util.ObjFactory;

public class RegisterDaoImpl implements RegisterDao{
	
	private DBTableOper dbTableOper = (DBTableOper)ObjFactory.getInstance().getObjectByKey("DBTableOper");
	/** 人员信息表 */
	private final static String T_QX_USER = "T_QX_USER";
	/** 获取序列 */
	public static final String next_seq = "SELECT NEXTVAL('hSeq')";
	/**
	 * 获取序列
	 * @return
	 */
	private static long getNextSeq(){
		DBOper dbOper = (DBOper)ObjFactory.getInstance().getObjectByKey("DBOper");
		RowSet rs = dbOper.executeAllQuery(next_seq);
		try {
			if(rs.next()){
				return rs.getLong("NEXTVAL('hSeq')");
			}else{
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	} 
	
	/**
	 * 查询新近联系人列表
	 * @param params
	 * @param photo_big
	 * @param photo_small
	 * @return getRecentUserList
	 */
	public boolean register(Map<String, String> params, byte[] photo_big, byte[] photo_small){
		DBTableObject userObj = dbTableOper.getTableObject(T_QX_USER, "USERNAME", params.get("USERNAME"));
		if(userObj.next()){
			return false;
		}else{
			userObj = new DBTableObject();
			userObj.setTableName(T_QX_USER);
			userObj.setWillDo(DBTableObject.TABINSERTDO);
			userObj.setFieldValue("ID", getNextSeq());
			userObj.setFieldValue("USERNAME", params.get("USERNAME"));
			userObj.setFieldValue("PASSWORD", params.get("PASSWORD"));
			userObj.setFieldValue("NAME", params.get("NAME"));
			userObj.setFieldValue("SEX", params.get("SEX"));
			userObj.setFieldValue("BZ", params.get("BZ"));
			userObj.setFieldValue("PHOTO_FILENAME", params.get("PHOTO_FILENAME"));
			userObj.setFieldBlobValue("PHOTO_BIG", photo_big);
			userObj.setFieldBlobValue("PHOTO_SMALL", photo_small);
			dbTableOper.executeTabObject(userObj);
			return true;
		}
	}
	/**
	 * 查询联系薄列表
	 * @param userId
	 * @return getRecentUserList
	 */
	public DBTableObject getDirectoryList(String userId){
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT B.ID, B.USERNAME, B.NAME, B.SEX, B.BZ, LEFT(getPY(B.NAME),1) LETTER FROM T_QX_DIRECTORY A ");
		sqlBuf.append(" JOIN T_QX_USER B ON A.USERID = B.ID ");
		sqlBuf.append(" WHERE A.USER_ID = ? AND A.TYPE = ? ORDER BY getPY(B.NAME) ");
		PreParam[] preParams = new PreParam[2];
		preParams[0] = new PreParam(userId, Types.VARCHAR);
		preParams[1] = new PreParam(Constants.USER_TYPE_USER, Types.VARCHAR);
		return dbTableOper.getTableObjectByPreSQL(sqlBuf.toString(), preParams);
	}
	/**
	 * 根据接收消息人查询发送人及其状态
	 * @param userId 发送人ID
	 * @param receiveId 接收人ID
	 * @return DBTableObject
	 */
	public DBTableObject getSendUserByReceiveUserId(String userId, String receiveId){
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT A.ID, B.WHITE_USER_ID, B.REJECT_FLAG, C.BLACK_USER_ID, ");
		sqlBuf.append(" D.NAME, D.USERNAME, D.SEX, D.BZ ");
		sqlBuf.append(" FROM T_QX_USER A ");
		sqlBuf.append(" LEFT JOIN T_QX_USERWHITE B ON A.ID = B.USER_ID AND B.WHITE_USER_ID = ? ");//白名单
		sqlBuf.append(" JOIN T_QX_USER D ON B.WHITE_USER_ID = D.ID ");
		sqlBuf.append(" LEFT JOIN T_QX_USERBLACK C ON A.ID = C.USER_ID AND C.BLACK_USER_ID = ? ");//黑名单
		sqlBuf.append(" WHERE A.ID = ? ");
		PreParam[] preParams = new PreParam[3];
		preParams[0] = new PreParam(userId, Types.BIGINT);
		preParams[1] = new PreParam(userId, Types.BIGINT);
		preParams[2] = new PreParam(receiveId, Types.BIGINT);
		return dbTableOper.getTableObjectByPreSQL(sqlBuf.toString(), preParams);
	}
	/**
	 * 根据分组ID查询消息发送接收人列表
	 * @param userId 消息发送人ID
	 * @param groupId 消息接收分组ID
	 * @return DBTableObject
	 */
	public DBTableObject getGroupUserByGroupId(String userId, String groupId){
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT C.ID, C.NAME, C.USERNAME, B.REJECT_FLAG  ");
		sqlBuf.append(" FROM T_QX_GROUP A ");
		sqlBuf.append(" JOIN T_QX_GROUPUSER B ON A.GROUP_ID = B.ID ");
		sqlBuf.append(" JOIN T_QX_USER C ON C.ID = B.USER_ID ");
		sqlBuf.append(" JOIN T_QX_USERBLACK D ON C.ID = D.USER_ID AND D.BLACK_USER_ID = ? ");
		sqlBuf.append(" WHERE A.ID = ? AND C.ID <> ? ");
		PreParam[] preParams = new PreParam[3];
		preParams[0] = new PreParam(userId, Types.BIGINT);
		preParams[1] = new PreParam(groupId, Types.BIGINT);
		preParams[2] = new PreParam(userId, Types.BIGINT);
		return dbTableOper.getTableObjectByPreSQL(sqlBuf.toString(), preParams);
	}
}
