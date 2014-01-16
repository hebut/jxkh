package org.iti.xypt.service;

import java.util.List;

import org.iti.xypt.entity.XyClass;

import com.uniwin.basehs.service.BaseService;

public interface XyClassService extends BaseService {
	/**
	 * ��ѯĳרҵ����İ༶�б�
	 * 
	 * @param kdid
	 * @return
	 */
	public List findByKdid(Long kdid);

	/**
	 * ��ѯĳרҵ�����ĳ�꼶�İ༶�б�
	 * 
	 * @param kdid
	 * @return
	 */
	public List findByKdid(Long kdid, int year);

	/**
	 * ��ѯĳЩϵ����רҵ�ľ��е��꼶�б�
	 * 
	 * @param deptList ϵ����רҵ�б�
	 * @return �꼶�б�
	 */
	public List getGradeList(List deptList);

	/**
	 * ����ϵ���꼶��ѯ�༶�б�
	 * 
	 * @param kdid
	 * @param grade
	 * @return
	 */
	public List findByKdidAndGrade(Long kdid, Integer grade);

	/**
	 * <li>��������:������֯��λ��źͰ༶��Ʋ�ѯ�༶
	 * 
	 * @param kdid ��֯��λ���
	 * @param Cname �༶���
	 * @return һ���༶����
	 * @author FengXinhong 2010-7-27
	 */
	public XyClass findByKdidAndCname(Long kdid, String Cname);

	/**
	 * <li>����ѧ���ĵ�¼�������Ұ༶
	 * 
	 * @author zhangli
	 * @param ku
	 * @return �༶ʵ��
	 */
	public XyClass findByKuid(Long ku);

	public List findByGradeAndName(Integer grade, String name, List dlist);

	/**
	 * ��ѯĳѧУĳ���ư༶�б�
	 * 
	 * @param cname
	 * @param kdid
	 * @return
	 */
	public List findByCnameAndKdid(String cname, Long kdid);

	/**
	 * ��ѯĳѧУĳ���ư༶�б�
	 * 
	 * @param cname
	 * @param kdid
	 * @return
	 */
	public List findByScnameAndKdid(String cname, Long kdid);

	/**
	 * <li>������֯��λ����б���Ұ༶
	 * 
	 * @author zhangli
	 * @param kdid ��֯��λ���
	 * @return �༶ʵ���б�
	 */
	public List findClassByKdid(List kdid);

	/**
	 * ���ݰ༶���ƺ�ѧԺϵ�б��жϰ༶����ϵ cl
	 * 
	 * @param cname
	 * @param deplist
	 * @return
	 */
	public List findClassByCnameAndDeplist(String cname, String ccname, List deplist);

	/**
	 * ���ݰ༶�������Ұ༶
	 * 
	 * @param cid
	 * @return
	 */
	public List findClassByCid(Long cid);

	/**
	 * @author zhangli <li>���ݰ༶����б���Ұ༶
	 * @param sname �༶����б�
	 * @return �༶�б�
	 */
	public List findClasByCsn(List sname);

	/**
	 * @author lys <li>���ݰ༶���ƺͰ༶���ڵ�λ��ѯ�༶ * @param sname �༶����б�
	 * @return �༶�б�
	 */
	public XyClass findByKdidAndCName(Long kdid, String cName);

	/**
	 * @author ���Ҹ���Ա����ĳ���ŵİ༶
	 */
	public List findClassForFDY(Long kdid, Long kuid);

	/**
	 * @author ����ĳѧԺ�İ༶
	 */
	public List findClassForCollege(Long kdid, Integer max, Integer min);

	/**
	 * ����ѧУ��źͰ༶��Ʋ�ѯ�༶�б�
	 * 
	 * @param kdid
	 * @param Sname
	 * @return
	 */
	public List findByKdidAndSname(Long kdid, String Cname);

	/**
	 * �ж�ĳһ���༶�Ƿ���ĳһ�༶�б���
	 * 
	 * @param xc
	 * @param clist
	 * @return
	 */
	public boolean CheckIfIn(XyClass xc, List clist);

	/**
	 * ���ݲ��ű�Ų�ѯ�꼶
	 * 
	 * @param kdid
	 * @return
	 */
	public List findgradeByKdid(Long kdid);

	/**
	 * <li>��������������ѧԺ��רҵ��Ų�ѯ���꼶��ѯ�༶�б�
	 * 
	 * @param kdid
	 * @param grade
	 * @return
	 */
	public List findByKdIdAndGrade(Long kdid, Integer grade);

	/**
	 * ����ѧУ��Ų�ѯ�꼶
	 * 
	 * @param kdid
	 * @return
	 */
	public List findgradeByScKdid(Long kdid);

	/**
	 * @return
	 */
	public List findClass(Long kdid);

	/**
	 * ���ݰ༶�������Ұ༶ʵ��
	 * 
	 * @param clid
	 * @return XyClass
	 * @author ����
	 */
	public XyClass hyfindclass(Long kdid);

	/**
	 * ���ݰ༶���ƺ�����ѧԺ���Ұ༶ʵ��
	 * 
	 * @param clname
	 * @param kdid
	 * @return XyClass
	 * @author ����
	 */
	public XyClass hyfindclassbykdidclname(Long kdid, String clname);

	/**
	 * ���ݰ༶�Ų��Ұ༶ʵ��
	 */
	public XyClass hyfindclassbycid(Long cid);
    //����רҵ����ѧԺ���Ұ༶
	public List findBykdIdorkdPid(Long kdid);
	/**
	 * ��ѯ����Ա��ĳ��ѧУ�������꼶
	 * @param kuid
	 * @param sid
	 * @return
	 */
	public List findByKuidAndSid(Long kuid,Long sid);
/**
 * ��ѯ����Ա��ĳ��ѧУ�����İ༶
 * @param kuid
 * @param sid
 * @return
 */
	public List findClassByKuidAndSid(Long kuid,Long sid);
	/**
	 * ��ѯ����Ա��ĳ��ѧУ������ĳ���꼶�İ༶
	 * @param kuid
	 * @param sid
	 * @param grade
	 * @return
	 */
	public List findClassByKuidAndSidAndGrade(Long kuid,Long sid,Integer grade);
	/**
	 * 
	 * @param kdid
	 * @param gradelist
	 * @return
	 */
	public List findbyKdidAndGradeList(Long kdid,List gradelist);
}
