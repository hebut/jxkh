package org.iti.kyjf.service;

import java.util.List;

import org.iti.kyjf.entity.Zbteacher;

import com.uniwin.basehs.service.BaseService;

public interface ZbteacherService extends BaseService {
	/**
	 * 
	 * @param year
	 * @param kdid
	 * @param tno
	 * @param tname
	 * @return
	 */
	public List<Zbteacher> findByYear(Integer year, Long kdid,String tno,String tname);
	
	/**
	 * 
	 * @param kuid
	 * @return
	 */
	public List findByKuid(Long kuid);
}
