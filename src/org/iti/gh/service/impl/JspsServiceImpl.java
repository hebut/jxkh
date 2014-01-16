package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GhJsps;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.JspsService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JspsServiceImpl extends BaseServiceImpl implements JspsService {

	public List<GhJsps> findByKuid(Long kuid) {
		String queryString = "from GhJsps as ps where ps.kuId = ?";
		return getHibernateTemplate().find(queryString,new Object[]{kuid});
	}

	public GhJsps getByKuidYear(Long kuid, Integer year) {
		String queryString = "from GhJsps as ps where ps.kuId = ? and ps.jspsYear = ?";
		List<GhJsps> list = getHibernateTemplate().find(queryString,new Object[]{kuid,year});
		if(null!=list&&list.size()>0)
		{
			return (GhJsps)list.get(0);
		}else{
			return null;
		}
	}

	public List<GhJsps> findByYear(Integer year) {
		String queryString = "from GhJsps as ps where ps.jspsYear = ?";
		return getHibernateTemplate().find(queryString,new Object[]{year});
	}

	public List<GhJsps> findByYearAndPage(Integer year, int pageNum,int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "from GhJsps as ps where ps.jspsYear = ?";
		Query query = session.createQuery(queryString);
		query.setParameter(0, year);
		query.setFirstResult(pageNum*pageSize);
	    query.setMaxResults(pageSize);
	    List<GhJsps> list = query.list();
	    session.close();
	    return list;
	}

	
}
