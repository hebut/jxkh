package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhPkpy;

import com.uniwin.basehs.service.BaseService;

public interface PkpyService extends BaseService {
	/**
	 * 通过用户编号查找所有的获评课选优、基本功竞赛
	 * @param kuId
	 * @return 获评课选优、基本功竞赛
	 */
	List findByKuid(Long kuId);
	 
	/**
	 * 
	 * @param kuId
	 * @param mc
	 * @return
	 */
	public GhPkpy findBykuidAndMc(Long kuId,String mc);

}
