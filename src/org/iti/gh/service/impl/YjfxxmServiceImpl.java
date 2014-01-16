package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhYjfxxm;
import org.iti.gh.service.YjfxxmService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class YjfxxmServiceImpl extends BaseServiceImpl implements YjfxxmService {

	public List<GhYjfxxm> findByGyId(Long gyid) {
		String queryString="from GhYjfxxm as gu where gu.gyId=? ";
		return getHibernateTemplate().find(queryString, gyid);
	}

}
