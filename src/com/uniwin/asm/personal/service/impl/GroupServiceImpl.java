package com.uniwin.asm.personal.service.impl;

import java.util.List;
import com.uniwin.asm.personal.entity.QzGroup;

import com.uniwin.asm.personal.service.GroupService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class GroupServiceImpl extends BaseServiceImpl implements GroupService {
	public QzGroup findNewCreateGroup(Long kuid) {
		String queryString = "from QzGroup where qzUser=? order by qzTime desc";
		return (QzGroup) getHibernateTemplate().find(queryString, new Object[] { kuid }).get(0);
	}

	public List findgroupBykuid(Long kuid) {
		String queryString = "from QzGroup as qg where qg.qzId in( select qm.qzId from QzMember as qm where qm.mbMember=? and qm.mbAccept=1 and qm.mbAgree=1)";
		return getHibernateTemplate().find(queryString, kuid);
	}

	public List findGroupByTypeOrName(Integer type, String name) {
		StringBuffer queryString = new StringBuffer("from QzGroup where qzName like '%" + name + "%' and ");
		if (type == -1)
			queryString.append("qzType<>?");
		else
			queryString.append("qzType=?");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { type });
	}

	public List<QzGroup> findMyGroup(Long kuId) {
		String queryString = "from QzGroup where qzUser=?)";
		return getHibernateTemplate().find(queryString, kuId);
	}
}
