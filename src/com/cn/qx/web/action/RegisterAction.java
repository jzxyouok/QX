package com.cn.qx.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.qx.struct.AbstractJsonAction;
import com.cn.qx.web.service.RegisterService;
import com.syit.web.MultipartRequest;
import com.syit.web.RequestFile;


public class RegisterAction extends AbstractJsonAction {
	
	@Autowired
	private RegisterService registerService;
	/**
	 * 获取新近联系人列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return getRecentUserList
	 * @throws Exception
	 */
	public ActionForward regist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("USERNAME", request.getParameter("username"));
		params.put("PASSWORD", request.getParameter("password"));
		params.put("NAME", request.getParameter("name"));
		params.put("SEX", request.getParameter("sex"));
		params.put("BIGFILE", request.getParameter("BIGFILE"));
		params.put("SMALLFILE", request.getParameter("SMALLFILE"));
		boolean isSuccess = registerService.register(params);
		writeSuccessToResponse(request, response);
		return null;
	}
	
}
