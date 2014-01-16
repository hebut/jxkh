package org.iti.jxkh.service;

import java.util.List;
import java.util.Set;

import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface JXKHMeetingService extends BaseService {

	/**
	 * <li>查找所有部门
	 */
	public List<WkTDept> findAllDep();

	/**
	 * <li>根据学术会议id查找所有与其相连的院内部门
	 */
	public List<JXKH_MeetingDept> findMeetingDeptByMeetingId(
			JXKH_MEETING meeting);

	/**
	 * <li>根据学术会议id查找所有与其相连的院内二级部门（除了河北科技情报研究院）——审核页面和绩分指定页面要用到
	 */
	public List<JXKH_MeetingDept> findMeetingDepByMeetingId(
			JXKH_MEETING meeting);
	
	/**
	 * <li>根据学术会议id查找所有与其相连的附件文档
	 */
	public Set<JXKH_MeetingFile> findMeetingFilesByMeetingId(
			JXKH_MEETING meeting);

	/**
	 * <li>根据人员编号查找其所参与的学术会议
	 */
	public List<JXKH_MEETING> findMeetingByKuLidAndPaging(String nameSearch,
			Integer stateSearch, String year, String kuLid, int pageNum,
			int pageSize);

	public int findMeetingByKuLid(String nameSearch, Integer stateSearch,
			String year, String kuLid);

	/**
	 * <li>根据学术会议id和附件类型查找所有与其相连的附件文档
	 */
	public List<JXKH_MeetingFile> findFilesByIdAndType(JXKH_MEETING meeting,
			String type);

	/**
	 * <li>根据部门编号查找本部门所有的学术会议(部门审核那初始化用)
	 */
	public List<JXKH_MEETING> findMeetingByKdNumberAndPaging(String KdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumber(String KdNumber);

	/**
	 * <li>根据条件查找学术会议（部门身份添加、审核-初始化，查找）
	 */
	public List<JXKH_MEETING> findMeetingByConditionAndPaging(String name,
			Integer auditState, Long type, String year, String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByCondition(String name, Integer auditState,
			Long type, String year, String kdNumber);

	/**
	 * <li>根据绩效指标中的指标属性值（必须是3位，根据前两位取出）查找相应奖励
	 */
	public List<Jxkh_BusinessIndicator> findRank(Integer kbValue);

	// 打印时用的（报表）
	public List<JXKH_MeetingDept> findWritingDept(JXKH_MEETING project);

	/**
	 * <li>查找符合条件的学术会议(业务办审核那初始化用)
	 */
	public List<JXKH_MEETING> findMeetingByAudit(int pageNum, int pageSize);

	public int findMeetingByAudit();

	/**
	 * <li>根据部门编号查找本部门所有的学术会议(部门审核那初始化用)<部门审核那真正用的service>
	 */
	public List<JXKH_MEETING> findMeetingByKdNumberAndPagings(String KdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumbers(String KdNumber);

	/**
	 * <li>根据学术会议id查找相关人员
	 */
	public List<JXKH_MeetingMember> findMeetingMemberByMeetingId(
			JXKH_MEETING meeting);

	/**
	 * <li>根据人员id查找其所属部门WkTUser
	 */
	public List<WkTUser> findWkTUserByManId(String writerId);

	/**
	 * <li>根据条件查找期刊论文<业务办审核那查找时用的service>
	 */
	public List<JXKH_MEETING> findMeetingByBusiAduitConditionAndPages(
			String name, Integer auditState, Long type, String auditDep,
			int pageNum, int pageSize);

	public int findMeetingByBusiAduitConditions(String name,
			Integer auditState, Long type, String auditDep);
	
	/**
	 * <li>根据登录人员所属部门编号和会议查找JXKH_MeetingDept(部门添加业务时绩分页面用的service)
	 */
	public JXKH_MeetingDept findMeetingDept(JXKH_MEETING m, String wktDeptId);
}
