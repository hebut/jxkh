package org.iti.jxkh.service;

import java.util.List;
import java.util.Set;

import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.JXKH_QKLWFile;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.JXKH_QklwSlMessage;

import com.uniwin.basehs.service.BaseService;

public interface JxkhQklwService extends BaseService {

	/**
	 * <li>��ѯ�ڿ����ĵĳ�Ա
	 */
	public List<JXKH_QKLWMember> findAwardMemberByAwardId(JXKH_QKLW meeting);

	/**
	 * <li>�����ڿ����Ĳ�������Ժ�ڲ���
	 */
	public List<JXKH_QKLWDept> findMeetingDeptByMeetingId(JXKH_QKLW meeting);
	
	/**
	 * <li>�����ڿ����Ĳ�������Ժ�ڶ������ţ����˿Ƽ��鱨�о�Ժ���������ҳ��ָ��ҳ����õ�
	 */
	public List<JXKH_QKLWDept> findMeetingDepByMeetingId(JXKH_QKLW meeting);

	/**
	 * <li>�����ڿ�����id�����������������ĸ����ĵ�
	 */
	public Set<JXKH_QKLWFile> findMeetingFilesByMeetingId(JXKH_QKLW meeting);

	/**
	 * <li>������Ա��Ų�������������ڿ����ģ�������������
	 */
	public List<JXKH_QKLW> findMeetingByKuLidAndPaging(String name,
			Integer auditState, Long type, String kuLid, int pageNum,
			int pageSize);

	public int findMeetingByKuLid(String name, Integer auditState, Long type,
			String kuLid);
	
	/**
	 * <li>������Ա��Ų�������������ڿ����ģ�������������,�����������������ƣ�������ȣ����״̬��new��
	 */
	public List<JXKH_QKLW>findQklwByKuLidAndPaging(String nameSearch,Integer stateSearch, String year, String kuLid, int pageNum,int pageSize);
	public int findQklwByKuLid(String nameSearch, Integer stateSearch,String year, String kuLid);

	/**
	 * <li>���ݲ��ű�Ų��ұ��������е��ڿ�����(��������ǳ�ʼ����)
	 */
	public List<JXKH_QKLW> findMeetingByKdNumberAndPage(String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumber(String kdNumber);

	/**
	 * <li>�������������ڿ�����
	 */
	public List<JXKH_QKLW> findMeetingByConditionAndPage(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize);

	public int findMeetingByCondition(String name, Integer auditState,
			Long type, String kdNumber);

	/**
	 * <li>���ݲ��ű�Ų��ұ��������е��ڿ�����(�����������ǳ�ʼ����)<��ʱ������>
	 */
	public List<JXKH_QKLW> findMeetingsByKdNumber(String kdNumber);

	/**
	 * <li>�������з����������ڿ�����(ҵ�������ǳ�ʼ����)
	 */
	public List<JXKH_QKLW> findMeetingByAuditAndPaging(int pageNum, int pageSize);

	public int findMeetingByAudit();

	/**
	 * <li>���ݲ��ű�Ų��ұ��������е��ڿ�����(��������ǳ�ʼ����)<��������������õ�service>
	 */
	public List<JXKH_QKLW> findMeetingByKdNumberAndPages(String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumbers(String kdNumber);

	/**
	 * <li>�������������ڿ�����<��������������õ�service>
	 */
	public List<JXKH_QKLW> findMeetingByConditionAndPages(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize);

	public int findMeetingByConditions(String name, Integer auditState,
			Long type, String kdNumber);

	/**
	 * <li>�����ڿ����Ĳ��������е���¼��Ϣ
	 */
	public List<JXKH_QklwSlMessage> findQklwSlMessageByMeetingId(
			JXKH_QKLW meeting);

	/**
	 * <li>�������������ڿ�����<ҵ�������ǲ���ʱ�õ�service>
	 */
	public List<JXKH_QKLW> findMeetingByBusiAduitConditionAndPages(String name,
			Integer auditState, Long type, String auditDep, int pageNum,
			int pageSize);

	public int findMeetingByBusiAduitConditions(String name,
			Integer auditState, Long type, String auditDep);

	/**
	 * <li>���ݵ�¼��Ա�������ű�ź��ڿ����Ĳ���JXKH_QKLWDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public JXKH_QKLWDept findQKLWDept(JXKH_QKLW m, String wktDeptId);
	
	public List<JXKH_QKLW> findQKLWbyName(String name,String year);
}
