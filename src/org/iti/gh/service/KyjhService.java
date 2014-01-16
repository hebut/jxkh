package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface KyjhService extends BaseService {

	/**
	 * 根据用户id查找未来五年计划
	 * @param kuid
	 * @return
	 */
	public List findbyKuid(Long kuid);
	/**
	 * 根据单位部门编号查找该单位部门教师未来五年计划
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
}
