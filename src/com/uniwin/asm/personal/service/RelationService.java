package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.QzRelation;
import com.uniwin.basehs.service.BaseService;

public interface RelationService extends BaseService {
	/**
	 * ��ѯδ�����ε�����
	 */
	public List<QzRelation> findRelation(Long kuid, Short show);

	/**
	 * ���������ѯȺ���Ա
	 */
	public List findMemberBySubject(Long sjId, Boolean sign);
}
