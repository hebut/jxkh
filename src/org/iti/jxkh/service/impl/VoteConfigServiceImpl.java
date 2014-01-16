package org.iti.jxkh.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.service.VoteConfigService;
import org.springframework.stereotype.Service;
import com.uniwin.basehs.service.impl.AnnBaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;

@Service("voteConfigService")
public class VoteConfigServiceImpl extends AnnBaseServiceImpl implements VoteConfigService {

	Logger logger = Logger.getLogger(VoteConfigServiceImpl.class);

	@SuppressWarnings("unchecked")
	public List<WkTDept> findAllDept() {
		String queryString = "from WkTDept where kdState = 0";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	
	@SuppressWarnings("unchecked")
	public List<JXKH_VoteConfig> findConfigByYear(String year) {
		String queryString = "from JXKH_VoteConfig where vcYear = ?";
		return getHibernateTemplate().find(queryString, new Object[] { year });
	}

	public List<JXKH_VoteConfig> findConfigByKuId(String kuId, String year) {
		String queryString = "from JXKH_VoteConfig where vcVoter like '%-" + kuId + "-%' and vcYear=?";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, year);
		@SuppressWarnings("unchecked")
		List<JXKH_VoteConfig> vclist = query.list();
		for (JXKH_VoteConfig vc : vclist) {
			List<JXKH_VoteResult> res = vc.getVcObject();
			vc.setVcObject(res);
			logger.debug(res.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return vclist;
	}

	@Override
	public List<JXKH_VoteConfig> findConfig(Long vcId) {
		String queryString = "from JXKH_VoteConfig where vcId =?";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, vcId);
		@SuppressWarnings("unchecked")
		List<JXKH_VoteConfig> vclist = query.list();
		for (JXKH_VoteConfig vc : vclist) {
			List<JXKH_VoteResult> res = vc.getVcObject();
			vc.setVcObject(res);
			logger.debug(res.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return vclist;
	}

	@Override
	public List<JXKH_VoteResult> findResultByVcId(Long vcId) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_VoteResult as a where a.config='"+vcId+"'");
		Query query = session.createQuery(queryString.toString());
		@SuppressWarnings("unchecked")
		List<JXKH_VoteResult>list=query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}
	@Override
	public List<WkTDept> findManageDept() {
		Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from WkTDept as a where 1=1");
		queryString.append(" and a.kdId in (2,297)");
		Query query = session.createQuery(queryString.toString());
		@SuppressWarnings("unchecked")
		List<WkTDept>list=query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}
}
