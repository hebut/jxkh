package com.uniwin.framework.service;

/**
 * <li>标题数据访问接口
 * @author DaLei
 * @2010-3-15
 */
import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;

public interface TitleService extends BaseService {
	/**
	 * <li>功能描述：获得标题的全部孩子标题
	 * 
	 * @param ptid
	 *            父标题编号
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTTitle> getChildTitle(Long ptid);

	/**
	 * <li>获得父标题孩子标题，不包括编号为tid的标题
	 * 
	 * @param ptid
	 *            父标题id
	 * @param tid
	 *            要求孩子节点不包含id为tid
	 * @return 返回属于ptid的孩子标题，但孩子标题编号不为tid
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTTitle> getChildTitleThree(Long ptid);

	public List<WkTTitle> getChildTitle(Long ptid, Long tid);

	/**
	 * <li>功能描述：获得用户访问的标题列表。
	 * 
	 * @param user
	 * @return List
	 * @author DaLei
	 */
	public List<WkTTitle> getTitlesOfUserAccess(WkTUser user);

	public List<WkTTitle> getTitlesOfUserAccess(WkTUser user, Long tpid);

	/**
	 * <li>功能描述： 获得用户管理的标题列表
	 * 
	 * @param deptList
	 *            用户管理部门列表
	 * @param titleList
	 *            要求标题必须在titleList列表中，若空则返回全部
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTTitle> getTitlesOfUserManager(WkTUser user, List<Long> deptList, List<WkTTitle> titleList);

	/**
	 * <li>功能描述：删除标题，同时在此删除标题对应的权限关系。
	 * 
	 * @param title
	 *            void
	 * @author DaLei
	 */
	public void delete(WkTTitle title);

	public List<WkTTitle> getTitleOfRoleAccess(Long ptid, Long rid);

	/**
	 * 获得标题的子标题，要求子标题必须角色的权限内
	 * 
	 * @param pid
	 *            父标题
	 * @param list
	 *            标题范围
	 * @return
	 */
	public List<WkTTitle> getChildTitleInlist(Long pid, Long krid, String typeTitle, Short kacode);
	public WkTTitle getTitle(Long tid) ;
}
