package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.service.QkdcService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class QkdcServiceImpl extends BaseServiceImpl implements QkdcService {

	public List findByKuid(Long kuId,String str) {
		String queryString = "from GhQkdc as dc where dc.kuId = ? and dc.nf = ?";
		return getHibernateTemplate().find(queryString, new Object[]{kuId,str});
	}
	
	public List findByKuid(Long kuId) {
		String queryString = "from GhQkdc as dc where dc.kuId = ? order by dc.qkId";
		return getHibernateTemplate().find(queryString, new Object[]{kuId});
	}

	public void deleteByUser(Long kuId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String del1 = "delete from GhCg where kuId=?";
		Query eupdate = session.createQuery(del1);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del2 = "delete from GhFmzl where kuId=?";
		eupdate = session.createQuery(del2);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();

		String del3 = "delete from GhJlhz where kuId=?";
		eupdate = session.createQuery(del3);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del4 = "delete from GhJxbg where kuId=?";
		eupdate = session.createQuery(del4);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del5 = "delete from GhKyjh where kuId=?";
		eupdate = session.createQuery(del5);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del6 = "delete from GhLwzl where kuId=?";
		eupdate = session.createQuery(del6);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del7 = "delete from GhPx where kuId=?";
		eupdate = session.createQuery(del7);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del8 = "delete from GhQkdc where kuId=?";
		eupdate = session.createQuery(del8);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del9 = "delete from GhQt where kuId=?";
		eupdate = session.createQuery(del9);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del10 = "delete from GhSk where kuId=?";
		eupdate = session.createQuery(del10);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del11 = "delete from GhXm where kuId=?";
		eupdate = session.createQuery(del11);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del12 = "delete from GhXshy where kuId=?";
		eupdate = session.createQuery(del12);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
		String del13 = "delete from GhZs where kuId=?";
		eupdate = session.createQuery(del13);
		eupdate.setLong(0, kuId);
		eupdate.executeUpdate();
		
	}

	public List getKuidByKdid(Long kdid) {
		String queryString = "select distinct dc.kuId from GhQkdc as dc,WkTUser as u where dc.kuId = u.kuId and u.kdId=?"+
		" or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)";
		return getHibernateTemplate().find(queryString, new Object[]{kdid,kdid});
	}

}
