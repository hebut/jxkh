package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhZz;

import com.uniwin.basehs.service.BaseService;

public interface ZzService extends BaseService {

	/**
	 * 根据用户的编号、用户名称，著作种类（1教材,2专著）
	 * @param kuId
	 * @param kuname
	 * @param lx
	 * @param jslwtyype
	 * @return
	 */
	public 	List findAllname(Long kuId,String kuname,Short lx ,Short jszztyype);
	
	/**
	 * 根据用户的编号、用户名称，著作种类（1教材,2专著）教师著作表类型（1 教材，2专著）
	 * @param kuId
	 * @param lx
	 * @param jszztyype
	 * @return
	 */
	public List findByKuidAndType(Long kuId,Short lx,Short jszztyype);
	
	/**
	 * 判重
	 * @param name
	 * @param lx
	 * @param isbn
	 * @return
	 */
	public boolean CheckOnlyOne(GhZz zz,String name,Short lx,String zb);
	
	/**
	 * 
	 * @param kdid
	 * @param lx
	 * @param state
	 * @return
	 */
	public List findSumKdId(long kdid, Short lx, Short state);
	/**
	 * 
	 * @param name
	 * @param lx
	 * @param zb
	 * @return
	 */
	public GhZz findByJcnameAndZbAndLx(String name, Short lx, String zb);
	
	/**
	 * 根据用户id，教材名称和主编姓名，查询没有建立关系的教材
	 * @param kuid
	 * @param jcname
	 * @param zb
	 * @param lx
	 * @param type
	 * @return
	 */
	public List findByKuidAndJcmcAndZbAndLxAndType(Long kuid,String jcname,String zb,Short lx,Short type );
	/**
	 *  查询已审核通过的教材或专著
	 * @param kuid
	 * @param year 
	 * @param lx
	 * @param kuid
	 * @return
	 */
	public List findtjzj(Long kdid, String year, Short lx,Long kuid) ;
	
	/**
	 * 根据著作ID查询列表
	 * @param ids
	 * @return
	 */
	public List<GhZz> findByZzIds(String ids);
}
