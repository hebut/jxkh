package com.iti.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConvertUtil {
	
	private static final Log log = LogFactory.getLog(ConvertUtil.class);
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat albumDate = new SimpleDateFormat("yyyyMMdd");
	private static DateFormat tf = new SimpleDateFormat("HH时mm分ss秒");
	private static DateFormat df_tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static DateFormat df_xz = new SimpleDateFormat("yyyyMMdd_HHmm");
	static DateFormat yr = new SimpleDateFormat("MM月dd日");

	static NumberFormat format = new DecimalFormat("#0.00");
	public static String convertDouble(Double d) {
		return format.format(d);
	}
	public static String convertBsxzDateString(Date  d) {
		try {
			return df_xz.format(d);
		} catch (Exception e) {
			return d.toString();
		}
	}
	public static String convertYRString(Long time) {
		return yr.format(new Date(time));
	}
	// 把str中的所有oldStr替换为newStr
	public static String replacehtml(String str, String oldStr, String newStr) {
		if (str != null) {
			int index = 0;
			int oldLen = oldStr.length(); // oldStr字符串长度
			int newLen = newStr.length(); // newStr字符串长度
			while (true) {
				index = str.indexOf(oldStr, index);
				if (index == -1) {
					return str;
				} else {
					str = str.substring(0, index) + newStr + str.substring(index + oldLen);
					index += newLen;
				}
			}
		} else {
			return "";
		}
	}
	public static String htmlTotxt(String s) {
		if(s==null||s.length()==0) return "";
		try {
			for (;;) {
				int i = s.indexOf("<xml>");
				int j = s.indexOf("</xml>");
				String tag = s.substring(i, j + 6);
				s = replacehtml(s, tag, "");
			}
		} catch (Exception e) {
		}
		try {
			for (;;) {
				int i = s.indexOf("<style");
				int j = s.indexOf("</style>");
				String tag = s.substring(i, j + 8);
				s = replacehtml(s, tag, "");
			}
		} catch (Exception e) {
		}
		BufferedReader in = new BufferedReader(new StringReader(s));
		String tag = "";
		int chr;
		try {
			while ((chr = in.read()) != -1) {
				tag = "";
				if (chr == '<') {
					tag = "";
					while ((chr = in.read()) != '>') {
						if (chr == -1) {
							return "html标记嵌套不完整！";
						}
						tag = tag + (char) chr;
					}
				}
				s = replacehtml(s, "<" + tag + ">", "");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		s = s.replaceAll("&nbsp;", "");
		return s;
	}
	
	/**
	 * <li>功能描述：获得字符串date的日期类型对象。
	 * 
	 * @param date
	 * @return Date
	 * @author DaLei
	 */
	public static Date getDate(String date) {
		try {
			return albumDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * <li>功能描述：将数据库中存储的日期时间数字字符串转化为日期格式字符串。
	 * 
	 * @param 日期及时间全数字字符串
	 * @return String
	 * @author DaLei
	 * @2010-3-15
	 */
	public static String getTimeString(String arg) {
		if (arg == null || arg.trim().length() < 12) {
			return "未登录";
		}
		StringBuffer sb = new StringBuffer(arg.substring(0, 4));
		sb.append("-" + arg.substring(4, 6));
		sb.append("-" + arg.substring(6, 8));
		sb.append(" " + arg.substring(8, 10));
		sb.append(":" + arg.substring(10, 12));
		return sb.toString();
	}
	public static String convertDateAndTimeString(Date d) {
		try {
			return df_tf.format(d);
		} catch (Exception e) {
			return d.toString();
		}
	}
	public static String convertTimeString(Date date) {
		return tf.format(date);
	}
	public static String convertDateString(Date date) {
		return df.format(date);
	}
	public static String getDateString(Date d) {
		return albumDate.format(d);
	}
	public static String getDateString(String arg) {
		StringBuffer sb = new StringBuffer(arg.substring(0, 4));
		sb.append("-" + arg.substring(4, 6));
		sb.append("-" + arg.substring(6, 8));
		return sb.toString();
	}
	/**
	 * 转换字符串为日期
	 * 
	 * @param sdate
	 * @return
	 * @throws ConversionException
	 */
	public static Date convertDate(String sdate) throws ConversionException {
		try {
			return df.parse(sdate);
		} catch (ParseException e) {
			log.error(e);
			throw new ConversionException("转换字符串" + sdate + "到日期错误");
		}
	}
}
