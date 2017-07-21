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


public class EncodingFilter implements Filter {
	
	private final static String UTF8CODE = "UTF-8";
	private static String loginURL = null;
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	

	public void init(FilterConfig config) throws ServletException {
		
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


	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		request.setCharacterEncoding("UTF-8");
		//XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper();
		arg2.doFilter(request, arg1);
	}
}
