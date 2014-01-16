package org.iti.gh.service.impl;


import java.util.List;

import org.iti.gh.entity.GhUseryjfx;
import org.iti.gh.service.UseryjfxService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class UseryjfxServiceImpl extends BaseServiceImpl implements
		UseryjfxService {
	public List<GhUseryjfx> findByKuid(Long kuId){
		String queryString="from GhUseryjfx as gu where gu.id.kuId=?";
		return getHibernateTemplate().find(queryString, kuId);
	}

	public List<GhUseryjfx> findByGyid(Long gyId) {
		String queryString="from GhUseryjfx as gu where gu.id.gyId=?";
		return getHibernateTemplate().find(queryString, gyId);
	}

}
