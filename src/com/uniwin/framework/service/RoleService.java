package com.uniwin.framework.service;

/**
 * <li>��ɫ���ݷ��ʽӿ�]
 * @author DaLei
 * @2010-3-16
 */
import java.util.List;

import org.iti.xypt.entity.Student;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTRole;

public interface RoleService extends BaseService {
	/**
	 * <li>������������ø���ɫȫ���ӽ�ɫ
	 * 
	 * @param ptid
	 *            ����ɫ���
	 * @return List
	 * @author DaLei
	 * @2010-3-16
	 */
	public List<WkTRole> getChildRole(Long ptid);

	/**
	 * <li>�������������ĳ���û����н�ɫ�б�
	 * 
	 * @param uid
	 *            �û����
	 * @return List
	 * @author DaLei
	 */
	public List<WkTRole> getRoleOfUser(Long uid);

	/**
	 * <li>�������������ĳ���û����н�ɫ�б����ҽ�ɫ����dlist�б��С�
	 * 
	 * @param uid
	 *            �û����
	 * @param dlist
	 *            �����б�
	 * @return List
	 * @author DaLei
	 */
	public List<WkTRole> getRoleOfUser(Long uid, List<Long> dlist);

	/**
	 * <li>��������������ӽ�ɫ����Ҫ���ӽ�ɫ������args�С�
	 * 
	 * @param ptid
	 * @param args
	 * @return List
	 * @author DaLei
	 */
	public List<WkTRole> getChildRole(Long ptid, List<Long> args);

	/**
	 * <li>��������������ӽ�ɫ����Ҫ���ӽ�ɫ������args�л����ǹ����ɫ��
	 * 
	 * @param ptid
	 * @param args
	 * @return List
	 * @author DaLei
	 */
	public List<WkTRole> getChildRoleOrDefault(Long ptid, List<Long> args);

	/**
	 * <li>����������ɾ����ɫ��ͬʱɾ����ɫ��Ӧ��Ȩ�޹�ϵ����ͬ�û��Ĺ�ϵ��
	 * 
	 * @param role
	 *            void
	 * @author DaLei
	 */
	public void delete(WkTRole role);

	public WkTRole findByRid(Long krId);

	public List<WkTRole> findByName(String rname);

	public List<WkTRole> FindByName(String rname);

	public List<WkTRole> findSelectAdmins(Long schid, char grade);

	/**
	 * ��ѯĳѧУ������ɫ
	 * 
	 * @param kdid
	 * @return
	 */
	public List<WkTRole> findDudaoRole(Long kdid);

	/**
	 * ��ѯĳ��ѧУ���㹤�������쵼��ɫ���趨ʱ������ֻ��һ����ɫ���д˹���
	 * 
	 * @param kdid
	 * @return
	 */
	public WkTRole findByGzl(Long kdid);

	/**
	 * ��ѯĳ��ѧУ����Ա��ɫ����ɫʵ���в��ޱ�ʾ����˲�ѯ�쵼��ɫ�������չ����ɫ��ų��Ⱥͼ���������õ�һ����ɫ
	 * 
	 * @param kdid
	 * @return
	 */
	public WkTRole getShcAmdinRole(Long kdid);

	/**
	 * ���ĳ���û����û���
	 * 
	 * @param kuId
	 * @return
	 */
	public List<WkTRole> getProleOfUser(Long kuId);

	/**
	 * ���ĳ���û����û��飬�û����й����ܹ�����ĳ�����⡣���ڷ���֪ͨ������ʹ��
	 * 
	 * @param kuId
	 * @param tid
	 *            ������
	 * @return
	 */
	public List<WkTRole> getProleOfUser(Long kuId, Long tid);

	/**
	 * ���ĳ���û����û����ڵĽ�ɫ
	 * 
	 * @param krId
	 *            �û���Ľ�ɫ����
	 * @param kuId
	 *            �û����û����
	 * @return
	 */
	public List<WkTRole> getChildRoleByKuid(Long krId, Long kuId);

	/**
	 * ��ѯĳ��֪ͨ��֪ͨ��ɫ
	 * 
	 * @param xmid
	 * @return
	 */
	public List<WkTRole> findByXmid(Long xmid);

	/**
	 * ���ݱ����ź�ѧУ��ţ������ܷ����������Ľ�ɫ
	 * 
	 * @param tid
	 * @param bgId
	 * @return
	 */
	public List<WkTRole> findByTidAndKdid(Long tid, Long kdid);

	/**
	 * ���ݽ�ɫ��������ѧ�Ų���ѧ��
	 */
	public List<Student> findStuByRole(List<Object> deplist, Long krid, String sname, String sno);
	/**
	 * ���ݵ�λ����ɫ��������ѧ�Ų��Ҹ���Ա�����༶��ѧ��
	 */	
	public List<Student> findStuByRole(Long kdid,Long kuid, Long krid, String sname, String sno);
	public List<Student> findStuByRole(Long clid, Long krid, String sname, String sno);

	public List<WkTRole> findAllRole(Long kdid);
	/**
	 * ��ȥ����
	 * @param kdid
	 * @return
	 */
	public List<WkTRole> findAllRoleWithout(Long kdid);
	public List<WkTRole> findbyKrAdmins(String KrAdmins);
	public List findrolebykuid(Long kuid);
	public List findByKdidAndKrname(Long kdid, String krname);
	/**
	 * ��ȥ����
	 * @param kdid
	 * @return
	 */
	public List getUserRoleId(Long uid);
	
}
