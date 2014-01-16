package org.iti.jxgl.service;

import org.iti.jxgl.entity.JxCourse;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface CourseService extends BaseService {
	/**
	 * <li>ͨ�����ε�λ���ҿγ��б�
	 * 
	 * @author Chen Li
	 * @param year
	 * @param term
	 * @param kdid
	 * @return �γ��б� 2010-7-11
	 */
	public List<JxCourse> FindCourseBykdId(Long kdid);

	/**
	 * <li>�������������ݿγ�ID�Ų��ҿγ�
	 * 
	 * @author zhanghlili
	 * @param kdid
	 * @return �γ�����
	 */
	public JxCourse FindCourseBykcbh(String kcbh);

	public JxCourse FindCourseBycid(Long cid);

	public JxCourse findByJcId(Long jcId);

	public JxCourse findByJcNo(String jcNo);

	public JxCourse findByJcbh(String jcbh);

	/**
	 * <li>�������������ݿγ̱��jc_id�б���ҿγ��б�
	 * 
	 * @param idlist
	 * @return
	 */
	public List findByIdList(List idlist);
	public List findBySYIdList(List idlist);

	public Integer getMaxNum();

	public String getNum();

	/**
	 * <li>�������ݿ��JX_COURSE���ֶ�JC_BH�д���˳��ŵ�����
	 * 
	 * @param kdid
	 * @return
	 */
	public Long checkDuplicate(JxCourse course);

	/**
	 * <li>��ѯ�γ̱����Ƿ����ظ���Ϣ�������򷵻ؿγ���������û���򷵻�0
	 * 
	 * @param jcid
	 * @return
	 */
	public JxCourse findByJcid(Long jcid);

	/**
	 * <li>���ݵ���γ̱��еĿγ̺Ų�ѯ�γ̱��
	 * 
	 * @param jcno
	 * @return
	 */
	public List findJcid();

	/**
	 * <li>����ʱtablename���ݿ���еĿγ���Ϣ���뵽course���ݿ��ͬʱ�õ���Ӧ�������ƻ���¼
	 * 
	 * @return
	 */
	public List ImportCourseGetsch(Long schoolkdid, String rxyear, Long kdid);

	/**
	 * <li>�������������ݿγ̱�ţ��б����ҿγ�ʵ��
	 * 
	 * @author zhangli
	 * @param jcid
	 * @return �γ�ʵ���б�
	 */
	public List findCourseByJcid(List jcid);

	/**
	 * <li>chenli <li>���������������������еĿγ���Ϣ���ҿγ̱��еĿγ�id
	 */
	public Long findcidBytaskcourse(JxCourse course);

	public Long findcidBytaskcourseentity(JxCourse course);

	/**
	 * chenli <li>���������������������еĿγ���Ϣ����ִ�мƻ�����ͬ�Ŀγ���Ϣ
	 * 
	 * @param course
	 * @return
	 */
	public List findsameCourse(JxCourse course);

	/**
	 * <li>�������������ݿγ����Ʋ��ҿγ̺�
	 */
	public JxCourse findjcIdByjcName(String jcName);

	/**
	 * <li>��������������ִ�мƻ��е���ݣ�ѧ�ڣ��꼶�����ű�ţ��γ̴�����ͳ�Ƹ���γ�ѧ�ֱ�������
	 * 
	 * @param year
	 * @param term
	 * @param Rxyear
	 * @param kdid
	 * @param ctype
	 * @return �γ����ʣ���ѧʱ����ѧ�֣����ϻ�ѧʱ�����ڿ�ѧʱ����ʵ��ѧʱ
	 * @author FengXinghong 2010-8-8
	 */
	public List findStaByYearAndTermAndRxyearAndDidAndType(String year, short term, String Rxyear, Long kdid, String ctype);

	/**
	 * <li>�������������������ƻ��е��꼶�����ű�ţ��γ̴�����ͳ�Ƹ���γ�ѧ�ֱ�������
	 * 
	 * @param year
	 *            �꼶
	 * @param kdid
	 *            ���ű��
	 * @param ctype
	 *            �γ̴�����
	 * @return �γ����ʣ���ѧʱ����ѧ�֣����ϻ�ѧʱ�����ڿ�ѧʱ����ʵ��ѧʱ
	 * @author FengXinhong 2010-8-9
	 */
	public List findByYearAndKdidAndType(String year, Long kdid, String ctype);

	/**
	 * cl
	 * 
	 * @param c
	 * @return
	 */
	public List findcidlistBytaskcourse(JxCourse c);

	/**
	 * ͨ���γ����Ʋ����������д��ڵĿγ�id
	 * 
	 * @param c
	 * @return
	 */
	public Long findcidBycname(String name);

	/**
	 * ͨ���γ�����jcid�б�
	 * 
	 * @param c
	 * @return
	 */
	public List findJcidByCname(String name);
	public List findJcidByCname(String name,short type);

	/**
	 * <li>�������������ݿγ̱�ţ��������еĿγ̱�ţ����ҿγ�
	 * 
	 * @author zhangli
	 * @param taskcid
	 * @return �γ�ʵ���б�
	 */
	public List findCourseByTask(List taskcid);

	/**
	 * <li>�������������ݿγ̺źͿγ������ҿγ�
	 */
	public JxCourse findCourseByJcNoAndJcName(String jcNo, String jcName);
	/**
	 * ͨ���γ����Ʋ����������д��ڵĿγ�id
	 * 
	 * @param c
	 * @return����
	 */
	public List findcidlistBycname(String name);
	public List findBylistcid(List listcid);
	
	/**
	 * <li>�������������ݿγ̱���б���ҿγ̣��ų�ʵ���
	 * @author  lili
	 * @param jcid �γ̱���б�
	 * @return �γ��б�
	 */
	public List findCourByJcid(List jcid);
	
	/**
	 * <li>��������������ѧ�꣬ѧ�ڣ���֯���ű�ţ��γ̱�ţ�����Ϊ�գ����Ծ��еĿγ�
	 * @param yearѧ�꣬
	 * @param term ѧ��
	 * @param kdid ��֯���ű��
	 * @param cid �γ̱�ţ�����Ϊ�գ�
	 * @return �γ��б�
	 */
	public List<JxCourse> findBypapercid(String year, Short term, Long kdid,Long  cid); 
 
	public List findCourByJcnoJcnameCrediType(String jcno,String jcname,Float xufen,short type);
 
}
