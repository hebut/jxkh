package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface XmzzService extends BaseService {
	
	/**
	 * �������ı�Ų������ĵ�����
	 * @param lwId
	 * @return
	 */
	List findByLwidAndKuid(Long lwid,Short lwType);
	/**
	 * ������Ŀ��Ų������ĵ�����
	 * @param lwId
	 * @return
	 */
	List findByKyidAndKuidAndType(Long kyid,Long kuid,Short lwType);
	/**
	 * ������Ŀ��Ŷ����ı�Ų������ĵ�����
	 * @param lwId
	 * @return
	 */
	List findByKyidAndKuidAndLwidAndType(Long kyid,Long kuid,Long lwid,Short lwType);
}
