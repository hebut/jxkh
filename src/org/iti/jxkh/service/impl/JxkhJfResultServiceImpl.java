package org.iti.jxkh.service.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_JFRESULT;
import org.iti.jxkh.service.JxkhJfResultService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JxkhJfResultServiceImpl extends BaseServiceImpl implements JxkhJfResultService {
	@Override
	public List<JXKH_JFRESULT> findJfByYear(String year) {
		Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_JFRESULT as a where a.year='"+year+"'");
		Query query = session.createQuery(queryString.toString());
		@SuppressWarnings("unchecked")
		List<JXKH_JFRESULT>list=query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}
	@Override
	public int findJfResult(String year) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_JFRESULT as a where a.year='"+year+"'");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}
}
