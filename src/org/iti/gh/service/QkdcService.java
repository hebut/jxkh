package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface QkdcService extends BaseService {

	List findByKuid(Long kuid);
	/**
	 * �����û���Ų������������¼
	 * @param kuId
	 * @return
	 * @author DATIAN
	 */
	List findByKuid(Long kuId,String str);
	/**
	 * ɾ���ý�ʦ�����м�¼
	 * @param kuId
	 */
	void deleteByUser(Long kuId);
	
	
	/**
	 * ��ѯĳ����λ��ȫ����д�˱�Ľ�ʦ
	 * @param kdid
	 * @return
	 */
	List getKuidByKdid(Long kdid);

}
