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
	 *���ݱ����id������������ţ����˺ӱ�ʡ�Ƽ��鱨�о�Ժ��������Ϣ���ҳ�ͼ���ָ��ҳҪ�õ� 
	 */
	public List<Jxkh_ReportDept> findReportDepByReportId(Jxkh_Report report);
	
	public List<Jxkh_Report> findReportByKuLid(String kuLid);

	// ͨ�����ű���Ҳ������±���
	public int findReportByKbNumber(String kbNumber);

	public List<Jxkh_Report> findReportByKbNumber(String kbNumber, int pageNum,
			int pageSize);

	// ͨ�����ű���Ҳ������²���Ϊ���е����ŵı���
	public int findReportByKbNumber2(String kbNumber);

	public List<Jxkh_Report> findReportByKbNumber2(String kbNumber,
			int pageNum, int pageSize);

	// ͨ�����ű�������б���
	public int findReportByKbNumberAll(String kbNumber);

	public List<Jxkh_Report> findReportByKbNumberAll(String kbNumber,
			int pageNum, int pageSize);

	// �����еĲ������ϵı���
	public int findReportByState();

	public List<Jxkh_Report> findReportByState(int pageNum, int pageSize);

	// ҵ������
	public int findReportByCondition(String nameSearch, Short stateSearch,
			String type, String depName);

	public List<Jxkh_Report> findReportByCondition(String nameSearch,
			Short stateSearch, String type, String depName, int pageNum,
			int pageSize);

	// ���Ų���
	public int findReportByCondition(String nameSearch, Short stateSearch,
			String type, String year, String kbNumber);

	public List<Jxkh_Report> findReportByCondition(String nameSearch,
			Short stateSearch, String type, String year, String kbNumber,
			int pageNum, int pageSize);

	// ���˲���
	public int findReportByConditions(String nameSearch, Short stateSearch,
			String year, String kuLid);

	public List<Jxkh_Report> findReportListByCondition(String nameSearch,
			Integer stateSearch, String year, String kuLid);

	/**
	 * <li>���ݵ�¼��Ա�������ű�źͱ������Jxkh_ReportDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public Jxkh_ReportDept findReportDept(Jxkh_Report m, String wktDeptId);
}
