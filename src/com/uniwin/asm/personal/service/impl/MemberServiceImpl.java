package com.uniwin.asm.personal.service.impl;

import java.util.List;

import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.asm.personal.service.MemberService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class MemberServiceImpl extends BaseServiceImpl implements MemberService {
	public List<QzMember> findGroup(Long kuid, Short accept, Short agree) {
		String queryString = "from QzMember where mbMember=? and mbAccept=? and mbAgree=?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, accept, agree });
	}

	public List findMemberInSameGroup(Long qzId) {
		String queryString = "from QzMember where qzId=?";
		return getHibernateTemplate().find(queryString, new Object[] { qzId });
	}

	public List findByQzIdAndKuId(Long qzId, Long kuId) {
		String queryString = "from QzMember where qzId=? and mbMember=?";
		return getHibernateTemplate().find(queryString, new Object[] { qzId, kuId });
	}

	public List findJoinGroup(Long qzId) {
		String queryString = "from QzMember where qzId=? and mbAccept=" + QzMember.ACCEPT_YES + " and mbAgree=" + QzMember.AGREE_YES;
		return getHibernateTemplate().find(queryString, new Object[] { qzId });
	}

	public List findJoinGroupExceptMyself(Long qzId, Long kuId, Integer sign) {
		String queryString;
		if (sign == 1)
			queryString = "from QzMember where qzId=? and mbMember<>? and mbAccept=" + QzMember.ACCEPT_YES + " and mbAgree=" + QzMember.AGREE_YES;
		else if (sign == 2)
			queryString = "from WkTUser where kuId in (select distinct mbMember from QzMember where qzId=? and mbMember<>? and mbAccept=" + QzMember.ACCEPT_YES + " and mbAgree=" + QzMember.AGREE_YES + ")";
		else
			queryString = "";
		return getHibernateTemplate().find(queryString, new Object[] { qzId, kuId });
	}

	public List<QzMember> findApplyGroup(Long qzId) {
		String queryString = "from QzMember where qzId=? and mbAgree=" + QzMember.AGREE_NO;
		return getHibernateTemplate().find(queryString, new Object[] { qzId });
	}

	public List findInviteMember(Long qzId) {
		String queryString = "from QzMember where qzId=? and mbAccept=" + QzMember.ACCEPT_NO;
		return getHibernateTemplate().find(queryString, new Object[] { qzId });
	}
}
