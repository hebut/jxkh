package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface KyjhService extends BaseService {

	/**
	 * �����û�id����δ������ƻ�
	 * @param kuid
	 * @return
	 */
	public List findbyKuid(Long kuid);
	/**
	 * ���ݵ�λ���ű�Ų��Ҹõ�λ���Ž�ʦδ������ƻ�
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
}
