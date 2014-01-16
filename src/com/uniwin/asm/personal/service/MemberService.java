package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.basehs.service.BaseService;

public interface MemberService extends BaseService {
	/**
	 * 查询与用户相关的群组
	 */
	public List<QzMember> findGroup(Long kuid, Short accept, Short agree);

	/**
	 * 查询属于某群组的所有成员
	 */
	public List findMemberInSameGroup(Long qzId);

	/**
	 * 根据群组ID和用户ID查询
	 */
	public List findByQzIdAndKuId(Long qzId, Long kuId);

	/**
	 * 查询加入群组的所有成员
	 */
	public List findJoinGroup(Long qzId);

	/**
	 * 查询除了自己其他加入群组的成员
	 */
	public List findJoinGroupExceptMyself(Long qzId, Long kuId, Integer sign);

	/**
	 * 查询申请加入群组的用户
	 */
	public List<QzMember> findApplyGroup(Long qzId);

	/**
	 * 查询发出邀请的用户
	 */
	public List findInviteMember(Long qzId);
}
