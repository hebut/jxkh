package com.uniwin.basehs.service.impl;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.uniwin.basehs.service.PageTemplate;
import com.uniwin.basehs.util.PageBean;
import com.uniwin.basehs.util.PageResult;

public class PageTemplateImpl extends HibernateTemplate implements PageTemplate {
	public PageTemplateImpl() {
	}

	public PageTemplateImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public PageResult find(String queryString, PageBean pageBean) {
		return find(queryString, (Object[]) null, pageBean);
	}

	public PageResult find(String queryString, Object value, PageBean pageBean) {
		return find(queryString, new Object[] { value }, pageBean);
	}

	public PageResult find(final String queryString, final Object[] values, final PageBean pageBean) {
		List<?> resultList = (List<?>) execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				prepareQuery(queryObject);
				queryObject.setFirstResult((pageBean.getPage() - 1) * pageBean.getPageSize());
				queryObject.setMaxResults(pageBean.getPageSize());
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return queryObject.list();
			}
		});
		String countQuery = getCountQueryString(queryString);
		pageBean.setCount((Integer.valueOf(find(countQuery, values).get(0).toString())).intValue());
		PageResult pageresult = new PageResult();
		pageresult.setResultList(resultList);
		pageresult.setPageBean(pageBean);
		return pageresult;
	}

	private String getCountQueryString(String queryString) {
		String countQuery = "";
		String s[] = queryString.split("order");
		if (s[0].toUpperCase().startsWith("SELECT")) {
			countQuery = "select count(*) from (" + s[0] + ")";
		} else {
			countQuery = "select count(*) " + s[0] + "";
		}
		return countQuery;
	}
}
