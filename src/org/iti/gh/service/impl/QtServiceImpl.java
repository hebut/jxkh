package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.QtService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class QtServiceImpl extends BaseServiceImpl implements QtService {
	public List findByKuid(Long kuId) {
		String queryString = "from GhQt as qt where qt.kuId = ?";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}

	public List findByKdId(long kdid) {
		String queryString="select distinct qt.qtMc from GhQt as qt where qt.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) ";
		return getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
	}

	public List findSumKdId(long kdid) {
		String queryString="from GhQt as qt where qt.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) ";
		List list=getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	public List findByMc(String name) {
		String queryString = "from GhQt as qt where qt.qtMc = ? order by qt.kuId";
		return getHibernateTemplate().find(queryString,new Object[]{name});
	}

}
