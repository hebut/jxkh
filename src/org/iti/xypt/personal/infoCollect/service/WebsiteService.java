package org.iti.xypt.personal.infoCollect.service;


import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTTotal;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTSite;
import com.uniwin.framework.entity.WkTUser;





public interface WebsiteService extends BaseService {
	
	/**
	 * 
	 * @param ptid 父站点ID
	 * @return  所有子站点
	 */
	public List getChildWebsite(Long ptid);
	public List getChildUsersort(Long ptid);
	public List getChildWebsite(Long ptid,Long did);
/**
 * 
 * @param kwid  站点ID
 * @return  站点
 */
	public WkTWebsite findBykwid(Long kwid);
	public WkTSite findByKsid(Long ksid) ;
	/**
	 * 获取站点的权限
	 */
	public List findAuthOfWebsite(Long kwid);
	/**
	 * 
	 * @param pid  父站点ID
	 * @return  父站点信息
	 */
	public List findByKwPid(Long pid);
	/**
	 * <li>获得具有管理权限的站点列表
	 * @param user
	 * @param deptList
	 * @param titleList
	 * @return 站点列表
	 * 2010-7-20
	 */
	public List getWebsiteOfUserManage(WkTUser user,List deptList,List titleList);
	public List getWebsiteOfUserManage(WkTUser user,List deptList);
	public List getWebsiteOfManage(WkTUser user,List deptList,List rlist);
	/**
	 * @param kwid 站点ID
	 * @return 站点权限
	 */
	public List getAuth(Long kwid);
	/**
	 * 
	 * @param kuid 用户ID
	 * @return 用户角色
	 */
	public List getRole(Long kuid);
	/**
	 * 
	 * @param kwid  站点ID
	 * @return 站点访问统计情况
	 */
	public WkTTotal getTotal(Long kwid);
	public List getChildNum(Long kusid);
}
