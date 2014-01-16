package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.JXKH_PointNumber;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_Writing;

import com.uniwin.basehs.service.AnnBaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface AuditResultService extends AnnBaseService {
	/**
	 * 查找业务部门
	 * 
	 * @return
	 */
	public List<WkTDept> findWorkDept();
	/**
	 * 查找管理部门
	 * @return
	 */
	public List<WkTDept> findManageDept();
	/**
	 * 根据部门查找用户
	 * @param deptString 多部门id拼成的字符串，中间用,隔开，末位后面没有逗号，例如1,2,299
	 * @return
	 */
	public List<WkTUser> findUserByDept(String deptString);

	/**
	 * 查找会议论文
	 * 
	 * @param kdName
	 * @return
	 */
	public List<JXKH_HYLW> findHyLw(String kdName, String year);

	/**
	 * 查找期刊论文
	 * 
	 * @param kdName
	 * @return
	 */
	public List<JXKH_QKLW> findQkLw(String kdName, String year);

	/**
	 * 查找著作
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Writing> findZz(String kdName, String year);

	/**
	 * 查找奖励
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Award> findJl(String kdName, String year);

	/**
	 * 查找视频
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Video> findSp(String kdName, String year);

	/**
	 * 查找项目
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Project> findJf(String kdName, String year);

	/**
	 * 查找专利
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Patent> findZl(String kdName, String year);

	/**
	 * 查找成果
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Fruit> findCg(String kdName, String year);

	/**
	 * 查看会议
	 * 
	 * @param kdName
	 * @return
	 */
	public List<JXKH_MEETING> findHy(String kdName, String year);

	/**
	 * 查看报告
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Report> findBg(String kdName, String year);

	/**
	 * 根据年份查询部门得分
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findDeptByYear(String year);

	/**
	 * 根据年份查询业务部门人员记录
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findWorkDeptByYear(String year);

	/**
	 * 查找业务部门记录
	 * 
	 * @param kdId
	 * @return
	 */
	public JXKH_AuditResult findWorkDeptByKdIdAndYear(Long kdId, String year);

	/**
	 * 根据档次查询业务人员
	 * 
	 * @param kdId
	 * @param year
	 * @param level
	 * @return
	 */
	public List<JXKH_AuditResult> findWorkByLevel(Long kdId, String year,
			Short level);

	/**
	 * 根据部门查询业务人员
	 * 
	 * @param kdId
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findWorkByKdId(Long kdId, String year);

	/**
	 * 根据年份查询管理人员
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findManageByYear(String year);

	/**
	 * 查询领导
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findLeaderByYear(Long kuId, String year);

	/**
	 * 根据年份查询领导
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findLeaderByYear(String year);

	public JXKH_AuditResult findPersonal(String year, Long kuId);

	public JXKH_AuditResult findDepartment(String year, Long kdId);

	public WkTDept findDepname(Long kdId);

	public WkTUser findUser(Long kuId);

	public List<JXKH_AuditResult> findAll(String year, Long kdId);

	public List<JXKH_HYLW> findMultiDeptHYLW(String year, String kdNumber);

	public List<JXKH_QKLW> findMultiDeptQKLW(String year, String kdNumber);

	public List<Jxkh_Writing> findMultiDeptZZ(String year, String kdNumber);

	public List<Jxkh_Award> findMultiDeptJL(String year, String kdNumber);

	public List<Jxkh_Video> findMultiDeptSP(String year, String kdNumber);

	public List<Jxkh_Project> findMultiDeptXM(String year, String kdNumber);

	public List<Jxkh_Patent> findMultiDeptZL(String year, String kdNumber);

	public List<Jxkh_Fruit> findMultiDeptCG(String year, String kdNumber);

	public List<JXKH_MEETING> findMultiDeptHY(String year, String kdNumber);

	public List<Jxkh_Report> findMultiDeptBG(String year, String kdNumber);

	public JXKH_MultiDept findMultiDeptByPrIdAndType(Long prId, Short type);

	public List<JXKH_AuditResult> findDept(String year);

	public JXKH_MultiDept findMultiDept(String kdNumber, Long prId, Short type);

	public JXKH_PointNumber findPointNumber(String year, Long kdId);

	public List<?> findListForShow(String year, String kdName, String kbName,
			Integer type);

	// 项目
	public Float countDeptPro(String kdNum, String year);

	public int countDeptProNum(String kdNum, String year, String type);

	public Float countDeptProType(String kdNum, String year, String type);
	
	public List<String> countDeptProType1(String kdNum, String year, String type);

	// 著作
	public Float countDeptWri(String kdNum, String year);
	
	public int countDeptWriNum(String kdNum, String year, String type);

	public Float countDeptWriType(String kdNum, String year, String type);
	
	public List<String> countDeptWriType1(String kdNum, String year,String type);

	// 专利
	public Float countDeptPat(String kdNum, String year);

	public int countDeptPatNum(String kdNum, String year, String type);

	public Float countDeptPatType(String kdNum, String year, String type);
	
	public List<String> countDeptPatType1(String kdNum, String year, String type) ;

	// 会议
	public Float countDeptMee(String kdNum, String year);

	public int countDeptMeeNum(String kdNum, String year, String type);

	public Float countDeptMeeType(String kdNum, String year, String type);
	
	public List<String> countDeptMeeType1(String kdNum, String year, String type);

	public float findSumScoreByKdNumberYearAndState(String kdnumber,
			String year, Short type);

	/**
	 * 查询某部门某项指标的总分
	 * 
	 * @param kdnumber
	 * @param year
	 * @param type
	 * @param id
	 *            指标id
	 * @return
	 */
	public Object[] findBiSumScore(String kdnumber, String year, Short type,
			Long id);
	public List<String> countDeptDetail(String kdNum, String year, String biName,Short type );
}