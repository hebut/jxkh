package com.iti.common.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeConvert implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -898273513143634773L;
	
	public static final SimpleDateFormat fromat_1 = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat fromat_2 = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat fromat_3 = new SimpleDateFormat("HHmm");
	public static final SimpleDateFormat fromat_4 = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat format_5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat format_6 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat format_7 = new SimpleDateFormat("yyyyMMddHHmm");
	
	/**
	 * 长整形转字符时间，转换为yyyy-MM-dd
	 * 
	 * @param time
	 * @return
	 */
	public static final String longConvertToTime(Long time) {
		return fromat_1.format(new Date(time));
	}
	/**
	 * 长整形转字符时间，转换为yyyy年MM月dd日
	 * @param time
	 * @return
	 */
	public static final String longConvertToTime2(Long time) {
		return fromat_2.format(new Date(time));
	}
	/**
	 * 长整形转字符时间，转换为HHmm
	 * @param time
	 * @return
	 */
	public static final String longConvertToTime3(Long time) {
		return fromat_3.format(new Date(time));
	}
	
	/**
	 * 长整形转字符时间，转换为yyyyMMddHHmm
	 * @param time
	 * @return
	 */
	public static final String longConvertToTime7(Long time) {
		return format_7.format(new Date(time));
	}
	/**
	 * 根据给定的时间格式，将给定的时间转换成Date对象
	 * 
	 * @param timeFormat
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static final Date getDate(SimpleDateFormat timeFormat, String time)
			throws ParseException {
		return timeFormat.parse(time);
	}
	/**
	 *  根据给定的时间格式、时间Long型转换成String
	 * @param timeFormat
	 * @param time
	 * @return
	 */
	public static final String getStringByLong(SimpleDateFormat timeFormat,Date time) {
		return timeFormat.format(time);
	}
	/**
	 * 从2010年开始获取年份直到当前年
	 * @return
	 */
	public static final List<String> getYearList() {
		List<String> yearList = new ArrayList<String>();
		Date currentDate = new Date();
		String currentTime = getStringByLong(fromat_4, currentDate);
		for(int i=Integer.valueOf(currentTime.substring(0, 4)).intValue();i>=2010;i--) {
			yearList.add(Integer.valueOf(i).toString());
		}
		return yearList;
	}
	/**
	 * <li>功能描述：日期加天数得到新的日期
	 * 
	 * @return Date
	 * @2010-7-22
	 */
	public static Date addDate(Date d, long day) throws ParseException {
		long time = d.getTime();
		day = day * 24 * 60 * 60 * 1000;
		time += day;
		return new Date(time);
	}
}
