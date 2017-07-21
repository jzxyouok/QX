package com.cn.qx.web.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.qx.struct.AbstractJsonAction;
import com.cn.qx.web.service.ChatService;
import com.cn.qx.web.service.LoginService;
import com.syit.dboperate.dbtable.DBTableObject;
import com.syit.dboperate.dbtable.DBTableOper;
import com.syit.util.ObjFactory;


public class ChatAction extends AbstractJsonAction {
	
	@Autowired
	private ChatService chatService;
	/**
	 * 获取新近联系人列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return getRecentUserList
	 * @throws Exception
	 */
	public ActionForward getRecentUserList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		JSONArray recentUserArray = chatService.getRecentUserList(userId);
		//初始化聊天列表
		for(int i = 0; i < recentUserArray.length(); i++){
			JSONObject recentUser = recentUserArray.getJSONObject(i);
			recentUser.put("messageList", new JSONArray());
		}
		writeJsonArrayToResponse(request, response, recentUserArray);
		return null;
	}
	/**
	 * 获取联系薄列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return getDirectoryList
	 * @throws Exception
	 */
	public ActionForward getDirectoryList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		JSONArray recentUserArray = chatService.getDirectoryList(userId);
		writeJsonArrayToResponse(request, response, recentUserArray);
		return null;
	}
	/**
	 * 接受并发送消息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward sendMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String receiveId = request.getParameter("receiveId");
		int receiveType = Integer.parseInt(request.getParameter("receiveType"));
		String message = request.getParameter("message");
		JSONObject resultObj = chatService.sendMessage(userId, name, receiveId, receiveType, message);
		writeJsonToResponse(request, response, resultObj);
		return null;
	}
	
	/**
	 * 读取图片地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSmallPhotoByUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		byte[] fileContent = chatService.getSmallPhotoByUserId(userId);
		if(fileContent!=null && fileContent.length > 0){
	    	InputStream in = new ByteArrayInputStream(fileContent);
	    	BufferedOutputStream bout = new BufferedOutputStream(
		            response.getOutputStream());
	    	try {
		        byte b[] = new byte[1024];
		        int len = in.read(b);
		        while (len > 0) {
		            bout.write(b, 0, len);
		            len = in.read(b);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        bout.close();
		        in.close();
		    }
		}
		return null;
	}
	
	/**
	 * 读取图片地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBigPhotoByUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		byte[] fileContent = chatService.getBigPhotoByUserId(userId);
		if(fileContent!=null && fileContent.length > 0){
	    	InputStream in = new ByteArrayInputStream(fileContent);
	    	BufferedOutputStream bout = new BufferedOutputStream(
		            response.getOutputStream());
	    	try {
		        byte b[] = new byte[1024];
		        int len = in.read(b);
		        while (len > 0) {
		            bout.write(b, 0, len);
		            len = in.read(b);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        bout.close();
		        in.close();
		    }
		}
		return null;
	}
}
