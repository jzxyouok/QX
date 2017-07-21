/*
 * StringUtil.java
 * 版权所有：江苏电力信息技术有限公司 2007 - 2012
 * 江苏电力信息技术有限公司保留所有权利，未经允许不得以任何形式使用。
 */
package com.cn.qx.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

/**
 * 将原有Utils中的String相关方法移入本类
 * @author Administrator
 *
 */
public class StringUtil extends StringUtils{
	/**
	 * 3
	 */
	private static final int INT3 = 3;
	/**
	 * 4
	 */
	private static final int INT4 = 4;
	/**
	 * 16
	 */
	private static final int INT16 = 16;
	/**
	 * 0x4e00
	 */
	private static final int INT0X4E00 = 0x4e00;
	/**
	 * 0x9fa5
	 */
	private static final int INT0X9FA5 = 0x9fa5;
	/**
	 * 0xf900
	 */
	private static final int INT0XF900 = 0xf900;
	/**
	 * 0xfa2d
	 */
	private static final int INT0XFA2D = 0xfa2d;
	
	/**
	 * 判断一个字符串是否有值，空格也不算有值
	 * @author Fu Qiming
	 * @param str 
	 * @return boolean
	 */
	public static boolean availableStr(String str){
		if(str!=null && !"".equals(str) && !"null".equals(str) && !"　".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
     * 截取子字符串
     *
     * @param str 需要截取的字符串
     * @param len 截取的位数
     * @return String 返回的子字符串
     */
    public static String getSubString(String str, int len) {
        if (isEmpty(str)) {
            return "";
        }

        if (0 >= len) {
            return str;
        }

        final int byteLength = getByteLength(str);

        if (byteLength < len) {
            return str;
        }

        StringBuffer sb = new StringBuffer(str);
        sb.setLength(len);

        while (getByteLength(sb.toString()) > len) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * 得到字节长度
     *
     * @param str 需要得到长度的字符串
     * @return 返回字节的长度
     */
    public static int getByteLength(String str) {
        int len = 0;

        if (isEmpty(str)) {
            return len;
        }

        try {
			len = str.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

        return len;
    }
    
    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return true：空 false：非空
     */
    public static boolean isEmpty(String str) {

        if (null == str || 0 == str.length()||"null".equals(str)||"".equals(str)) {
            return true;
        }

        return false;
    }

	/**
	 * 把字符串中的双引号和反斜杠替换为画面能够识别的字符串
	 * @param inStr 
	 * @return outStr
	 */
	public static String escapeSpecialChar(String inStr){
		if (null == inStr || "null".equals(inStr) || "".equals(inStr)) {
			return "";
		}

		String outStr = "";
		
		//替换双引号
		String srcStr1 = "\"";	
		String destStr1 = "\\\\\"";  

		
		//替换反斜杠
		String srcStr2 = "\\\\";
		String destStr2 = "\\\\\\\\";
		
		outStr = inStr.replaceAll(srcStr2, destStr2);
		outStr = outStr.replaceAll(srcStr1, destStr1);
		
		return outStr;
		
	}
	
	/**
	 * 将String中的xml格式的Excel的关键字转义
	 * @param str 
	 * @return String
	 */
	public static String encodeExcelXmlStr(String str){
		if(isEmpty(str)){
			return str;
		}
		return str.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\"","&quot;" )
				.replaceAll("\r\n","&#10;")
				.replaceAll("\n","&#10;");
	}
	
	
	/**
	 *判断输入字符串是否为整型数据 
	 * @param str	字符串
	 * @return	boolean
	 */
	public static boolean isInteger(String str) {
		if(availableStr(str)) {
			try {
				Integer.parseInt(str);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 *判断输入字符串是否为长整型数据 
	 * @param str	字符串
	 * @return	boolean
	 */
	public static boolean isLong(String str) {
		if(availableStr(str)) {
			try {
				Long.parseLong(str);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 *判断输入字符串是否为单精度浮点型数据
	 * @param str	字符串
	 * @return	boolean
	 */
	public static boolean isFloat(String str) {
		if(availableStr(str)) {
			try {
				Float.parseFloat(str);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 *判断输入字符串是否为双精度浮点型数据
	 * @param str	字符串
	 * @return	boolean
	 */
	public static boolean isDouble(String str) {
		if(availableStr(str)) {
			try {
				NumberUtil.parseDouble(str);
//				原有Double.parseDouble(str) 有安全漏洞 
//				Double.parseDouble(str);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 判断字符是否为汉字（汉字标点不为汉字字符）
	 * @param oneChar 
	 * @return boolean
	 */
	public static boolean isChineseWithoutPunctuation(char oneChar){
		if((oneChar >= INT0X4E00 && oneChar <= INT0X9FA5)
		 ||(oneChar >= INT0XF900 && oneChar <=INT0XFA2D)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符是否为汉字（汉字标点为汉字字符）
	 * @param oneChar 
	 * @return boolean
	 */
	public static boolean isChineseWithPunctuation(char oneChar) {
    	Character.UnicodeBlock ub = Character.UnicodeBlock.of(oneChar);
    	if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS 
    	 || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS 
    	 || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A 
    	 || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B 
    	 || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION 
    	 || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS 
    	 || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
             return true; 
         } 
         return false; 
     } 

	
	/**
	 * 把ip地址格式化为：000.000.000.000
	 * @param ip 
	 * @return 返回规格ip
	 */
	public static String strfullip(String ip){
		StringBuffer buff = new StringBuffer();
		buff.append("");
		String strzero = "000";
		int ilen = 0;
		if(ip != null){
			String[] arrip = ip.split("\\.");
			if(arrip.length == INT4){
				for(int i = 0; i < INT4; i++){
					if (i==0){
						ilen = arrip[i].length();
						if(ilen < INT3){
							buff.append(strzero.substring(0,INT3-ilen)).append(arrip[i]);
						}else{
							buff.append(arrip[i]);
						}
					}else{
						ilen = arrip[i].length();
						if(ilen < INT3){
							buff.append(".").append(strzero.substring(0,INT3-ilen)).append(arrip[i]);
						}else{
							buff.append(".").append(arrip[i]);
						}
					}
				}
			}
		}
		return buff.toString();
	}
	
    /**
     * 根据传入的字符串和编码取得对应的编码的字符串，主要用于前后台参数传递场景，防止出现乱码
     * @param str 
     * @param charSet 
     * @return String
     */
	public static String getStringByCharset(String str, String charSet){
		if (isEmpty(str)||isEmpty(charSet)) {
			return null;
        }
		try {
			return  String.valueOf(str.getBytes(charSet));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据源字符串和编码取得字符串，并获取处理后的字符串长度
	 * @param str 
	 * @param charSet 
	 * @return int
	 */
	public  static int getStringByLength(String str, String charSet){
		str = getStringByCharset(str,charSet);
		if(availableStr(str)){
			return str.length();
		}else{
			return 0;
		}
	}
	
	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 * @param s	原始字符串
	 * @return	替换危险字符之后的字符串
	 */
	public static String xssEncode(String s) {
		if (s == null || "".equals(s)) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + INT16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append('＞');// 全角大于号
				break;
			case '<':
				sb.append('＜');// 全角小于号
				break;
//			case '\'':
//				sb.append('‘');// 全角单引号
//				break;
//			case '\"':
//				sb.append('“');// 全角双引号
//				break;
//			case '&':
//				sb.append('＆');// 全角
//				break;
//			case '%':
//				sb.append('％');// 全角
//				break;
			case '+':
				sb.append("＋");// 全角
				break;	
			case ';':
				sb.append("；");// 全角
				break;	
			case '\\':
				sb.append('＼');// 全角斜线
				break;
//			case '/':
//				sb.append('／');// 全角斜线
//				break;
			case '#':
				sb.append('＃');// 全角井号
				break;
//			case ':':
//				sb.append('：');// 全角冒号
//				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
}
