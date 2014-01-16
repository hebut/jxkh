/**
 * 
 */
package org.iti.xypt.service;

import java.util.List;
import org.iti.xypt.entity.XyUserrole;
import com.uniwin.basehs.service.BaseService;

/**
 * 
 * @author DaLei
 * @version $Id: XyUserRoleService.java,v 1.1 2011/08/31 07:03:09 ljb Exp $
 */
public interface XyUserRoleService extends BaseService {

	public void update(XyUserrole xyu);

	public List getUserRole(Long kuid, Long kdid);

	public List getonlyUserRole(Long kuid, Long kdid);

	public List getUserRole(Long kuid);

	/**
	 * ��ѯĳ���û���tid�����¾��н�ɫ�б�
	 * 
	 * @param kuid
	 * @param tid
	 * @return
	 */
	public List getUserRoleOfTitle(Long kuid, Long tid);

	/**
	 * ��ѯĳ���û���tid�����½�ɫ�б���ɫ����krid��ɫ��
	 * 
	 * @param kuid
	 * @param tid ������
	 * @param krid ��ɫ����
	 * @return
	 */
	public List getUserRoleOfTitle(Long kuid, Long tid, Long krid);

	/**
	 * ������֯��������Ҫ�г�������֯�����¾���ĳ��ѧУ�ķ��ʴ˹��ܵĽ�ɫ
	 * 
	 * @param kuid
	 * @param tid
	 * @param kdid
	 * @return
	 */
	public List getUserRoleOfTitleAndDept(Long kuid, Long tid, Long kdid);

	public Long getRoleId(String rname, Long kdid);

	/**
	 * <li>������������ѯĳ��ɫ�û���ĳ��λ�е���Ŀ��
	 * 
	 * @param rid ��ɫ���
	 * @param kdid ��λ���
	 * @return Long
	 * @author DaLei
	 */
	public Long countByRidAndKdid(Long rid, Long kdid);

	/**
	 * * <li>������������ѯĳ��ɫ�û���ĳ��λ�е���Ŀ,����ɫ�û����ܾ���ĳ��ɫnorid��
	 * 
	 * @param rid ��ɫ���
	 * @param kdid ��λ���
	 * @param norid ���ܾ��н�ɫ���
	 * @return Long
	 * @author DaLei
	 */
	public Long countByRidAndKdid(Long rid, Long kdid, Long norid);

	/**
	 * * <li>������������ѯĳ����λ�в���ĳ��ѧ����ͬ�����Ľ�ʦ��ָ����ʦ�Ľ�ʦ�û�����
	 * 
	 * @param rid ��ɫ���
	 * @param kdid ��λ���
	 * @param kuid ��ѧ����ָ����ʦ���û����
	 * @param bdbId ѧ������˫���ϵ���
	 * @return Long
	 * @author lys
	 */
	public Long countNoBsPeerview(Long rid, Long kdid, Long kuid, Long bdbId);

	/**
	 * ���ڹ�������ʦ�� ��ѯĳ�������¾��н�ʦ��ɫ������ĳ���赥λ�����ʦ�е��û���Ŀ
	 * 
	 * @param rid
	 * @param kdid
	 * @param buid
	 * @return
	 */
	public Long countNoBsTeacher(Long rid, Long kdid, Long buid);

	public Long countNoCqTeacherAndschid(Long rid, Long kdid, Long schid);

	public void saveXyUserrole(XyUserrole urole);

	public void deleteXyUserrole(XyUserrole urole);

	/**
	 * <li>������������ѯĳ����ʦ���и�ѧУȫ����ɫ��ϵ
	 * 
	 * @param krid ��ɫ���
	 * @param kuid ��ʦ���û���� List
	 * @author DaLei
	 */
	public List findByKridAndKuid(Long krid, Long kuid);

	/**
	 * <li>������������ѯ��ʦ���и�ѧУȫ����ɫ��ϵ
	 * 
	 * @param kuid ��ʦ���û����
	 * @param kdid ��ʦ����ѧУ��� List
	 * @author DaLei
	 */
	public List findByKuidAndKdid(Long kuid, Long kdid);

	/**
	 * <li>������������ѯһ���û��Ƿ񻹾���������ɫ��
	 * 
	 * @param kuid
	 * @return Long
	 * @author DaLei
	 */
	public List findByKuid(Long kuid);

	/**
	 * ��ѯĳ��λ�ľ���ĳ��ɫ�Ĺ�ϵ�б�
	 * 
	 * @param krid
	 * @param kdid
	 * @return
	 */
	public List findByKridAndKdid(Long krid, Long kdid);

	/**
	 * ��ѯĳѧУ��ĳ��������̵ľ���ĳ��ɫ�Ĺ�ϵ�б�
	 * 
	 * @param krid
	 * @param kdid
	 * @return
	 */
	public List findByKridAndBgidAndKPPdid(Long krid, Long bgid, Long kdid);

	public List findByKrid(Long krid);

	public List findStuHeaderRole(Long kdId);

	/**
	 * ��ѯ�û��ĸ���Ա���ͽ�ɫ
	 */
	public List findFDYRole(Long kuid, Long kdid);

	public List findByKridAndKdidAndName(Long krid, Long kdid, String name, Long wpid);

	public List findByKridAndKdidAndNameAndThid(Long krid, Long kdid, String name, String thid);
}
