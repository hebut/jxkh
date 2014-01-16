package org.iti.jxgl.service.impl;

import java.util.List;

import org.iti.jxgl.entity.JxYear;
import org.iti.jxgl.service.YearService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class YearServiceImpl extends BaseServiceImpl implements YearService {
	public List getAllYear() {
		String queryString = "from JxYear as model";
		return getHibernateTemplate().find(queryString);
	}

	public List getAllGrade() {
		String queryString = "from JxYear as model";
		return getHibernateTemplate().find(queryString);
	}
}
