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
	 * <li>�������в���
	 */
	public List<WkTDept> findAllDep();

	/**
	 * <li>����ѧ������id������������������Ժ�ڲ���
	 */
	public List<JXKH_MeetingDept> findMeetingDeptByMeetingId(
			JXKH_MEETING meeting);

	/**
	 * <li>����ѧ������id������������������Ժ�ڶ������ţ����˺ӱ��Ƽ��鱨�о�Ժ���������ҳ��ͼ���ָ��ҳ��Ҫ�õ�
	 */
	public List<JXKH_MeetingDept> findMeetingDepByMeetingId(
			JXKH_MEETING meeting);
	
	/**
	 * <li>����ѧ������id�����������������ĸ����ĵ�
	 */
	public Set<JXKH_MeetingFile> findMeetingFilesByMeetingId(
			JXKH_MEETING meeting);

	/**
	 * <li>������Ա��Ų������������ѧ������
	 */
	public List<JXKH_MEETING> findMeetingByKuLidAndPaging(String nameSearch,
			Integer stateSearch, String year, String kuLid, int pageNum,
			int pageSize);

	public int findMeetingByKuLid(String nameSearch, Integer stateSearch,
			String year, String kuLid);

	/**
	 * <li>����ѧ������id�͸������Ͳ����������������ĸ����ĵ�
	 */
	public List<JXKH_MeetingFile> findFilesByIdAndType(JXKH_MEETING meeting,
			String type);

	/**
	 * <li>���ݲ��ű�Ų��ұ��������е�ѧ������(��������ǳ�ʼ����)
	 */
	public List<JXKH_MEETING> findMeetingByKdNumberAndPaging(String KdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumber(String KdNumber);

	/**
	 * <li>������������ѧ�����飨���������ӡ����-��ʼ�������ң�
	 */
	public List<JXKH_MEETING> findMeetingByConditionAndPaging(String name,
			Integer auditState, Long type, String year, String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByCondition(String name, Integer auditState,
			Long type, String year, String kdNumber);

	/**
	 * <li>���ݼ�Чָ���е�ָ������ֵ��������3λ������ǰ��λȡ����������Ӧ����
	 */
	public List<Jxkh_BusinessIndicator> findRank(Integer kbValue);

	// ��ӡʱ�õģ�����
	public List<JXKH_MeetingDept> findWritingDept(JXKH_MEETING project);

	/**
	 * <li>���ҷ���������ѧ������(ҵ�������ǳ�ʼ����)
	 */
	public List<JXKH_MEETING> findMeetingByAudit(int pageNum, int pageSize);

	public int findMeetingByAudit();

	/**
	 * <li>���ݲ��ű�Ų��ұ��������е�ѧ������(��������ǳ�ʼ����)<��������������õ�service>
	 */
	public List<JXKH_MEETING> findMeetingByKdNumberAndPagings(String KdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumbers(String KdNumber);

	/**
	 * <li>����ѧ������id���������Ա
	 */
	public List<JXKH_MeetingMember> findMeetingMemberByMeetingId(
			JXKH_MEETING meeting);

	/**
	 * <li>������Աid��������������WkTUser
	 */
	public List<WkTUser> findWkTUserByManId(String writerId);

	/**
	 * <li>�������������ڿ�����<ҵ�������ǲ���ʱ�õ�service>
	 */
	public List<JXKH_MEETING> findMeetingByBusiAduitConditionAndPages(
			String name, Integer auditState, Long type, String auditDep,
			int pageNum, int pageSize);

	public int findMeetingByBusiAduitConditions(String name,
			Integer auditState, Long type, String auditDep);
	
	/**
	 * <li>���ݵ�¼��Ա�������ű�źͻ������JXKH_MeetingDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public JXKH_MeetingDept findMeetingDept(JXKH_MEETING m, String wktDeptId);
}
