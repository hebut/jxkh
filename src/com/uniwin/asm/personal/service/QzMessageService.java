package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface QzMessageService extends BaseService {
	/**
	 * ��������id��ѯ�ظ�/�鿴���������Ϣ������ʱ�䵹��
	 * 
	 * 1����鿴��2�ظ�
	 */
	public List findBtSjid(Long sjid, Short type);

	/**
	 * ��ѯ�һظ���������
	 */
	public List findMyReply(Long kuid);
}
