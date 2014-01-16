package com.uniwin.asm.personal.service.impl;

import java.util.List;

import com.uniwin.asm.personal.entity.XYFeedBackReply;
import com.uniwin.asm.personal.service.XYFeedBackService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class XYFeedBackServiceImpl extends BaseServiceImpl implements XYFeedBackService {
	public List findByKuid(Long kuid) {
		String queryString = "from XYFeedBack as fd where fd.kuId = ?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public List getAll() {
		String queryString = "from XYFeedBack as fd order by fd.fbisRep ,fd.fbAddtime desc";
		return getHibernateTemplate().find(queryString);
	}

	public List findByFbid(Long fbId) {
		String queryString = "from XYFeedBackReply as fr where fr.fbId = ?";
		return getHibernateTemplate().find(queryString, new Object[] { fbId });
	}

	public XYFeedBackReply findByFbidAndKuid(Long fbId, Long kuId) {
		String queryString = "from XYFeedBackReply as fr where fr.fbId = ? and fr.kuId = ?";
		List list = getHibernateTemplate().find(queryString, new Object[] { fbId, kuId });
		if (list.size() == 0) {
			return null;
		} else {
			return (XYFeedBackReply) list.get(0);
		}
	}
}
