package com.iti.common.util;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;
public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {

	private static Logger log = Logger.getLogger(ContextLoaderListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.debug("��������");
		super.contextInitialized(event);
		log.debug("�����������");
		BeanFactory.setWebApplicationContext(WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext()));

		/*// Load Data to Cache
		BaseService baseService = (BaseService) BeanFactory
				.getBean("baseService");
		// WkTDept
		EhcacheUtil.loadWkTDeptToCache(baseService);
		// WkTRole
		EhcacheUtil.loadWkTRoleToCache(baseService);
		// WkTTitle
		EhcacheUtil.loadWkTTitleToCache(baseService);
//		// WkTUserRole
//		EhcacheUtil.loadWkTUserRoleToCache(baseService);
*/
		PropertiesLoader.loader("upload", "resources/upload/upload.properties");
	}
}
