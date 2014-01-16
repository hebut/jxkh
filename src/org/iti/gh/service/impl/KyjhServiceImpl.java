package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.KyjhService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class KyjhServiceImpl extends BaseServiceImpl implements KyjhService {

	public List findbyKuid(Long kuid) {
		String queryString="from GhKyjh as gk where gk.kuId=?";
		return getHibernateTemplate().find(queryString, kuid);
	}

	public List findByKdId(long kdid) {
		String queryString="from GhKyjh as ky where ky.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) order by ky.kuId";
		return getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
	}

	
}
