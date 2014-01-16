package org.iti.jxkh.service.impl;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_IndicatorHistory;
import org.iti.jxkh.service.BusinessIndicatorService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public  class BusinessIndicatorServiceImpl extends BaseServiceImpl implements BusinessIndicatorService {

	
	public List<Jxkh_BusinessIndicator> getChildBusiness(Long ptid) {
		String queryString="from Jxkh_BusinessIndicator as b where b.kbPid=? order by b.kbOrdno"; 
		return getHibernateTemplate().find(queryString, new Object[]{ptid});
	}
	
	@Override
	public List<Jxkh_BusinessIndicator> getUseChild(Long ptid) {
		String queryString="from Jxkh_BusinessIndicator as b where b.kbPid=? and b.kbStatus =0 order by b.kbOrdno"; 
		return getHibernateTemplate().find(queryString, new Object[]{ptid});
	}

	@Override
	public List<Jxkh_BusinessIndicator> findAll() {
		String queryString="from Jxkh_BusinessIndicator as b order by b.kbValue"; 
		return getHibernateTemplate().find(queryString, new Object[]{});
	}

	@Override
	public List<Jxkh_BusinessIndicator> findFirstIndicator() {
		String queryString="from Jxkh_BusinessIndicator as b where b.kbValue like '_' order by b.kbValue"; 
		return getHibernateTemplate().find(queryString, new Object[]{});
	}

	@Override
	public List<Jxkh_BusinessIndicator> getChild(Long ptid, Long tid) {
		String queryString="from Jxkh_BusinessIndicator as b where b.kbPid=? and b.kbId !=? order by b.kbOrdno"; 
		return getHibernateTemplate().find(queryString, new Object[]{ptid,tid});
	}

	@Override
	public List<Jxkh_BusinessIndicator> findIndicator(Long pid) {
		String queryString="from Jxkh_BusinessIndicator as b where b.kbId =? order by b.kbOrdno"; 
		return getHibernateTemplate().find(queryString, new Object[]{pid});
	}

	@Override
	public List<Jxkh_IndicatorHistory> findIndicatorByTime(String jihTime) {
		String queryString="from Jxkh_IndicatorHistory as jih where jih.jihTime like '"+jihTime+"%' order by jih.kbValue"; 
		return getHibernateTemplate().find(queryString, new Object[]{});
	}

	@Override
	public List<Jxkh_IndicatorHistory> findHistoryByTimeAndPid(String jihTime,Long pid) {
		String queryString;
		if(pid != null) {
			queryString="from Jxkh_IndicatorHistory as jih where jih.jihTime like '"+jihTime+"%' and jih.kbPid=? order by jih.kbValue"; 
		}else {
			queryString="from Jxkh_IndicatorHistory as jih where jih.jihTime like '"+jihTime+"%'order by jih.kbValue"; 
		}
			
		return getHibernateTemplate().find(queryString, new Object[]{pid});
	}

	

//	@Override
//	public List<Jxkh_IndicatorHistory> findSecondByTime(String jihTime,Long pid) {
//		String queryString="from Jxkh_IndicatorHistory as jih where jih.jihTime like '"+jihTime+"%' and jih.kbPid =? order by jih.kbValue"; 
//		return getHibernateTemplate().find(queryString, new Object[]{pid});
//	}
//
//	@Override
//	public List<Jxkh_IndicatorHistory> findThirdByTime(String jihTime,Long pid) {
//		String queryString="from Jxkh_IndicatorHistory as jih where jih.jihTime like '"+jihTime+"%' and and jih.kbPid =? order by jih.kbValue"; 
//		return getHibernateTemplate().find(queryString, new Object[]{pid});
//	}

	public Jxkh_BusinessIndicator getEntityByName(String name) {
		String queryString = "from Jxkh_BusinessIndicator as b where b.kbName=?";
		@SuppressWarnings("unchecked")
		List<Jxkh_BusinessIndicator> blist = getHibernateTemplate().find(queryString, new Object[] { name });
		if (blist.size() == 0) {
			return null;
		} else {
			return blist.get(0);
		}
	}

	@Override
	public List<Jxkh_BusinessIndicator> findById(Long kbId) {
		String queryString="from Jxkh_BusinessIndicator as b where b.kbId =?"; 
		return getHibernateTemplate().find(queryString, new Object[]{kbId});
	}

	@Override
	public List<Jxkh_IndicatorHistory> findByid(Long kbId) {
		String queryString="from Jxkh_IndicatorHistory as b where b.kbId =?"; 
		return getHibernateTemplate().find(queryString, new Object[]{kbId});
	}

	@Override
	public Jxkh_BusinessIndicator findBykbValue(String kbvalue) {
		String queryString = "from Jxkh_BusinessIndicator as b where b.kbValue=?";
		@SuppressWarnings("unchecked")
		List<Jxkh_BusinessIndicator> blist = getHibernateTemplate().find(queryString, new Object[] { kbvalue });
		if (blist.size() == 0) {
			return null;
		} else {
			return blist.get(0);
		}
	}
}
