package org.iti.wp.service.impl;

import java.util.List;

import org.iti.wp.service.WpTypeService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class WpTypeServiceImpl extends BaseServiceImpl implements WpTypeService {

	

	public List findbykdid(Long kdid) {
		String queryString="from WpType as wt where wt.kdId=? order by wt.wtId";
		return getHibernateTemplate().find(queryString, new Object[]{kdid});
	}
	public List findbykdidAndname(Long kdid,String name) {
		String queryString="from WpType as wt where wt.kdId=? and wt.wtName=?";
		return getHibernateTemplate().find(queryString, new Object[]{kdid,name});
	}


}
