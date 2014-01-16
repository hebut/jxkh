package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTGuidereg;
import org.iti.xypt.personal.infoCollect.service.GuideService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;



public class GuideServiceImpl extends BaseServiceImpl implements GuideService{

	public List findGuideListById(Long id) {
		
		String query="from WkTGuidereg as model where model.keId=? ORDER BY kgOrderid ASC";
		return getHibernateTemplate().find(query,id);
	}

	public WkTGuidereg findById(Long gid,Long orderId) {
		// TODO Auto-generated method stub
		String query="from WkTGuidereg as u where u.keId=? and u.kgOrderid=?";
		return (WkTGuidereg) getHibernateTemplate().find(query,new Object[]{gid,orderId}).get(0);
		
	}

}
