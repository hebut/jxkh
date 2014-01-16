package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJlhz;

import com.uniwin.basehs.service.BaseService;

public interface JlhzService extends BaseService {

	/**
	 * 根据单位部门编号查找该单位部门教师交流合作情况
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short ifcj,Short state);
	/**
	 * 根据用户查找该教师的合作项目
	 * @param kuid
	 * @return
	 */
	public List findByKuid(long kuid,Short ifcj);
	/**
	 * 根据单位部门编号查找该单位部门教师交流合作情况，同类项目合并
	 * @param kdid
	 * @return
	 */
	public Long findSumKdId(long kdid,Short ifcj,Short state);
	/**
	 * 根据名称查找该教师的合作项目
	 * @param kuid
	 * @return
	 */
	public List findByMc(String name,Short ifcj, Short state);
	
	/**
	 * 根据教师用户单位id，是否参加，审核状态
	 * @param kdid
	 * @param ifcj
	 * @param state
	 * @return
	 */
	public List findByKdidAndCjAndState(Long kdid,Short ifcj,Short state);
	
	/**
	 * 判重
	 * @param jlhz
	 * @param name
	 * @param ifcj
	 * @return
	 */
	public boolean CheckOnlyOne(GhJlhz jlhz,String name,String hzdx,Short ifcj,Long kuid);
/**
 * 
 * @param kdid
 * @param year
 * @param kuid
 * @param ifcj
 * @param lx
 * @return
 */
	public List findBykdidAndYearAndKuidAndifcj(String year,Long kuid,Short ifcj,Short lx);
}
