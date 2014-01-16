package org.iti.jxgl.common.util;

public class Display {
	public String FloatToInteger(float num) {
		String strnum = "";
		if (num != 0) {
			int b_index = 0;
			strnum = String.valueOf(num);
			int e_index = strnum.length();
			if (e_index > 0) {
				b_index = strnum.lastIndexOf(".");
				if (b_index > 0 && (".00".equals(strnum.substring(b_index, e_index)) || ".0".equals(strnum.substring(b_index, e_index)))) {
					strnum = strnum.substring(0, strnum.lastIndexOf("."));
				}
			}
		}
		return strnum;
	}
	
	//处理浮点型数据--duqing
	public  float xiaoshudian(float f) {
		 java.math.BigDecimal bigDecimal = new java.math.BigDecimal(f);
		  bigDecimal = bigDecimal.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		  float g=(float) bigDecimal.doubleValue();
		  return g;
	}
	
	
}
