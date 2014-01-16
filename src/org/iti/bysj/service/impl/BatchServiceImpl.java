package org.iti.bysj.service.impl;

import java.util.List;

import org.iti.bysj.entity.BsBatch;
import org.iti.bysj.entity.BsStudent;
import org.iti.bysj.service.BatchService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class BatchServiceImpl extends BaseServiceImpl implements BatchService {

	public BsBatch findByBsId(Long BsId) {
		String queryString = "from BsBatch as bb where bb.bbId in (select bs.bbId from BsStudent as bs where bs.bsId=?)";
		List bslist=getHibernateTemplate().find(queryString, BsId);
		if(bslist.size()>0){
		return (BsBatch)bslist.get(0);
		}else{
			return null;
		}
	}

	public List findByBuId(Long BuId) {
		String queryString = "from BsBatch as bb where bb.buId =?";
		return getHibernateTemplate().find(queryString, BuId);
	}

	public BsBatch findByBuidAndBbname(Long Buid, String Bbname) {
		String queryString = "from BsBatch as bb where bb.buId=? and bb.bbName=?";
		List bblist = getHibernateTemplate().find(queryString, new Object[] { Buid, Bbname });
		if (bblist.size() > 0) {
			return (BsBatch) bblist.get(0);
		} else {
			return null;
		}
	}

	public List findByBuidandBbname(Long Buid, String Bbname) {
		String queryString = "from BsBatch as bb where bb.buId =? and bb.bbName=?";
		return getHibernateTemplate().find(queryString, new Object[] { Buid, Bbname });
	}
}
