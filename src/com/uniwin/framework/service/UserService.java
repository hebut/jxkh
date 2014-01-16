package com.uniwin.framework.service;

/**
 * <li>用户数据访问接口
 * @author DaLei
 * @2010-3-15
 */
import java.util.List;

import org.iti.xypt.entity.Teacher;

import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface UserService extends BaseService {
	
	public List FindBykuId(Long kuId);
	
	/**
	 * @param lname
	 * @param tname
	 * @return
	 */
	public List<WkTUser> searchUserInfo(String lname, String tname, List<Long> deptList);
	/**
	 * <li>功能描述：获得具有某角色的全部用户
	 * 
	 * @param rid 角色编号
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersOfRole(Long rid);

	/**
	 * <li>功能描述：获得不具有某角色的全部用户。
	 * 
	 * @param rid 角色编号
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersNotRole(Long rid);

	/**
	 * <li>功能描述：获得属于某个组织的全部用户。
	 * 
	 * @param did 部门编号
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersOfDept(Long did);

	/**
	 * <li>功能描述：获得组织全部用户，并搜索用户名。
	 * 
	 * @param did 部门编号
	 * @param key 搜索关键字
	 * @return List
	 * @author DaLei
	 */
	public List<WkTUser> getUsersOfDept(Long did, String key);

	public void deleteUser(WkTUser user);
	
	public List<WkTUser> loginUser(String username);

	/**
	 * <li>功能描述：用户登陆时候根据用户名和密码查找用户。
	 * 
	 * @param username
	 * @param pasword
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> loginUser(String username, String pasword);

	/**
	 * <li>功能描述：获得具有某角色的全部用户
	 * 
	 * @param rid 角色编号
	 * @param dlist 用户所在部门列表
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersOfRole(Long rid, List<Long> dlist);

	/**
	 * <li>功能描述：获得不具有某角色的全部用户。
	 * 
	 * @param rid 角色编号
	 * @param dlist 用户所在部门列表
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersNotRole(Long rid, List<Long> dlist);

	/**
	 * <li>功能描述：根据用户名查询用户列表，用在新建用户时判断用户名是否重复
	 * 
	 * @param loginName
	 * @return List
	 * @author DaLei
	 */
	public List<WkTUser> getUsersByLogin(String loginName);
	/**
	 * 根据员工编号查找用户
	 * @param staffId
	 * @return List
	 * @author WeifangWang
	 */
	public List<WkTUser> getUserByStaffid(String staffId);

	/**
	 * <li>功能描述：根据用户部门。找这个部门中的某种角色的用户列表
	 * 
	 * @param kdid
	 * @return List
	 * 
	 */
	public List<WkTUser> getUSersByRole(Long kdid, String rname);

	/**
	 * <li>功能描述:根据角色和部门查询用户
	 * 
	 * @param rlist
	 * @param dlist
	 * @return
	 */
	public List<WkTUser> getUsersByRAndD(List<Long> rlist, List<Long> dlist);

	/**
	 * <li>功能描述：找某种角色的所有用户
	 * 
	 * @param rlist
	 * @return
	 */
	public List<WkTUser> getUsersByrlist(List<Long> rlist);

	/**
	 * <li>功能描述：根据uid查找用户
	 * 
	 * @param uid
	 * @return
	 */
	public WkTUser getUserByuid(Long uid);

	/**
	 * <li>功能描述：根据教师列表，查找教师用户列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<WkTUser> getUserBytlist(List<Teacher> tlist);

	/**
	 * <li>功能描述：根据kuid查找用户
	 * 
	 * @param kuid
	 * @return
	 */
	public WkTUser getUserBykuid(Long kuid);

	/**
	 * <li>功能描述：系级别上查找教师姓名列表
	 * 
	 * @param thid
	 * @return
	 */
	public WkTUser findTnameByThid(String thid);

	/**
	 * <li>功能描述：根据教师编号查找教师姓名列表
	 * 
	 * @param btid
	 * @return
	 */
	public WkTUser findTnameByBtid(Long btid);

	/**
	 * <li>功能描述：根据毕设学生编号查找学生姓名列表
	 * 
	 * @param bsid
	 * @return
	 */
	public WkTUser findSnameBybsid(Long bsid);

	public List<WkTUser> findByTrueName(String trueName, List<WkTDept> dlist);

	public List<WkTUser> findByTeacherTrueName(String trueName, List<WkTDept> dlist);

	public List<WkTUser> findByStudentTrueName(String trueName, List<WkTDept> dlist);

	public List<WkTUser> findSnameBystid(String stid);

	/**
	 * @author zhangli <li>功能描述根据教师查找教师姓名
	 * @param telist 教师列表
	 * @return 用户列表(按名字顺序)
	 * 
	 */
	public List<WkTUser> findNameByTeacher(List<Teacher> telist);

	/**
	 * <li>功能描述：根据uid查找用户
	 * 
	 * @param uid
	 * @return
	 */
	public List<WkTUser> findUserBykuid(Long kuid);

	public List<WkTUser> findBykrIdOrkrid(Long krId, Long krid);
	public List<WkTUser> findBykrId(Long krId);

	/**
	 * <li>功能描述：在某群组中，根据部门编号或用户姓名查找用户
	 */
	public List<WkTUser> findUserForGroupByKdIdAndName(Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate);
	public List<WkTUser> findUserForGroupByKdIdAndName(List<com.uniwin.asm.personal.entity.QzMember> ilist,List<com.uniwin.asm.personal.entity.QzMember> qlist,Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate);

	/**
	 * <li>根据部门，教师名和教师号查询教师用户
	 * 
	 * @param deplist
	 * @param tname
	 * @param tno
	 * @return
	 */
	public List<WkTUser> findByDlistAndTnameAndTno(List<WkTDept> deplist, String tname, String tno);
	/**
	 * <li>根据部门编号查询该部门的学生
	 * 
	 * @param deplist
	 * @param tname
	 * @param tno
	 * @return
	 */
	public List<WkTUser> findstudentBykdid(Long kdid);
	/**
	 * <li>根据部门编号查询该部门以及该部门的下级和下下级部门的用户
	 * 
	 * @param deplist
	 * @param tname
	 * @param tno
	 * @return
	 */
	public List<WkTUser> findUserBykdid(Long kdid);
	public List<WkTUser> finduserbykunameandkdid(String name,Long kdid);
	public List<WkTUser> finduserbykuLnameandkdid(String name,Long kdid);
	
	public List<WkTUser> findByKdId(Long kdId);
}
