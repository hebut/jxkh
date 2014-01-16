package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface QtService extends BaseService {
	/**
	 * �����û��ı�������ҿ�������
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ��������
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ����������ͬ����Ŀ�ϲ�
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid);
	/**
	 * �������Ʋ��Ҹõ�λ���Ž�ʦ��������
	 * @param kdid
	 * @return
	 */
	public List findByMc(String name);
}
