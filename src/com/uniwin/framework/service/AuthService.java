package com.uniwin.framework.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;

public interface AuthService extends BaseService {
	public List<WkTAuth> getChildAuth(Long aid);
	/**
	 * 根据角色编号找出可以访问的标题
	 * 
	 * @param krid
	 *            角色编号
	 * @return 角色可以访问的标题列表
	 */
	public List<WkTAuth> findByKrid(Long krid);
	/**
	 * 查询不同类型的权限（若参数是1 返回的是标题的权限，2则是频道的权限）
	 * 
	 * @param katype
	 * @return
	 */
	public List<WkTAuth> findByType(String katype);

	/**
	 * 
	 * @param krid
	 * @return 角色信息
	 */
	public WkTRole findrole(Long krid);

	/**
	 * 
	 * @param kdid
	 * @return WkTDept 部门信息
	 */
	public WkTDept finddep(Long kdid);

	/**
	 * <li>功能描述：获得权限列表。
	 * 
	 * @param tid
	 *            标题编号
	 * @param type
	 *            类型编号
	 * @param rlist
	 *            允许角色列表
	 * @param dlist
	 *            允许部门列表
	 * @return List<TitltAuth>
	 * @author DaLei
	 */
	public List<WkTAuth> getAuthOfTitle(Long tid, List<Long> rlist, List<Long> dlist);

	/**
	 * <li>功能描述：删除某个标题的权限..要求权限中的角色和部门在rlist和dlist中
	 * 
	 * @param tid
	 * @param rlist
	 * @param dlist
	 *            void
	 * @author DaLei
	 */
	public void deleteAuthOfTitle(Long tid, List<Long> rlist, List<Long> dlist);

	/**
	 * <li>功能描述：删除某个频道的权限..要求权限中的角色和部门在rlist和dlist中
	 * 
	 * @param cid
	 * @param rlist
	 * @param dlist
	 *            void
	 * @author XiaoFeng
	 */
	public void deleteAuthOfChanel(Long cid, List<Long> rlist, List<Long> dlist);

	/**
	 * <li>功能描述：获得与auth中列值相同的权限列表，用在权限设置的标题增量设置中。
	 * 
	 * @param auth
	 * @return List
	 * @author DaLei
	 */
	public List<WkTAuth> findByExample(WkTAuth auth);

	/**
	 * <li>功能描述：获得某个用户管理的标题的权限。
	 * 
	 * @param deptList
	 *            用户管理的部门列表
	 * @param tid
	 *            获得权限的标题
	 * @return List
	 * @author DaLei
	 */
	public List<WkTAuth> getAuthOfTitle(List<Long> deptList, Long tid);

	/**
	 * 获取所有频道的权限列表
	 * 
	 * @param depList
	 *            部门列表
	 * @param cid
	 *            获取频道的权限
	 * @return List
	 */
	public List<WkTAuth> getAuthOfChanel(List<Long> depList, Long cid);

	/**
	 * <li>功能描述：获得栏目的权限列表。
	 * 
	 * @param cid
	 *            栏目编号
	 * @param type
	 *            类型编号
	 * @param rlist
	 *            允许角色列表
	 * @param dlist
	 *            允许部门列表
	 * @return List<TitltAuth>
	 * @author DaLei
	 */
	public List<WkTAuth> getAuthOfChanel(Long cid, List<Long> rlist, List<Long> dlist);

	/**
	 * 获取频道的权限
	 */
	public List<WkTAuth> findAuthOfChanel(Long kcid);

	public void deleteByRole(WkTRole role);

	public void copyAuthByRole(WkTRole frole, WkTRole trole);

	/**
	 * 根据角色编号和标题访问权限和权限码找出可以访问的标题
	 * 
	 * @param krid
	 *            角色编号
	 * @param type
	 *            标题访问权限
	 * @param code
	 *            权限码
	 * @return 角色可以访问的标题列表
	 */
	public List<WkTAuth> findByKridAndTypeAndCode(Long krid, String type, Short code);
	public void deleteAuthOfTask(Long cid,List rlist,List dlist);
	public List<WkTAuth> getAuthOfTask(List depList, Long wid);
}
