package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_PerTrans;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface DutyChangeService extends BaseService {
	/**
	 * 查找出某部门所有的人员调动信息
	 * @param Long dept
	 * @author WeifangWang
	 */
	public List<JXKH_PerTrans> findDutyChangeByDept(Long dept);
	/**
	 * 分页查询某部门所有的人员调动信息
	 * @param Long dept
	 * @param int pageNum
	 * @param int pageSize
	 * @author WeifangWang
	 */
	public List<JXKH_PerTrans> findChangeByPage(Long dept,int pageNum, int pageSize);
	/**
	 * 查找出某人的相关调动历史信息
	 * @param kuId
	 * @return
	 */
	public List<JXKH_PerTrans> findChangeByKuid(Long kuId);
	/**
	 * 分页查找所有用户
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<WkTUser> findUser(String kuName,String staffId,Long kdId);
	public List<WkTUser> findUserByPage(int pageNum, int pageSize);
	/**
	 * 根据名称查找部门
	 * @param deptName
	 * @return
	 */
	public List<WkTDept> findAllDept(String deptName);
	/**
	 * 计算该用户在某年某业务积给某部门的分值
	 * @param deptNum
	 * @param userNum
	 * @param year
	 * @param type
	 * @return
	 */
	public float getAllScoreByDeptAndUserAndYear(String deptNum,String userNum,String year,short type);
	/**
	 * 得到该用户在某年积给某部门的所有业务成员记录
	 * @param deptNum
	 * @param userNum
	 * @param year
	 * @param type
	 * @return
	 */
	public List<Object> getAllMemberByDeptAndUserAndYear(String deptNum,String userNum,String year,short type);
}
