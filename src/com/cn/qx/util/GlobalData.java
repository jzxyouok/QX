package com.cn.qx.util;

import java.io.Serializable;
import java.util.regex.Pattern;

public final class GlobalData implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 不判断Session的URI的正则表达式
	 */
	private static final String UNCHECKURI_REGEX="(.*/system.do)|(.*/login.*)|(.*/image.*)|(.*/jpg.*)|(.*/png.*)|(.*/css.*)|(.*/ext/.*)|(.*/public.*)";
	public static final Pattern UNCHECKURI_PATTERN =Pattern.compile(UNCHECKURI_REGEX);
	/**
	 * 判断Session的URI的正则表达式
	 */	
	private static final String CHECKURI_REGEX="(.*\\.jsp)|(.*\\.do)|(.*\\.js)";
	public static final Pattern CHECKURI_PATTERN=Pattern.compile(CHECKURI_REGEX);
	/**
	 * 不缓存的URI的正则表达式(开发时不缓存JS)
	 */	
	private static String NOCACHEURI_REGEX="(.*\\.jsp)|(.*\\.do)|(.*\\.js)";
	public static Pattern NOCACHEURI_PATTERN=Pattern.compile(NOCACHEURI_REGEX);
}
