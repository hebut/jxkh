package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhJslw;
import org.iti.gh.service.JslwService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JslwServiceImpl extends BaseServiceImpl implements JslwService {

	public GhJslw findByKuidAndLwidAndType(Long kuid, Long lwid, Short type) {
	String queryString = "from GhJslw as gj where gj.kuId=? and gj.lwzlId=? and gj.jslwtype=?";
		List slist = getHibernateTemplate().find(queryString,new Object[] { kuid, lwid, type });
		if (slist != null && slist.size() > 0) {
			return (GhJslw) slist.get(0);
		} else {
			return null;
		}

	}

	public List findByLwidAndType(Long lwid, Short type) {
		String queryString = "from GhJslw as gj where  gj.lwzlId=? and gj.jslwtype=?";
		List slist = getHibernateTemplate().find(queryString,new Object[] { lwid, type });
		if (slist != null && slist.size() > 0) {
			return  slist;
		} else {
			return null;
		}
	}
	
	

}
