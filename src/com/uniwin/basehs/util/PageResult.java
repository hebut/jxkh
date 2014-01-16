package com.uniwin.basehs.util;

import java.util.List;

public class PageResult {
	private List<?> resultList;
	private PageBean pageBean;

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List<?> getResultList() {
		return resultList;
	}

	
	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}
}
