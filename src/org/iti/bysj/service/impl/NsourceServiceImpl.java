package org.iti.bysj.service.impl;

import java.util.List;

import org.iti.bysj.entity.BsGpunit;
import org.iti.bysj.entity.BsNsource;
import org.iti.bysj.service.NsourceService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class NsourceServiceImpl extends BaseServiceImpl implements
		NsourceService {

	public List findByBuid(Long buid, short type) {
		String queryString = "from BsNsource as bn where bn.buId=? and bn.bnsType=?";
		return getHibernateTemplate().find(queryString,new Object[] { buid, type });
	}

	public BsNsource findByBnsid(Long bnsid) {
		String queryString = "from BsNsource as bn where bn.bnsId=?";
		List gulist= getHibernateTemplate().find(queryString,new Object[]{bnsid});
		if(gulist.size()>0){
			return (BsNsource)gulist.get(0);
		}else{
			return null;
		}
	}

	public List findByBuId(Long buid) {
		String queryString = "from BsNsource as bn where bn.buId=? ";
		return getHibernateTemplate().find(queryString,new Object[] { buid});
	}

}
