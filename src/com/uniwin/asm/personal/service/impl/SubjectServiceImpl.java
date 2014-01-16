package com.uniwin.asm.personal.service.impl;

import java.util.List;

import com.uniwin.asm.personal.service.SubjectService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class SubjectServiceImpl extends BaseServiceImpl implements SubjectService {
	public List findByQzid(Long qzid) {
		String queryString = "from QzSubject as qs where qs.qzId=? order by qs.sjTime desc";
		return getHibernateTemplate().find(queryString, qzid);
	}

	public List findByKuid(Long kuid) {
		String queryString = "from QzSubject as qs where qs.sjBuilder =? order by sjTime desc";
		return getHibernateTemplate().find(queryString, kuid);
	}

	public List findSubject(Long qzid) {
		String queryString = "from QzSubject as qs where qs.qzId=?";
		return getHibernateTemplate().find(queryString, qzid);
	}
}
