package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.ZcqkService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ZcqkServiceImpl extends BaseServiceImpl implements ZcqkService {


	public List findByKuid(Long kuId) {
		String queryString = "from GhZcqc as zc where zc.kuId = ? ";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}



}
