package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhSk;

import com.uniwin.basehs.service.BaseService;

public interface SkService extends BaseService {
	/**
	 * 根据单位部门编号查找该单位部门教师授课情况
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	public List findByKdIdAudit(long kdid);
	/**
	 * 根据单位部门编号查找该单位部门教师授课情况，合并同类项目
	 * @param kdid
	 * @return
	 */
	public Long findSumKdId(long kdid);
	/**
	 * 根据用户查找该教师的授课情况
	 * @param kuid
	 * @return
	 */
	public List findByKuid(long kuid);
	/**
	 * 根据名称查找该教师的授课情况
	 * @param kuid
	 * @return
	 */
	public List findByMc(String name);
	
	public List<GhSk> findBySkIds(String ids);
	
}
