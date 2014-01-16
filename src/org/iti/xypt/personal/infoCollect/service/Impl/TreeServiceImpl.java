package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.List;

import org.iti.xypt.personal.infoCollect.service.TreeService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;




public class TreeServiceImpl extends BaseServiceImpl implements TreeService {

	public List findAll() {
		String query="from WkTTasktype as model where model.ktaPid!=0";
		return getHibernateTemplate().find(query);
	}

	public List findById(Long id) {
		String query="from WkTTasktype as model where model.ktaPid=?";
		return getHibernateTemplate().find(query,new Object[]{id});
	}
}
