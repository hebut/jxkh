package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.QzRelation;
import com.uniwin.basehs.service.BaseService;

public interface RelationService extends BaseService {
	/**
	 * 查询未被屏蔽的主题
	 */
	public List<QzRelation> findRelation(Long kuid, Short show);

	/**
	 * 根据主题查询群组成员
	 */
	public List findMemberBySubject(Long sjId, Boolean sign);
}
