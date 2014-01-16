package org.iti.gh.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <li>��������:����ת������Ҫwebkey���ݿ��������ַ�����java�������ڻ���ת��
 * @author webkey
 * @version 1.0
 */
public class DateUtil {

	static DateFormat df_tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * <li>���������������ݿ��д洢�����������ַ���ת��Ϊ���ڸ�ʽ�ַ�����
	 * @param ����ȫ�����ַ���
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
	 * <li>���������������ݿ��д洢�����������ַ���ת��Ϊʱ���ʽ�ַ������������£��м���"."������
	 * 
	 * @param ����ȫ�����ַ���
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
	 * <li>���������������ݿ��д洢��ʱ���������ַ���ת��Ϊʱ���ʽ�ַ�����
	 * @param ʱ����ȫ�����ַ���
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
	 * <li>���������������ݿ��д洢������ʱ�������ַ���ת��Ϊ���ڸ�ʽ�ַ�����
	 * @param ���ڼ�ʱ��ȫ�����ַ���
	 * @return
	 * String 
	 * @author webkey
	 * @2010-3-15
	 */
	public static String getTimeString(String arg){
		if(arg==null||arg.trim().length()<12){
			return "δ��¼";
		}		
		StringBuffer sb=new StringBuffer(arg.substring(0, 4));
		sb.append("-"+arg.substring(4, 6));
		sb.append("-"+arg.substring(6, 8));
		sb.append(" "+arg.substring(8, 10));
		sb.append(":"+arg.substring(10,12));
		return sb.toString();
	}
	
	/**
	 * <li>������������java��������ת��Ϊȫ���ֵ��ַ���,��ȡ������
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
	 * <li>������������java��������ת��Ϊȫ���ֵ��ַ���,��ȡ����
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
	 * <li>��������������ַ���date���������Ͷ���
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
	 * <li>��������������ַ������ں�ʱ���������ڡ�
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
