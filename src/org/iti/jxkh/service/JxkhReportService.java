package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportMember;

import com.uniwin.basehs.service.BaseService;

public interface JxkhReportService extends BaseService {

	public List<Jxkh_ReportMember> findReportMemberByReportId(Jxkh_Report report);

	public List<Jxkh_ReportDept> findReportDeptByReportId(Jxkh_Report report);

	/**
	 *根据报告的id查找其二级部门（除了河北省科技情报研究院）——信息审核页和绩分指定页要用到 
	 */
	public List<Jxkh_ReportDept> findReportDepByReportId(Jxkh_Report report);
	
	public List<Jxkh_Report> findReportByKuLid(String kuLid);

	// 通过部门编号找部门以下报告
	public int findReportByKbNumber(String kbNumber);

	public List<Jxkh_Report> findReportByKbNumber(String kbNumber, int pageNum,
			int pageSize);

	// 通过部门编号找部门以下并且为主承担部门的报告
	public int findReportByKbNumber2(String kbNumber);

	public List<Jxkh_Report> findReportByKbNumber2(String kbNumber,
			int pageNum, int pageSize);

	// 通过部门编号找所有报告
	public int findReportByKbNumberAll(String kbNumber);

	public List<Jxkh_Report> findReportByKbNumberAll(String kbNumber,
			int pageNum, int pageSize);

	// 找所有的部门以上的报告
	public int findReportByState();

	public List<Jxkh_Report> findReportByState(int pageNum, int pageSize);

	// 业务办查找
	public int findReportByCondition(String nameSearch, Short stateSearch,
			String type, String depName);

	public List<Jxkh_Report> findReportByCondition(String nameSearch,
			Short stateSearch, String type, String depName, int pageNum,
			int pageSize);

	// 部门查找
	public int findReportByCondition(String nameSearch, Short stateSearch,
			String type, String year, String kbNumber);

	public List<Jxkh_Report> findReportByCondition(String nameSearch,
			Short stateSearch, String type, String year, String kbNumber,
			int pageNum, int pageSize);

	// 个人查找
	public int findReportByConditions(String nameSearch, Short stateSearch,
			String year, String kuLid);

	public List<Jxkh_Report> findReportListByCondition(String nameSearch,
			Integer stateSearch, String year, String kuLid);

	/**
	 * <li>根据登录人员所属部门编号和报告查找Jxkh_ReportDept(部门添加业务时绩分页面用的service)
	 */
	public Jxkh_ReportDept findReportDept(Jxkh_Report m, String wktDeptId);
}
