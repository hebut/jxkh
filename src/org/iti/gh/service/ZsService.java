package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface ZsService extends BaseService {
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦ�������
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	/**
	 * �����û����Ҹý�ʦ���������
	 * @param kuid
	 * @return
	 */
	public List findByKuid(long kuid);
	/**
	 * �����û�����ݲ��Ҹý�ʦ���������
	 * @param kuid��year
	 * @return
	 */
	public List findByKuidAndYear(long kuid,String year);
}
