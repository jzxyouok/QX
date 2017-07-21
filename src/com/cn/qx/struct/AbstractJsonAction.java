package com.cn.qx.struct;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cn.qx.exception.ApplicationException;
import com.cn.qx.util.ExceptionUtil;
import com.cn.qx.util.WebUtils;
import com.syit.web.MultipartRequest;

public class AbstractJsonAction extends DispatchAction{
	private static Logger logger = Logger.getLogger(AbstractJsonAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			//处理文件上传
			if (isMultipart(request)){
				request = MultipartRequest.wrapRequest(request);
			}else{
				response.setContentType("application/x-json; charset=utf-8");
			}
			return super.execute(mapping, form, request, response);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			if(WebUtils.isAjaxRequest(request) || isMultipart(request)){
				generateErrJson(e, request, response);
			}else{
				writeScriptAlertToResponse(response,e.getMessage());
			}
		}
		return null;
	}
	
	protected final void setHtmlResponse(HttpServletResponse response) {
		response.reset();
		response.setContentType("text/html; charset=UTF-8");
	}
	
	protected void writeScriptAlertToResponse(HttpServletResponse response,
			String msg) {
		setHtmlResponse(response);
		try {

			String str = "<script>alert('系统出错：'+" + JSONObject.valueToString(msg)
					+ ");</script>";
			System.out.println(str);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().print(str);
//			response.flushBuffer();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 判断是否附件上传
	 * @param request
	 * @return
	 */
	protected boolean isMultipart(HttpServletRequest request) {

		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			//如果不是post方法，那么一定不是附件上传
			return false;
		}

		String contentType = request.getContentType();
		if ((contentType != null) && contentType.startsWith("multipart/form-data")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void generateErrJson(Exception e, HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		Throwable ex = ExceptionUtil.getOrigErr(e);
		log.error("Web端JsonAction基类处理出错", ex);

		JSONObject res = new JSONObject();
		res.put("success", false);
		JSONObject errInf = new JSONObject();
		if (ex instanceof ApplicationException) {
			ApplicationException appEx = (ApplicationException) ex;
			errInf.put("clientCode", appEx.getErrorCode());
			errInf.put("infType", appEx.getInfType());
			errInf.put("showDetial", false);
		} else {
			errInf.put("clientCode", "");
			errInf.put("infType", 2);
			errInf.put("showDetial", false);
		}
		if (ex.getMessage() != null) {
			errInf.put("message", ex.getMessage());
		} else {
			errInf.put("message", "未知错误");
		}
		errInf.put("stackTrace", ExceptionUtil.getExceptionStackTrace(ex));
		res.put("errors", errInf);

		if(isMultipart(request)){
			response.setContentType("text/html; charset=utf-8");
		}else{
			response.setContentType("application/x-json; charset=utf-8");
		}
		
		if(isMultipart(request)){
			StringBuffer sb = new StringBuffer(100);
			sb.append("<head><script>document.domain='").append("sse").append("';</script></head>");
			sb.append("<body>");
			sb.append(res.toString());
			sb.append("</body>");
			System.out.println(sb.toString());
			response.getWriter().write(sb.toString());
		}else{
			System.out.println(res.toString());
			response.getWriter().print(res.toString());	
		}
	}
	
	
	
	/**
	 * 向response中写入成功信息
	 * @param request
	 * @param response
	 * @throws JSONException 
	 * @throws IOException 
	 */
	protected void writeSuccessToResponse(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		writeJsonToResponse(request, response, obj);
	}
	
	
	/**
	 * 将json对象格式化后写到response中去
	 * @param request
	 * @param response
	 * @param jsonObj
	 * @throws IOException
	 */
	protected void writeJsonToResponse(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObj) throws IOException{
        boolean scriptTag = false;
        String cb = request.getParameter("callback");
        if(cb != null){
            scriptTag = true;
            response.setContentType("text/javascript; charset=UTF-8");
        } else{
        	if(WebUtils.isAjaxRequest(request)){
        		response.setContentType("application/x-json; charset=UTF-8");
        	}else{
        		response.setContentType("text/html; charset=UTF-8");
        	}
            
        }
        PrintWriter writer = response.getWriter();
        if(scriptTag)
            writer.write(cb + "(");
        writer.print(jsonObj.toString());
        if(scriptTag)
            writer.write(");");
        writer.flush();
	}
	
	/**
	 * 将json对象格式化后写到response中去
	 * @param request
	 * @param response
	 * @param jsonObj
	 * @throws IOException
	 */
	protected void writeJsonArrayToResponse(HttpServletRequest request, HttpServletResponse response, JSONArray jsoArr) throws IOException{
        boolean scriptTag = false;
        String cb = request.getParameter("callback");
        if(cb != null){
            scriptTag = true;
            response.setContentType("text/javascript; charset=UTF-8");
        } else{
        	if(WebUtils.isAjaxRequest(request)){
        		response.setContentType("application/x-json; charset=UTF-8");
        	}else{
        		response.setContentType("text/html; charset=UTF-8");
        	}
        }
        PrintWriter writer = response.getWriter();
        if(scriptTag){
//    		writer.write("document.domain='"+GlobalData.getDomainName()+"';");
            writer.write(cb + "(");
        }

        writer.print(jsoArr.toString());
        if(scriptTag){
            writer.write(");");
        }
        writer.flush();
	}
}
