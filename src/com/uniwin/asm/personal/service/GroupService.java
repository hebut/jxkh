package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.basehs.service.BaseService;

public interface GroupService extends BaseService {
	/**
	 * ��ѯ�û����������Ⱥ��
	 */
	public QzGroup findNewCreateGroup(Long kuid);

	/**
	 * ��ѯĳһ�û�����������Ⱥ��
	 * 
	 * @param kuid
	 * @return
	 */
	public List findgroupBykuid(Long kuid);

	/**
	 * �������ͻ����Ʋ�ѯȺ��
	 */
	public List findGroupByTypeOrName(Integer type, String name);

	/**
	 * ��ѯ�Լ�����������Ⱥ��
	 */
	public List<QzGroup> findMyGroup(Long kuId);
}
