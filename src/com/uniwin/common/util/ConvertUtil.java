package com.uniwin.common.util;

/**
 * ����ת��������
 * @author DaLei
 * @2010-3-15
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Executions;

public class ConvertUtil {
	private static final Log log = LogFactory.getLog(ConvertUtil.class);
	public static final String SPT = System.getProperty("file.separator");
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	static DateFormat nf = new SimpleDateFormat("yyyy��MM��dd��");
	static DateFormat tf = new SimpleDateFormat("HHʱmm��ss��");
	static DateFormat df_tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static DateFormat df_mf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static DateFormat df_xz = new SimpleDateFormat("yyyyMMdd_HHmm");
	static DateFormat album = new SimpleDateFormat("yyyyMMdd");
	static NumberFormat format = new DecimalFormat("#0.00");
	static DateFormat yr = new SimpleDateFormat("MM��dd��");
    
	public static String convertDouble(Double d) {
		return format.format(d);
	}

	public static String convertYRString(Long time) {
		return yr.format(new Date(time));
	}

	/**
	 * ת������Ϊ�ַ���
	 * 
	 * @param date
	 * @return
	 */
	public static String convertString(Date date) {
		return df.format(date);
	}

	/**
	 * ת������Ϊ�ַ�����ֹ������
	 * 
	 * @param date
	 * @return
	 */
	public static String convertMString(Date date) {
		return df_mf.format(date);
	}

	/**
	 * ת���ַ���Ϊ����
	 * 
	 * @param sdate
	 * @return
	 * @throws ConversionException
	 */
	public static Date convertMDate(String sdate) throws ConversionException {
		try {
			return df_mf.parse(sdate);
		} catch (ParseException e) {
			log.error(e);
			throw new ConversionException("ת���ַ���" + sdate + "�����ڴ���");
		}
	}

	public static String convertStringalbum(Date date) {
		return album.format(date);
	}

	public static Date convertAbbumDate(String date) {
		try {
			return album.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * ת���ַ���Ϊ����
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
			throw new ConversionException("ת���ַ���" + sdate + "�����ڴ���");
		}
	}

	/**
	 * ת���ַ���Ϊ����ʱ�䣬��ȷ����
	 * 
	 * @param sdate
	 * @return
	 * @throws ConversionException
	 */
	public static Date convertDateAndTime(String sdate) throws ConversionException {
		try {
			return df_tf.parse(sdate);
		} catch (ParseException e) {
			log.error(e);
			throw new ConversionException("ת���ַ���" + sdate + "������ʱ�����");
		}
	}

	public static String convertDateAndTimeString(Long d) {
		try {
			Date date = new Date(d);
			return df_tf.format(date);
		} catch (Exception e) {
			return d.toString();
		}
	}
	public static String convertDateAndMiniterString(Long d) {
		try {
			Date date = new Date(d);
			return df_mf.format(date);
		} catch (Exception e) {
			return d.toString();
		}
	}
	public static String convertBsxzDateString(Date  d) {
		try {
			return df_xz.format(d);
		} catch (Exception e) {
			return d.toString();
		}
	}
	public static String convertDateAndTimeString(Date d) {
		try {
			return df_tf.format(d);
		} catch (Exception e) {
			return d.toString();
		}
	}

	public static int convertInt(String value) throws ConversionException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new ConversionException("ת���ַ���" + value + "����������");
		}
	}

	/**
	 * ת������ΪС����ʽ#.##
	 * 
	 * @param value
	 * @return
	 */
	public static String convertCurrency(int value) {
		int ivalue = value / 100;
		int fvalue = value % 100;
		if (fvalue < 10) {
			return ivalue + ".0" + fvalue;
		} else {
			return ivalue + "." + fvalue;
		}
	}

	/**
	 * ת��ʱ��Ϊ�ַ���
	 * 
	 * @param date
	 * @return
	 */
	public static String convertTimeString(Date date) {
		return tf.format(date);
	}

	public static String converTimeString(Long d) {
		Date date = new Date();
		Long time = date.getTime() - d;
		time = time / 1000;
		String stime = new String();
		if (time < 60)
			stime = time.toString() + "��ǰ";
		else {
			time = time / 60;
			if (time < 60)
				stime = time.toString() + "����ǰ";
			else {
				time = time / 60;
				if (time < 24)
					stime = time.toString() + "Сʱǰ";
				else {
					time = time / 24;
					if (time.intValue() == 1) {
						stime = "����";
					} else if (time.intValue() == 2) {
						stime = "ǰ��";
					} else if (time.intValue() < 7) {
						stime = time.toString() + "��ǰ";
					} else {
						Date temp = new Date(d);
						// DateFormat d1 = DateFormat.getDateTimeInstance();
						DateFormat d1 = DateFormat.getDateInstance();
						stime = d1.format(temp);
					}
				}
			}
		}
		return stime;
	}

	public static String convertImageString(String imgString) {
		for (int i = 1; i < 31; i++) {
			imgString = replace("[em:" + i + ":]", "<img src=\"image/face/" + i + ".gif\" class=\"face\">", imgString);
		}
		imgString = replace("[img]", "<img src=\"", imgString);
		imgString = replace("[/img]", "\">", imgString);
		return imgString;
	}

	public static String convertStringImage(String imgString) {
		for (int i = 1; i < 31; i++) {
			imgString = replace("<img src=\"image/face/" + i + ".gif\" class=\"face\">", "[em:" + i + ":]", imgString);
		}
		return imgString;
	}

	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null) {
			return null;
		}
		StringBuffer bf = new StringBuffer();
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			bf.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = -1;
		}
		bf.append(source);
		return bf.toString();
	}

	/**
	 * 
	 * @param strUrl
	 * @return ����Ƿ������ӣ������򷵻� "error open url"
	 */
	public static String getContent(String strUrl) {
		try {
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n ");
			}
			br.close();
			String ss = new String(sb.toString().getBytes(), "utf-8");
			return ss;
		} catch (Exception e) {
			return "error open url" + strUrl;
		}
	}

	public static String convertFileSize(Long size) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
		long k = size / 1024;
		if (k > 1024) {
			long m = k / 1024;
			return df.format(m) + "M";
		} else {
			return df.format(k) + "k";
		}
	}

	public static String convertAlbumString(Date d) {
		return album.format(d);
	}

	public static String convertPokeIconidString(Integer iconid) {
		switch (iconid) {
		case 1:
			return "<img src=\"image/poke/cyx.gif\"/>��һ��";
		case 2:
			return "<img src=\"image/poke/wgs.gif\"/>�ո���";
		case 3:
			return "<img src=\"image/poke/wx.gif\"/>΢Ц";
		case 4:
			return "<img src=\"image/poke/jy.gif\"/>����";
		case 5:
			return "<img src=\"image/poke/pmy.gif\"/>������";
		case 6:
			return "<img src=\"image/poke/yb.gif\"/>ӵ��";
		case 7:
			return "<img src=\"image/poke/fw.gif\"/>����";
		case 8:
			return "<img src=\"image/poke/nyy.gif\"/>������";
		case 9:
			return "<img src=\"image/poke/gyq.gif\"/>��һȭ";
		case 10:
			return "<img src=\"image/poke/dyx.gif\"/>��һ��";
		case 11:
			return "<img src=\"image/poke/yw.gif\"/>����";
		case 12:
			return "<img src=\"image/poke/ppjb.gif\"/>���ļ��";
		case 13:
			return "<img src=\"image/poke/yyk.gif\"/>ҧһ��";
		default:
			return null;
		}
	}

	public static boolean ifurl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int state = conn.getResponseCode();
			System.out.println(state);
			if (state == 200)
				return true;
			else
				return false;
		} catch (IOException e) {
			return false;
		}
	}

	public static String encrypt(String inStr) {
		MessageDigest md = null;
		String out = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(inStr.getBytes());
			out = byte2hex(digest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public static String byte2hex(byte[] b) // ������ת�ַ���
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "0";
		}
		return hs.toUpperCase();
	}

	public static String datetostring(Date date) {
		return nf.format(date) + tf.format(date);
	}

	public static String convertDateString(Date date) {
		return nf.format(date);
	}

	public static String htmlTotxt(String s) {
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
							return "html���Ƕ�ײ�������";
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

	// ��str�е�����oldStr�滻ΪnewStr
	public static String replacehtml(String str, String oldStr, String newStr) {
		if (str != null) {
			int index = 0;
			int oldLen = oldStr.length(); // oldStr�ַ�������
			int newLen = newStr.length(); // newStr�ַ�������
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
	public static int countDays(String begin,String end){
		  int days = 0;
		  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar c_b = Calendar.getInstance();
		  Calendar c_e = Calendar.getInstance();
		  
		  try{
		   c_b.setTime(df.parse(begin));
		   c_e.setTime(df.parse(end));
		   
		   while(c_b.before(c_e)){
		    days++;
		    c_b.add(Calendar.DAY_OF_YEAR, 1);
		   }
		   
		  }catch(ParseException pe){
		   System.out.println("���ڸ�ʽ����Ϊ��yyyy-MM-dd���磺2010-4-4.");
		  }
		  return days; 

	}
	/** 
     * <li>�������������ϵͳ��ǰ·����
     * @param HttpServletRequest
     * @param httpservletrequest
     * @author bobo
     * @serialData 2010-7-21    
     * @return
     */
	public static String getPath(String path){
		String s = Executions.getCurrent().getDesktop().getWebApp().getRealPath(path);
		if (!s.endsWith(SPT)){
			s = s + SPT;
		}
		return s;
	}
	
	// public static void main(String[] args){
	// System.out.println(convertDateAndTime("2009-12-10 23:33:01").getTime());
	// }
}
