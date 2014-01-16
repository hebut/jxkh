package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhPx;

import com.uniwin.basehs.service.BaseService;

public interface PxService extends BaseService {
	/**
	 * 根据用户的编号来查找培训
	 * @param kuId
	 * @return
	 */
	List findByKuid(Long kuId);
	/**
	 * 根据单位部门编号查找该单位部门教师培训
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	/**
	 * 根据单位部门编号查找该单位部门教师培训，同类项目合并
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short state);
	/**
	 * 根据名称来查找培训
	 * @param kuId
	 * @return
	 */
	List findByMc(String name);
	/**
	 * 根据用户部门id，审核状态查询指导学生情况
	 * @param kdid
	 * @param state
	 * @return
	 */
	public List findByKdidAndState(Long kdid,Short state);
	/**
	 * 根据名称来查找培训 
	 * @param kuid
	 * @param mc
	 * @return
	 */
	public GhPx findByKuidAndMc(Long kuid,String mc,String time);
}
