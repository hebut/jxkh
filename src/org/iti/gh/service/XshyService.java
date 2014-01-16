package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhXshy;

import com.uniwin.basehs.service.BaseService;

public interface XshyService extends BaseService {
	/**
	 * 根据单位部门编号查找该单位部门教师学术会议
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid,Short ifcj);
	/**
	 * 根据单位部门编号查找该单位部门教师学术会议，同类项目合并
	 * @param kdid
	 * @return
	 */
	public List findSumKdId(long kdid,Short ifcj,Short state);
	/**
	 * 根据用户查找该教师的学术会议
	 * @param kuid
	 * @return
	 */
	public List findByKuid(long kuid,Short ifcj);
	/**
	 * 根据名称查找该教师的学术会议
	 * @param kuid
	 * @return
	 */
	public List findByMc(String name,Short ifcj, Short state);
	
	/**
	 * <li>功能描述:根据用户部门编号和会议的审核
	 * @param kdid
	 * @param state
	 * @return
	 */
	public List findByKdidAndState(Long kdid,Short ifcj, Short state);
	
	/**
	 * 判重
	 * @param xshy
	 * @param name
	 * @param ifcj
	 * @return
	 */
	public boolean CheckOnlyOne(GhXshy xshy,String name,Short ifcj,Long kuid);
	
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param ifcj
	 * @param lx
	 * @param hyef
	 * @return
	 */
	 public List findByKdidAndYearAndKuidAndIfcjAndLx(String year,Long kuid,Short ifcj,Short lx,String hyef);
}
