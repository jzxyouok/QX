package com.cn.qx.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.NumberUtils;

import sun.misc.FloatingDecimal;

/**
 * 
 * 数字工具类 <p>
 * 创建日期：2013-7-9<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author zhou
 * @version 1.0
 */
public class NumberUtil extends NumberUtils implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 两位小数
	 */
	public static final String PATTERN="%.2f";
	/**
	 * 一万
	 */
	public static final int TEN_THOUSAND=10000;
	/**
	 * 10
	 */
	public static final int INT10=10;
	/**
	 * 3
	 */
	public static final int INT3=3;
	/**
	 * 4
	 */
	public static final int INT4=4;
	/**
	 * 5
	 */
	public static final int INT5=5;
	/**
	 * 9
	 */
	public static final int INT9=9;
	/**
	 * 20
	 */
	public static final int INT20=20;
	/**
	 * 100
	 */
	public static final int INT100=100;
	/**
	 * 400
	 */
	public static final int INT400=400;
	
	
	 /** 
     * 判断是否是大于零的Integer整数 
     * @param obj 
     * @return boolean
     */  
    public static boolean isGTIntegerZero(Object obj){  
        return ((obj!=null)&&(obj instanceof Integer)&&(((Integer)obj).intValue()>0));  
    } 
    
    /** 
     * 判断是否是大于零的Long整数 
     * @param obj 
     * @return boolean
     */  
    public static boolean isGTLongZero(Object obj){  
        return ((obj!=null)&&(obj instanceof Long)&&(((Long)obj).longValue()>0));  
    }  

    /** 
     * 精确的加法运算 
     * @param v1 
     * @param v2 
     * @return double
     */  
    public static double add(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.add(b2).doubleValue();  
    } 
	
    /** 
     * 精确的减法运算 
     * @param v1 被减数 
     * @param v2 减数 
     * @return double
     */  
    public static double subtract(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.subtract(b2).doubleValue();  
    }  

    /** 
     * 提供精确的乘法运算 
     * @param v1 
     * @param v2 
     * @return double
     */  
    public static double multiply(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.multiply(b2).doubleValue();  
    }
    
    /** 
     * 提供精确的乘法运算，并对运算结果截位. 
     * @param v1 
     * @param v2 
     * @param scale 运算结果小数后精确的位数 
     * @return double
     */  
    public static double multiply(double v1, double v2, int scale) {  
        if (scale < 0) {  
            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
        }  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.multiply(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  

    /** 
     * 提供（相对）精确的除法运算 
     * @param v1 
     * @param v2 
     * @return double
     */  
    public static double divide(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.divide(b2).doubleValue();
    }

    /** 
     * 提供（相对）精确的除法运算. 
     * 由scale参数指定精度，以后的数字四舍五入. 
     * @param v1 被除数 
     * @param v2 除数 
     * @param scale 表示表示需要精确到小数点以后几位 
     * @return double
     */  
    public static double divide(double v1, double v2, int scale) {  
        if (scale < 0) {  
            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
        }  
  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();  
    } 
    
    /** 
     * 提供精确的小数位四舍五入处理. 
     * @param v 需要四舍五入的数字 
     * @param scale 小数点后保留几位 
     * @return double
     */  
    public static double round(double v, int scale) {  
        if (scale < 0) {  
            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
        }  
        BigDecimal b = new BigDecimal(v);  
        return NumberUtil.parseDouble(b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString());
    } 
    
    /** 
     * 返回两个数中大的一个值 
     * @param v1 需要被对比的第一个数  
     * @param v2 需要被对比的第二个数  
     * @return 返回两个数中大的一个值 
     */  
    public static double returnMax(double v1,double v2){  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.max(b2).doubleValue();  
    } 
    
    /** 
     * 返回两个数中小的一个值 
     * @param v1 需要被对比的第一个数  
     * @param v2 需要被对比的第二个数  
     * @return 返回两个数中小的一个值 
     */  
    public static double returnMin(double v1,double v2){  
        BigDecimal b1= new BigDecimal(v1);  
        BigDecimal b2= new BigDecimal(v2);  
        return b1.min(b2).doubleValue();  
    }
    
    /** 
     * 精确对比两个数字  
     * @param v1 需要被对比的第一个数  
     * @param v2 需要被对比的第二个数  
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1  
     */  
    public static int compareTo(double v1,double v2){  
        BigDecimal b1= new BigDecimal(v1);  
        BigDecimal b2= new BigDecimal(v2);  
        return b1.compareTo(b2);  
    }  

    
	
	/**
	 * 获得随机的10位数字
	 * @return String
	 */
	public static String  randomTenNumbers(){
		return RandomStringUtils.randomNumeric(INT10);
	}
	
	
    /** 
     * 判断是平年还是闰年 
     * @date Mar 11, 2012 
     * @param year 
     * @return boolean
     */   
    public static boolean isLeapyear(int year) {  
        if ((year % INT4 == 0 && year % INT100 != 0) || (year % INT400) == 0) {  
            return true;  
        } 
            return false;  
    }  
    
    /** 
     * 金额元转分 
     * @see 注意:该方法可处理贰仟万以内的金额,且若有小数位,则不限小数位的长度 
     * @see 注意:如果你的金额达到了贰仟万以上,则不推荐使用该方法,否则计算出来的结果会令人大吃一惊 
     * @param amount  金额的元进制字符串 
     * @return String 金额的分进制字符串 
     */  
    public static String moneyYuanToFen(String amount){  
        if(StringUtil.isEmpty(amount)){  
            return amount;  
        }  
        //传入的金额字符串代表的是一个整数  
        if(-1 == amount.indexOf(".")){  
            return Integer.parseInt(amount) * INT100 + "";  
        }  
        //传入的金额字符串里面含小数点-->取小数点前面的字符串,并将之转换成单位为分的整数表示  
        int moneyFen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * INT100;  
        //取到小数点后面的字符串  
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));  
        //amount=12.3  
        if(pointBehind.length() == 1){  
            return moneyFen + Integer.parseInt(pointBehind)*INT10 + "";  
        }  
        //小数点后面的第一位字符串的整数表示  
        int pointString = Integer.parseInt(pointBehind.substring(0, 1));  
        //小数点后面的第二位字符串的整数表示  
        int pointString2 = Integer.parseInt(pointBehind.substring(1, 2));  
        //amount==12.03,amount=12.00,amount=12.30  
        if(pointString == 0){  
            return moneyFen + pointString2 + "";  
        }else{  
            return moneyFen + pointString*INT10 + pointString2 + "";  
        }  
    }
	
    /** 
     * 金额元转分 
     * @see 该方法会将金额中小数点后面的数值,四舍五入后只保留两位....如12.345-->12.35 
     * @see 注意:该方法可处理贰仟万以内的金额 
     * @see 注意:如果你的金额达到了贰仟万以上,则非常不建议使用该方法,否则计算出来的结果会令人大吃一惊 
     * @param amount  金额的元进制字符串 
     * @return String 金额的分进制字符串 
     */  
    public static String moneyYuanToFenByRound(String amount){  
        if(StringUtil.isEmpty(amount)){  
            return amount;  
        }  
        if(-1 == amount.indexOf(".")){  
            return Integer.parseInt(amount) * INT100 + "";  
        }  
        int moneyFen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * INT100;  
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));  
        if(pointBehind.length() == 1){  
            return moneyFen + Integer.parseInt(pointBehind)*INT10 + "";  
        }  
        int pointString1 = Integer.parseInt(pointBehind.substring(0, 1));  
        int pointString2 = Integer.parseInt(pointBehind.substring(1, 2));  
        //下面这种方式用于处理pointBehind=245,286,295,298,995,998等需要四舍五入的情况  
        if(pointBehind.length() > 2){  
            int pointString3 = Integer.parseInt(pointBehind.substring(2, INT3));  
            if(pointString3 >= INT5){  
                if(pointString2 == INT9){  
                    if(pointString1 == INT9){  
                        moneyFen = moneyFen + INT100;  
                        pointString1 = 0;  
                        pointString2 = 0;  
                    }else{  
                        pointString1 = pointString1 + 1;  
                        pointString2 = 0;  
                    }  
                }else{  
                    pointString2 = pointString2 + 1;  
                }  
            }  
        }  
        if(pointString1 == 0){  
            return moneyFen + pointString2 + "";  
        }else{  
            return moneyFen + pointString1*INT10 + pointString2 + "";  
        }  
    }  
      
      
    /** 
     * 金额分转元 
     * @see 注意:如果传入的参数中含小数点,则直接原样返回 
     * @see 该方法返回的金额字符串格式为<code>00.00</code>,其整数位有且至少有一个,小数位有且长度固定为2 
     * @param amount  金额的分进制字符串 
     * @return String 金额的元进制字符串 
     */  
    public static String moneyFenToYuan(String amount){  
        if(StringUtil.isEmpty(amount)){  
            return amount;  
        }  
        if(amount.indexOf(".") > -1){  
            return amount;  
        }  
        if(amount.length() == 1){  
            return "0.0" + amount;  
        }else if(amount.length() == 2){  
            return "0." + amount;  
        }else{  
            return amount.substring(0, amount.length()-2) + "." + amount.substring(amount.length()-2);  
        }  
    }  
      
    
    
    /** 
     * 获取系统流水号 
     * @see  若欲指定返回值的长度or是否全由数字组成等,you can call  {@link TradePortalUtil#getSysJournalNo(int, boolean)} 
     * @return 长度为20的全数字 
     */  
    public static String getSysJournalNo(){  
        return getSysJournalNo(INT20, true);  
    }  
      
      
    /** 
     * 获取系统流水号 
     * @param length   指定流水号长度 
     * @param isNumber 指定流水号是否全由数字组成 
     * @return String
     */  
    public static String getSysJournalNo(int length, boolean isNumber){  
        //replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串  
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");  
        if(uuid.length() > length){  
            uuid = uuid.substring(0, length);  
        }else if(uuid.length() < length){  
            for(int i=0; i<length-uuid.length(); i++){
            	SecureRandom random = new SecureRandom();
				try {
					random = SecureRandom.getInstance("SHA1PRNG");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
                uuid = uuid + Math.round(random.nextDouble() *INT9);  
            }  
        }  
        if(isNumber){  
            return uuid.replaceAll("a", "1").replaceAll("b", "2")
            		.replaceAll("c", "3").replaceAll("d", "4").replaceAll("e", "5").replaceAll("f", "6");  
        }else{  
            return uuid;  
        }  
    }  
	
    
    /**
     * JDK1.6_024以前的parseDouble有bug
     * 使用本方法替代
     * @param str 
     * @return double
     */
    public static double parseDouble(String str){
    	return FloatingDecimal.readJavaFormatString(str).doubleValue();
    }
	
	
    /** 
     * 判断是否为IP地址 符合返回ture 
     * @param str 
     * @return boolean 
     */  
    public static  boolean isIpaddress(String str) {  
        return regular(str,"((?:(?:25[0-5]|2[0-4]//d|[01]?//d?//d)//.){3}(?:25[0-5]|2[0-4]//d|[01]?//d?//d))");  
    } 
	
    
    /**  
     * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture 
     * @param str 
     * @return boolean 
     */  
    public static  boolean isNumber(String str) {  
        return regular(str,"^-?([1-9]//d*//.//d*|0//.//d*[1-9]//d*|0?//.0+|0)$");  
    }  
    
    
    /** 
     * 判断字段是否为INTEGER  符合返回cure 
     * @param str 
     * @return boolean 
     */  
    public static  boolean isInteger(String str) {  
        return regular(str,"^-?(([1-9]//d*$)|0)");  
    }  
    /** 
     * 判断字段是否为正整数正则表达式 >=0 符合返回ture 
     * @param str 
     * @return boolean 
     */  
    public static  boolean isINTEGERNEGATIVE(String str) {  
        return regular(str,"^[1-9]//d*|0$");  
    } 
    
    /** 
     * 判断字段是否为负整数正则表达式 <=0 符合返回ture 
     * @param str 
     * @return boolean 
     */  
    public static  boolean isINTEGERPOSITIVE(String str) {  
        return regular(str,"^-[1-9]//d*|0$");  
    }     
    /** 
     * 判断字段是否为DOUBLE 符合返回ture 
     * @param str 
     * @return boolean 
     */  
    public static  boolean isDouble(String str) {  
        return regular(str,"^-?([1-9]//d*//.//d*|0//.//d*[1-9]//d*|0?//.0+|0)$");  
    }  
    
    /** 
     * 匹配是否符合正则表达式pattern 匹配返回true 
     * @param str 匹配的字符串 
     * @param pattern 匹配模式 
     * @return boolean 
     */  
    public static  boolean regular(String str,String pattern){  
        if(null == str || str.trim().length()<=0)  
            return false;           
        Pattern p = Pattern.compile(pattern);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    } 
 
	/**
	 *将数字格式化成为货币格式输出 
	 * @param number	输入数字
	 * @return	货币格式
	 */
	public static String formatCurrency(double number){
		NumberFormat formater = NumberFormat.getCurrencyInstance();		
		return formater.format(number);
	}
	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
	 * 要用到正则表达式
	 * @param n 要转换大写的数字
	 * @return 转换后的字符串
	 */
	public static String digitUppercase(double n){
		String []fraction = {"角", "分"};
	    String []digit = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	    String [][]unit = {{"元", "万", "亿"},{"", "拾", "佰", "仟"}};
	    String head = n < 0? "负": "";
	    n = Math.abs(n);
	    String s = "";
	    for (int i = 0; i < fraction.length; i++) {
	        s += (digit[(int)(Math.floor(n*INT10*Math.pow(INT10, i))%INT10)] + fraction[i]).replaceAll("(零.)+", "");
	    }
	    if(s.length()<1){
		    s = "整";	
	    }
	    int integerPart = (int)Math.floor(n);
	    for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
	        String p ="";
	        for (int j = 0; j < unit[1].length && n > 0; j++) {
	            p = digit[integerPart%INT10]+unit[1][j] + p;
	            integerPart = integerPart/INT10;
	        }
	        s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
	    }
	    return head + s.replaceAll("(零.)*零元", "元").
	replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}
	
	/**
	 * 获得两数之间的随机数（num1<num2）
	 * @param num1 数值1
	 * @param num2 数值2
	 * @return 随机数
	 */
	public static  double getRandomForm2Num(double num1,double num2){
		return  num1+Math.random()*(num2-num1);
	}
}
