package org.iti.xypt.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface FudaoService extends BaseService {
	/**
	 *<li>功能描述：根据用户编号查询负责的班级
	 */
	List findClassByKuIdAndSchid(Long KuId,Long Schid);

	/**
	 *<li>功能描述：根据组织部门编号查询下属的班级
	 */
	List findClassByKdId(Long KdId);
	/**
	 *<li>功能描述：根据用户编号查询负责的班级
	 */
	List findClassByKuIdAndKdid(Long KuId,Long Kdid);
	/**
	 * 根据用户编号、学校id和年级查询负责的班级
	 * @param KuId
	 * @param Schid
	 * @param grade
	 * @return
	 */
	public List findByKuidAndSchidAndGrade(Long KuId,Long Schid,Integer grade);
}
