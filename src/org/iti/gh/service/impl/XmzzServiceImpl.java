package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.XmzzService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class XmzzServiceImpl extends BaseServiceImpl implements XmzzService {

	

	public List findByLwidAndKuid(Long lwid,Short lwType) {
		String queryString = "from GhXmzz as xmzz where xmzz.lwId=? and xmzz.lwType=? ";
		return getHibernateTemplate().find(queryString,new Object[]{lwid,lwType});
	}

	public List findByKyidAndKuidAndType(Long kyid, Long kuid,Short lwType) {
		String queryString = "from GhXmzz as xmzz where xmzz.kyId=?  and xmzz.kuId = ? and xmzz.lwType=? ";
		return getHibernateTemplate().find(queryString,new Object[]{kyid,kuid,lwType});
	}

	public List findByKyidAndKuidAndLwidAndType(Long kyid, Long kuid, Long lwid,Short lwType) {
		String queryString = "from GhXmzz as xmzz where xmzz.kyId=?  and xmzz.kuId = ? and xmzz.lwId = ? and xmzz.lwType=?";
		return getHibernateTemplate().find(queryString,new Object[]{kyid,kuid,lwid,lwType});
	}
}
