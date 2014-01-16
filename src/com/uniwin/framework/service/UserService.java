package com.uniwin.framework.service;

/**
 * <li>�û����ݷ��ʽӿ�
 * @author DaLei
 * @2010-3-15
 */
import java.util.List;

import org.iti.xypt.entity.Teacher;

import com.uniwin.asm.personal.entity.QzMember;
import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public interface UserService extends BaseService {
	
	public List FindBykuId(Long kuId);
	
	/**
	 * @param lname
	 * @param tname
	 * @return
	 */
	public List<WkTUser> searchUserInfo(String lname, String tname, List<Long> deptList);
	/**
	 * <li>������������þ���ĳ��ɫ��ȫ���û�
	 * 
	 * @param rid ��ɫ���
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersOfRole(Long rid);

	/**
	 * <li>������������ò�����ĳ��ɫ��ȫ���û���
	 * 
	 * @param rid ��ɫ���
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersNotRole(Long rid);

	/**
	 * <li>�����������������ĳ����֯��ȫ���û���
	 * 
	 * @param did ���ű��
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersOfDept(Long did);

	/**
	 * <li>���������������֯ȫ���û����������û�����
	 * 
	 * @param did ���ű��
	 * @param key �����ؼ���
	 * @return List
	 * @author DaLei
	 */
	public List<WkTUser> getUsersOfDept(Long did, String key);

	public void deleteUser(WkTUser user);
	
	public List<WkTUser> loginUser(String username);

	/**
	 * <li>�����������û���½ʱ������û�������������û���
	 * 
	 * @param username
	 * @param pasword
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> loginUser(String username, String pasword);

	/**
	 * <li>������������þ���ĳ��ɫ��ȫ���û�
	 * 
	 * @param rid ��ɫ���
	 * @param dlist �û����ڲ����б�
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersOfRole(Long rid, List<Long> dlist);

	/**
	 * <li>������������ò�����ĳ��ɫ��ȫ���û���
	 * 
	 * @param rid ��ɫ���
	 * @param dlist �û����ڲ����б�
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTUser> getUsersNotRole(Long rid, List<Long> dlist);

	/**
	 * <li>���������������û�����ѯ�û��б������½��û�ʱ�ж��û����Ƿ��ظ�
	 * 
	 * @param loginName
	 * @return List
	 * @author DaLei
	 */
	public List<WkTUser> getUsersByLogin(String loginName);
	/**
	 * ����Ա����Ų����û�
	 * @param staffId
	 * @return List
	 * @author WeifangWang
	 */
	public List<WkTUser> getUserByStaffid(String staffId);

	/**
	 * <li>���������������û����š�����������е�ĳ�ֽ�ɫ���û��б�
	 * 
	 * @param kdid
	 * @return List
	 * 
	 */
	public List<WkTUser> getUSersByRole(Long kdid, String rname);

	/**
	 * <li>��������:���ݽ�ɫ�Ͳ��Ų�ѯ�û�
	 * 
	 * @param rlist
	 * @param dlist
	 * @return
	 */
	public List<WkTUser> getUsersByRAndD(List<Long> rlist, List<Long> dlist);

	/**
	 * <li>������������ĳ�ֽ�ɫ�������û�
	 * 
	 * @param rlist
	 * @return
	 */
	public List<WkTUser> getUsersByrlist(List<Long> rlist);

	/**
	 * <li>��������������uid�����û�
	 * 
	 * @param uid
	 * @return
	 */
	public WkTUser getUserByuid(Long uid);

	/**
	 * <li>�������������ݽ�ʦ�б����ҽ�ʦ�û��б�
	 * 
	 * @param uid
	 * @return
	 */
	public List<WkTUser> getUserBytlist(List<Teacher> tlist);

	/**
	 * <li>��������������kuid�����û�
	 * 
	 * @param kuid
	 * @return
	 */
	public WkTUser getUserBykuid(Long kuid);

	/**
	 * <li>����������ϵ�����ϲ��ҽ�ʦ�����б�
	 * 
	 * @param thid
	 * @return
	 */
	public WkTUser findTnameByThid(String thid);

	/**
	 * <li>�������������ݽ�ʦ��Ų��ҽ�ʦ�����б�
	 * 
	 * @param btid
	 * @return
	 */
	public WkTUser findTnameByBtid(Long btid);

	/**
	 * <li>�������������ݱ���ѧ����Ų���ѧ�������б�
	 * 
	 * @param bsid
	 * @return
	 */
	public WkTUser findSnameBybsid(Long bsid);

	public List<WkTUser> findByTrueName(String trueName, List<WkTDept> dlist);

	public List<WkTUser> findByTeacherTrueName(String trueName, List<WkTDept> dlist);

	public List<WkTUser> findByStudentTrueName(String trueName, List<WkTDept> dlist);

	public List<WkTUser> findSnameBystid(String stid);

	/**
	 * @author zhangli <li>�����������ݽ�ʦ���ҽ�ʦ����
	 * @param telist ��ʦ�б�
	 * @return �û��б�(������˳��)
	 * 
	 */
	public List<WkTUser> findNameByTeacher(List<Teacher> telist);

	/**
	 * <li>��������������uid�����û�
	 * 
	 * @param uid
	 * @return
	 */
	public List<WkTUser> findUserBykuid(Long kuid);

	public List<WkTUser> findBykrIdOrkrid(Long krId, Long krid);
	public List<WkTUser> findBykrId(Long krId);

	/**
	 * <li>������������ĳȺ���У����ݲ��ű�Ż��û����������û�
	 */
	public List<WkTUser> findUserForGroupByKdIdAndName(Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate);
	public List<WkTUser> findUserForGroupByKdIdAndName(List<com.uniwin.asm.personal.entity.QzMember> ilist,List<com.uniwin.asm.personal.entity.QzMember> qlist,Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate);

	/**
	 * <li>���ݲ��ţ���ʦ���ͽ�ʦ�Ų�ѯ��ʦ�û�
	 * 
	 * @param deplist
	 * @param tname
	 * @param tno
	 * @return
	 */
	public List<WkTUser> findByDlistAndTnameAndTno(List<WkTDept> deplist, String tname, String tno);
	/**
	 * <li>���ݲ��ű�Ų�ѯ�ò��ŵ�ѧ��
	 * 
	 * @param deplist
	 * @param tname
	 * @param tno
	 * @return
	 */
	public List<WkTUser> findstudentBykdid(Long kdid);
	/**
	 * <li>���ݲ��ű�Ų�ѯ�ò����Լ��ò��ŵ��¼������¼����ŵ��û�
	 * 
	 * @param deplist
	 * @param tname
	 * @param tno
	 * @return
	 */
	public List<WkTUser> findUserBykdid(Long kdid);
	public List<WkTUser> finduserbykunameandkdid(String name,Long kdid);
	public List<WkTUser> finduserbykuLnameandkdid(String name,Long kdid);
	
	public List<WkTUser> findByKdId(Long kdId);
}
