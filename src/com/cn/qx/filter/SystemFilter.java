package com.cn.qx.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cn.qx.util.GlobalData;

public class SystemFilter implements Filter {
	
	private final static String UTF8CODE = "UTF-8";
	private static String loginURL = null;
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 过滤器
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(request instanceof HttpServletRequest){
			HttpServletRequest httpReq = (HttpServletRequest)request;
			HttpServletResponse httpResp = (HttpServletResponse)response;
			httpReq.setCharacterEncoding(UTF8CODE);
			HttpSession session = httpReq.getSession(false);
			String url = httpReq.getRequestURI();
			if (GlobalData.NOCACHEURI_PATTERN.matcher(url).matches()){
				System.out.println("无缓存：" + url);
	        	setNoCacheHeader(httpResp);
	        }
//			if (GlobalData.UNCHECKURI_PATTERN.matcher(url).matches()){
//				System.out.println("不判断session：" + url);
//	        	chain.doFilter(request, response);
//	        }else{
//		        if (!WebUtils.isPublicResquest(httpReq) 
//		        		&& GlobalData.CHECKURI_PATTERN.matcher(url).matches()){
//		        	System.out.println("判断session：" + url);
////		        	if(session==null||session.getAttribute("USER_SESSION")==null){
////		        		httpReq.setAttribute("REURL", url);
////		        		httpResp.setContentType(UTF8CODE);
////		        		httpResp.sendRedirect(httpReq.getContextPath() + loginURL);
////		        	}else{
////		        		chain.doFilter(request, response);
////		        	}
//		        	chain.doFilter(request, response);
//		        }else{
//		        	System.err.println("过滤掉的：" + url);
//		        	httpResp.sendRedirect(httpReq.getContextPath() + loginURL);
//		        }
//	        }
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig config) throws ServletException {
		loginURL = config.getInitParameter("loginURL").trim();
		
	}
	
	
	private void setNoCacheHeader(HttpServletResponse httpResp){
        // Set standard HTTP/1.1 no-cache headers.
		httpResp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		httpResp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
		httpResp.setHeader("Pragma", "no-cache");
        // Set to expire far in the past. Prevents caching at the proxy server
		httpResp.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
	}
}
