package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface ZcqkService extends BaseService {

	/**
	 * ͨ���û���Ų������е���ѵ���
	 * @param kuId
	 * @return ��ѵ���
	 */
	List findByKuid(Long kuId);
	

}
