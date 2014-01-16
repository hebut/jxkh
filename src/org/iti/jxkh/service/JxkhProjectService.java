package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_PatentFile;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectFile;
import org.iti.jxkh.entity.Jxkh_ProjectFund;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.entity.Jxkh_WritingDept;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface JxkhProjectService extends BaseService {
	/**
	 * <li>查找所有纵向项目
	 */
	public List<Jxkh_Project> findAllZP();

	public List<Jxkh_Project> findAllHP();

	public List<Jxkh_Project> findAllCP();

	public List<Jxkh_Patent> findAllPatent();

	public List<Jxkh_Writing> findAllWriting();

	/**
	 * <li>根据登录用户查找相应纵向项目
	 */
	public List<Jxkh_Project> findZPBymemberId(String memberId);
	
	public List<Jxkh_Project> findProjectBySortAndMemberIdAndPaging(int pageNo, int pageSize ,Short sort, String memberId);

	public List<Jxkh_Project> findHPBymemberId(String memberId);

	public List<Jxkh_Project> findCPBymemberId(String memberId);

	public List<Jxkh_Patent> findPatentBymemberId(String memberId);

	public List<Jxkh_Writing> findWritingBymemberId(String memberId);
	

	/**
	 * <li>查找相应绩效指标
	 */
	public List<Jxkh_BusinessIndicator> findRank();

	public List<Jxkh_BusinessIndicator> findSort();

	public List<Jxkh_BusinessIndicator> findWSort();

	/**
	 * <li>查找对应项目经费
	 */
	public List<Jxkh_ProjectFund> findFunds(Jxkh_Project project, Short sort);

	/**
	 * <li>功能描述：根据项目查找出该项目的经费支出总额
	 * 
	 * @param
	 * @return 总额
	 * @author YuSong
	 */
	public List sumOut(Jxkh_Project project, String year);

	/**
	 * <li>功能描述：根据项目id查找出该项目的经费收入总额
	 * 
	 * @param FPId
	 * @return 总额
	 * @author YuSong
	 */
	public List sumIn(Jxkh_Project project, String year);
	/**
	 * 根据项目、年度查找对应的项目经费
	 * @param project
	 * @param year
	 * @return
	 */
	public List<Jxkh_ProjectFund> getFundByYearAndProject(Jxkh_Project project, String year);

	public WkTUser findWktUserByMemberUserId(String kuLid);

	public WkTDept findWkTDeptByDept(String deptId);

	public List<Jxkh_ProjectMember> findProjectMember(Jxkh_Project project);

	public List<Jxkh_ProjectDept> findProjectDept(Jxkh_Project project);

	public List<Jxkh_PatentInventor> findPatentMember(Jxkh_Patent project);

	public List<Jxkh_PatentDept> findPatentDept(Jxkh_Patent project);

	public List<Jxkh_Writer> findWritingMember(Jxkh_Writing project);

	public List<Jxkh_WritingDept> findWritingDept(Jxkh_Writing project);

	/**
	 * <li>根据业务的id查找其对应的参与二级部门（除了河北省科技情报研究院）——信息审核页和绩分指定页要用到
	 */
	public List<Jxkh_ProjectDept> findProjectDep(Jxkh_Project project);
	
	public List<Jxkh_PatentDept> findPatentDep(Jxkh_Patent project);
	
	public List<Jxkh_WritingDept> findWritingDep(Jxkh_Writing project);
	
	/**
	 * <li>部门查找功能
	 */
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, String kdNumber);

	// 部门业务-查找
	public List<Jxkh_Project> findZPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// 部门业务-查找
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// 部门业务-查找
	public List<Jxkh_Project> findZPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, String kdNumber);

	// 部门业务-查找
	public List<Jxkh_Project> findHPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// 部门业务-查找
	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// 部门业务-查找
	public List<Jxkh_Project> findHPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, String kdNumber);

	// 部门业务-查找
	public List<Jxkh_Project> findCPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// 部门业务-查找
	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// 部门业务-查找
	public List<Jxkh_Project> findCPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// 部门业务-查找
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// 部门业务-查找
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// 部门业务-查找
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// 部门业务-查找
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	/**
	 * <li>业务办查找功能
	 */
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep);

	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep);

	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep);

	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep);

	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep);

	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize);

	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize);

	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize);

	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize);

	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize);

	/**
	 * <li>部门显示初始化
	 */
	public List<Jxkh_Project> findAllZPByDept(String kdNumber);

	public List<Jxkh_Project> findAllZPByDept(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Project> findAllHPByDept(String kdNumber);

	public List<Jxkh_Project> findAllHPByDept(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Project> findAllCPByDept(String kdNumber);

	public List<Jxkh_Project> findAllCPByDept(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Patent> findAllPatentByDept(String kdNumber);

	public List<Jxkh_Patent> findAllPatentByDept(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Writing> findAllWritingByDept(String kdNumber);

	public List<Jxkh_Writing> findAllWritingByDept(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Project> findAllZPByDept1(String kdNumber);

	public List<Jxkh_Project> findAllZPByDept1(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Project> findAllHPByDept1(String kdNumber);

	public List<Jxkh_Project> findAllHPByDept1(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Project> findAllCPByDept1(String kdNumber);

	public List<Jxkh_Project> findAllCPByDept1(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Patent> findAllPatentByDept1(String kdNumber);

	public List<Jxkh_Patent> findAllPatentByDept1(String kdNumber, int pageNo,
			int pageSize);

	public List<Jxkh_Writing> findAllWritingByDept1(String kdNumber);

	public List<Jxkh_Writing> findAllWritingByDept1(String kdNumber,
			int pageNo, int pageSize);

	/**
	 * <li>查找所有纵向项目
	 */
	public List<Jxkh_Project> findAllZPByBusi();

	public List<Jxkh_Project> findAllHPByBusi();

	public List<Jxkh_Project> findAllCPByBusi();

	public List<Jxkh_Patent> findAllPatentByBusi();

	public List<Jxkh_Writing> findAllWritingByBusi();

	public List<Jxkh_Project> findAllZPByBusi(int pageNo, int pageSize);

	public List<Jxkh_Project> findAllHPByBusi(int pageNo, int pageSize);

	public List<Jxkh_Project> findAllCPByBusi(int pageNo, int pageSize);

	public List<Jxkh_Patent> findAllPatentByBusi(int pageNo, int pageSize);

	public List<Jxkh_Writing> findAllWritingByBusi(int pageNo, int pageSize);

	public List<Jxkh_Project> findMultiDeptProject(String year);

	public JXKH_MultiDept findMultiDeptByPrId(Long prId);

	/**
	 * <li>根据知识产权id和附件类型查找所有与其相连的附件文档（20120306）
	 */
	public List<Jxkh_PatentFile> findFilesByIdAndType(Jxkh_Patent patent,
			String type);

	/**
	 * <li>根据登录用户根据条件查找相应纵向项目（20120329）
	 */
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Integer stateSearch, String year, String memberId);

	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Integer stateSearch, String year, String memberId);

	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Integer stateSearch, String year, String memberId);

	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Integer stateSearch, String year, String memberId);

	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Integer stateSearch, String year, String memberId);

	/**
	 * <li>根据登录人员所属部门编号和项目查找Jxkh_ProjectDept(部门添加业务时绩分页面用的service)
	 */
	public Jxkh_ProjectDept findProjectDept(Jxkh_Project m, String wktDeptId);

	/**
	 * <li>根据登录人员所属部门编号和专利查找Jxkh_PatentDept(部门添加业务时绩分页面用的service)
	 */
	public Jxkh_PatentDept findPatentDept(Jxkh_Patent m, String wktDeptId);

	/**
	 * <li>根据登录人员所属部门编号和著作查找Jxkh_WritingDept(部门添加业务时绩分页面用的service)
	 */
	public Jxkh_WritingDept findWritingDept(Jxkh_Writing m, String wktDeptId);

	public List<Jxkh_ProjectFile> getProjectFile(Jxkh_Project project);
}
