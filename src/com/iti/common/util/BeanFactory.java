package com.iti.common.util;

import org.springframework.web.context.WebApplicationContext;

public class BeanFactory {
	/**
	 * Spring WebӦ�û���
	 */
	private static WebApplicationContext webApplicationContext = null;

	public static void setWebApplicationContext(WebApplicationContext webApplicationContext) {
		BeanFactory.webApplicationContext = webApplicationContext;
	}
	/**
	 * �õ�����ΪbeanName��Beanʵ��
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		if (webApplicationContext == null) {
			throw new RuntimeException("Ӧ�û�������");
		}
		return webApplicationContext.getBean(beanName);
	}
}
