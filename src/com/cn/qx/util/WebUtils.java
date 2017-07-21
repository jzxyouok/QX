package com.cn.qx.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.qx.exception.ApplicationException;


public final class WebUtils {
	/**
	 * 判断空字符串
	 * @param str 字符串
	 * @return boolean
	 */
	public static boolean isEmptyStr(String str){
		if(str==null||"".equals(str.trim())||"　".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String decodeUTF8(HttpServletRequest request, String field) throws UnsupportedEncodingException{
		if(StringUtil.availableStr(request.getParameter(field))){
			return java.net.URLDecoder.decode(request.getParameter(field),"UTF-8");
		}else{
			return request.getParameter(field);
		}
	}
	
	public static boolean isPublicResquest(HttpServletRequest request){
    	Cookie[] cs=request.getCookies();
    	boolean isPub=false;
    	if (cs!=null){
    		for (int i=0;i<cs.length;i++){
    			if ("Public".equals(cs[i].getName())){
    				if ("1".equals(cs[i].getValue())) {
    					isPub=true;break;
    				}
    			}
    		}
    	}
    	return isPub;
	}
	
	/**
	 * 判断是否ajax请求
	 * @param request 
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String head = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(head)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 返回一个错误信息的执行路径信息
	 * @param err
	 * @return
	 */
	public static String getExceptionStackTrace(Throwable err){
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		err.printStackTrace(pw);
		return sw.toString();
	}	
	
	public static String convertEntityListToJsonStore(List<?> entityList) 
		throws Exception{
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		convertEntityListToJsonObject(entityList, jsonArray, null, 0);
		jsonObject.put("success", true);
		jsonObject.put("count", jsonArray.length());
		jsonObject.put("records", jsonArray);
		return jsonObject.toString();
	}
	
	private static void convertEntityListToJsonObject(List<?> entityList,
			JSONArray jsonArray, JSONObject jsonObject, int bm)throws Exception{
		if(entityList!=null){
			for(int i = 0; i < entityList.size(); i++){
				Object obj = entityList.get(i);
				if(obj!=null){
					JSONObject parentJsonObject = new JSONObject();
					if(jsonObject!=null){
						Iterator<String> jt = jsonObject.keys();
						while(jt.hasNext()){
							String tempKey = jt.next();
							parentJsonObject.put(jt.next(), jsonObject.get(tempKey));
						}
					}
					convertEntityObjectToJsonObject(obj, jsonArray, parentJsonObject, bm);
				}
			}
		}else{
			return;
		}
	}
	
	private static void convertEntityObjectToJsonObject(Object entityObject, 
			JSONArray jsonArray, JSONObject jsonObject, int bm)
		throws Exception{
		Method[] methods = entityObject.getClass().getDeclaredMethods();
		//entity是否为叶子对象
		boolean isleaf = true;
		//一个查询中是否存在多个同级list
		int toManyList = 0;
		String key = null;
		String value = null;
		Annotation annotation = null;
		for(int i = 0; i < methods.length; i++){
			toManyList = 0;
			//普通字段处理
			annotation = methods[i].getAnnotation(Column.class);
			if(annotation!=null){
				Method method = Column.class.getMethod("name");
				key = (String)method.invoke(annotation);
				if(bm!=0) key += "_" + bm;
				value = String.valueOf(methods[i].invoke(entityObject));
				jsonObject.put(key, value);
			}else{
				throw new ApplicationException("非entity对象，无法解析json");
			}
			//one-to-many处理
			annotation = methods[i].getAnnotation(OneToMany.class);
			if(annotation!=null){
				List<?> resultList = (List<?>)methods[i].invoke(entityObject);
				if(resultList!=null&&resultList.size()>0){
					if(toManyList++>0)throw new ApplicationException("查询存在笛卡尔积，无法解析json");
					//处理子集
					convertEntityListToJsonObject(resultList,jsonArray,jsonObject,++bm);
					isleaf = false;
				}
			}
			//many-to-many处理
			annotation = methods[i].getAnnotation(ManyToMany.class);
			if(annotation!=null){
				List<?> resultList = (List<?>)methods[i].invoke(entityObject);
				if(resultList!=null&&resultList.size()>0){
					if(toManyList++>0)throw new ApplicationException("查询存在笛卡尔积，无法解析json");
					//处理子集
					convertEntityListToJsonObject(resultList,jsonArray,jsonObject,++bm);
					isleaf = false;
				}
			}
			//many-to-one处理
			annotation = methods[i].getAnnotation(ManyToOne.class);
			if(annotation!=null){
				Object childEntityObject = (Object)methods[i].invoke(entityObject);
				if(childEntityObject!=null){
					//处理子集
					convertEntityObjectToJsonObject(childEntityObject,jsonArray,jsonObject,++bm);
					isleaf = false;
				}
			}
			//one-to-one处理
			annotation = methods[i].getAnnotation(OneToOne.class);
			if(annotation!=null){
				Object childEntityObject = (Object)methods[i].invoke(entityObject);
				if(childEntityObject!=null){
					//处理子集
					convertEntityObjectToJsonObject(childEntityObject,jsonArray,jsonObject,++bm);
					isleaf = false;
				}
			}
		}
		if(isleaf)jsonArray.put(jsonObject);
	}
	
	public static String getOrderStr(String sql, ServletRequest req){
		if(!WebUtils.isEmptyStr(sql)){
			String sortStr = "";
			Map<String, String[]> paramMap = req.getParameterMap();
			int index = 0;
			while(paramMap.containsKey("iSortCol_" + index)){
				if(!WebUtils.isEmptyStr(req.getParameter("iSortCol_" + index))){
					int sortIndex = Integer.parseInt(req.getParameter("iSortCol_" + index));
					String sortColumn = req.getParameter("mDataProp_" + sortIndex);
					String sortMethod = req.getParameter("sSortDir_" + index);
					if(WebUtils.isEmptyStr(sortStr)){
						sortStr += sortColumn + "  " + sortMethod;
					}else{
						sortStr += "," + sortColumn + "  " + sortMethod;
					}
				}
				index++;
			}
			if(WebUtils.isEmptyStr(sortStr)){
				return sql;
			}else{
				return " SELECT * FROM (" + sql + " ) SORTTABLE ORDER BY " + sortStr;
			}
		}
		return sql;
	}
	
}
