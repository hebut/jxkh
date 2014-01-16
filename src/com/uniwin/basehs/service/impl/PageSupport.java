package com.uniwin.basehs.service.impl;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.uniwin.basehs.service.PageTemplate;

public class PageSupport extends HibernateDaoSupport {
	private PageTemplate pageTemplate;

	protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
		pageTemplate = new PageTemplateImpl(sessionFactory);
		return (HibernateTemplate) pageTemplate;
	}

	public PageTemplate getPageTemplate() {
		return pageTemplate;
	}

	public void setPageTemplate(PageTemplate pageTemplate) {
		this.pageTemplate = pageTemplate;
	}
}
