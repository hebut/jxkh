package org.iti.kyjf.service;

import java.util.List;

import org.iti.kyjf.entity.Kyzb;

import com.uniwin.basehs.service.BaseService;

public interface ZbService extends BaseService{
	/**
	 * 
	 * @param year
	 * @param kdid
	 * @return
	 */
	public Kyzb findByYear(Integer year,Long kdid);
	/**
	 * 
	 * @param kdid
	 * @return
	 */
	public List findByKdid(Long kdid);
}
