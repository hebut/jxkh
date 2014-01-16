package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhPx;
import org.iti.gh.service.PxService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class PxServiceImpl extends BaseServiceImpl implements PxService {
	public List findByKuid(Long kuId) {
		String queryString = "from GhPx as px where px.kuId = ?";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}

	public List findByKdId(long kdid) {
		String queryString="select distinct px.pxMc from GhPx as px where px.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		return getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
	}

	public List findSumKdId(long kdid, Short state) {
		String queryString="from GhPx as px where px.kuId in(select tea.kuId from Teacher as tea) and px.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(state!=null){
			queryString=queryString+" and px.auditState="+state+"";
		}else{
			queryString=queryString+" and px.auditState is null";
		}
		List list=getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
		if(list!=null&&list.size()>0){
			return  list;
		}else{
			return null;
		}
	}

	public List findByMc(String name) {
		String queryString = "from GhPx as px where px.pxMc = ?  order by px.pxBrzy ,px.kuId";
		return getHibernateTemplate().find(queryString,new Object[]{name});
	}

	public List findByKdidAndState(Long kdid, Short state) {
		String queryString = "from GhPx as px where px.kuId in(select user.kuId from WkTUser as user where user.kdId=? or user.kdId in(select d.kdId from WkTDept as d where d.kdPid=?)) and px.kuId in(select tea.kuId from Teacher as tea)";
			if(state!=null){
				queryString=queryString+" and px.auditState="+state+"";
			}else{
				queryString=queryString+" and px.auditState is null";
			}
			return getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
	}

	public GhPx findByKuidAndMc(Long kuid, String mc, String time) {
		String queryString="from GhPx as px where px.kuId=? and px.pxMc=? and px.pxTime=?";
		List list=getHibernateTemplate().find(queryString, new Object[] {kuid,mc,time});
		if(list!=null&&list.size()>0){
			return (GhPx)list.get(0);
		} else{
			return null;
		
		}
	}

}
