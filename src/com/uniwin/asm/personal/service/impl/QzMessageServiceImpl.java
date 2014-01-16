package com.uniwin.asm.personal.service.impl;

import java.util.List;

import com.uniwin.asm.personal.service.QzMessageService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class QzMessageServiceImpl extends BaseServiceImpl implements QzMessageService {
	public List findBtSjid(Long sjid, Short type) {
		String queryString = "";
		if (type == 1) {
			queryString = "from QzMessage as qm where qm.sjId=? order by mgTime";
		} else if (type == 2) {
			queryString = "from QzMessage as qm where qm.sjId=? and (qm.mgContent is not null or qm.mgPath is not null )order by mgTime";
		}
		return getHibernateTemplate().find(queryString, sjid);
	}

	public List findMyReply(Long kuid) {
		String queryString = "from QzSubject where sjId in (select distinct sjId from QzMessage where mgSpeaker=?) and sjBuilder<>?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, kuid });
	}
}
