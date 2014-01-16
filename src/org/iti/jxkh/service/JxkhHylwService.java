package org.iti.jxkh.service;

import java.util.List;
import java.util.Set;

import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.entity.JXKH_HYlwSlMessage;
import org.iti.jxkh.entity.JXKH_QKLW;

import com.uniwin.basehs.service.BaseService;

public interface JxkhHylwService extends BaseService {
	/**
	 * <li>查找所有会议论文的Index列表项
	 */
	public List<JXKH_HYLW> findIndexListbox();

	/**
	 * <li>查询改会议论文的成员
	 */
	public List<JXKH_HULWMember> findAwardMemberByAwardId(JXKH_HYLW meeting);

	/**
	 * <li>根据会议论文查找所有院内部门
	 */
	public List<JXKH_HYLWDept> findMeetingDeptByMeetingId(JXKH_HYLW meeting);
	
	/**
	 * <li>根据会议论文查找所有院内二级部门（除了河北省科技情报研究院），审核页面指定页面会用到
	 */
	public List<JXKH_HYLWDept> findMeetingDepByMeetingId(JXKH_HYLW meeting);

	/**
	 * <li>根据会议论文id查找所有与其相连的附件文档
	 */
	public Set<JXKH_HYLWFile> findMeetingFilesByMeetingId(JXKH_HYLW meeting);

	/**
	 * <li>根据人员编号查找其所参与的会议论文，根据条件查找
	 */
	public List<JXKH_HYLW> findMeetingByKuLidAndpPaging(String name,Integer auditState, Long type, String kuLid, int pageNum,int pageSize);
	public int findMeetingByKuLid(String name, Integer auditState, Long type,String kuLid);

	/**
	 * <li>根据部门编号查找本部门所有的会议论文(部门审核那初始化用)
	 */
	public List<JXKH_HYLW> findMeetingByKdNumberAndPaging(String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumber(String kdNumber);

	/**
	 * <li>根据条件查找会议论文(部门添加业务那里初始化和查找)
	 */
	public List<JXKH_HYLW> findMeetingByConditionAndPage(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize);

	public int findMeetingByCondition(String name, Integer auditState,
			Long type, String kdNumber);
	/**
	 * <li>根据条件查找会议论文，其中条件包括：名称，审核状态，积分年度，用户
	 */
	public List<JXKH_HYLW>findHylwByKuLidAndPaging(String nameSearch,Integer stateSearch, String year, String kuLid, int pageNum,int pageSize);
    public int findHylwByKuLid(String nameSearch, Integer stateSearch,String year, String kuLid);
	
	/**
	 * <li>查找所有符合条件的会议论文(业务办审核那初始化用)
	 */
	public List<JXKH_HYLW> findMeetingByAuditAndPaging(int pageNum, int pageSize);

	public int findMeetingByAudit();

	/**
	 * <li>根据部门编号查找本部门所有的会议论文(部门审核那初始化用)<部门审核那真正用的service>
	 */
	public List<JXKH_HYLW> findMeetingByKdNumberAndPagings(String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumbers(String kdNumber);

	/**
	 * <li>根据条件查找会议论文<部门审核那真正用的service>
	 */
	public List<JXKH_HYLW> findMeetingByConditionAndPages(String name,
			Integer auditState, String kdNumber, int pageNum, int pageSize);

	public int findMeetingByConditions(String name, Integer auditState,
			String kdNumber);

	/**
	 * <li>根据会议论文查找其所有的收录信息
	 */
	public List<JXKH_HYlwSlMessage> findHylwSlMessageByMeetingId(
			JXKH_HYLW meeting);

	/**
	 * <li>根据条件查找期刊论文<业务办审核那查找时用的service>
	 */
	public List<JXKH_HYLW> findMeetingByBusiAduitConditionAndPages(String name,
			Integer auditState, Long type, String auditDep, int pageNum,
			int pageSize);

	public int findMeetingByBusiAduitConditions(String name,
			Integer auditState, Long type, String auditDep);

	/**
	 * <li>根据登录人员所属部门编号和会议论文查找JXKH_HYLWDept(部门添加业务时绩分页面用的service)
	 */
	public JXKH_HYLWDept findHYLWDept(JXKH_HYLW m, String wktDeptId);
	
	public List<JXKH_HYLW> findHYLWbyName(String name,String year);
}
