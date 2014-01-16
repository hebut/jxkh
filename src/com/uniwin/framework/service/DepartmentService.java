package com.uniwin.framework.service;

/**
 * <li>组织部门数据访问接口
 * @author DaLei
 * @2010-3-15
 */
import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;

public interface DepartmentService extends BaseService {
	/**
	 * 根据用户id和阶段名称查询学校
	 * @param kuid
	 * @param ktname
	 * @return
	 */
	public List<WkTDept> findByKuidAndKtname(Long kuid,String ktname);
	public List<WkTDept> getChildrenbyPtid(Long ptid);
	public List<WkTDept> getChildrenbyPtidForWork(Long ptid);
	/**
	 * <li>功能描述：获得某组织部门的全部孩子部门列表。
	 * 
	 * @param ptid
	 *            父组织部门编号
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTDept> getChildDepartment(Long ptid, String kdtype);

	/**
	 * <li>功能描述：获得某组织部门的全部孩子部门列表。
	 * 
	 * @param ptid
	 *            父组织部门编号
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTDept> getChildDepdw(Long ptid);

	/**
	 * <li>功能描述：获得父组织部门全部孩子部门，但不包括编号为notdid的部门
	 * 
	 * @param ptid
	 *            父组织部门
	 * @param notdid
	 *            列表中不包括的部门
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTDept> getChildDepartment(Long ptid, Long notdid);

	/**
	 * <li>功能描述：删除一个部门，重新该函数是因为需要删除部门相关记录
	 * 
	 * @param dept
	 *            要删除的部门
	 * @return List
	 * @author DaLei
	 * @2010-3-29
	 */
	public void delete(WkTDept dept);

	/**
	 * <li>功能描述：获得直属于某部门用户数目
	 * 
	 * @param did
	 * @return Long
	 * @author DaLei
	 */
	public Long getUserCount(Long did);

	public WkTDept findByDid(Long kdid);

	/**
	 * <li>功能描述：根据某一角色的用户查询所在的所有部门
	 * 
	 * @param krid
	 * @return 部门列表
	 * @author FengXinhong 2010-4-15
	 */
	public List<WkTDept> getDepByuser(List<Long> rlist);

	/**
	 * <li>功能描述：查询用户具有角色中所在部门列表
	 * 
	 * @param kuid
	 * @return List
	 * @author DaLei
	 */
	public List<WkTDept> getRootList(Long kuid);

	public WkTDept findByKdname(String kdname);

	/**
	 * <li>功能描述：查询某部门的下下级的所有部门
	 * 
	 * @param kuid
	 * @return List
	 * @author DaLei
	 */
	public List<WkTDept> findByPpKdid(Long kdid);

	/**
	 * <li>功能描述：查找开课单位列表
	 * 
	 * @return 单位列表
	 */
	public List<WkTDept> findKkDep();

	/**
	 * 查询某个学校是否拥有某个组织编号的组织
	 * 
	 * @param kdNum
	 * @param kdid
	 * @return
	 */
	public WkTDept findByKdnumberAndKdSchid(String kdNum, Long kdid);

	public List<WkTDept> findByLevelAndSchid(Integer level, Long kdid);

	/**
	 * 根据某个毕设单位查询组织单位
	 * 
	 * @param 　buid
	 * @return
	 */
	public List<WkTDept> findByBuid(Long buid);

	/**
	 * 查询辅导员管辖的部门
	 */
	public List<WkTDept> findDeptForFDY(Long kipid, Long kuid);

	/**
	 * 查询某部门下指定类型的部门
	 */
	public List<WkTDept> findDeptByKdidAndType(Long kdid, String type);
	/**
	 * 查询某个部门是否属于某个部门的子部门
	 */
	public List<WkTDept> findbykdidkdpid(Long kdid,Long kdpid);
	public List<WkTDept> findDanweiAndBumenByKpid(Long kdid);
	public List getChildDepartment(Long ptid) ;
	public WkTDept findByKdnameandschid(String kdname,Long kdpid);
	
	public List<WkTDept> getDeptByNum(String num);
}
