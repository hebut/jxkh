package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface QtService extends BaseService {
	/**
	 * 根据用户的编号来查找科研其他
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId);
	/**
	 * 根据单位部门编号查找该单位部门教师科研其他
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	/**
	 * 根据单位部门编号查找该单位部门教师科研其他，同类项目合并
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid);
	/**
	 * 根据名称查找该单位部门教师科研其他
	 * @param kdid
	 * @return
	 */
	public List findByMc(String name);
}
