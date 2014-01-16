package com.uniwin.basehs.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConversionException;

import com.uniwin.common.util.ConvertUtil;

public class PageBean {
	private int count = 0;
	private int pageSize = 6;
	private int pageCount = 0;
	private int page = 1;
	private String p_COUNT = "f_count";
	private String p_PAGESIZE = "f_pageSize";
	private String p_PAGECOUNT = "f_pageCount";
	private String p_PAGE = "f_page";
	private String p_Form = "f_pageForm";

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		if (pageSize > 0) {
			pageCount = (count - 1) / pageSize + 1;
		}
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static PageBean getPageBean(HttpServletRequest request, HttpServletResponse response) {
		PageBean pageBean = new PageBean();
		try {
			pageBean.setCount(ConvertUtil.convertInt(request.getParameter(pageBean.p_COUNT)));
			pageBean.setPageSize(ConvertUtil.convertInt(request.getParameter(pageBean.p_PAGESIZE)));
			pageBean.setPageCount(ConvertUtil.convertInt(request.getParameter(pageBean.p_PAGECOUNT)));
			pageBean.setPage(ConvertUtil.convertInt(request.getParameter(pageBean.p_PAGE)));
		} catch (ConversionException e) {
			pageBean = new PageBean();
		}
		return pageBean;
	}

	public String getP_COUNT() {
		return p_COUNT;
	}

	public String getP_PAGE() {
		return p_PAGE;
	}

	public String getP_PAGECOUNT() {
		return p_PAGECOUNT;
	}

	public String getP_PAGESIZE() {
		return p_PAGESIZE;
	}

	public void setP_COUNT(String p_count) {
		p_COUNT = p_count;
	}

	public void setP_PAGE(String p_page) {
		p_PAGE = p_page;
	}

	public void setP_PAGECOUNT(String p_pagecount) {
		p_PAGECOUNT = p_pagecount;
	}

	public void setP_PAGESIZE(String p_pagesize) {
		p_PAGESIZE = p_pagesize;
	}

	public String getP_Form() {
		return p_Form;
	}

	public void setP_Form(String form) {
		p_Form = form;
	}
}
