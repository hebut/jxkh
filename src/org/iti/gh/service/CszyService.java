package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface CszyService extends BaseService {

	/**
	 * ͨ���û���Ų���
	 * @param kuId
	 * @return �񽱿��гɹ�
	 */
	List findByCsid(Long csId);
	
}
