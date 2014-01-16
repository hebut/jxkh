package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.basehs.service.BaseService;

public interface MemberService extends BaseService {
	/**
	 * ��ѯ���û���ص�Ⱥ��
	 */
	public List<QzMember> findGroup(Long kuid, Short accept, Short agree);

	/**
	 * ��ѯ����ĳȺ������г�Ա
	 */
	public List findMemberInSameGroup(Long qzId);

	/**
	 * ����Ⱥ��ID���û�ID��ѯ
	 */
	public List findByQzIdAndKuId(Long qzId, Long kuId);

	/**
	 * ��ѯ����Ⱥ������г�Ա
	 */
	public List findJoinGroup(Long qzId);

	/**
	 * ��ѯ�����Լ���������Ⱥ��ĳ�Ա
	 */
	public List findJoinGroupExceptMyself(Long qzId, Long kuId, Integer sign);

	/**
	 * ��ѯ�������Ⱥ����û�
	 */
	public List<QzMember> findApplyGroup(Long qzId);

	/**
	 * ��ѯ����������û�
	 */
	public List findInviteMember(Long qzId);
}
