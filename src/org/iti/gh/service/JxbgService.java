package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJxbg;

import com.uniwin.basehs.service.BaseService;

public interface JxbgService extends BaseService {

	/**
	 * 根据单位部门编号查找该单位部门教师讲学报告情况
	 * @param kdid
	 * @return
	 */
	public List findByKdIdAndCj(long kdid,Short ifcj,Short state);
	/**
	 * 根据用户查找该教师的讲学报告
	 * @param kuid
	 * @return
	 */
	public List findByKuidAndCj(long kuid,Short ifcj);
	/**
	 * 根据用户部门id，是否参加，审核状态查询讲学报告
	 * @param kdid
	 * @param ifcj
	 * @param state
	 * @return
	 */
	public List findByKdidAndCjAndState(Long kdid,Short ifcj,Short state);
	
	/**
	 * 
	 * @param jxbg
	 * @param bgname
	 * @param jxhymc
	 * @param ifcj
	 * @return
	 */
	public boolean CheckOnlyOne(GhJxbg jxbg, String bgname,String jxhymc,Short ifcj,Long kuid);
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @param ifcj
	 * @param lx
	 * @return
	 */
	public List findByKdidAndYearAndKuidAndIfcj(String year,Long kuid,Short ifcj,Short lx);
}
