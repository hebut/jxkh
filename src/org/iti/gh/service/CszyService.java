package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface CszyService extends BaseService {

	/**
	 * 通过用户编号查找
	 * @param kuId
	 * @return 获奖科研成果
	 */
	List findByCsid(Long csId);
	
}
