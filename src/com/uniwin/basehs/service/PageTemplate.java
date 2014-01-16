package com.uniwin.basehs.service;

import com.uniwin.basehs.util.PageBean;
import com.uniwin.basehs.util.PageResult;

public interface PageTemplate {
	public PageResult find(String queryString, PageBean pageBean);

	public PageResult find(String queryString, Object value, PageBean pageBean);

	public PageResult find(final String queryString, final Object[] values, final PageBean pageBean);
}
