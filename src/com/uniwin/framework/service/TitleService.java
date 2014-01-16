package com.uniwin.framework.service;

/**
 * <li>�������ݷ��ʽӿ�
 * @author DaLei
 * @2010-3-15
 */
import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;

public interface TitleService extends BaseService {
	/**
	 * <li>������������ñ����ȫ�����ӱ���
	 * 
	 * @param ptid
	 *            ��������
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTTitle> getChildTitle(Long ptid);

	/**
	 * <li>��ø����⺢�ӱ��⣬���������Ϊtid�ı���
	 * 
	 * @param ptid
	 *            ������id
	 * @param tid
	 *            Ҫ���ӽڵ㲻����idΪtid
	 * @return ��������ptid�ĺ��ӱ��⣬�����ӱ����Ų�Ϊtid
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTTitle> getChildTitleThree(Long ptid);

	public List<WkTTitle> getChildTitle(Long ptid, Long tid);

	/**
	 * <li>��������������û����ʵı����б�
	 * 
	 * @param user
	 * @return List
	 * @author DaLei
	 */
	public List<WkTTitle> getTitlesOfUserAccess(WkTUser user);

	public List<WkTTitle> getTitlesOfUserAccess(WkTUser user, Long tpid);

	/**
	 * <li>���������� ����û�����ı����б�
	 * 
	 * @param deptList
	 *            �û��������б�
	 * @param titleList
	 *            Ҫ����������titleList�б��У������򷵻�ȫ��
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTTitle> getTitlesOfUserManager(WkTUser user, List<Long> deptList, List<WkTTitle> titleList);

	/**
	 * <li>����������ɾ�����⣬ͬʱ�ڴ�ɾ�������Ӧ��Ȩ�޹�ϵ��
	 * 
	 * @param title
	 *            void
	 * @author DaLei
	 */
	public void delete(WkTTitle title);

	public List<WkTTitle> getTitleOfRoleAccess(Long ptid, Long rid);

	/**
	 * ��ñ�����ӱ��⣬Ҫ���ӱ�������ɫ��Ȩ����
	 * 
	 * @param pid
	 *            ������
	 * @param list
	 *            ���ⷶΧ
	 * @return
	 */
	public List<WkTTitle> getChildTitleInlist(Long pid, Long krid, String typeTitle, Short kacode);
	public WkTTitle getTitle(Long tid) ;
}
