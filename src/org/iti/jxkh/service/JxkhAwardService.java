package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_Project;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface JxkhAwardService extends BaseService {
	/**
	 * <li>查找所有奖励
	 */
	public List<Jxkh_Award> findAllAward();

	/**
	 * <li>根据人员id查找相应奖励
	 */
	public List<Jxkh_Award> findAwardBysubmitId(String submitId);

	/**
	 * <li>根据绩效指标属性值查找相应奖励
	 */
	public List<Jxkh_BusinessIndicator> findRank(Long kbPid);

	/**
	 * <li>查找所有成果
	 */
	public List<Jxkh_Fruit> findAllFruit();

	/**
	 * <li>查询奖励的成员
	 */
	public List<Jxkh_AwardMember> findAwardMemberByAwardId(Jxkh_Award award);

	/**
	 * <li>查询奖励的部门
	 */
	public List<Jxkh_AwardDept> findAwardDeptByAwardId(Jxkh_Award award);
	
	/**
	 * <li>查询奖励的二级部门（除河北省科技情报研究院）——审核页面和绩分指定页面要用到
	 */
	public List<Jxkh_AwardDept> findAwardDepByAwardId(Jxkh_Award award);

	/**
	 * <li>查询奖励的第一个部门
	 */
	public List<Jxkh_AwardDept> findAwardDeptByAwardId2(Jxkh_Award award);

	/**
	 * <li>查询所有技校指标
	 */
	public List<Jxkh_BusinessIndicator> findAllBusinessIndicator();

	/**
	 * <li>查询除了管理员意外的所有内部成员
	 */
	public List<WkTUser> findUser();

	/**
	 * <li>查询所有的二级部门
	 */
	public List<WkTDept> findDept();
	/**
	 * <li>查询部门  kdpid in(0,1)and kdstate(0)
	 */
	public List<WkTDept> findDeptAll();
	/**
	 * <li>查询属于当前登录用户提交的和参与的所有奖励
	 */
	public List<Jxkh_Award> findAwardByKuLid(String nameSearch,
			Integer stateSearch, String year, String kuLid);

	/**
	 * <li>根据提交人编号查找对应的内部人员
	 */
	public WkTUser findWktUserByMemberUserId(String kuLid);

	/**
	 * <li>根据部门编号查找对应的部门
	 */
	public WkTDept findWkTDeptByDept(String deptId);

	/**
	 * <li>根据部门编号查找对应的奖励，部门审核主界面使用
	 */
	public List<Jxkh_Award> findAwardByDept(String memberSearch);

	/**
	 * <li>查找部门，且这些部门中不包含当前业务所包含的部门
	 */
	public List<WkTDept> findWktDeptNotInListBox2(String dlist);

	/**
	 * <li>查找人员，且这些部门中不包含当前业务所包含的人员
	 */
	public List<WkTUser> findWkTUserNotInListBox2(String dlist);

	public List<WkTUser> findWkTUserNotInListBox2ByDept(String dlist, Long kdId);

	/**
	 * <li>部门管理业务初始化
	 */
	public List<Jxkh_Award> findAwardByKdNumberAndPaging(String kdNumber,
			int pageNum, int pageSize);

	public int findAwardByKdNumber(String kdNumber);

	/**
	 * <li>部门审核业务初始化
	 */
	public List<Jxkh_Award> findAwardByKdNumberAndPaging2(String kdNumber,
			int pageNum, int pageSize);

	public int findAwardByKdNumber2(String kdNumber);

	/**
	 * <li>根据部门编号查询人员，在部门管理初始化时使用
	 */
	public int findAwardByKdNumberAll(String kdNumber);

	public List<Jxkh_Award> findAwardByKdNumberAll(String kdNumber,
			int pageNum, int pageSize);

	public int findAwardByAudit();

	public List<Jxkh_Award> findAwardByAudit(int pageNum, int pageSize);

	/**
	 * <li>业务办查找符合条件的所有奖励
	 */
	public int findAwardByCondition(String nameSearch, Short auditSearch,
			Long type, String auditDep);

	public List<Jxkh_Award> findAwardByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep, int pageNum,
			int pageSize);

	/**
	 * <li>部门查找符合条件的所有本部门奖励，部门管理业务时的查找
	 */
	public int findAwardByCondition(String nameSearch, Short auditSearch,
			Long type, String year, String kdNumber);

	public List<Jxkh_Award> findAwardByConditionAndPaging(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNum, int pageSize);

	public List<Jxkh_Project> findAllPByConditon(String name, Short sort);

	public List<WkTUser> findWkTUserByCondition(String nameSearch,
			Long deptSearch, String dlist);

	public List<WkTDept> findWktDeptByCondition(String nameSearch,
			String numberSearch, String dlist);

	public List<WkTDept> findDept1();

	/**
	 * 考核人员设定页面的查询的service
	 */
	public List<WkTUser> findWkTUserByConditions(String staffIdSearch,
			String nameSearch, Long deptSearch, String dlist);

	public List<JXKH_AppraisalMember> findAllAppraisalMember();
	
	/**
	 * <li>根据登录人员所属部门编号和奖励查找Jxkh_AwardDept(部门添加业务时绩分页面用的service)
	 */
	public Jxkh_AwardDept findAwardDept(Jxkh_Award m, String wktDeptId);
	/**
	 * 根据部门查找已经添加的考核人员
	 * @param deptId
	 */
	public List<JXKH_AppraisalMember>findpeoByDept(String deptName);
	/**
	 * 根据姓名、编号、所在部门查询已经添加的管理部门人员
	 * @author WXY
	 * @param 
	 */
	public List<JXKH_AppraisalMember> findCheckedUserByConditions(String staffIdSearch,
			String nameSearch, Long deptSearch, String dlist);
}
