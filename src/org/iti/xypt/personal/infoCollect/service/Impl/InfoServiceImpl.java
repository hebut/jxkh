package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.service.InfoService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;


public class InfoServiceImpl extends BaseServiceImpl implements InfoService{

	public void save(WkTOrinfo wkTOrinfo) {
		getHibernateTemplate().save(wkTOrinfo);
	}

	
	public List findBySite(Long id) {
		// TODO Auto-generated method stub
		String query="from WkTDistribute as model where model.keId=? and model.kbStatus=0 ";
		return getHibernateTemplate().find(query,new Object[]{id});
	}


	
	public List findAllPubNews() {
		// TODO Auto-generated method stub
		String query="from WkTDistribute as model where  model.kbStatus=0 ";
		return getHibernateTemplate().find(query);
	}

}
