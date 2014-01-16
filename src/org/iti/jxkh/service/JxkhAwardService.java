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
	 * <li>�������н���
	 */
	public List<Jxkh_Award> findAllAward();

	/**
	 * <li>������Աid������Ӧ����
	 */
	public List<Jxkh_Award> findAwardBysubmitId(String submitId);

	/**
	 * <li>���ݼ�Чָ������ֵ������Ӧ����
	 */
	public List<Jxkh_BusinessIndicator> findRank(Long kbPid);

	/**
	 * <li>�������гɹ�
	 */
	public List<Jxkh_Fruit> findAllFruit();

	/**
	 * <li>��ѯ�����ĳ�Ա
	 */
	public List<Jxkh_AwardMember> findAwardMemberByAwardId(Jxkh_Award award);

	/**
	 * <li>��ѯ�����Ĳ���
	 */
	public List<Jxkh_AwardDept> findAwardDeptByAwardId(Jxkh_Award award);
	
	/**
	 * <li>��ѯ�����Ķ������ţ����ӱ�ʡ�Ƽ��鱨�о�Ժ���������ҳ��ͼ���ָ��ҳ��Ҫ�õ�
	 */
	public List<Jxkh_AwardDept> findAwardDepByAwardId(Jxkh_Award award);

	/**
	 * <li>��ѯ�����ĵ�һ������
	 */
	public List<Jxkh_AwardDept> findAwardDeptByAwardId2(Jxkh_Award award);

	/**
	 * <li>��ѯ���м�Уָ��
	 */
	public List<Jxkh_BusinessIndicator> findAllBusinessIndicator();

	/**
	 * <li>��ѯ���˹���Ա����������ڲ���Ա
	 */
	public List<WkTUser> findUser();

	/**
	 * <li>��ѯ���еĶ�������
	 */
	public List<WkTDept> findDept();
	/**
	 * <li>��ѯ����  kdpid in(0,1)and kdstate(0)
	 */
	public List<WkTDept> findDeptAll();
	/**
	 * <li>��ѯ���ڵ�ǰ��¼�û��ύ�ĺͲ�������н���
	 */
	public List<Jxkh_Award> findAwardByKuLid(String nameSearch,
			Integer stateSearch, String year, String kuLid);

	/**
	 * <li>�����ύ�˱�Ų��Ҷ�Ӧ���ڲ���Ա
	 */
	public WkTUser findWktUserByMemberUserId(String kuLid);

	/**
	 * <li>���ݲ��ű�Ų��Ҷ�Ӧ�Ĳ���
	 */
	public WkTDept findWkTDeptByDept(String deptId);

	/**
	 * <li>���ݲ��ű�Ų��Ҷ�Ӧ�Ľ������������������ʹ��
	 */
	public List<Jxkh_Award> findAwardByDept(String memberSearch);

	/**
	 * <li>���Ҳ��ţ�����Щ�����в�������ǰҵ���������Ĳ���
	 */
	public List<WkTDept> findWktDeptNotInListBox2(String dlist);

	/**
	 * <li>������Ա������Щ�����в�������ǰҵ������������Ա
	 */
	public List<WkTUser> findWkTUserNotInListBox2(String dlist);

	public List<WkTUser> findWkTUserNotInListBox2ByDept(String dlist, Long kdId);

	/**
	 * <li>���Ź���ҵ���ʼ��
	 */
	public List<Jxkh_Award> findAwardByKdNumberAndPaging(String kdNumber,
			int pageNum, int pageSize);

	public int findAwardByKdNumber(String kdNumber);

	/**
	 * <li>�������ҵ���ʼ��
	 */
	public List<Jxkh_Award> findAwardByKdNumberAndPaging2(String kdNumber,
			int pageNum, int pageSize);

	public int findAwardByKdNumber2(String kdNumber);

	/**
	 * <li>���ݲ��ű�Ų�ѯ��Ա���ڲ��Ź����ʼ��ʱʹ��
	 */
	public int findAwardByKdNumberAll(String kdNumber);

	public List<Jxkh_Award> findAwardByKdNumberAll(String kdNumber,
			int pageNum, int pageSize);

	public int findAwardByAudit();

	public List<Jxkh_Award> findAwardByAudit(int pageNum, int pageSize);

	/**
	 * <li>ҵ�����ҷ������������н���
	 */
	public int findAwardByCondition(String nameSearch, Short auditSearch,
			Long type, String auditDep);

	public List<Jxkh_Award> findAwardByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep, int pageNum,
			int pageSize);

	/**
	 * <li>���Ų��ҷ������������б����Ž��������Ź���ҵ��ʱ�Ĳ���
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
	 * ������Ա�趨ҳ��Ĳ�ѯ��service
	 */
	public List<WkTUser> findWkTUserByConditions(String staffIdSearch,
			String nameSearch, Long deptSearch, String dlist);

	public List<JXKH_AppraisalMember> findAllAppraisalMember();
	
	/**
	 * <li>���ݵ�¼��Ա�������ű�źͽ�������Jxkh_AwardDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public Jxkh_AwardDept findAwardDept(Jxkh_Award m, String wktDeptId);
	/**
	 * ���ݲ��Ų����Ѿ���ӵĿ�����Ա
	 * @param deptId
	 */
	public List<JXKH_AppraisalMember>findpeoByDept(String deptName);
	/**
	 * ������������š����ڲ��Ų�ѯ�Ѿ���ӵĹ�������Ա
	 * @author WXY
	 * @param 
	 */
	public List<JXKH_AppraisalMember> findCheckedUserByConditions(String staffIdSearch,
			String nameSearch, Long deptSearch, String dlist);
}
