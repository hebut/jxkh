package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GH_PROJECTSOURCE;
import org.iti.gh.service.ProjectSourceService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ProjectSourceServiceImpl extends BaseServiceImpl implements ProjectSourceService {

	public List<GH_PROJECTSOURCE> findByName(String name) {
		String queryString = "from GH_PROJECTSOURCE as ps where ps.psName like '%"+name+"%'";
		return getHibernateTemplate().find(queryString);
	}

	public List<GH_PROJECTSOURCE> findByNameAndPaging(String name,int pageNum,int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "from GH_PROJECTSOURCE as ps where ps.psName like '%"+name+"%' order by ps.psId";
		Query query = session.createQuery(queryString);
        query.setFirstResult(pageNum*pageSize);
        query.setMaxResults(pageSize);
        List<GH_PROJECTSOURCE>  list = query.list();
        session.close();
		return list;
	}

	public List<Long> getCountByName(String name) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select count(ps.psId) from GH_PROJECTSOURCE as ps where ps.psName like '%"+name+"%'";
		Query query = session.createQuery(queryString);
		List<Long> list = query.list();
		session.close();
		return list;
	}

	public boolean isNameRepeat(String name) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "from GH_PROJECTSOURCE as ps where ps.psName = ?";
		Query query = session.createQuery(queryString);
		query.setParameter(0, name);
		List<GH_PROJECTSOURCE> list = query.list();
		boolean is = false;
		if(list.size()>0)
			is = true;
		return is;
	}
	
	
}
