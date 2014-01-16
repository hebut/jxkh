package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.PxqkService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class PxqkServiceImpl extends BaseServiceImpl implements PxqkService {


	public List findByKuid(Long kuId) {
		String queryString = "from GhPxqk as px where px.kuId = ? ";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}



}
