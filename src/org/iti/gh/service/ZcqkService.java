package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface ZcqkService extends BaseService {

	/**
	 * 通过用户编号查找所有的培训情况
	 * @param kuId
	 * @return 培训情况
	 */
	List findByKuid(Long kuId);
	

}
