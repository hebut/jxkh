package org.iti.jxkh.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.entity.JXKH_VoteResult;
import org.iti.jxkh.service.VoteResultService;
import org.springframework.stereotype.Service;
import com.uniwin.basehs.service.impl.AnnBaseServiceImpl;
import com.uniwin.framework.entity.WkTRole;

@Service("voteResultService")
public class VoteResultServiceImpl extends AnnBaseServiceImpl implements VoteResultService {
	Logger logger = Logger.getLogger(VoteResultServiceImpl.class);

	@SuppressWarnings("unchecked")
	public List<JXKH_VoteResult> findResultByVcId(Long vcId) {
		String queryString = "from JXKH_VoteResult where config = ? order by vrNumber desc";
		return getHibernateTemplate().find(queryString, new Object[] { vcId });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_VoteResult> findResultByYear(String year) {
		String queryString = "from JXKH_VoteResult where vrYear = ? order by vrNumber desc";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, year);
		List<JXKH_VoteResult> vrlist = query.list();
		for (JXKH_VoteResult vr : vrlist) {
			JXKH_VoteConfig vc = vr.getConfig();
			vr.setConfig(vc);
			logger.debug(vc.getVcName());
		}
		return vrlist;
	}

	@SuppressWarnings("unchecked")
	public List<WkTRole> findMyRole(Long kuId) {
		String queryString = "from WkTRole where krId in (select distinct id.krId from WkTUserole where id.kuId = ?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuId });
	}

	@Override
	public List<JXKH_VoteResult> findByYearAndKuId(String year,Long kuId) {
		String queryString = "from JXKH_VoteResult where vrYear = ? and kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year,kuId });
	}
}
