/*
 * DateUtil.java
 * 版权所有：江苏电力信息技术有限公司 2007 - 2012
 * 江苏电力信息技术有限公司保留所有权利，未经允许不得以任何形式使用。
 */
package com.cn.qx.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;

/**
 * 将原有Utils中Date相关方法移入
 * @author Administrator
 *
 */
public class DateUtil extends DateUtils{
	/**
	 * 默认日期格式
	 */
	public static final String DEFAULTDATEPATTERN = "yyyy-MM-dd";
	/**
	 * 默认日期时间格式
	 */
	public static final String DEFAULTDATETIMEPATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认时间格式
	 */
	public static final String DEFAULTTIMEPATTERN = "HH:mm:ss";
	/**
	 * 默认日期时间格式(精确到分)
	 */
	public static final String DEFAULTDATETIMEWITHOUTSECPATTERN =  "yyyy-MM-dd HH:mm";
	/**
	 * 13
	 */
	private static final int INT13 = 13;
	/**
	 * 24
	 */
	private static final int INT24 = 24;
	/**
	 * 1000
	 */
	private static final int INT1000 = 1000;
	/**
	 * 28
	 */
	private static final int INT28 = 28;
	/**
	 * 3600
	 */
	private static final int INT3600 = 3600;
	/**
	 * 7
	 */
	private static final int INT7 = 7;
	
	/**
	 * 8
	 */
	private static final int INT8 = 8;
	/**
	 * 6
	 */
	private static final int INT6 = 6;
	/**
	 * 4
	 */
	private static final int INT4 = 4;
	/**
	 * 31
	 */
	private static final int INT31 = 31;
	/**
	 * 100
	 */
	private static final int INT100 = 100;
	
	
	/**
	 * 400
	 */
	private static final int INT400 = 400;
	/**
	 * 取两个日期相差的月份差
	 * @param d1 日期1
	 * @param d2 日期2
	 * @return 整数
	 */
	public static int getDatesMinusMonths(Date d1,Date d2){
		return Math.max(0, (int) (Math.abs(d1.getTime()-d2.getTime())/INT3600/INT24/INT1000/INT28)-1);
	}
	
	/**
	 * 根据格式化模式，格式化日期类型
	 * @param d			日期，如果是null，则默认之为当前日期
	 * @param pattern	模式，如果是null，则默认之为典型的年月日时分秒格式
	 * @return	以指定格式显示的日期字符串
	 */
	public static String formatDateTime(Date d, String pattern){
		if (d==null){
			d = new Date();
		}
		if (!StringUtil.availableStr(pattern)){
			pattern = DEFAULTDATETIMEPATTERN;
		}
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		String s = sf.format(d);
		return s;
	}
	/**
	 * 用典型的年月日格式格式化模式，格式化日期类型
	 * @param d			日期，如果是null，则默认之为当前日期
	 * @return	以典型的年月日格式格式显示的日期字符串
	 */
	public static String formatDate(Date d){
		return formatDateTime(d,DEFAULTDATEPATTERN);
	}
	/**
	 * 用典型的年月日时分秒格式格式化模式，格式化日期类型
	 * @param d			日期，如果是null，则默认之为当前日期
	 * @return	以典型的年月日时分秒格式格式显示的日期字符串
	 */
	public static String formatDateTime(Date d){
		return formatDateTime(d,null);
	}	
	/**
	 * 根据格式化模式，格式化当前日期类型
	 * @param pattern	模式，如果是null，则默认之为典型的年月日时分秒格式
	 * @return	以指定格式显示的日期字符串
	 */
	public static String formatDateTime(String pattern){
		return formatDateTime(null,pattern);
	}
	/**
	 * 用典型的年月日格式格式化模式，格式化当前日期
	 * @return	以典型的年月日格式格式显示的日期字符串
	 */
	public static String formatDate(){
		return formatDateTime(null,DEFAULTDATEPATTERN);
	}
	/**
	 * 用典型的年月日时分秒格式格式化模式，格式化当前日期
	 * @return	以典型的年月日时分秒格式格式显示的日期字符串
	 */
	public static String formatDateTime(){
		return formatDateTime(null,null);
	}	
	
	
	/**
	 * 把一个日期时间转换为格式化的字符串，按照yyyy-MM-dd HH:mm
	 * @param date 要转换的日期时间
	 * @return 格式化后字符串，
	 */
	public static String formatDateTimeWithoutSecond(Date date){
		return formatDateTime(date, DEFAULTDATETIMEWITHOUTSECPATTERN);
	}
	
	/**
	 * 按指定格式解析字符串，把它转化成Date对象,如果字符串没有值，返回null
	 * @param dateTimeStr 
	 * @param pattern 
	 * @return Date
	 */
	public static Date parseDateTime(String dateTimeStr,String pattern){
		if (!StringUtil.availableStr(dateTimeStr)) return null;
		if (!StringUtil.availableStr(pattern)){
			pattern = DEFAULTDATETIMEPATTERN;
		}		
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		try {
			return sf.parse(dateTimeStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 按yyyy-MM-dd HH:mm:ss解析字符串，把它转化成Date对象,如果字符串没有值，返回null
	 * @param dateTimeStr 
	 * @return Date
	 */
	public static Date parseDateTime(String dateTimeStr){
		return parseDateTime(dateTimeStr,null);
	}
	/**
	 * 按yyyy-MM-dd HH:mm:ss解析字符串，把它转化成Date对象,如果字符串没有值，返回null
	 * @param dateStr 
	 * @return Date
	 */
	public static Date parseDate(String dateStr){
		return parseDateTime(dateStr,DEFAULTDATEPATTERN);
	}
	
	/**
	 * 取本周的第一天
	 * @param d 
	 * @return Date
	 */
	public static Date getFirstDayOfWeek(Date d){
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_WEEK, 1);
		return c.getTime();
	}
	
	/**
	 * 取本周的最后一天
	 * @param d 
	 * @return Date
	 */
	public static Date getLastDayOfWeek(Date d){
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_WEEK, INT7);
		return c.getTime();
	}
	/**
	 * 取上周的第一天
	 * @param d 
	 * @return Date
	 */
	public static Date getFirstDayOfLastWeek(Date d){
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		if(1==c.get(Calendar.DAY_OF_WEEK)){
			c.add(Calendar.DAY_OF_MONTH, -INT13);
			return c.getTime();
		}
		c.add(Calendar.DAY_OF_MONTH, -INT7);
		c.set(Calendar.DAY_OF_WEEK, 2);
		return c.getTime();
	}
	/**
	 * 取当前日期上周的日期
	 * @param d 
	 * @return Date
	 */
	public static Date getDayOfLastWeek(Date d){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, -INT7);
		return c.getTime();
	}
	
	/**
	 * 取上周的最后一天
	 * @param d 
	 * @return Date
	 */
	public static Date getLastDayOfLastWeek(Date d){
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		if(1==c.get(Calendar.DAY_OF_WEEK)){
			c.add(Calendar.DAY_OF_MONTH, -INT7);
			return c.getTime();
		}
		c.add(Calendar.DAY_OF_MONTH, -INT7);
		c.set(Calendar.DAY_OF_WEEK, 2);
		c.add(Calendar.DAY_OF_MONTH, INT6);
		return c.getTime();
	}
	/**
	 * 取本月第一天
	 * @param d 
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(Date d){
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	/**
	 * 取下n个月的第一天
	 * @param d 
	 * @param n 
	 * @return Date
	 */
	public static Date getFirstDayOfNextMonth(Date d,int n){
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+n);
		return c.getTime();
	}
	
	/**
	 * 获得当前月份的最后第一天。
	 * @param d 
	 * @return 当前月份的最后第一天。
	 */
	public static Date getLastDayOfMonth(Date d) {
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		int dayOfMonth=c.get(Calendar.DAY_OF_MONTH);
		int maxDaysOfMonth=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DAY_OF_MONTH, maxDaysOfMonth-dayOfMonth);
		return c.getTime();
	}
	/**
	 * 获得下n个月的最后一天。
	 * @param d 
	 * @param n 
	 * @return Date
	 */
	public static Date getLastDayOfNextMonth(Date d,int n) {
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+n);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	
	
	/**
	 * 取本年第一天
	 * @param d 
	 * @return Date
	 */
	public static Date getFirstDayOfYear(Date d){
		Calendar c =GregorianCalendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_YEAR, 1);
		return c.getTime();
	}
	
	
	/**
	 * 取当前日期，时、分、秒、毫秒部分设置为0
	 * @return 当前日期
	 */
	public static java.util.Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.AM_PM, Calendar.AM);
		return cal.getTime();
	}
	
	/**
	 * 取当前日期并转化为格式为:yyyy-MM-dd的字符串,时、分、秒、毫秒部分设置为0
	 * @return 当前时间按照yyyy-MM-dd格式化的字符串
	 */
	public static String getCurrentDateAsString() {
		Calendar cal = Calendar.getInstance();
		return formatDate(cal.getTime());
	}

	/**
	 * 获得当前的系统时间，毫秒设置为0。
	 * @return 当前时间精确到秒，不包括日期部分。
	 */
	public static java.sql.Time getCurrentTime() {
		Calendar cal= Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		return new java.sql.Time(cal.getTime().getTime());
	}
	
	/**
	 * 获得当前的系统时间，毫秒设置为0，并格式化为字符串。
	 * @return 当前时间的字符串表示，精确到秒，不包括日期部分。
	 */
	public static String getCurrentTimeAsString() {
		java.sql.Time time= getCurrentTime();
		return formatDateTime(time,DEFAULTTIMEPATTERN);
	}

	/**
	 * 获得当前的系统时间，秒、毫秒设置为0。
	 * @return 当前时间精确到秒，不包括日期部分。
	 */
	public static java.sql.Time getCurrentTimeWithoutSecond() {
		Calendar cal= Calendar.getInstance();
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return new java.sql.Time(cal.getTime().getTime());
	}
	
	/**
	 * 获得当前的系统时间，毫秒设置为0，并格式化为字符串。
	 * @return 当前时间的字符串表示，精确到秒，不包括日期部分。
	 */
	public static String getCurrentTimeWithoutSecondAsString() {
		java.sql.Time time= getCurrentTimeWithoutSecond();
		return formatDateTime(time,DEFAULTDATETIMEWITHOUTSECPATTERN);
	}
	
	/**
	 * 获得当前的系统时间，毫秒设置为0。
	 * @return 当前时间，精确到秒。
	 */
	public static Timestamp getCurrentTimestamp() {
		Calendar cal= Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		return new Timestamp(cal.getTime().getTime());
	}
	
	/**
	 * 获得当前的系统时间，毫秒设置为0，并格式化为字符串。
	 * @return 当前时间的字符串表示，精确到秒。
	 */
	public static String getCurrentTimestampAsString() {
		Timestamp timestamp= getCurrentTimestamp();
		return formatDateTime(timestamp);
	}

	/**
	 * 获得当前的系统时间，秒、毫秒设置为0。
	 * @return 当前时间精确到分钟。
	 */
	public static Timestamp getCurrentTimestampWithoutSecond() {
		Calendar cal= Calendar.getInstance();
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return new Timestamp(cal.getTime().getTime());
	}
	
	/**
	 * 获得当前的系统时间，秒、毫秒设置为0，并格式化为字符串。
	 * @return 当前时间的字符串表示精确到分钟。
	 */
	public static String getCurrentTimestampWithoutSecondAsString() {
		Timestamp timestamp= getCurrentTimestampWithoutSecond();
		return formatDateTimeWithoutSecond(timestamp);
	}
	
	/**
	 * 取当前年份
	 * @return int
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 取当前月份
	 * @return int
	 */
	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 取得当前是星期几
	 * 星期一为第一天返回1,星期天返回0
	 * @return int
	 */
	public static int getCurrentWeekDay(){
		 Calendar calendar = Calendar.getInstance();
	     return calendar.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	/**
	 * 取得当前秒
	 * @return int
	 */
	public static int getCurrentSecond(){
		 Calendar calendar = Calendar.getInstance();
	     return calendar.get(Calendar.SECOND);
	}
	/**
	 * 取得当前分
	 * @return int
	 */
	public static int getCurrentMinute(){
		 Calendar calendar = Calendar.getInstance();
	     return calendar.get(Calendar.MINUTE);
	}
	/**
	 * 取得当前小时
	 * @return int
	 */
	public static int getCurrentHour(){
		 Calendar calendar = Calendar.getInstance();
	     return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 取得当前天
	 * @return int
	 */
	public static int getCurrentDay(){
		 Calendar calendar = Calendar.getInstance();
	     return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
    /**
	 * 从Timestamp获得日期值
	 * @param timestamp 
	 * @return 去掉时间后的日期
	 */
	public static java.util.Date getDate(Timestamp timestamp) {
		if (timestamp!=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(timestamp);
			cal.set(Calendar.HOUR,0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);
			return new java.sql.Date(cal.getTime().getTime()); 
		}
		return null;
	}
	

	/**
	 * 获得当前月份的第一天。
	 * @return 当前月份的第一天。
	 */
	public static java.sql.Date getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		return new java.sql.Date(cal.getTime().getTime());
	}
	
	/**
	 * 获得当前月份的第一天并且按照yyyy-MM-dd格式化为字符串
	 * @return 当前月份的第一天按照yyyy-MM-dd格式化的字符串
	 */
	public static String getFirstDayOfMonthAsString() {
		java.sql.Date date= getFirstDayOfMonth();
		return formatDate(date);
	}
		
	/**
	 * 获得当前月份的最后第一天。
	 * @return 当前月份的最后第一天。
	 */
	public static java.sql.Date getLastDayOfMonth() {
		Calendar cal= Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.MONTH, true);
		cal.roll(Calendar.DATE, false);
		return new java.sql.Date(cal.getTime().getTime());
	}
	
	/**
	 * 获得当前月份的最后一天并且按照yyyy-MM-dd格式化为字符串
	 * @return 当前月份的最后一天按照yyyy-MM-dd格式化的字符串
	 */
	public static String getLastDayOfMonthAsString() {
		java.sql.Date date= getLastDayOfMonth();
		return formatDate(date);
	}
	/**
	 * 获取两个日期之间的天数
	 * @param d1 
	 * @param d2 
	 * @return int
	 */
	public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
		    - d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}
	/**
	 * 功能描述：取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String yyyy-MM-dd 格式
	 */
	public static String getMonthBegin(String strdate) {
		Date date = null;
		date = parseDate(strdate);
		return formatDateTime(date, "yyyy-MM") + "-01";
	}

	/**
	 * 功能描述：取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String 日期字符串 yyyy-MM-dd格式
	 */
	public static String getMonthEnd(String strdate) {
		Date date = null;
		Calendar calendar = null;
		date = parseDate(getMonthBegin(strdate));
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}
	
    /** 
     * 判断是平年还是闰年 
     * @param year 年份
     * @return  boolean
     */    
    public static boolean isLeapyear(int year) {  
        if ((year % INT4 == 0 && year % INT100 != 0) || (year % INT400) == 0) {  
            return true;  
        }   
        return false;   
    } 
    
    /**
     * 通过传入日期参数，返回该日期月份的对应天数
     * @param date 
     * @return int
     */
    public  static int getDaysOfMonth(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 通过时间字符串和时间格式验证时间字符串是否合法
     * @param dateStr 
     * @param pattern 
     * @return boolean
     */
	public static boolean isValidDate(String dateStr,String pattern){
		return NumberUtil.regular(dateStr, pattern);
	}
	
	/**
	 * 通过时间计算得到昨天的日期
	 * @param d
	 * @return
	 */
	public static String getLastDay(Date d){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, -1);
		String lastDate = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULTDATEPATTERN); 
		return lastDate;
	}
	
	/**
	 * 取当前日期上周的日期的前一天日期
	 * @param d 
	 * @return Date
	 */
	public static String getDayOfLastWeekBefore(Date d){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, -INT8);
		String lastDate = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULTDATEPATTERN); 
		return lastDate;
	}
	
	/**
	 * 取当前日期30天前一天的日期
	 * @param d 
	 * @return Date
	 */
	public static String getDayOfLastMonthBefore(Date d){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, -INT31);
		String lastDate = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULTDATEPATTERN); 
		return lastDate;
	}
	
	/**
	 * 取当前时间的前一个小时值
	 * @param d 
	 * @return Date
	 */
	public static String getlastHour(Date d){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(d);
		c.add(Calendar.HOUR_OF_DAY, -1);
		String lastDateTime = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULTDATETIMEPATTERN); 
		return lastDateTime;
	}
	
	
	/**
	 * 取当前日期半年前的同期时间
	 * @param d 
	 * @return Date
	 */
	public static Date getDayOfHalfYear(Date d){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, -INT6);
		return c.getTime();
	}

}
