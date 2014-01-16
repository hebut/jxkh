package com.uniwin.asm.personal.service.impl;

import java.util.List;

import com.uniwin.asm.personal.entity.QzRelation;
import com.uniwin.asm.personal.service.RelationService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class RelationServiceImpl extends BaseServiceImpl implements RelationService {
	public List<QzRelation> findRelation(Long kuid, Short state) {
		String queryString = "from QzRelation where kuId=? and rlShow=? order by rlState asc";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, state });
	}

	public List findMemberBySubject(Long sjId, Boolean sign) {
		String queryString;
		if (sign)
			queryString = "select kuId from QzRelation where sjId=?";
		else
			queryString = "from QzRelation where sjId=?";
		return getHibernateTemplate().find(queryString, new Object[] { sjId });
	}
}
