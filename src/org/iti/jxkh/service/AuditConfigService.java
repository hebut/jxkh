package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_JFRESULT;

import com.uniwin.basehs.service.AnnBaseService;
import com.uniwin.framework.entity.WkTUser;

public interface AuditConfigService extends AnnBaseService {
	/**
	 * ������ݲ���
	 * 
	 * @param year
	 * @return
	 */
	public JXKH_AuditConfig findByYear(String year);

	/**
	 * ����ҵ����Ա����
	 * 
	 * @return
	 */
	public int findWorker();

	/**
	 * ���ҹ�����Ա����
	 * 
	 * @return
	 */
	public int findManager();

	/**
	 * �����쵼����
	 * 
	 * @return
	 */
	public int findLeader();

	/**
	 * ���Ҳ����ڱ�����
	 * 
	 * @param kdId
	 * @return
	 */
	public int findDeptMember(Long kdId);
	/**
	 * ���Ҳ����������������ڱ����Ƹ
	 * @param kdId
	 * @return
	 */
	
	public int findDeptAllMember(Long kdId);
	
	/**
	 * ��ѯĳ����ָ���μӿ��˵���Ա����Ŀ
	 * @param deptid
	 * @author WXY
	 * 
	 */
	public int findCheckPeoByDeptId(Long KdId);
	/**
	 * ���ݲ��Ų��Ҳμӿ��˵���Ա
	 * @param kdId
	 * @author WXY
	 */
	public List<JXKH_AppraisalMember>findpeoByDept(Long deptId);
	/**
	 * ���ݲ��Ų��Ҳμӿ��˵�Ժ�쵼
	 * @param deptName
	 * @author WXY
	 */
	public List<JXKH_AppraisalMember>findleaderByDept(String deptName);
	
	/**
	 * ����������ѯ��Ա
	 * @author WXY
	 * @param kuid
	 * 
	 */
	public List<WkTUser>findUser(Long kuId);
	
	/**
	 * �����������Ҳμӿ��˵Ĺ����ŵ���Ա
	 * @author WXY
	 * @param kdId
	 * @return
	 */
	public List<JXKH_AppraisalMember>findManagerPeo(String staffIdSearch,String nameSearch, Long deptSearch);
	
	public List<WkTUser> findDeptMemberList(Long kdId);

	public List<WkTUser> findLeaderList();
	
	public int findDeptMemberByName(Long kdId,String Name,String StaffId);
	
	public List<WkTUser> findDeptMemberListByName(Long kdId,String Name,String StaffId);
	
	/**
	 * @author WXY
	 * ͨ����ݲ����ܻ��ּ�¼����
	 * @param year
	 */
	public List<JXKH_JFRESULT>findJfByYear(String year);
}
