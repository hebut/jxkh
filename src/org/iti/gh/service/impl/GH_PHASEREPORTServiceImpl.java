package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.CgService;
import org.iti.gh.service.GH_PHASEREPORTService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class GH_PHASEREPORTServiceImpl extends BaseServiceImpl implements GH_PHASEREPORTService {

	
	public List findStageByProidAndKulid(Long kyId, Long kuId) {	
		String query = "from GH_PHASEREPORT as pr where pr.proId=? and pr.kuId=?";
		return getHibernateTemplate().find(query, new Object[]{kyId,kuId});
	}
	public List findPageCount(Long kyId,Long kuId){
		String query = "select count(pr.id) from GH_PHASEREPORT as pr where pr.proId=? and pr.kuId=?";
		return getHibernateTemplate().find(query, new Object[]{kyId,kuId});		
	}
	
	public List findStageByProidAndKulid(Long kyId,Long kuId,int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		 queryString.append("from GH_PHASEREPORT as pr where pr.proId=? and pr.kuId=?");
		 Query query = session.createQuery(queryString.toString());
		 query.setParameter(0, kyId);
		 query.setParameter(1, kuId);
		  query.setFirstResult(pageNum*pageSize);
	        query.setMaxResults(pageSize);
	        List<GhXm> list = query.list();
	        session.close();
	        return list;		
	}
	public List findByKyxmId(Long kyId) {
		String query = "from GH_PHASEREPORT as pr where pr.proId=?";
		return getHibernateTemplate().find(query, new Object[]{kyId});		
	}
	
	public List findByKyxmId(Long kyId, int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from GH_PHASEREPORT as pr where pr.proId=?");
		Query query = session.createQuery(queryString.toString());
		query.setParameter(0, kyId);
		query.setFirstResult(pageNum*pageSize);
		query.setMaxResults(pageSize);
		List<GhXm> list = query.list();
		session.close();
		return list;
	}
	
	public List findReportSum(Long kyId) {
		String query = "select count(pr.id) from GH_PHASEREPORT as pr where pr.proId=?";
		return getHibernateTemplate().find(query, new Object[]{kyId});		
	}
	
	public List findByKyxmIdAndReportName(Long kyId, String phRepoName,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from GH_PHASEREPORT as pr where pr.proId=? and pr.phRepoName like ?");
		Query query = session.createQuery(queryString.toString());
		query.setParameter(0, kyId);
		query.setParameter(1, "%"+phRepoName+"%");
		query.setFirstResult(pageNum*pageSize);
		query.setMaxResults(pageSize);
		List<GhXm> list = query.list();
		session.close();
		return list;
	}
	
	public List findReportTotalSum(Long kyId, String phRepoName) {
		String query = "select count(pr.id) from GH_PHASEREPORT as pr where pr.proId=? and pr.phRepoName like ?";
		return getHibernateTemplate().find(query, new Object[]{kyId,"%"+phRepoName+"%"});
	}
	
	public List findByKyxmIdAndKeyWord(Long kyId, String keyWord, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from GH_PHASEREPORT as pr where pr.proId=? and pr.keyWord like ?");
		Query query = session.createQuery(queryString.toString());
		query.setParameter(0, kyId);
		query.setParameter(1, "%"+keyWord+"%");
		query.setFirstResult(pageNum*pageSize);
		query.setMaxResults(pageSize);
		List<GhXm> list = query.list();
		session.close();
		return list;
	}
	
	public List findReportTotalSumByKeyWord(Long kyId, String keyWord) {
		String query = "select count(pr.id) from GH_PHASEREPORT as pr where pr.proId=? and pr.keyWord like ?";
		return getHibernateTemplate().find(query, new Object[]{kyId,"%"+keyWord+"%"});
	}
	
	

}
