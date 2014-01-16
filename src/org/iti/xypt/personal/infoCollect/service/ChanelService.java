package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTUser;




public interface ChanelService extends BaseService {
	/**
	 * 
	 * @param ptid  父栏目ID
	 * @return  所有子栏目
	 */
	public List getChildChanel(Long ptid);
	/**
	 * 
	 * @param chanelname  栏目名称
	 * @return  该名称对应的栏目
	 */
	public List findByChanelname(String chanelname);
	/**
	 * 
	 * @param pid 父栏目ID
	 * @return  某栏目的父栏目
	 */
	public List findByKcPid(Long pid);
	/**
	 * 
	 * @param pcid 父栏目ID
	 * @param cid  栏目ID
	 * @return  除该子栏目以外的所有子栏目
	 */
	public List getChildChanel(Long pcid,Long cid);
	/**
	 *
	 * <li>获得具有管理权限的栏目列表
	 * @author FengXinhong
	 * @param user
	 * @param deptList
	 * @param titleList
	 * @return 栏目列表
	 */
	public List getChanelsOfUserManage(WkTUser user,List deptList,List titleList);
	public List getChanelsOfUserAccess(WkTUser user,Long kcpid);
	/**
	 * 
	 * @param kcid  栏目ID
	 * @return  该ID对应的栏目
	 */
	public WkTChanel findBykcid(Long kcid);
	public List getChanelByKwid(Long kwid);
	/**
	 * 
	 * @param pid  父栏目ID
	 * @param kwid  站点ID
	 * @return  该站点下对应栏目的所有子栏目
	 */
	public List getChanelByKwid(Long pid,Long kwid);
	/**
	 * 
	 * @param kcid 栏目ID
	 * @return 引用栏目列表
	 */
	public List getSiteChanel(Long kcid);
	/**
	 * 
	 * @param kcid  栏目ID
	 * @return 被引用栏目列表
	 */
	public List getSite(Long kcid);
	/**
	 * 
	 * @return  父栏目不存在的栏目
	 */
	public List getNoPcid();
	/**
	 * 
	 * @param kwid 站点ID
	 * @return 该站点下的顶级栏目
	 */
	public List getChanel(Long kwid);
	/**
	 * 
	 * @param kiid 信息ID
	 * @return 共享栏目
	 */
	public List getSchanel(Long kiid);
	/**
	 * 
	 * @param cid 栏目ID
	 * @return 引用栏目
	 */
	public List getSiteCha(Long cid);
}
