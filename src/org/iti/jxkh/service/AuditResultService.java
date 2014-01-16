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
	 * ����ҵ����
	 * 
	 * @return
	 */
	public List<WkTDept> findWorkDept();
	/**
	 * ���ҹ�����
	 * @return
	 */
	public List<WkTDept> findManageDept();
	/**
	 * ���ݲ��Ų����û�
	 * @param deptString �ಿ��idƴ�ɵ��ַ������м���,������ĩλ����û�ж��ţ�����1,2,299
	 * @return
	 */
	public List<WkTUser> findUserByDept(String deptString);

	/**
	 * ���һ�������
	 * 
	 * @param kdName
	 * @return
	 */
	public List<JXKH_HYLW> findHyLw(String kdName, String year);

	/**
	 * �����ڿ�����
	 * 
	 * @param kdName
	 * @return
	 */
	public List<JXKH_QKLW> findQkLw(String kdName, String year);

	/**
	 * ��������
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Writing> findZz(String kdName, String year);

	/**
	 * ���ҽ���
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Award> findJl(String kdName, String year);

	/**
	 * ������Ƶ
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Video> findSp(String kdName, String year);

	/**
	 * ������Ŀ
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Project> findJf(String kdName, String year);

	/**
	 * ����ר��
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Patent> findZl(String kdName, String year);

	/**
	 * ���ҳɹ�
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Fruit> findCg(String kdName, String year);

	/**
	 * �鿴����
	 * 
	 * @param kdName
	 * @return
	 */
	public List<JXKH_MEETING> findHy(String kdName, String year);

	/**
	 * �鿴����
	 * 
	 * @param kdName
	 * @return
	 */
	public List<Jxkh_Report> findBg(String kdName, String year);

	/**
	 * ������ݲ�ѯ���ŵ÷�
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findDeptByYear(String year);

	/**
	 * ������ݲ�ѯҵ������Ա��¼
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findWorkDeptByYear(String year);

	/**
	 * ����ҵ���ż�¼
	 * 
	 * @param kdId
	 * @return
	 */
	public JXKH_AuditResult findWorkDeptByKdIdAndYear(Long kdId, String year);

	/**
	 * ���ݵ��β�ѯҵ����Ա
	 * 
	 * @param kdId
	 * @param year
	 * @param level
	 * @return
	 */
	public List<JXKH_AuditResult> findWorkByLevel(Long kdId, String year,
			Short level);

	/**
	 * ���ݲ��Ų�ѯҵ����Ա
	 * 
	 * @param kdId
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findWorkByKdId(Long kdId, String year);

	/**
	 * ������ݲ�ѯ������Ա
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findManageByYear(String year);

	/**
	 * ��ѯ�쵼
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_AuditResult> findLeaderByYear(Long kuId, String year);

	/**
	 * ������ݲ�ѯ�쵼
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

	// ��Ŀ
	public Float countDeptPro(String kdNum, String year);

	public int countDeptProNum(String kdNum, String year, String type);

	public Float countDeptProType(String kdNum, String year, String type);
	
	public List<String> countDeptProType1(String kdNum, String year, String type);

	// ����
	public Float countDeptWri(String kdNum, String year);
	
	public int countDeptWriNum(String kdNum, String year, String type);

	public Float countDeptWriType(String kdNum, String year, String type);
	
	public List<String> countDeptWriType1(String kdNum, String year,String type);

	// ר��
	public Float countDeptPat(String kdNum, String year);

	public int countDeptPatNum(String kdNum, String year, String type);

	public Float countDeptPatType(String kdNum, String year, String type);
	
	public List<String> countDeptPatType1(String kdNum, String year, String type) ;

	// ����
	public Float countDeptMee(String kdNum, String year);

	public int countDeptMeeNum(String kdNum, String year, String type);

	public Float countDeptMeeType(String kdNum, String year, String type);
	
	public List<String> countDeptMeeType1(String kdNum, String year, String type);

	public float findSumScoreByKdNumberYearAndState(String kdnumber,
			String year, Short type);

	/**
	 * ��ѯĳ����ĳ��ָ����ܷ�
	 * 
	 * @param kdnumber
	 * @param year
	 * @param type
	 * @param id
	 *            ָ��id
	 * @return
	 */
	public Object[] findBiSumScore(String kdnumber, String year, Short type,
			Long id);
	public List<String> countDeptDetail(String kdNum, String year, String biName,Short type );
}