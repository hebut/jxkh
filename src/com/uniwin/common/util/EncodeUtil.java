package com.uniwin.common.util;

/**
 * <li>MD5�������,�������û�������м��ܴ洢
 * @author DaLei
 * @2010-3-15
 */
import java.util.Date;

public class EncodeUtil {
	/**
	 * <li>��������������JDK�Դ�MD5���ܶ��ַ��˽��м���
	 * 
	 * @param argҪ���ܵ��ַ���
	 * @return String ���ܺ��ַ���
	 * @author DaLei
	 */
	public static String encodeByMD5(String arg) {
		return arg;
		// java.security.MessageDigest alga;
		// try {
		// alga = java.security.MessageDigest.getInstance("MD5");
		// alga.update(arg.getBytes());
		// byte[] digesta = alga.digest();
		// return byte2hex(digesta);
		// } catch (NoSuchAlgorithmException e) {
		// e.printStackTrace();
		// return null;
		// }
	}

	/**
	 * <li>������������JDK�Դ����ܺ�Ķ�������ת��Ϊ�ַ�����
	 * 
	 * @param b
	 * @return String
	 * @author DaLei
	 * @2010-3-15
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static void main(String args[]) {
		String e1 = EncodeUtil.encodeByMD5("");
		System.out.println(e1);
		String e2 = EncodeUtil.encodeByMD5(e1);
		System.out.println(EncodeUtil.encodeByMD5(e2));
	}

	public static String encodeByDES(String buid) {
		DESPlus des;
		try {
			des = new DESPlus("2011-11-11");
			return des.encrypt(buid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buid;
	}

	public static String encodeByDES(Long buid) {
		DESPlus des;
		try {
			des = new DESPlus("2011-11-11");
			return des.encrypt(buid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buid + "";
	}

	public static Long decodeByDES(String buid) {
		DESPlus des;
		try {
			des = new DESPlus("2011-11-11");
			return Long.parseLong(des.decrypt(buid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	public static String decodeByDESStr(String buid) {
		DESPlus des;
		try {
			des = new DESPlus("2011-11-11");
			return String.valueOf(des.decrypt(buid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
