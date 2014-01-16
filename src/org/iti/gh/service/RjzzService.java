package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhRjzz;

import com.uniwin.basehs.service.BaseService;

public interface RjzzService extends BaseService {
	/**
	 * 通过用户编号查找所有的软件著作
	 * @param kuId
	 * @return 软件著作
	 */
	List findByKuid(Long kuId);
	/**
	 * 通过用户编号和用户名称查找有该用户有关但并非该用户添加的软件著作
	 * @param kuid
	 * @param kuname
	 * @return
	 */
	List findByKuidAndUname(Long kuid,String kuname);
	
	/**
	 * <li>功能描述：根据单位id、软件著作的审核状态查询软件著作
	 * @param kdid
	 * @param state
	 * @return
	 */
	List findByKdidAndState(Long kdid,Short state);
	/**
	 * 
	 * @param rjzz
	 * @param mc
	 * @param softno
	 * @return
	 */
	boolean CheckOnlyOne(GhRjzz rjzz,String mc,String softno);
	/**
	 * 
	 * @param mc
	 * @param softno
	 * @return
	 */
	public List findByRjnameAndDjh(String mc, String softno);
	/**
	 * 
	 * @param mc
	 * @param softno
	 * @return
	 */
	public GhRjzz findBynameAndSoftno(String mc, String softno);
	
	/**
	 * 
	 * @param kdid
	 * @param year
	 * @param kuid
	 * @return
	 */
	public List findBykdidAndYearAndKuid(String year,Long kuid);
	
	/**
	 * 根据软件著作ID查询列表
	 * @param ids
	 * @return
	 */
	public List<GhRjzz> findByRjIds(String ids);
}
