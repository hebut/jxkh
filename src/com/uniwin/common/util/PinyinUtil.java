package com.uniwin.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Change chinese to pin yin ����תƴ��
 * 
 * @author yuanchao
 * 
 */
public class PinyinUtil {
	// log
	private static Log log = LogFactory.getLog(PinyinUtil.class);

	/**
	 * ��ȡƴ��
	 * 
	 * @param zhongwen
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getPinYin(String zhongwen) throws BadHanyuPinyinOutputFormatCombination {
		log.debug("-------->>Input ZhongWen=" + zhongwen);
		String zhongWenPinYin = "";
		char[] chars = zhongwen.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i], getDefaultOutputFormat());
			// ��ת�����������ַ�ʱ,����null
			if (pinYin != null) {
				zhongWenPinYin += capitalize(pinYin[0]);
			} else {
				zhongWenPinYin += chars[i];
			}
		}
		log.debug("-------->>Output PinYin=" + zhongWenPinYin);
		return zhongWenPinYin;
	}

	/**
	 * Default Format Ĭ�������ʽ
	 * 
	 * @return
	 */
	public static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// Сд
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// û����������
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u��ʾ
		return format;
	}

	/**
	 * Capitalize ����ĸ��д
	 * 
	 * @param s
	 * @return
	 */
	public static String capitalize(String s) {
		char ch[];
		ch = s.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		String newString = new String(ch);
		return newString;
	}
}
