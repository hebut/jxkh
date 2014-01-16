package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.YjfxlwService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class YjfxlwServiceImpl extends BaseServiceImpl implements YjfxlwService {

	public List findTypeByGyid(Long gyid) {
		String queryString="select distinct(lw.glType),lw.gyId from GhYjfxlw as lw where lw.gyId=?";
		return getHibernateTemplate().find(queryString, gyid);
	}

	public Long countByGyidAndTypeAndYear(Long gyid, Short type, Integer year) {
		String queryString="select sum(lw.glNum) from GhYjfxlw as lw where lw.gyId=? and lw.glType=? and lw.glYear=?";
		//return (Long)getHibernateTemplate().find(queryString, new Object[]{gyid,type,year}).get(0) ;
		List list=getHibernateTemplate().find(queryString, new Object[]{gyid,type,year});
		return (Long)list.get(0) ;
	}
	public List findByGyidAndTypeAndYear(Long gyid, Short type, Integer year) {
		String queryString="from GhYjfxlw as lw where lw.gyId=? and lw.glType=? and lw.glYear=?";
		//return (Long)getHibernateTemplate().find(queryString, new Object[]{gyid,type,year}).get(0) ;
		return getHibernateTemplate().find(queryString, new Object[]{gyid,type,year});
		 
	}

}
