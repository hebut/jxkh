package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.CszyService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class CszyServiceImpl extends BaseServiceImpl implements CszyService {


	public List findByCsid(Long csId) {
		String queryString = "from GhCszy as cszy where cszy.csId = ?";
		return getHibernateTemplate().find(queryString,new Object[]{csId});
	}

}
