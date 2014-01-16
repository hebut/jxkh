/**
 * 
 */
package org.iti.xypt.service;

import java.util.List;
import org.iti.xypt.entity.XyUserrole;
import com.uniwin.basehs.service.BaseService;

/**
 * 
 * @author DaLei
 * @version $Id: XyUserRoleService.java,v 1.1 2011/08/31 07:03:09 ljb Exp $
 */
public interface XyUserRoleService extends BaseService {

	public void update(XyUserrole xyu);

	public List getUserRole(Long kuid, Long kdid);

	public List getonlyUserRole(Long kuid, Long kdid);

	public List getUserRole(Long kuid);

	/**
	 * 查询某个用户在tid标题下具有角色列表
	 * 
	 * @param kuid
	 * @param tid
	 * @return
	 */
	public List getUserRoleOfTitle(Long kuid, Long tid);

	/**
	 * 查询某个用户在tid标题下角色列表，角色属于krid角色组
	 * 
	 * @param kuid
	 * @param tid 标题编号
	 * @param krid 角色组编号
	 * @return
	 */
	public List getUserRoleOfTitle(Long kuid, Long tid, Long krid);

	/**
	 * 管理组织部门中需要列出管理组织功能下具有某个学校的访问此功能的角色
	 * 
	 * @param kuid
	 * @param tid
	 * @param kdid
	 * @return
	 */
	public List getUserRoleOfTitleAndDept(Long kuid, Long tid, Long kdid);

	public Long getRoleId(String rname, Long kdid);

	/**
	 * <li>功能描述：查询某角色用户在某单位中的数目。
	 * 
	 * @param rid 角色编号
	 * @param kdid 单位编号
	 * @return Long
	 * @author DaLei
	 */
	public Long countByRidAndKdid(Long rid, Long kdid);

	/**
	 * * <li>功能描述：查询某角色用户在某单位中的数目,但角色用户不能具有某角色norid。
	 * 
	 * @param rid 角色编号
	 * @param kdid 单位编号
	 * @param norid 不能具有角色编号
	 * @return Long
	 * @author DaLei
	 */
	public Long countByRidAndKdid(Long rid, Long kdid, Long norid);

	/**
	 * * <li>功能描述：查询某个单位中不是某个学生的同行评阅教师和指导教师的教师用户数。
	 * 
	 * @param rid 角色编号
	 * @param kdid 单位编号
	 * @param kuid 该学生的指导教师的用户编号
	 * @param bdbId 学生所在双向关系编号
	 * @return Long
	 * @author lys
	 */
	public Long countNoBsPeerview(Long rid, Long kdid, Long kuid, Long bdbId);

	/**
	 * 用在管理毕设教师中 查询某个部门下具有教师角色但不在某毕设单位毕设教师中的用户数目
	 * 
	 * @param rid
	 * @param kdid
	 * @param buid
	 * @return
	 */
	public Long countNoBsTeacher(Long rid, Long kdid, Long buid);

	public Long countNoCqTeacherAndschid(Long rid, Long kdid, Long schid);

	public void saveXyUserrole(XyUserrole urole);

	public void deleteXyUserrole(XyUserrole urole);

	/**
	 * <li>功能描述：查询某个教师具有该学校全部角色关系
	 * 
	 * @param krid 角色编号
	 * @param kuid 教师的用户编号 List
	 * @author DaLei
	 */
	public List findByKridAndKuid(Long krid, Long kuid);

	/**
	 * <li>功能描述：查询教师具有该学校全部角色关系
	 * 
	 * @param kuid 教师的用户编号
	 * @param kdid 教师所属学校编号 List
	 * @author DaLei
	 */
	public List findByKuidAndKdid(Long kuid, Long kdid);

	/**
	 * <li>功能描述：查询一个用户是否还具有其他角色。
	 * 
	 * @param kuid
	 * @return Long
	 * @author DaLei
	 */
	public List findByKuid(Long kuid);

	/**
	 * 查询某单位的具有某角色的关系列表
	 * 
	 * @param krid
	 * @param kdid
	 * @return
	 */
	public List findByKridAndKdid(Long krid, Long kdid);

	/**
	 * 查询某学校的某个毕设过程的具有某角色的关系列表
	 * 
	 * @param krid
	 * @param kdid
	 * @return
	 */
	public List findByKridAndBgidAndKPPdid(Long krid, Long bgid, Long kdid);

	public List findByKrid(Long krid);

	public List findStuHeaderRole(Long kdId);

	/**
	 * 查询用户的辅导员类型角色
	 */
	public List findFDYRole(Long kuid, Long kdid);

	public List findByKridAndKdidAndName(Long krid, Long kdid, String name, Long wpid);

	public List findByKridAndKdidAndNameAndThid(Long krid, Long kdid, String name, String thid);
}
