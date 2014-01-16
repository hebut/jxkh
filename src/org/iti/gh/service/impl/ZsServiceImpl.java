package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.ZsService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ZsServiceImpl extends BaseServiceImpl implements ZsService {

	public List findByKdId(long kdid) {
		String queryString="from GhZs as zs where zs.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		return getHibernateTemplate().find(queryString, new Object[] { kdid,kdid });
	}

	public List findByKuid(long kuid) {
		String queryString="from GhZs as zs where zs.kuId=? order by zs.year";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public List findByKuidAndYear(long kuid, String year) {
		String queryString="from GhZs as zs where zs.kuId=? and zs.year=?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid,year});
	}

}
