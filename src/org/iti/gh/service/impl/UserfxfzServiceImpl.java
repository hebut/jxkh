package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhFxfz;
import org.iti.gh.service.UserfxfzService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class UserfxfzServiceImpl  extends BaseServiceImpl implements UserfxfzService {

	public List<GhFxfz> findByGyid(Long gyId) {
		String queryString="from GhFxfz as gu where gu.id.gyId=?";
		return getHibernateTemplate().find(queryString, gyId);
	}

	public List<GhFxfz> findFxfzByGyid(Long sch,Long kuid) {
		String queryString="from GhFxfz as gu where gu.id.kuId=? and gu.id.gyId in (select yjfx.gyId from " +
				"GhYjfx as yjfx where yjfx.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid=?))";
		return getHibernateTemplate().find(queryString,new Object[]{kuid,sch});
	}

	public List<GhFxfz> findByKuid(Long kuid) {
		String queryString="from GhFxfz as gu where gu.id.kuId=?";
		return getHibernateTemplate().find(queryString, kuid);
	}


}
