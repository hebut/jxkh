package org.iti.xypt.service;

import java.util.List;

import org.iti.xypt.entity.XyClass;

import com.uniwin.basehs.service.BaseService;

public interface XyClassService extends BaseService {
	/**
	 * 查询某专业下面的班级列表
	 * 
	 * @param kdid
	 * @return
	 */
	public List findByKdid(Long kdid);

	/**
	 * 查询某专业下面的某年级的班级列表
	 * 
	 * @param kdid
	 * @return
	 */
	public List findByKdid(Long kdid, int year);

	/**
	 * 查询某些系或者专业的具有的年级列表
	 * 
	 * @param deptList 系或者专业列表
	 * @return 年级列表
	 */
	public List getGradeList(List deptList);

	/**
	 * 根据系和年级查询班级列表
	 * 
	 * @param kdid
	 * @param grade
	 * @return
	 */
	public List findByKdidAndGrade(Long kdid, Integer grade);

	/**
	 * <li>功能描述:根据组织单位编号和班级简称查询班级
	 * 
	 * @param kdid 组织单位编号
	 * @param Cname 班级简称
	 * @return 一个班级对象
	 * @author FengXinhong 2010-7-27
	 */
	public XyClass findByKdidAndCname(Long kdid, String Cname);

	/**
	 * <li>根据学生的登录主键查找班级
	 * 
	 * @author zhangli
	 * @param ku
	 * @return 班级实体
	 */
	public XyClass findByKuid(Long ku);

	public List findByGradeAndName(Integer grade, String name, List dlist);

	/**
	 * 查询某学校某名称班级列表
	 * 
	 * @param cname
	 * @param kdid
	 * @return
	 */
	public List findByCnameAndKdid(String cname, Long kdid);

	/**
	 * 查询某学校某名称班级列表
	 * 
	 * @param cname
	 * @param kdid
	 * @return
	 */
	public List findByScnameAndKdid(String cname, Long kdid);

	/**
	 * <li>根据组织单位编号列表查找班级
	 * 
	 * @author zhangli
	 * @param kdid 组织单位编号
	 * @return 班级实体列表
	 */
	public List findClassByKdid(List kdid);

	/**
	 * 根据班级名称和学院系列表，判断班级所属系 cl
	 * 
	 * @param cname
	 * @param deplist
	 * @return
	 */
	public List findClassByCnameAndDeplist(String cname, String ccname, List deplist);

	/**
	 * 根据班级主键查找班级
	 * 
	 * @param cid
	 * @return
	 */
	public List findClassByCid(Long cid);

	/**
	 * @author zhangli <li>根据班级简称列表查找班级
	 * @param sname 班级简称列表
	 * @return 班级列表
	 */
	public List findClasByCsn(List sname);

	/**
	 * @author lys <li>根据班级名称和班级所在单位查询班级 * @param sname 班级简称列表
	 * @return 班级列表
	 */
	public XyClass findByKdidAndCName(Long kdid, String cName);

	/**
	 * @author 查找辅导员所带某部门的班级
	 */
	public List findClassForFDY(Long kdid, Long kuid);

	/**
	 * @author 查找某学院的班级
	 */
	public List findClassForCollege(Long kdid, Integer max, Integer min);

	/**
	 * 根据学校编号和班级简称查询班级列表
	 * 
	 * @param kdid
	 * @param Sname
	 * @return
	 */
	public List findByKdidAndSname(Long kdid, String Cname);

	/**
	 * 判断某一个班级是否在某一班级列表中
	 * 
	 * @param xc
	 * @param clist
	 * @return
	 */
	public boolean CheckIfIn(XyClass xc, List clist);

	/**
	 * 根据部门编号查询年级
	 * 
	 * @param kdid
	 * @return
	 */
	public List findgradeByKdid(Long kdid);

	/**
	 * <li>功能描述：根据学院或专业编号查询、年级查询班级列表
	 * 
	 * @param kdid
	 * @param grade
	 * @return
	 */
	public List findByKdIdAndGrade(Long kdid, Integer grade);

	/**
	 * 根据学校编号查询年级
	 * 
	 * @param kdid
	 * @return
	 */
	public List findgradeByScKdid(Long kdid);

	/**
	 * @return
	 */
	public List findClass(Long kdid);

	/**
	 * 根据班级主键查找班级实体
	 * 
	 * @param clid
	 * @return XyClass
	 * @author 贺亚
	 */
	public XyClass hyfindclass(Long kdid);

	/**
	 * 根据班级名称和所属学院查找班级实体
	 * 
	 * @param clname
	 * @param kdid
	 * @return XyClass
	 * @author 贺亚
	 */
	public XyClass hyfindclassbykdidclname(Long kdid, String clname);

	/**
	 * 根据班级号查找班级实体
	 */
	public XyClass hyfindclassbycid(Long cid);
    //根据专业或者学院查找班级
	public List findBykdIdorkdPid(Long kdid);
	/**
	 * 查询辅导员在某个学校所带的年级
	 * @param kuid
	 * @param sid
	 * @return
	 */
	public List findByKuidAndSid(Long kuid,Long sid);
/**
 * 查询辅导员在某个学校所带的班级
 * @param kuid
 * @param sid
 * @return
 */
	public List findClassByKuidAndSid(Long kuid,Long sid);
	/**
	 * 查询辅导员在某个学校所带的某个年级的班级
	 * @param kuid
	 * @param sid
	 * @param grade
	 * @return
	 */
	public List findClassByKuidAndSidAndGrade(Long kuid,Long sid,Integer grade);
	/**
	 * 
	 * @param kdid
	 * @param gradelist
	 * @return
	 */
	public List findbyKdidAndGradeList(Long kdid,List gradelist);
}
