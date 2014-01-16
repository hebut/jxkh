package org.iti.bysj.service.impl;

import java.util.List;

import org.iti.bysj.entity.BsCphase;
import org.iti.bysj.service.CphaseService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class CphaseServiceImpl extends BaseServiceImpl implements CphaseService {

	public List findByBbId(Long bbid) {

		String queryString = "from BsCphase as bc where bc.bbId=?";
		return getHibernateTemplate().find(queryString, new Object[] { bbid });
	}

	public List findByBbidAndUpuser(long bbid, short upuser) {
		String queryString = "from BsCphase as bc where bc.bbId=? and bc.bcpUpuser=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { bbid, upuser });
	}

	public List findByBprid(Long bprid) {
		String queryString = "from BsCphase as bc where bc.buId in (select bp.buId from BsProject as bp where bp.bprId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { bprid });
	}

	public List findByBuId(Long BuId) {
		String queryString = "from BsCphase as bc where bc.buId =?";
		return getHibernateTemplate().find(queryString, new Object[] { BuId });
	}

	public List findByBuidAndUpuser(Long buid, short upuser) {
		String queryString = "from BsCphase as bc where bc.buId=? and bc.bcpUpuser=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { buid, upuser });
	}

	public List findByBuIdAndBbId(long buid, long bbid) {
		String queryString = "from BsCphase as bc where bc.buId=? and bc.bbId=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { buid, bbid });
	}
	public Long getMinStartAndBuid(Long buId) {
		String queryString="select min(bp.bcpStart) from BsCphase as bp where bp.buId=?  and bp.bcpStart<>0";
	    List list= getHibernateTemplate().find(queryString, new Object[]{buId});
	    if(list==null || list.size()==0){
	    	return  0L;
	    }else{
	    	return (Long)list.get(0);
	    }
	}
	public Long getMaxEndAndBbid(Long bbId) {
		String queryString="select max(bp.bcpEnd) from BsCphase as bp where bp.bbId=?  and bp.bcpEnd<>0";
	    List list= getHibernateTemplate().find(queryString, new Object[]{bbId});
	    if(list==null || list.size()==0){
	    	return  0L;
	    }else{
	    	return (Long)list.get(0);
	    }
	}
	public Long getMinStartAndBbid(Long bbId) {
		String queryString="select min(bp.bcpStart) from BsCphase as bp where bp.bbId=?  and bp.bcpStart<>0";
	    List list= getHibernateTemplate().find(queryString, new Object[]{bbId});
	    if(list==null || list.size()==0){
	    	return  0L;
	    }else{
	    	return (Long)list.get(0);
	    }
	}
	public Long getMaxEndAndBuid(Long buId) {
		String queryString="select max(bp.bcpEnd) from BsCphase as bp where bp.buId=?  and bp.bcpEnd<>0";
	    List list= getHibernateTemplate().find(queryString, new Object[]{buId});
	    if(list==null || list.size()==0){
	    	return  0L;
	    }else{
	    	return (Long)list.get(0);
	    }
	}
//	public Long findWhenNowNB(Long now, Long buid) {
//		String queryString = "from BsCphase as bc where bc.buId=?";
//		List lst = getHibernateTemplate().find(queryString,
//				new Object[] { buid });
//		for (int i = 0; i < lst.size(); i++) {
//			BsCphase cp = (BsCphase) lst.get(i);
//			if (now > cp.getBcpStart()
//					&& now + 24 * 3600 * 1000 < cp.getBcpEnd()) {
//				return cp.getBcpId();
//			}
//		}
//		return null;
//	}
//
//	public Long findWhenNowYB(Long now, Long bbid) {
//		String queryString = "from BsCphase as bc where bc.bbId=?";
//		List lst = getHibernateTemplate().find(queryString,
//				new Object[] { bbid });
//		for (int i = 0; i < lst.size(); i++) {
//			BsCphase cp = (BsCphase) lst.get(i);
//			if (now > cp.getBcpStart()
//					&& now < cp.getBcpEnd()+ 24 * 3600 * 1000 ) {
//				return cp.getBcpId();
//			}
//		}
//		return null;
//	}
}
