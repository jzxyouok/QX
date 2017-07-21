package com.cn.qx.exception;

public class ApplicationException extends RuntimeException{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5687103400979439551L;
	/**
	 * 警告,此时系统不记日志
	 */
	public static final int INFTYPE_WARNING=1;
	/**
	 * 错误,此时系统记日志
	 */
	public static final int INFTYPE_ERROR=2;
	
	private int infType = INFTYPE_WARNING;
	
	private boolean showDetial = false;
	
	private String errorCode = "";

	public int getInfType() {
		return infType;
	}

	public void setInfType(int infType) {
		this.infType = infType;
	}

	public boolean isShowDetial() {
		return showDetial;
	}

	public void setShowDetial(boolean showDetial) {
		this.showDetial = showDetial;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public ApplicationException() {
    	super();
    }
	
	public ApplicationException(String message){
		super(message);
		this.setInfType(INFTYPE_WARNING);
	}
	
	public ApplicationException(Throwable cause) {
    	super(cause);
    	this.setInfType(INFTYPE_ERROR);
    }
    
    public ApplicationException(String message, Throwable cause) {
    	super(message,cause);
    	this.setInfType(INFTYPE_ERROR);
    	
    }
    
    public ApplicationException(String message,int infType) {
    	super(message);
    	this.setInfType(infType);
    }
    
    public ApplicationException(String message, Throwable cause,int infType) {
    	super(message,cause);
    	this.setInfType(infType);
    }

}
