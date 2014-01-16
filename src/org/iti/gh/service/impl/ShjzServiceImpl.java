package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.ShjzService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ShjzServiceImpl extends BaseServiceImpl implements ShjzService {

	public List FindByKuid(long kuid) {
		String queryString="from GhShjz as jz where jz.kuId=?  order by jz.jzTime";
		return getHibernateTemplate().find(queryString,new Object[]{kuid});
	}

}
