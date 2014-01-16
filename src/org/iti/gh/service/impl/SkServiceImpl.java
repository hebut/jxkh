package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhSk;
import org.iti.gh.service.SkService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class SkServiceImpl extends BaseServiceImpl implements SkService {
//
//	public List findByPxmcAndKdid(String mc, long kdid) {
//		String queryString="from GhPx as px where px.pxMc=? and px.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
//		return getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
//	}

	public List findByKdId(long kdid) {
		String queryString="select distinct px.skMc from GhSk as px where px.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		return getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
	}

	public List findByKuid(long kuid) {
		String queryString="from GhSk as px where px.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] {kuid});
	}

	public Long findSumKdId(long kdid) {
		String queryString="select count(distinct px.skMc) from GhSk as px where px.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		List list=getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
		if(list!=null&&list.size()>0){
			return (Long)list.get(0);
		}else{
			return 0l;
		}
	}

	public List findByMc(String name) {
		String queryString="from GhSk as px where px.skMc=?  order by px.kuId";
		return getHibernateTemplate().find(queryString, new Object[] {name});
	}
	
	public List findByKdIdAudit(long kdid) {
		String queryString=" from GhSk as px where px.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) and px.auditState is 0";
		return getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
	}

	public List<GhSk> findBySkIds(String ids) {
		String queryString = "from GhSk as sk where sk.skId in ("+ids+") order by sk.skYear desc";
		return getHibernateTemplate().find(queryString);
	}


}
