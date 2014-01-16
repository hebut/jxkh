package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhUserdept;
import org.iti.gh.service.UserdeptService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class UserdeptServiceImpl extends BaseServiceImpl implements
		UserdeptService {

	public List<GhUserdept> findByKdId(Long kdid) {
		String queryString="from GhUserdept as userdep where userdep.id.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] {kdid});
	}

	public List<GhUserdept> findByKuId(Long kuid) {
		String queryString="from GhUserdept as userdep where userdep.id.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] {kuid});
	}

}
