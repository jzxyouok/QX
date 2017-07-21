package com.cn.qx.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;

public class ExceptionUtil {
	/**
	 * 返回一个错误信息的执行路径信息
	 * @author Fu Qiming
	 * @param err
	 * @return
	 */
	public static String getExceptionStackTrace(Throwable err){
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		err.printStackTrace(pw);
		return sw.toString();
	}
	
	public static Throwable getOrigErr(Throwable th){
		Throwable ex=th;
		while (ex.getCause()!=null) ex=ex.getCause();
		if (ex instanceof ServletException){
			Throwable exTmp=((ServletException)ex).getRootCause();
			if (exTmp!=null) {
				ex=exTmp;
				while (ex.getCause()!=null) ex=ex.getCause();
			}
		}
		return ex;
	}	
}
