package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_JFRESULT;

import com.uniwin.basehs.service.AnnBaseService;
import com.uniwin.framework.entity.WkTUser;

public interface AuditConfigService extends AnnBaseService {
	/**
	 * 根据年份查找
	 * 
	 * @param year
	 * @return
	 */
	public JXKH_AuditConfig findByYear(String year);

	/**
	 * 查找业务人员总数
	 * 
	 * @return
	 */
	public int findWorker();

	/**
	 * 查找管理人员总数
	 * 
	 * @return
	 */
	public int findManager();

	/**
	 * 查找领导总数
	 * 
	 * @return
	 */
	public int findLeader();

	/**
	 * 查找部门在编人数
	 * 
	 * @param kdId
	 * @return
	 */
	public int findDeptMember(Long kdId);
	/**
	 * 查找部门总人数，包括在编和外聘
	 * @param kdId
	 * @return
	 */
	
	public int findDeptAllMember(Long kdId);
	
	/**
	 * 查询某部门指定参加考核的人员的数目
	 * @param deptid
	 * @author WXY
	 * 
	 */
	public int findCheckPeoByDeptId(Long KdId);
	/**
	 * 根据部门查找参加考核的人员
	 * @param kdId
	 * @author WXY
	 */
	public List<JXKH_AppraisalMember>findpeoByDept(Long deptId);
	/**
	 * 根据部门查找参加考核的院领导
	 * @param deptName
	 * @author WXY
	 */
	public List<JXKH_AppraisalMember>findleaderByDept(String deptName);
	
	/**
	 * 根据条件查询人员
	 * @author WXY
	 * @param kuid
	 * 
	 */
	public List<WkTUser>findUser(Long kuId);
	
	/**
	 * 根据条件查找参加考核的管理部门的人员
	 * @author WXY
	 * @param kdId
	 * @return
	 */
	public List<JXKH_AppraisalMember>findManagerPeo(String staffIdSearch,String nameSearch, Long deptSearch);
	
	public List<WkTUser> findDeptMemberList(Long kdId);

	public List<WkTUser> findLeaderList();
	
	public int findDeptMemberByName(Long kdId,String Name,String StaffId);
	
	public List<WkTUser> findDeptMemberListByName(Long kdId,String Name,String StaffId);
	
	/**
	 * @author WXY
	 * 通过年份查找总积分记录条数
	 * @param year
	 */
	public List<JXKH_JFRESULT>findJfByYear(String year);
}
