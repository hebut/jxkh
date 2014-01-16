package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.YjfxhyService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class YjfxhyServiceImpl extends BaseServiceImpl implements YjfxhyService {
	public List findByGyidAndTypeAndSfAndYear(Long gyid,Short type,Short sf,Integer year) {
		String queryString=" from GhYjfxhy as hy where hy.gyId=? and hy.ghType=? and hy.ghSf=? and hy.ghYear=?";
		return getHibernateTemplate().find(queryString, new Object[]{gyid,type,sf,year});
	}
}
