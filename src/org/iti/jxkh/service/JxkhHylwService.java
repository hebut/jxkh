package org.iti.jxkh.service;

import java.util.List;
import java.util.Set;

import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.entity.JXKH_HYlwSlMessage;
import org.iti.jxkh.entity.JXKH_QKLW;

import com.uniwin.basehs.service.BaseService;

public interface JxkhHylwService extends BaseService {
	/**
	 * <li>�������л������ĵ�Index�б���
	 */
	public List<JXKH_HYLW> findIndexListbox();

	/**
	 * <li>��ѯ�Ļ������ĵĳ�Ա
	 */
	public List<JXKH_HULWMember> findAwardMemberByAwardId(JXKH_HYLW meeting);

	/**
	 * <li>���ݻ������Ĳ�������Ժ�ڲ���
	 */
	public List<JXKH_HYLWDept> findMeetingDeptByMeetingId(JXKH_HYLW meeting);
	
	/**
	 * <li>���ݻ������Ĳ�������Ժ�ڶ������ţ����˺ӱ�ʡ�Ƽ��鱨�о�Ժ�������ҳ��ָ��ҳ����õ�
	 */
	public List<JXKH_HYLWDept> findMeetingDepByMeetingId(JXKH_HYLW meeting);

	/**
	 * <li>���ݻ�������id�����������������ĸ����ĵ�
	 */
	public Set<JXKH_HYLWFile> findMeetingFilesByMeetingId(JXKH_HYLW meeting);

	/**
	 * <li>������Ա��Ų�����������Ļ������ģ�������������
	 */
	public List<JXKH_HYLW> findMeetingByKuLidAndpPaging(String name,Integer auditState, Long type, String kuLid, int pageNum,int pageSize);
	public int findMeetingByKuLid(String name, Integer auditState, Long type,String kuLid);

	/**
	 * <li>���ݲ��ű�Ų��ұ��������еĻ�������(��������ǳ�ʼ����)
	 */
	public List<JXKH_HYLW> findMeetingByKdNumberAndPaging(String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumber(String kdNumber);

	/**
	 * <li>�����������һ�������(�������ҵ�������ʼ���Ͳ���)
	 */
	public List<JXKH_HYLW> findMeetingByConditionAndPage(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize);

	public int findMeetingByCondition(String name, Integer auditState,
			Long type, String kdNumber);
	/**
	 * <li>�����������һ������ģ������������������ƣ����״̬��������ȣ��û�
	 */
	public List<JXKH_HYLW>findHylwByKuLidAndPaging(String nameSearch,Integer stateSearch, String year, String kuLid, int pageNum,int pageSize);
    public int findHylwByKuLid(String nameSearch, Integer stateSearch,String year, String kuLid);
	
	/**
	 * <li>�������з��������Ļ�������(ҵ�������ǳ�ʼ����)
	 */
	public List<JXKH_HYLW> findMeetingByAuditAndPaging(int pageNum, int pageSize);

	public int findMeetingByAudit();

	/**
	 * <li>���ݲ��ű�Ų��ұ��������еĻ�������(��������ǳ�ʼ����)<��������������õ�service>
	 */
	public List<JXKH_HYLW> findMeetingByKdNumberAndPagings(String kdNumber,
			int pageNum, int pageSize);

	public int findMeetingByKdNumbers(String kdNumber);

	/**
	 * <li>�����������һ�������<��������������õ�service>
	 */
	public List<JXKH_HYLW> findMeetingByConditionAndPages(String name,
			Integer auditState, String kdNumber, int pageNum, int pageSize);

	public int findMeetingByConditions(String name, Integer auditState,
			String kdNumber);

	/**
	 * <li>���ݻ������Ĳ��������е���¼��Ϣ
	 */
	public List<JXKH_HYlwSlMessage> findHylwSlMessageByMeetingId(
			JXKH_HYLW meeting);

	/**
	 * <li>�������������ڿ�����<ҵ�������ǲ���ʱ�õ�service>
	 */
	public List<JXKH_HYLW> findMeetingByBusiAduitConditionAndPages(String name,
			Integer auditState, Long type, String auditDep, int pageNum,
			int pageSize);

	public int findMeetingByBusiAduitConditions(String name,
			Integer auditState, Long type, String auditDep);

	/**
	 * <li>���ݵ�¼��Ա�������ű�źͻ������Ĳ���JXKH_HYLWDept(�������ҵ��ʱ����ҳ���õ�service)
	 */
	public JXKH_HYLWDept findHYLWDept(JXKH_HYLW m, String wktDeptId);
	
	public List<JXKH_HYLW> findHYLWbyName(String name,String year);
}
