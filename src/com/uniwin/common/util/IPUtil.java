package com.uniwin.common.util;

import com.uniwin.framework.entity.WkTAuth;

/**
 * <li>功能描述: 进行IP地址拆分判断
 * 
 * @author DaLei
 * @version $Id: IPUtil.java,v 1.1 2011/08/31 07:03:03 ljb Exp $
 */
public class IPUtil {
	/**
	 * <li>功能描述：将用户输入的IP字符串设置为WkTAuth中规定格式
	 * 
	 * @param au
	 * @param ip
	 *            void
	 * @author DaLei
	 */
	public static void setIP(WkTAuth au, String ip) {
		String ip1 = ip.substring(0, ip.indexOf("."));
		if (ip1.indexOf("-") == -1) {
			if (ip1.equalsIgnoreCase("*")) {
				au.setKaIp11(0L);
				au.setKaIp12(0L);
			} else {
				au.setKaIp11(Long.parseLong(ip1));
				au.setKaIp12(Long.parseLong(ip1));
			}
		} else {
			String ip11 = ip1.substring(0, ip1.indexOf("-"));
			String ip12 = ip1.substring(ip1.indexOf("-") + 1);
			au.setKaIp11(Long.parseLong(ip11));
			au.setKaIp12(Long.parseLong(ip12));
		}
		ip = ip.substring(ip.indexOf(".") + 1);
		String ip2 = ip.substring(0, ip.indexOf("."));
		if (ip2.indexOf("-") == -1) {
			if (ip2.equalsIgnoreCase("*")) {
				au.setKaIp21(0L);
				au.setKaIp22(0L);
			} else {
				au.setKaIp21(Long.parseLong(ip2));
				au.setKaIp22(Long.parseLong(ip2));
			}
		} else {
			String ip21 = ip2.substring(0, ip2.indexOf("-"));
			String ip22 = ip2.substring(ip2.indexOf("-") + 1);
			au.setKaIp21(Long.parseLong(ip21));
			au.setKaIp22(Long.parseLong(ip22));
		}
		ip = ip.substring(ip.indexOf(".") + 1);
		String ip3 = ip.substring(0, ip.indexOf("."));
		if (ip3.indexOf("-") == -1) {
			if (ip3.equalsIgnoreCase("*")) {
				au.setKaIp31(0L);
				au.setKaIp32(0L);
			} else {
				au.setKaIp31(Long.parseLong(ip3));
				au.setKaIp32(Long.parseLong(ip3));
			}
		} else {
			String ip31 = ip3.substring(0, ip3.indexOf("-"));
			String ip32 = ip3.substring(ip3.indexOf("-") + 1);
			au.setKaIp31(Long.parseLong(ip31));
			au.setKaIp32(Long.parseLong(ip32));
		}
		ip = ip.substring(ip.indexOf(".") + 1);
		String ip4 = ip;
		if (ip4.indexOf("-") == -1) {
			if (ip4.equalsIgnoreCase("*")) {
				au.setKaIp41(0L);
				au.setKaIp42(0L);
			} else {
				au.setKaIp41(Long.parseLong(ip4));
				au.setKaIp42(Long.parseLong(ip4));
			}
		} else {
			String ip41 = ip4.substring(0, ip4.indexOf("-"));
			String ip42 = ip4.substring(ip4.indexOf("-") + 1);
			au.setKaIp41(Long.parseLong(ip41));
			au.setKaIp42(Long.parseLong(ip42));
		}
	}

	/**
	 * <li>功能描述：判断用户登陆IP字符串与被限制的字符串是否匹配
	 * 
	 * @param loginIP
	 *            用户登陆字符串
	 * @param consIP
	 *            限制的字符串
	 * @return boolean
	 * @author DaLei
	 */
	public boolean checkIP(String loginIP, WkTAuth au) {
		if (au.getIP().length() == 0)
			return true;
		String ip1 = loginIP.substring(0, loginIP.indexOf("."));
		if (!isBetween(Long.parseLong(ip1), au.getKaIp11(), au.getKaIp12())) {
			return false;
		}
		loginIP = loginIP.substring(loginIP.indexOf(".") + 1);
		String ip2 = loginIP.substring(0, loginIP.indexOf("."));
		if (!isBetween(Long.parseLong(ip2), au.getKaIp21(), au.getKaIp22())) {
			return false;
		}
		loginIP = loginIP.substring(loginIP.indexOf(".") + 1);
		String ip3 = loginIP.substring(0, loginIP.indexOf("."));
		if (!isBetween(Long.parseLong(ip3), au.getKaIp31(), au.getKaIp32())) {
			return false;
		}
		loginIP = loginIP.substring(loginIP.indexOf(".") + 1);
		String ip4 = loginIP;
		if (!isBetween(Long.parseLong(ip4), au.getKaIp41(), au.getKaIp42())) {
			return false;
		}
		return true;
	}

	private boolean isBetween(Long uip, Long minIP, Long maxIP) {
		if (minIP.intValue() == 0)
			return true;
		if (uip.intValue() >= minIP.intValue() && uip.intValue() <= maxIP.intValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <li>功能描述：获得IP的long数组。
	 * 
	 * @param loginIP
	 * @return Long[]
	 * @author DaLei
	 */
	public static Long[] getIPLong(String loginIP) {
		if (loginIP.startsWith("0") || loginIP.startsWith("127")) {
			return new Long[] { 127L, 0L, 0L, 1L };
		}
		//Long[] ips = new Long[4];
		String ip1 = loginIP.substring(0, loginIP.indexOf("."));
		loginIP = loginIP.substring(loginIP.indexOf(".") + 1);
		String ip2 = loginIP.substring(0, loginIP.indexOf("."));
		loginIP = loginIP.substring(loginIP.indexOf(".") + 1);
		String ip3 = loginIP.substring(0, loginIP.indexOf("."));
		loginIP = loginIP.substring(loginIP.indexOf(".") + 1);
		String ip4 = loginIP;
		return new Long[] { Long.parseLong(ip1), Long.parseLong(ip2), Long.parseLong(ip3), Long.parseLong(ip4) };
	}

	public static void main(String[] arsg) {
		WkTAuth au = new WkTAuth();
		IPUtil.setIP(au, "101.1.*.111-152");
		System.out.println(au.getIP());
	}
}
