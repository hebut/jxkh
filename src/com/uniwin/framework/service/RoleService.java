package com.uniwin.framework.service;

/**
 * <li>角色数据访问接口]
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;

import org.iti.xypt.entity.Student;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTRole;

public interface RoleService extends BaseService {
	/**
	 * <li>功能描述：获得父角色全部子角色
	 * 
	 * @param ptid
	 *            父角色编号
	 * @return List
	 * @author DaLei
	 * @2010-3-16
	 */
	public List<WkTRole> getChildRole(Long ptid);

	/**
	 * <li>功能描述：获得某个用户具有角色列表。
	 * 
	 * @param uid
	 *            用户编号
	 * @return List
	 * @author DaLei
	 */
	public List<WkTRole> getRoleOfUser(Long uid);

	/**
	 * <li>功能描述：获得某个用户具有角色列表，并且角色属于dlist列表中。
	 * 
	 * @param uid
	 *            用户编号
	 * @param dlist
	 *            部门列表
	 * @return List
	 * @author DaLei
	 */
	public List<WkTRole> getRoleOfUser(Long uid, List<Long> dlist);

	/**
	 * <li>功能描述：获得子角色，并要求子角色部门在args中。
	 * 
	 * @param ptid
	 * @param args
	 * @return List
	 * @author DaLei
	 */
	public List<WkTRole> getChildRole(Long ptid, List<Long> args);

	/**
	 * <li>功能描述：获得子角色，并要求子角色部门在args中或者是共享角色。
	 * 
	 * @param ptid
	 * @param args
	 * @return List
	 * @author DaLei
	 */
	public List<WkTRole> getChildRoleOrDefault(Long ptid, List<Long> args);

	/**
	 * <li>功能描述：删除角色，同时删除角色对应的权限关系与其同用户的关系。
	 * 
	 * @param role
	 *            void
	 * @author DaLei
	 */
	public void delete(WkTRole role);

	public WkTRole findByRid(Long krId);

	public List<WkTRole> findByName(String rname);

	public List<WkTRole> FindByName(String rname);

	public List<WkTRole> findSelectAdmins(Long schid, char grade);

	/**
	 * 查询某学校督导角色
	 * 
	 * @param kdid
	 * @return
	 */
	public List<WkTRole> findDudaoRole(Long kdid);

	/**
	 * 查询某个学校计算工作量的领导角色，设定时候限制只能一个角色具有此功能
	 * 
	 * @param kdid
	 * @return
	 */
	public WkTRole findByGzl(Long kdid);

	/**
	 * 查询某个学校管理员角色，角色实体中并无标示，因此查询领导角色，并按照管理角色编号长度和级别排序后获得第一个角色
	 * 
	 * @param kdid
	 * @return
	 */
	public WkTRole getShcAmdinRole(Long kdid);

	/**
	 * 获得某个用户的用户组
	 * 
	 * @param kuId
	 * @return
	 */
	public List<WkTRole> getProleOfUser(Long kuId);

	/**
	 * 获得某个用户的用户组，用户具有功能能够访问某个标题。。在发送通知公告中使用
	 * 
	 * @param kuId
	 * @param tid
	 *            标题编号
	 * @return
	 */
	public List<WkTRole> getProleOfUser(Long kuId, Long tid);

	/**
	 * 获得某个用户的用户组内的角色
	 * 
	 * @param krId
	 *            用户组的角色名称
	 * @param kuId
	 *            用户的用户编号
	 * @return
	 */
	public List<WkTRole> getChildRoleByKuid(Long krId, Long kuId);

	/**
	 * 查询某个通知的通知角色
	 * 
	 * @param xmid
	 * @return
	 */
	public List<WkTRole> findByXmid(Long xmid);

	/**
	 * 根据标题编号和学校编号，查找能访问这个标题的角色
	 * 
	 * @param tid
	 * @param bgId
	 * @return
	 */
	public List<WkTRole> findByTidAndKdid(Long tid, Long kdid);

	/**
	 * 根据角色、姓名或学号查找学生
	 */
	public List<Student> findStuByRole(List<Object> deplist, Long krid, String sname, String sno);
	/**
	 * 根据单位、角色、姓名或学号查找辅导员所带班级的学生
	 */	
	public List<Student> findStuByRole(Long kdid,Long kuid, Long krid, String sname, String sno);
	public List<Student> findStuByRole(Long clid, Long krid, String sname, String sno);

	public List<WkTRole> findAllRole(Long kdid);
	/**
	 * 除去树根
	 * @param kdid
	 * @return
	 */
	public List<WkTRole> findAllRoleWithout(Long kdid);
	public List<WkTRole> findbyKrAdmins(String KrAdmins);
	public List findrolebykuid(Long kuid);
	public List findByKdidAndKrname(Long kdid, String krname);
	/**
	 * 除去树根
	 * @param kdid
	 * @return
	 */
	public List getUserRoleId(Long uid);
	
}
