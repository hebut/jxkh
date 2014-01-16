package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GH_ARCHIVE;
import org.iti.gh.service.ArchiveService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ArchiveServiceImpl extends BaseServiceImpl implements ArchiveService {

	public List<GH_ARCHIVE> findByKyId(Long kyId,Long kuId,short category) {
		String queryString = "from GH_ARCHIVE as ar where ar.arProId = ? and ar.arUpUserId = ? and ar.arCategory = ? order by ar.arPostDate desc";
		return getHibernateTemplate().find(queryString,new Object[]{kyId,kuId,category});
	}

	public List<GH_ARCHIVE> findByKyIdAndUserIdAndPage(Long kyId, Long kuId,short category,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "from GH_ARCHIVE as ar where ar.arProId = ? and ar.arUpUserId = ? and ar.arCategory = ? order by ar.arPostDate desc";
		Query query = session.createQuery(queryString);
		query.setParameter(0, kyId);
		query.setParameter(1, kuId);
		query.setShort(2, category);
        query.setFirstResult(pageNum*pageSize);
        query.setMaxResults(pageSize);
        List<GH_ARCHIVE>  list = query.list();
        session.close();
		return list;
	}

	public List<Long> getCountByKyIdAndKuId(Long kyId, Long kuId,short category) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select count(ar.arId) from GH_ARCHIVE as ar where ar.arProId = ? and ar.arUpUserId = ? and ar.arCategory = ?";
		Query query = session.createQuery(queryString);
		query.setParameter(0, kyId);
		query.setParameter(1, kuId);
		query.setShort(2, category);
		List<Long> list = query.list();
		session.close();
		return list;
	}
}
