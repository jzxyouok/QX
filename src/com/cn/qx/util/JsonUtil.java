
package com.cn.qx.util;

import java.sql.Types;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.syit.dboperate.RecPageInfor;
import com.syit.dboperate.dbtable.DBTabAndPageInfo;
import com.syit.dboperate.dbtable.DBTableColumn;
import com.syit.dboperate.dbtable.DBTableObject;
import com.syit.dboperate.dbtable.DBTableRow;


/**
 *  原来Utils类中的JSON相关操作 <p>
 * 创建日期：2012-4-17<br>
 * 修改历史：<br>
 * 修改日期：2012-4-17<br>
 * 修改作者：Administrator<br>
 * 修改内容：创建类<br>
 * @author Administrator
 * @version 1.0
 */
public class JsonUtil {
	
	/**
	 * 私有构造
	 */
	private JsonUtil() {}
	
	/**
	 * 常量20
	 */
	private static final int TWENTY = 20;
	
	/**
	 * 常量25
	 */
	private static final int TWENTYFIVE = 25;
	
	/**
	 * 常量40
	 */
	private static final int FORTY = 40;
	
	/**
	 * 常量50
	 */
	private static final int FIFTY = 50;
	
	/**
	 * 把DBTableObject对象转化成JSONStore对象相关的JSON字符串
	 * 
	 * @param tabObj tabOjb
	 * @param rpi rpi
	 * @return
	 * @throws JSONException JSONException
	 * @author Fu Qiming
	 * @return Json的String形式
	 */
	public static String getJsonStoreDataByTabObj(DBTableObject tabObj,
			RecPageInfor rpi) throws JSONException {
		int initSize = tabObj.size() * (tabObj.getColInfor().size()) * FORTY + FORTY;
		StringBuffer sbf = new StringBuffer(initSize);
		sbf.append("{count:");
		if (rpi != null)
			sbf.append(rpi.getRecCount());
		else
			sbf.append(tabObj.size());
		sbf.append(",records:");
		fillJSONArrayStrFromTabObj(tabObj, sbf);
		sbf.append("}");
		return sbf.toString();
	}

	/**
	 * 把DBTableObject对象转化成JSONArray字符串
	 * 
	 * @param tabObj tabObj
	 * @return Json的String形式
	 * @author Fu Qiming
	 * @throws JSONException JSONException
	 */
	public static String getJSONArrayStrFromTabObj(DBTableObject tabObj)
			throws JSONException {
		DBTableColumn dbColumn = tabObj.getColInfor();
		int colCnt = dbColumn.size();
		StringBuffer sbf = new StringBuffer(tabObj.size() * colCnt * FORTY);
		fillJSONArrayStrFromTabObj(tabObj, sbf);
		return sbf.toString();
	}

	/**
	 * fillJsonArrayStrFormTabObj
	 * @param tabObj tabObj
	 * @param sbf sbf
	 * @throws JSONException JsonException
	 */
	private static void fillJSONArrayStrFromTabObj(DBTableObject tabObj,
			StringBuffer sbf) throws JSONException {
		sbf.append("[");
		tabObj.reset();
		boolean isFirst = true;
		while (tabObj.next()) {
			if (!isFirst) {
				sbf.append(",");
			} else
				isFirst = false;
			fillJSONObjStrFromTabObjRow(tabObj, sbf);
		}
		sbf.append("]");
	}

	/**
	 * 根据tabObj转换json字符串
	 * @param tabObj tabObj
	 * @return Json的String形式
	 */
	public static String getJSONStrFromTabObjRow(DBTableObject tabObj) {
		DBTableColumn dbColumn = tabObj.getColInfor();
		int colCnt = dbColumn.size();
		StringBuffer sbf = new StringBuffer(colCnt * TWENTY);
		try {
			fillJSONObjStrFromTabObjRow(tabObj, sbf);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return sbf.toString();
	}

	/**
	 * 根据tabObj转换json字符串
	 * @param tabObj tabObj
	 * @param sbf sbf
	 * @throws JSONException JSONException
	 */
	private static void fillJSONObjStrFromTabObjRow(DBTableObject tabObj,
			StringBuffer sbf) throws JSONException {
		DBTableColumn dbColumn = tabObj.getColInfor();
		int colCnt = dbColumn.size();
		sbf.append("{");
		for (int i = 0; i < colCnt; i++) {
			if (i > 0)
				sbf.append(",");
			String value = tabObj.getFieldValue(i);
			int type = dbColumn.getType(i);
			if (type == Types.CHAR || type == Types.VARCHAR
					|| type == Types.LONGVARCHAR || type == Types.CLOB) {
				value = JSONObject.valueToString(value);
			} else {
				if (value == null)
					value = "null";
				else
					value = '"' + value + '"';
			}
			sbf.append(dbColumn.get(i)).append(":").append(value);
		}
		sbf.append("}");
	}

	/**
	 * 根据tabObj转换json字符串
	 * @param tabObj tabObj
	 * @param blanketNeeded blanketNeeded
	 * @return Json的String形式
	 */
	public static String getJSONStrFromTabObjRow(DBTableObject tabObj,
			boolean blanketNeeded) {
		if (blanketNeeded) {
			return getJSONStrFromTabObjRow(tabObj);
		} else {
			DBTableColumn dbColumn = tabObj.getColInfor();
			int colCnt = dbColumn.size();
			StringBuffer sbf = new StringBuffer(colCnt * TWENTY);
			try {
				fillJSONObjStrFromTabObjRow(tabObj, sbf, blanketNeeded);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
			return sbf.toString();
		}
	}

	/**
	 * fillJsonObjStrFromTabObjRow
	 * @param tabObj tabObj
	 * @param sbf sbf
	 * @param blanketNeeded blanketNeeded
	 * @throws JSONException JSONException
	 */
	private static void fillJSONObjStrFromTabObjRow(DBTableObject tabObj,
			StringBuffer sbf, boolean blanketNeeded) throws JSONException {
		if (blanketNeeded) {
			fillJSONObjStrFromTabObjRow(tabObj, sbf);
		} else {
			DBTableColumn dbColumn = tabObj.getColInfor();
			int colCnt = dbColumn.size();
			for (int i = 0; i < colCnt; i++) {
				if (i > 0)
					sbf.append(",");
				String value = tabObj.getFieldValue(i);
				int type = dbColumn.getType(i);
				if (type == Types.CHAR || type == Types.VARCHAR
						|| type == Types.LONGVARCHAR || type == Types.CLOB) {
					value = JSONObject.valueToString(value);
				} else {
					if (value == null)
						value = "null";
					else
						value = '"' + value + '"';
				}
				sbf.append(dbColumn.get(i)).append(":").append(value);
			}
		}
	}
	
	/**
	 * 返回Form提交后处理不成功信息的JSON给客户端
	 * @param msg 不成功信息
	 * @return json字符串
	 * @author Fu Qiming
	 */
	public static String getFormTipJsonInfo(String msg){
		return getFormTipJsonInfo(msg,"");
	}	
	
	/**
	 * 返回Form提交后处理不成功信息的JSON给客户端
	 * @param msg 不成功信息
	 * @param detailMessage 不成功的详细信息
	 * @return jsonStr
	 * @author Fu Qiming
	 */
	public static String getFormTipJsonInfo(String msg,String detailMessage){
		StringBuffer sbf=new StringBuffer(FIFTY);
		try {
			sbf.append("{success:false,errors:{infType:1,message:").append(JSONObject.valueToString(msg))
				.append(",detailMessage:").append(JSONObject.valueToString(detailMessage))
				.append("}}");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return sbf.toString();
	}
	/**
	 * 返回Form提交后处理成功的JSON给客户端
	 * @param jsonStr 要返回给客户端的JSON数据，通过data可获取
	 * @return json字符串
	 * @author Fu Qiming
	 */
	public static String getFormSuccessJsonInfo(String jsonStr){
		return getFormSuccessJsonInfo(jsonStr,true);
	}
	/**
	 * 返回Form提交后处理成功的JSON给客户端
	 * @param jsonStr 要返回给客户端的JSON数据，通过data可获取
	 * @param needWrap 是否需要指定用data属性，如果已经自己指定则不需要
	 * @return json字符串
	 * @author Fu Qiming
	 */
	public static String getFormSuccessJsonInfo(String jsonStr,boolean needWrap){
		if (StringUtil.availableStr(jsonStr)){
			StringBuffer sbf=new StringBuffer(TWENTYFIVE+jsonStr.length());
			sbf.append("{success:true,");
			if (needWrap) {
				sbf.append("data:").append(jsonStr).append("}");
			}else{
				sbf.append(jsonStr).append("}");
			}
			return sbf.toString();
		}else{
			return "{success:true}";
		}
	}	
	
	/**
	 * 把EXT的分页请求信息转化成RecPageInfor对象
	 * @param req req
	 * @return 分页信息
	 * @author Fu Qiming
	 */
	public static RecPageInfor getRecPageInfor(ServletRequest req){
		String tmp=req.getParameter("pagination");
		if (tmp==null || "yes".equalsIgnoreCase(tmp) || "1".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp)){
			String limit=req.getParameter("limit");
			if (limit==null){
				limit=req.getParameter("pageSize");
			}
			return getRecPageInfor(req.getParameter("start"),limit);
		}else return null;
	}
	
	/**
	 * 把EXT的分页请求信息转化成RecPageInfor对象
	 * @param req req
	 * @return 分页信息
	 * @author xusi
	 */
	public static RecPageInfor getRecPageInforByJQuery(ServletRequest req){
		String tmp=req.getParameter("pagination");
		if (tmp==null || "yes".equalsIgnoreCase(tmp) || "1".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp)){
			String limit=req.getParameter("iDisplayLength");
			if (limit==null){
				limit=req.getParameter("iDisplayLength");
			}
			return getRecPageInfor(req.getParameter("iDisplayStart"),limit);
		}else return null;
	}
	
	/**
	 * 把EXT的分页请求信息转化成RecPageInfor对象
	 * @param start start
	 * @param limit limit
	 * @return 分页信息
	 * @author Fu Qiming
	 */
	public static RecPageInfor getRecPageInfor(String start,String limit){
		RecPageInfor rpi=new RecPageInfor(TWENTY,1);
		if (StringUtil.availableStr(limit)){
			rpi.setCntPerPage(Integer.parseInt(limit));
			if (StringUtil.availableStr(start)){
				int iTmp=Integer.parseInt(start);
				iTmp=iTmp/rpi.getCntPerPage()+1;
				rpi.setIPage(iTmp);
			}
		}
		return rpi;
	}
	
	
	/**
	 * 将DBOper中的带分页信息的DBTableObject转换为一个JSON对象，包含success和count信息
	 * @param pagedDBTab Dboper中的DBTabAndPageInfo对象
	 * @return JSON对象
	 * @throws JSONException JSONException
	 */
	public static JSONObject getJsonObjByPagedDBTab(DBTabAndPageInfo pagedDBTab) throws JSONException {
		JSONObject jsonObj = new JSONObject();
    	jsonObj.put("success",true);
		jsonObj.put("count",pagedDBTab.getRpi().getRecCount() );
		jsonObj.put("records",myGetJsonArrayByDBTab(pagedDBTab.getTabobj()));
		return jsonObj;
	}

	public static JSONObject getJsonObjByPagedDBTabJQuery(DBTabAndPageInfo pagedDBTab, 
			HttpServletRequest request) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("sEcho", request.getParameter("sEcho"));
		jsonObj.put("iTotalRecords", pagedDBTab.getRpi().getRecCount());
		jsonObj.put("iTotalDisplayRecords", pagedDBTab.getRpi().getRecCount());
		jsonObj.put("aaData", myGetJsonArrayByDBTab(pagedDBTab.getTabobj()));
		return jsonObj;
	}
	
	
	/**
	 * 将DBOper查询到的多条数据转换为一个JSON对象，包含success和count信息
	 * @param dbTab Dboper中的DBTableObject对象
	 * @return JSON对象
	 * @throws JSONException JSONException
	 */
	public static JSONObject getJsonObjectByDBTab(DBTableObject dbTab) throws JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success",true);
		jsonObj.put("count",dbTab.size() );
		jsonObj.put("records",myGetJsonArrayByDBTab(dbTab));
		return jsonObj;
	}
	
	/**
	 * 将DBOper查询返回的一条记录转换为JSON格式，包括success信息
	 * @param row DBOper中的DBTableRow对象
	 * @return 该记录转换的JSON格式数据
	 * @throws JSONException JSONException
	 */
	public static JSONObject getJsonObjectByDBRow(DBTableRow row) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success",true);
		JSONObject data =myGetJsonObjectByDBRow(row);
		jsonObj.put("data",data);
		return jsonObj;
	}
	
	/**
	 * 将DBOper查询返回的一条记录转换为JSON格式，包括success信息
	 * @param obj DBOper中的DBTableObject对象，但仅仅转换其中一行数据
	 * @return 该记录转换的JSON格式数据
	 * @throws JSONException JSONException
	 */
	public static JSONObject getJsonObjectByDBRow(DBTableObject obj) throws JSONException{
		DBTableRow row = obj.getCurrRow();

		return getJsonObjectByDBRow(row);
	}
	
	/**
	 * 将DBOper中的DBTableObject对象转换为一个JSONArray，不包含count和row信息
	 * @param dbTab DBTableObject对象
	 * @return JSONArray 对象
	 * @throws JSONException JSONException
	 */
	public static JSONArray myGetJsonArrayByDBTab(DBTableObject dbTab) throws JSONException{
		JSONArray jsonArray =  new JSONArray();
		for (Object o : dbTab) {
			DBTableRow row  =(DBTableRow) o;
			JSONObject jsonObject = myGetJsonObjectByDBRow(row);
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * 将一个DBOper中的一个DBTableRow对象转换为JSON对象，不包含success和data信息
	 * @param row DBTableRow
	 * @return  JSON对象
	 * @throws JSONException JSONException
	 */
	public static JSONObject myGetJsonObjectByDBRow(DBTableRow row) throws JSONException{
		
		
		JSONObject  jsonObject= new JSONObject();
		if(row == null){
			//如果row为空，直接返回空的JSON Object
			return jsonObject;
		}
		DBTableColumn dbColumn =  row.getColInfor();
		int colCnt = dbColumn.capSize();
		for (int i = 0; i < colCnt; i++) {
			String value = row.getFieldValue(i);
			//所有的json的属性名都转为大写
			String colName = dbColumn.get(i).toUpperCase();
			jsonObject.put(colName, value);
		}
		return jsonObject;
	}
	
	/**
	 * 将jsonObj中所有的数据，fill到dbObj中
	 * @param dbObj dbObj
	 * @param jsonObj jsonObj
	 * @throws JSONException  JSONException
	 */
	@SuppressWarnings("unchecked")
	public static void fillDBTableWithJson(DBTableObject dbObj,JSONObject jsonObj) throws JSONException{
		for(Iterator<String> keys = jsonObj.keys();keys.hasNext();){
			
			String key = keys.next();
			
			//碰到key属性（大部分时间就是ID），那么就不填充值
			if(key.equalsIgnoreCase( dbObj.getKeyField())){
				continue;
			}
			String value = jsonObj.getString(key);
			dbObj.setFieldValue(key, value);
		}
	}
	
	/**
	 * 将Map数据集合转化为json
	 * @param map 数据集合
	 * @param jsonObj json
	 */
	public static void fillMapWithJson(Map<String, String> map,JSONObject jsonObj){
		Iterator<Entry<String, String>> iter =  map.entrySet().iterator();
		try {
			while(iter.hasNext()){
				Entry<String, String > entry = iter.next();
				jsonObj.put(entry.getKey(), entry.getValue());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
