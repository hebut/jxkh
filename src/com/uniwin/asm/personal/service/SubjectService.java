package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface SubjectService extends BaseService {
	/**
	 * <li>��������������Ⱥ��id��ѯ���·�����������
	 */
	public List findByQzid(Long qzid);

	/**
	 * <li>�����������������ⴴ�����û�id��ѯ�䷢����������
	 */
	public List findByKuid(Long kuid);

	/**
	 * <li>�����������������ⴴ�����û�id��ѯ�䷢����������
	 */
	public List findSubject(Long qzid);
}
