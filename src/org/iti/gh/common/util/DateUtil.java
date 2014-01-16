package org.iti.gh.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <li>功能描述:日期转换，主要webkey数据库中日期字符串及java类型日期互相转换
 * @author webkey
 * @version 1.0
 */
public class DateUtil {

	static DateFormat df_tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * <li>功能描述：将数据库中存储的日期数字字符串转化为日期格式字符串。
	 * @param 日期全数字字符串
	 * @return
	 * String 
	 * @author webkey
	 * @2010-3-15
	 */
		
	public static String getDateString(String arg){
		StringBuffer sb=new StringBuffer(arg.substring(0, 4));
		sb.append("-"+arg.substring(4, 6));
		sb.append("-"+arg.substring(6, 8));
		return sb.toString();
	}
	
	/**
	 * <li>功能描述：将数据库中存储的日期数字字符串转化为时间格式字符串，保留到月，中间用"."隔开。
	 * 
	 * @param 日期全数字字符串
	 * @return String
	 * @author LY
	 * @2011-6-19
	 */
	public static String getMonthString(String arg)
	{
		StringBuilder sb = new StringBuilder(arg.substring(0, 4));
		sb.append("." + arg.substring(4, 6));
		return sb.toString();
	}
	
	/**
	 * <li>功能描述：将数据库中存储的时分秒数字字符串转化为时间格式字符串。
	 * @param 时分秒全数字字符串
	 * @return
	 * String 
	 * @author webkey
	 * @2010-3-15
	 */
	
	public static String getHourString(String arg){
		if(arg.length()<6)
			arg = "0" + arg;
		StringBuffer sb = new StringBuffer(arg.substring(0, 2));
		sb.append(":"+arg.substring(2, 4));
		sb.append(":"+arg.subSequence(4, 6));
		return sb.toString();
	}
	
	/**
	 * <li>功能描述：将数据库中存储的日期时间数字字符串转化为日期格式字符串。
	 * @param 日期及时间全数字字符串
	 * @return
	 * String 
	 * @author webkey
	 * @2010-3-15
	 */
	public static String getTimeString(String arg){
		if(arg==null||arg.trim().length()<12){
			return "未登录";
		}		
		StringBuffer sb=new StringBuffer(arg.substring(0, 4));
		sb.append("-"+arg.substring(4, 6));
		sb.append("-"+arg.substring(6, 8));
		sb.append(" "+arg.substring(8, 10));
		sb.append(":"+arg.substring(10,12));
		return sb.toString();
	}
	
	/**
	 * <li>功能描述：将java日期类型转换为全数字的字符串,截取到日期
	 * @param d
	 * @return
	 * String 
	 * @author webkey
	 * @2010-3-20
	 */
	public static String getDateString(Date d){
		DateFormat albumDate = new SimpleDateFormat("yyyyMMdd");
		return albumDate.format(d);
	}
	
	/**
	 * <li>功能描述：将java日期类型转换为全数字的字符串,截取到秒
	 * @param d
	 * @return
	 * String 
	 * @author webkey
	 * @2010-3-20
	 */
	public static String getDateTimeString(Date d){
		DateFormat albumTime = new SimpleDateFormat("yyyyMMddHHmmss");
		return albumTime.format(d);
	}
	
	/**
	 * <li>功能描述：获得字符串date的日期类型对象。
	 * @param date
	 * @return
	 * Date 
	 * @author webkey
	 */
	public static Date getDate(String date){
		DateFormat albumDate = new SimpleDateFormat("yyyyMMdd");
		try {
			return albumDate.parse(date);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <li>功能描述：获得字符串日期和时间类型日期。
	 * @param date
	 * @return
	 * Date 
	 * @author webkey
	 */
	
	public static Date getDateAndTime(String date){
		DateFormat albumTime = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return albumTime.parse(date);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
	System.out.println(	DateUtil.getDateTimeString(new Date()));

	}
	
	public static String convertDateAndTimeString(Date d){		
		try{			 
			return df_tf.format(d);			
		} catch(Exception e) {
			return d.toString();
		}
	}

}
