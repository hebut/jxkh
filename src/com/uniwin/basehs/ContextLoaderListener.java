package com.uniwin.basehs;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.uniwin.basehs.util.BeanFactory;

public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("start");
		super.contextInitialized(event);
		System.out.println("success");
		BeanFactory.setWebApplicationContext(WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
	}

}
