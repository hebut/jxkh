package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTPickreg;
import org.iti.xypt.personal.infoCollect.service.PickService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;



public class PickServiceImpl extends BaseServiceImpl implements PickService {

	public List findpickReg(Long id) {
		String query="from WkTPickreg as model where model.keId=? ORDER BY kpOrderid ASC";
		return getHibernateTemplate().find(query,new Object[]{id});
	}

	public WkTPickreg findByPidAndOrderId(Long pid, Long orderId) {
		String query="from WkTPickreg as u where u.keId=? and u.kpOrderid=?";
		return (WkTPickreg) getHibernateTemplate().find(query,new Object[]{pid,orderId}).get(0);
		
	}

	

}
