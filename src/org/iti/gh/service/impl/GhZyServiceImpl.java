package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.GhZyService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class GhZyServiceImpl extends BaseServiceImpl implements GhZyService {

	public List findByChild(Long zyPid) {
		String queryString="select from GhZy as zy where zy.zyPid=?";
		return getHibernateTemplate().find(queryString, new Object[] {zyPid});
	}

}
