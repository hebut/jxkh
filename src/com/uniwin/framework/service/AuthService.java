package com.uniwin.framework.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;

public interface AuthService extends BaseService {
	public List<WkTAuth> getChildAuth(Long aid);
	/**
	 * ���ݽ�ɫ����ҳ����Է��ʵı���
	 * 
	 * @param krid
	 *            ��ɫ���
	 * @return ��ɫ���Է��ʵı����б�
	 */
	public List<WkTAuth> findByKrid(Long krid);
	/**
	 * ��ѯ��ͬ���͵�Ȩ�ޣ���������1 ���ص��Ǳ����Ȩ�ޣ�2����Ƶ����Ȩ�ޣ�
	 * 
	 * @param katype
	 * @return
	 */
	public List<WkTAuth> findByType(String katype);

	/**
	 * 
	 * @param krid
	 * @return ��ɫ��Ϣ
	 */
	public WkTRole findrole(Long krid);

	/**
	 * 
	 * @param kdid
	 * @return WkTDept ������Ϣ
	 */
	public WkTDept finddep(Long kdid);

	/**
	 * <li>�������������Ȩ���б�
	 * 
	 * @param tid
	 *            ������
	 * @param type
	 *            ���ͱ��
	 * @param rlist
	 *            �����ɫ�б�
	 * @param dlist
	 *            �������б�
	 * @return List<TitltAuth>
	 * @author DaLei
	 */
	public List<WkTAuth> getAuthOfTitle(Long tid, List<Long> rlist, List<Long> dlist);

	/**
	 * <li>����������ɾ��ĳ�������Ȩ��..Ҫ��Ȩ���еĽ�ɫ�Ͳ�����rlist��dlist��
	 * 
	 * @param tid
	 * @param rlist
	 * @param dlist
	 *            void
	 * @author DaLei
	 */
	public void deleteAuthOfTitle(Long tid, List<Long> rlist, List<Long> dlist);

	/**
	 * <li>����������ɾ��ĳ��Ƶ����Ȩ��..Ҫ��Ȩ���еĽ�ɫ�Ͳ�����rlist��dlist��
	 * 
	 * @param cid
	 * @param rlist
	 * @param dlist
	 *            void
	 * @author XiaoFeng
	 */
	public void deleteAuthOfChanel(Long cid, List<Long> rlist, List<Long> dlist);

	/**
	 * <li>���������������auth����ֵ��ͬ��Ȩ���б�����Ȩ�����õı������������С�
	 * 
	 * @param auth
	 * @return List
	 * @author DaLei
	 */
	public List<WkTAuth> findByExample(WkTAuth auth);

	/**
	 * <li>�������������ĳ���û�����ı����Ȩ�ޡ�
	 * 
	 * @param deptList
	 *            �û�����Ĳ����б�
	 * @param tid
	 *            ���Ȩ�޵ı���
	 * @return List
	 * @author DaLei
	 */
	public List<WkTAuth> getAuthOfTitle(List<Long> deptList, Long tid);

	/**
	 * ��ȡ����Ƶ����Ȩ���б�
	 * 
	 * @param depList
	 *            �����б�
	 * @param cid
	 *            ��ȡƵ����Ȩ��
	 * @return List
	 */
	public List<WkTAuth> getAuthOfChanel(List<Long> depList, Long cid);

	/**
	 * <li>���������������Ŀ��Ȩ���б�
	 * 
	 * @param cid
	 *            ��Ŀ���
	 * @param type
	 *            ���ͱ��
	 * @param rlist
	 *            �����ɫ�б�
	 * @param dlist
	 *            �������б�
	 * @return List<TitltAuth>
	 * @author DaLei
	 */
	public List<WkTAuth> getAuthOfChanel(Long cid, List<Long> rlist, List<Long> dlist);

	/**
	 * ��ȡƵ����Ȩ��
	 */
	public List<WkTAuth> findAuthOfChanel(Long kcid);

	public void deleteByRole(WkTRole role);

	public void copyAuthByRole(WkTRole frole, WkTRole trole);

	/**
	 * ���ݽ�ɫ��źͱ������Ȩ�޺�Ȩ�����ҳ����Է��ʵı���
	 * 
	 * @param krid
	 *            ��ɫ���
	 * @param type
	 *            �������Ȩ��
	 * @param code
	 *            Ȩ����
	 * @return ��ɫ���Է��ʵı����б�
	 */
	public List<WkTAuth> findByKridAndTypeAndCode(Long krid, String type, Short code);
	public void deleteAuthOfTask(Long cid,List rlist,List dlist);
	public List<WkTAuth> getAuthOfTask(List depList, Long wid);
}
