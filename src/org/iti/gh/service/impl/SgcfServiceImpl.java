package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.SgcfService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class SgcfServiceImpl extends BaseServiceImpl implements SgcfService {

	public List FindByKuid(long kuid) {
		String queryString="from GhSgcf as cf where cf.kuId=?  order by cf.sgYear";
		return getHibernateTemplate().find(queryString,new Object[]{kuid});
	}
}
