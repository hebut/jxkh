package com.uniwin.framework.service.impl;

import java.util.List;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.service.WebsiteService;

public class WebsiteServiceImpl extends BaseServiceImpl implements WebsiteService {

	public List findByLidAndPassword(String lid, String pw) {
		
		String queryString = "from WkTSite as s where s.ksLid=? and s.ksPassword=?";
		return getHibernateTemplate().find(queryString, new Object[] { lid, pw });
    }
    public List findByMac(String mac) {
		
		String queryString = "from WkTSite as s where s.ksMac=? ";
		return getHibernateTemplate().find(queryString, new Object[] { mac});
    }
    public List findByLidAndPasswordAndMac(String lid, String pw,String mac) {
		
		String queryString = "from WkTSite as s where s.ksLid=? and s.ksPassword=? and s.ksMac=?";
		return getHibernateTemplate().find(queryString, new Object[] { lid, pw ,mac});
    }
}
