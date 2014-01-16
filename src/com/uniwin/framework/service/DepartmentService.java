package com.uniwin.framework.service;

/**
 * <li>��֯�������ݷ��ʽӿ�
 * @author DaLei
 * @2010-3-15
 */
import java.util.List;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;

public interface DepartmentService extends BaseService {
	/**
	 * �����û�id�ͽ׶����Ʋ�ѯѧУ
	 * @param kuid
	 * @param ktname
	 * @return
	 */
	public List<WkTDept> findByKuidAndKtname(Long kuid,String ktname);
	public List<WkTDept> getChildrenbyPtid(Long ptid);
	public List<WkTDept> getChildrenbyPtidForWork(Long ptid);
	/**
	 * <li>�������������ĳ��֯���ŵ�ȫ�����Ӳ����б�
	 * 
	 * @param ptid
	 *            ����֯���ű��
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTDept> getChildDepartment(Long ptid, String kdtype);

	/**
	 * <li>�������������ĳ��֯���ŵ�ȫ�����Ӳ����б�
	 * 
	 * @param ptid
	 *            ����֯���ű��
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTDept> getChildDepdw(Long ptid);

	/**
	 * <li>������������ø���֯����ȫ�����Ӳ��ţ������������Ϊnotdid�Ĳ���
	 * 
	 * @param ptid
	 *            ����֯����
	 * @param notdid
	 *            �б��в������Ĳ���
	 * @return List
	 * @author DaLei
	 * @2010-3-15
	 */
	public List<WkTDept> getChildDepartment(Long ptid, Long notdid);

	/**
	 * <li>����������ɾ��һ�����ţ����¸ú�������Ϊ��Ҫɾ��������ؼ�¼
	 * 
	 * @param dept
	 *            Ҫɾ���Ĳ���
	 * @return List
	 * @author DaLei
	 * @2010-3-29
	 */
	public void delete(WkTDept dept);

	/**
	 * <li>�������������ֱ����ĳ�����û���Ŀ
	 * 
	 * @param did
	 * @return Long
	 * @author DaLei
	 */
	public Long getUserCount(Long did);

	public WkTDept findByDid(Long kdid);

	/**
	 * <li>��������������ĳһ��ɫ���û���ѯ���ڵ����в���
	 * 
	 * @param krid
	 * @return �����б�
	 * @author FengXinhong 2010-4-15
	 */
	public List<WkTDept> getDepByuser(List<Long> rlist);

	/**
	 * <li>������������ѯ�û����н�ɫ�����ڲ����б�
	 * 
	 * @param kuid
	 * @return List
	 * @author DaLei
	 */
	public List<WkTDept> getRootList(Long kuid);

	public WkTDept findByKdname(String kdname);

	/**
	 * <li>������������ѯĳ���ŵ����¼������в���
	 * 
	 * @param kuid
	 * @return List
	 * @author DaLei
	 */
	public List<WkTDept> findByPpKdid(Long kdid);

	/**
	 * <li>�������������ҿ��ε�λ�б�
	 * 
	 * @return ��λ�б�
	 */
	public List<WkTDept> findKkDep();

	/**
	 * ��ѯĳ��ѧУ�Ƿ�ӵ��ĳ����֯��ŵ���֯
	 * 
	 * @param kdNum
	 * @param kdid
	 * @return
	 */
	public WkTDept findByKdnumberAndKdSchid(String kdNum, Long kdid);

	public List<WkTDept> findByLevelAndSchid(Integer level, Long kdid);

	/**
	 * ����ĳ�����赥λ��ѯ��֯��λ
	 * 
	 * @param ��buid
	 * @return
	 */
	public List<WkTDept> findByBuid(Long buid);

	/**
	 * ��ѯ����Ա��Ͻ�Ĳ���
	 */
	public List<WkTDept> findDeptForFDY(Long kipid, Long kuid);

	/**
	 * ��ѯĳ������ָ�����͵Ĳ���
	 */
	public List<WkTDept> findDeptByKdidAndType(Long kdid, String type);
	/**
	 * ��ѯĳ�������Ƿ�����ĳ�����ŵ��Ӳ���
	 */
	public List<WkTDept> findbykdidkdpid(Long kdid,Long kdpid);
	public List<WkTDept> findDanweiAndBumenByKpid(Long kdid);
	public List getChildDepartment(Long ptid) ;
	public WkTDept findByKdnameandschid(String kdname,Long kdpid);
	
	public List<WkTDept> getDeptByNum(String num);
}
