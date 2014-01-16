package com.uniwin.framework.security;

/**
 * <li>权限过滤器，过滤servlet方式打开的URL
 * @author DaLei
 * @2010-3-16
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet Filter implementation class SecurityFilter
 */
public class SecurityFilter implements Filter {
	
	private static Log log=LogFactory.getLog(SecurityFilter.class);
	/**
	 * Default constructor.
	 */
	public String basePath;

	public SecurityFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		log.info(httpRequest.getRemoteAddr());
		// 获得访问的URL地址
		String filterPath = httpRequest.getServletPath();
		// 判断是否有权限访问URL
		if (SecurityUtil.checkPermission(filterPath, httpRequest.getSession())) {
			chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(basePath + "/admin/login.zul");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		basePath = fConfig.getServletContext().getContextPath();
	}
}
