package org.iti.jxkh.service;

import java.util.List;
import java.util.Set;

import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.JXKH_QKLWFile;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.JXKH_QklwSlMessage;

import com.uniwin.basehs.service.BaseService;

public interface JxkhQklwService extends BaseService {

	/**
	 * <li>查询期刊论文的成员
	 */
	public List<JXKH_QKLWMember> findAwardMemberByAwardId(JXKH_QKLW meeting);

	/**
	 * <li>根据期刊论文查找所有院内部门
	 */
	public List<JXKH_QKLWDept> findMeetingDeptByMeetingId(JXKH_QKLW meeting);
	
	/**
	 * <li>根据期刊论文查找所有院内二级部门（除了科技情报研究院）——审核页和指定页里会用到
	 */
	public List<JXKH_QKLWDept> findMeetingDepByMeetingId(JXKH_QKLW meeting);

	/**
	 * <li>根据期刊论文id查找所有与其相连的附件文档
	 */
	public Set<JXKH_QKLWFile> findMeetingFilesByMeetingId(JXKH_QKLW meeting);

	/**
	 * <li>根据人员编号查找其所参与的期刊论文，根据条件查找
	 */
	public List<JXKH_QKLW> findMeetingByKuLidAndPaging(String name,
			Integer auditState, Long type, String kuLid, int pageNum,
			int pageSize);

	public int findMeetingByKuLid(String name, Integer auditState, Long type,
			String kuLid);
	
	/**
	 * <li>根据人员编号查找其所参与的期刊论文，根据条件查找,其中条件包括：名称，积分年度，审核状态（new）
	 */
	public List<JXKH_QKLW>findQklwByKuLidAndPaging(String nameSearch,Integer stateSearch, String year, String kuLid, int pageNum,int pageSize);
	public int findQklwByKuLid(String nameSearch, Integer stateSearch,String year, String kuLid);

	/**
	 * <li>根据部门编号查找本部门所有的期刊论文(部门审核那初始化用)
	 */
	public List<JXKH_QKLW> findMeetingByKdNumberAndPage(String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumber(String kdNumber);

	/**
	 * <li>根据条件查找期刊论文
	 */
	public List<JXKH_QKLW> findMeetingByConditionAndPage(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize);

	public int findMeetingByCondition(String name, Integer auditState,
			Long type, String kdNumber);

	/**
	 * <li>根据部门编号查找本部门所有的期刊论文(部门身份添加那初始化用)<暂时不用了>
	 */
	public List<JXKH_QKLW> findMeetingsByKdNumber(String kdNumber);

	/**
	 * <li>查找所有符合条件的期刊论文(业务办审核那初始化用)
	 */
	public List<JXKH_QKLW> findMeetingByAuditAndPaging(int pageNum, int pageSize);

	public int findMeetingByAudit();

	/**
	 * <li>根据部门编号查找本部门所有的期刊论文(部门审核那初始化用)<部门审核那真正用的service>
	 */
	public List<JXKH_QKLW> findMeetingByKdNumberAndPages(String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumbers(String kdNumber);

	/**
	 * <li>根据条件查找期刊论文<部门审核那真正用的service>
	 */
	public List<JXKH_QKLW> findMeetingByConditionAndPages(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize);

	public int findMeetingByConditions(String name, Integer auditState,
			Long type, String kdNumber);

	/**
	 * <li>根据期刊论文查找其所有的收录信息
	 */
	public List<JXKH_QklwSlMessage> findQklwSlMessageByMeetingId(
			JXKH_QKLW meeting);

	/**
	 * <li>根据条件查找期刊论文<业务办审核那查找时用的service>
	 */
	public List<JXKH_QKLW> findMeetingByBusiAduitConditionAndPages(String name,
			Integer auditState, Long type, String auditDep, int pageNum,
			int pageSize);

	public int findMeetingByBusiAduitConditions(String name,
			Integer auditState, Long type, String auditDep);

	/**
	 * <li>根据登录人员所属部门编号和期刊论文查找JXKH_QKLWDept(部门添加业务时绩分页面用的service)
	 */
	public JXKH_QKLWDept findQKLWDept(JXKH_QKLW m, String wktDeptId);
	
	public List<JXKH_QKLW> findQKLWbyName(String name,String year);
}
