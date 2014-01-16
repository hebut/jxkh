package org.iti.bysj.service.impl;

import java.util.List;

import org.iti.bysj.service.PhaseService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class PhaseServiceImpl extends BaseServiceImpl implements PhaseService {

	public List findByBbId(Long bbid) {
		String queryString="from BsPhase as bp where bp.bbId=?";
	    return getHibernateTemplate().find(queryString, new Object[]{bbid});
	}

	public List findByBuIdAndOrder(Long buid, int order) {
		
		String queryString="from BsPhase as bp where bp.buId=? and bp.bpOrder=?";
	    return getHibernateTemplate().find(queryString, new Object[]{buid,order});
	}



	public List findByBuId(Long buid) {
		
		String queryString="from BsPhase as bp where bp.buId=? order by bp.bpOrder";
		return getHibernateTemplate().find(queryString, new Object[]{buid});
		 
	}

	public List findByBuIdAndBbId(Long buId, Long bbId) {

		String queryString="from BsPhase as bp where bp.buId=? and bp.bbId=?";
		return getHibernateTemplate().find(queryString,new Object[] {buId,bbId});
	}

	public List findByBbIdAndOrder(Long buid, int order) {
		String queryString="from BsPhase as bp where bp.bbId=? and bp.bpOrder=?";
	    return getHibernateTemplate().find(queryString, new Object[]{buid,order});
	}

	public Long getMaxEndAndBuidAndOder(Long buId,int order) {
		String queryString="select max(bp.bpEnd) from BsPhase as bp where bp.buId=? and bp.bpOrder=?";
	    List list= getHibernateTemplate().find(queryString, new Object[]{buId,order});
	    if(list!=null && list.size()!=0){
	    	return (Long)list.get(0);
	    }else{
	    	return 0L;
	    }
	}

	public Long getMinStartAndBuidAndOder(Long buId, int order) {
		String queryString="select min(bp.bpStart) from BsPhase as bp where bp.buId=? and bp.bpOrder=? and bp.bpStart<>0";
	    List list= getHibernateTemplate().find(queryString, new Object[]{buId,order});
	    if(list==null || list.size()==0){
	    	return  0L;
	    }else{
	    	return (Long)list.get(0);
	    }
	}

	public List findByBbIdHaveRc(Long bbid) {
		String queryString="from BsPhase as bp where   bp.bbId=? and bp.bpStart<>0";
		return getHibernateTemplate().find(queryString,new Object[] {bbid});
	}

	public List findByBuIdHaveRc(Long buid) {
		String queryString="from BsPhase as bp where   bp.buId=? and bp.bpStart<>0";
		return getHibernateTemplate().find(queryString,new Object[] {buid});
	}

	public List findByKuidAndTime(Long kuid, Long time) {
		String queryString="from BsPhase as bp where   bp.bpStart<=? and bp.bpEnd >=? and bp.buId in (select unit.buId from BsGpunit as unit where (unit.kdId in (select xy.id.kdId from XyNUrd as xy where xy.id.kuId=? and xy.id.kdId<>0))) order by bp.bpStart";
		return getHibernateTemplate().find(queryString,new Object[] {time,time,kuid});
	}
	public List findByKuidAndTime2(Long kuid, Long time) {
		String queryString="from BsPhase as bp where   bp.bpStart<=? and bp.bpEnd >=? and bp.buId in (select unit.buId from BsGpunit as unit where (unit.kdId in (select xy.id.kdId from XyNUrd as xy where xy.id.kuId=? and xy.id.kdId<>0))"+
		"or (unit.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid in (select xy.id.kdId from XyNUrd as xy where xy.id.kuId=? and xy.id.kdId<>0))) "+
        ") order by bp.bpStart";
		return getHibernateTemplate().find(queryString,new Object[] {time,time,kuid,kuid});
	}
	public List findByKuidAndTime3(Long kuid, Long time) {
		String queryString="from BsPhase as bp where   bp.bpStart<=? and bp.bpEnd >=? and bp.buId in (select unit.buId from BsGpunit as unit where (unit.kdId in (select xy.id.kdId from XyNUrd as xy where xy.id.kuId=? and xy.id.kdId<>0))"+
		"or (unit.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid in (select xy.id.kdId from XyNUrd as xy where xy.id.kuId=? and xy.id.kdId<>0))) "+
        "or  (unit.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid in (select dept.kdId from WkTDept as dept where dept.kdPid in (select xy.id.kdId from XyNUrd as xy where xy.id.kuId=? and xy.id.kdId<>0))))) order by bp.bpStart";
		return getHibernateTemplate().find(queryString,new Object[] {time,time,kuid,kuid,kuid});
	}

	public List findByKuidAndNow(Long kuid, Long time) {
		Long end=time-24*3600*1000;
		String queryString="select distinct bp.bpId  from BsPhase as bp where   bp.bpStart<=? and bp.bpEnd >=? and bp.buId in (select unit.buId from BsGpunit as unit where (unit.kdId in (select xy.id.kdId from XyNUrd as xy where xy.id.kuId=? and xy.id.kdId<>0)))  ";
		return getHibernateTemplate().find(queryString,new Object[] {time,end,kuid});
	}
	
}
