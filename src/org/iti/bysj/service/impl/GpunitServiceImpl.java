package org.iti.bysj.service.impl;

import java.util.List;

import org.iti.bysj.entity.BsGpunit;
import org.iti.bysj.entity.BsPeerview;
import org.iti.bysj.service.GpunitService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class GpunitServiceImpl extends BaseServiceImpl implements GpunitService {

	public List findByBgid(Long bgid) {
		String queryString = "from BsGpunit as bg where bg.bgId=?";
		return getHibernateTemplate().find(queryString, new Object[] {bgid});
	}

	public Short findByBuid(Long Buid) {
		String queryString="select bg.buIfbatch from BsGpunit as bg where bg.buId=?";
		return (Short)getHibernateTemplate().find(queryString, new Object[] { Buid }).get(0);
	}

	public Short findDbzgByBuid(long buid) {
		String queryString="select bg.buDbzg from BsGpunit as bg where bg.buId=?";
		return (Short)getHibernateTemplate().find(queryString, new Object[] { buid }).get(0);
	}

	public Integer findDbzgByBuidAndBbid(long buid, Long bbid) {		
		return null;
	}

	public Short findPacketwayByBuid(Long buid) {
		String queryString="select bg.buPacketway from BsGpunit as bg where bg.buId=?";
		return (Short)getHibernateTemplate().find(queryString, new Object[] { buid }).get(0);
	}

	public Integer findPacketwayByBuidAndBbid(Long buid, Long bbid) {
		return null;
	}
	public Short findSpacketByBuid(long buid) {
		String queryString="select bg.buSpacket from BsGpunit as bg where bg.buId=?";
		return (Short)getHibernateTemplate().find(queryString, new Object[] { buid }).get(0);
	}


	public Integer findSpacketByBuidAndBbid(long buid, Long bbid) {
    	return null;
	}
	public List findByBtId(Long BtId) {
		String queryString = "from BsGpunit as bg where bg.buId in (select bt.buId from BsTeacher as bt where bt.btId=?)";
		return getHibernateTemplate().find(queryString,BtId) ;
	}
	/* (non-Javadoc)
	 * @see org.iti.bysj.service.GpunitService#findByKdidAndGpid(java.lang.Long, java.lang.Long)
	 */
	public BsGpunit findByKdidAndGpid(Long kdId, Long gpid) {	
		String queryString="from BsGpunit as gu where gu.kdId=? and gu.bgId=?";
		List gulist= getHibernateTemplate().find(queryString,new Object[]{kdId,gpid});
		if(gulist.size()>0){
			return (BsGpunit)gulist.get(0);
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.iti.bysj.service.GpunitService#findByKuidAndBgid(java.lang.Long, java.lang.Long)
	 */
	public List findByKuidAndBgid(Long kuid, Long bgid) {
		String queryString="from BsGpunit as gu where gu.bgId=? and gu.buId in (select bt.buId from BsTeacher as bt where bt.kuId=?)";
		return getHibernateTemplate().find(queryString,new Object[]{bgid,kuid});
	}
	public Short isUpdateFruit(Long buid) {
		String queryString="select bg.buIsfruit from BsGpunit as bg where bg.buId=?";
		return (Short)getHibernateTemplate().find(queryString, new Object[]{buid}).get(0);
	}

	/* (non-Javadoc)
	 * @see org.iti.bysj.service.GpunitService#findByPkdidAndBgid(java.lang.Long, java.lang.Long)
	 */
	public List findByPkdidAndBgid(Long kdid, Long bgid) {
		String queryString="from BsGpunit as gu where gu.bgId=? and gu.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)";
		return getHibernateTemplate().find(queryString,new Object[]{bgid,kdid});
	}



	public List findByPeerviewKuidAndBgid(Long kuid, Long bgid) {
		String query="from BsGpunit as gu where gu.bgId=? and  gu.buId in(select peer.buId from BsPeerview as peer where peer.kuId=? and peer.bpvState=?)";
		return find(query, new Object[]{bgid,kuid,BsPeerview.BPV_STATE_Yes});
	}

	public Integer countBUMAX(Long bgid) {
		String queryString="select count(*) from BsGpunit as gu where gu.buMax=0 and gu.bgId=?";
		List list=getHibernateTemplate().find(queryString,new Object[]{bgid});
		if(list.size()==0){
			return 0;
		}
		return Integer.valueOf(list.get(0).toString());
	}

	public Integer countBsGpunit(Long bgid) {
		
		String queryString="select count(*) from BsGpunit as gu where gu.bgId=?";
		List list=getHibernateTemplate().find(queryString,new Object[]{bgid});
		if(list.size()==0){
			return 0;
		}
		return Integer.valueOf(list.get(0).toString());
	}
}
