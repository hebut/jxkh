package com.iti.common.util;

import org.springframework.web.context.WebApplicationContext;

public class BeanFactory {
	/**
	 * Spring Web应用环境
	 */
	private static WebApplicationContext webApplicationContext = null;

	public static void setWebApplicationContext(WebApplicationContext webApplicationContext) {
		BeanFactory.webApplicationContext = webApplicationContext;
	}
	/**
	 * 得到名称为beanName的Bean实例
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		if (webApplicationContext == null) {
			throw new RuntimeException("应用环境错误");
		}
		return webApplicationContext.getBean(beanName);
	}
}
