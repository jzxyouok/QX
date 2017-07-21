package com.cn.qx.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.qx.struct.AbstractJsonAction;
import com.cn.qx.web.service.LoginService;


public class LoginAction extends AbstractJsonAction {
	
	@Autowired
	private LoginService loginService;
	
	public ActionForward userLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		JSONObject loginInfo = loginService.getQxLoginInfo(userName, password);
		if(loginInfo==null){
			loginInfo = new JSONObject();
			loginInfo.put("success", false);
		}
		writeJsonToResponse(request, response, loginInfo);
		return null;
	}
	
	
//	public ActionForward loginForClient(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LoginForm loginForm = (LoginForm) form;
//		String clientRand = loginForm.getTxtSN();
//		String correctRand = (String) request.getSession().getAttribute("rand");
//		if(clientRand.equals(correctRand)){
//			SystemUser user = loginService.getSystemUserInfo(loginForm);
//			if(user!=null){
//				request.getSession(true).setAttribute("USER_SESSION",user);
//				if(null!=request.getSession().getAttribute("REURL")){
//					String preUrl = (String)request.getSession().getAttribute("REURL");
//					request.getSession().removeAttribute("REURL");
//					response.sendRedirect(preUrl);
//				}else{
//					response.sendRedirect(request.getContextPath()+"/index.jsp");
//				}
//			}else{
//				response.sendRedirect(request.getContextPath()+"/login.jsp?userName=" 
//						+ java.net.URLEncoder.encode(loginForm.getUsername(),"utf-8")
//						+ "&errorCode=1");
//			}
//		}else{
//			response.sendRedirect(request.getContextPath()+"/login.jsp?userName=" 
//					+ java.net.URLEncoder.encode(loginForm.getUsername(),"utf-8")
//					+ "&errorCode=2");
//		}
//		return null;
//	}
	
	
//	public ActionForward loginForClient(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		LoginForm loginForm = (LoginForm) form;
////		String clientRand = loginForm.getTxtSN();
////		String correctRand = (String) request.getSession().getAttribute("rand");
//		SystemUser user = loginService.getSystemUserInfo(loginForm);
//		if(user!=null){
//			request.getSession(true).setAttribute("USER_SESSION",user);
//			if(null!=request.getSession().getAttribute("REURL")){
//				String preUrl = (String)request.getSession().getAttribute("REURL");
//				request.getSession().removeAttribute("REURL");
//				response.sendRedirect(preUrl);
//			}else{
//				response.sendRedirect(request.getContextPath()+"/index.html");
//			}
//		}else{
//			response.sendRedirect(request.getContextPath()+"/login.jsp?userName=" 
//					+ java.net.URLEncoder.encode(loginForm.getUserName(),"utf-8")
//					+ "&errorCode=1");
//		}
//		return null;
//	}
}
