package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GhJszz;
import org.iti.gh.entity.GhZz;
import org.iti.gh.service.JszzService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JszzServiceImpl extends BaseServiceImpl implements JszzService {

	public GhJszz findByKuidAndLwidAndType(Long kuid, Long zzid, Short type) {
		 
		String queryString="from GhJszz as jszz where jszz.kuId=? and jszz.zzId=? and jszz.jszzType=?";
		List list=getHibernateTemplate().find(queryString, new Object[]{kuid,zzid,type});
		if(list!=null&&list.size()>0){
			return (GhJszz)list.get(0);
		}else{
	     	return null;
		} 
	}

	public List findByLwidAndType(Long zzid, Short type) {
		String queryString="from GhJszz as jszz where  jszz.zzId=? and jszz.jszzType=?";
		return getHibernateTemplate().find(queryString, new Object[]{zzid,type});
	}

	public List<Long> findCountByKuidAndType(Long kuid, Short type,String selfs) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select count(zz.zzId) from GhZz as zz where zz.auditState="+GhZz.AUDIT_YES
			+" and zz.zzId in (select jszz.jszzId from GhJszz as jszz where jszz.kuId=?" 
			+" and jszz.jszzType=? and jszz.zzSelfs = ?)";
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuid);
		query.setParameter(1, type);
		query.setParameter(2, selfs);
		List<Long> list = query.list();
		session.close();
		return list;
	}

	public int getWordsByKuidType(Long kuid, Short type) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select sum(cast(jszz.zzWords as int )) from GhJszz as jszz where jszz.kuId=?" +
				" and jszz.jszzType=? and jszz.zzId in (select zz.zzId from GhZz as zz where zz.auditState="+GhZz.AUDIT_YES+")";
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuid);
		query.setParameter(1, type);
		List<Long> list = query.list();
		int words = 0;
		if(null!=list&&list.size()>0)
		{
			Long num = list.get(0);
			if(null!=num&&!"".equals(num))
				words = num.intValue();
		}
		session.close();
		return words;
	}
	
	

}
