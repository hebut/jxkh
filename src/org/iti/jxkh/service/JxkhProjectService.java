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
	 * <li>��������������Ŀ
	 */
	public List<Jxkh_Project> findAllZP();

	public List<Jxkh_Project> findAllHP();

	public List<Jxkh_Project> findAllCP();

	public List<Jxkh_Patent> findAllPatent();

	public List<Jxkh_Writing> findAllWriting();

	/**
	 * <li>���ݵ�¼�û�������Ӧ������Ŀ
	 */
	public List<Jxkh_Project> findZPBymemberId(String memberId);
	
	public List<Jxkh_Project> findProjectBySortAndMemberIdAndPaging(int pageNo, int pageSize ,Short sort, String memberId);

	public List<Jxkh_Project> findHPBymemberId(String memberId);

	public List<Jxkh_Project> findCPBymemberId(String memberId);

	public List<Jxkh_Patent> findPatentBymemberId(String memberId);

	public List<Jxkh_Writing> findWritingBymemberId(String memberId);
	

	/**
	 * <li>������Ӧ��Чָ��
	 */
	public List<Jxkh_BusinessIndicator> findRank();

	public List<Jxkh_BusinessIndicator> findSort();

	public List<Jxkh_BusinessIndicator> findWSort();

	/**
	 * <li>���Ҷ�Ӧ��Ŀ����
	 */
	public List<Jxkh_ProjectFund> findFunds(Jxkh_Project project, Short sort);

	/**
	 * <li>����������������Ŀ���ҳ�����Ŀ�ľ���֧���ܶ�
	 * 
	 * @param
	 * @return �ܶ�
	 * @author YuSong
	 */
	public List sumOut(Jxkh_Project project, String year);

	/**
	 * <li>����������������Ŀid���ҳ�����Ŀ�ľ��������ܶ�
	 * 
	 * @param FPId
	 * @return �ܶ�
	 * @author YuSong
	 */
	public List sumIn(Jxkh_Project project, String year);
	/**
	 * ������Ŀ����Ȳ��Ҷ�Ӧ����Ŀ����
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
	 * <li>����ҵ���id�������Ӧ�Ĳ���������ţ����˺ӱ�ʡ�Ƽ��鱨�о�Ժ��������Ϣ���ҳ�ͼ���ָ��ҳҪ�õ�
	 */
	public List<Jxkh_ProjectDept> findProjectDep(Jxkh_Project project);
	
	public List<Jxkh_PatentDept> findPatentDep(Jxkh_Patent project);
	
	public List<Jxkh_WritingDept> findWritingDep(Jxkh_Writing project);
	
	/**
	 * <li>���Ų��ҹ���
	 */
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, String kdNumber);

	// ����ҵ��-����
	public List<Jxkh_Project> findZPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// ����ҵ��-����
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// ����ҵ��-����
	public List<Jxkh_Project> findZPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, String kdNumber);

	// ����ҵ��-����
	public List<Jxkh_Project> findHPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// ����ҵ��-����
	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// ����ҵ��-����
	public List<Jxkh_Project> findHPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, String kdNumber);

	// ����ҵ��-����
	public List<Jxkh_Project> findCPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// ����ҵ��-����
	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// ����ҵ��-����
	public List<Jxkh_Project> findCPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// ����ҵ��-����
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// ����ҵ��-����
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	// ����ҵ��-����
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber);

	// ����ҵ��-����
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize);

	/**
	 * <li>ҵ�����ҹ���
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
	 * <li>������ʾ��ʼ��
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
	 * <li>��������������Ŀ
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
	 * <li>����֪ʶ��Ȩid�͸������Ͳ����������������ĸ����ĵ���20120306��
	 */
	public List<Jxkh_PatentFile> findFilesByIdAndType(Jxkh_Patent patent,
			String type);

	/**
	 * <li>���ݵ�¼�û���������������Ӧ������Ŀ��20120329��
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
	 * <li>���ݵ�¼��Ա�������ű�ź���Ŀ����Jxkh_ProjectDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public Jxkh_ProjectDept findProjectDept(Jxkh_Project m, String wktDeptId);

	/**
	 * <li>���ݵ�¼��Ա�������ű�ź�ר������Jxkh_PatentDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public Jxkh_PatentDept findPatentDept(Jxkh_Patent m, String wktDeptId);

	/**
	 * <li>���ݵ�¼��Ա�������ű�ź���������Jxkh_WritingDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public Jxkh_WritingDept findWritingDept(Jxkh_Writing m, String wktDeptId);

	public List<Jxkh_ProjectFile> getProjectFile(Jxkh_Project project);
}
