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
	public static final SimpleDateFormat fromat_2 = new SimpleDateFormat("yyyy��MM��dd��");
	public static final SimpleDateFormat fromat_3 = new SimpleDateFormat("HHmm");
	public static final SimpleDateFormat fromat_4 = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat format_5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat format_6 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat format_7 = new SimpleDateFormat("yyyyMMddHHmm");
	
	/**
	 * ������ת�ַ�ʱ�䣬ת��Ϊyyyy-MM-dd
	 * 
	 * @param time
	 * @return
	 */
	public static final String longConvertToTime(Long time) {
		return fromat_1.format(new Date(time));
	}
	/**
	 * ������ת�ַ�ʱ�䣬ת��Ϊyyyy��MM��dd��
	 * @param time
	 * @return
	 */
	public static final String longConvertToTime2(Long time) {
		return fromat_2.format(new Date(time));
	}
	/**
	 * ������ת�ַ�ʱ�䣬ת��ΪHHmm
	 * @param time
	 * @return
	 */
	public static final String longConvertToTime3(Long time) {
		return fromat_3.format(new Date(time));
	}
	
	/**
	 * ������ת�ַ�ʱ�䣬ת��ΪyyyyMMddHHmm
	 * @param time
	 * @return
	 */
	public static final String longConvertToTime7(Long time) {
		return format_7.format(new Date(time));
	}
	/**
	 * ���ݸ�����ʱ���ʽ����������ʱ��ת����Date����
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
	 *  ���ݸ�����ʱ���ʽ��ʱ��Long��ת����String
	 * @param timeFormat
	 * @param time
	 * @return
	 */
	public static final String getStringByLong(SimpleDateFormat timeFormat,Date time) {
		return timeFormat.format(time);
	}
	/**
	 * ��2010�꿪ʼ��ȡ���ֱ����ǰ��
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
	 * <li>�������������ڼ������õ��µ�����
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
