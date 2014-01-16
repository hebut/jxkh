package org.iti.xypt.service;

import java.util.Date;
import java.util.List;

import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyClass;

import com.uniwin.basehs.service.BaseService;
import com.uniwin.framework.entity.WkTDept;

public interface StudentService extends BaseService {

	public void update(Student student);

	/**
	 * 
	 * <li>ͨ���û���Ų���ѧ��
	 * 
	 * @author zhangxue
	 * @param kuid
	 * @return ѧ�� 2010-7-12
	 */
	public Student findByKuid(Long kuid);

	/*
	 * ���ݲ��ű�źͽ�������ѧ���б� kdid ���ű�� sgrade ���� lys
	 */

	public List findByKdidAndSgrade(Long kdid, Integer stGrade);

	/*
	 * ���ݲ��ű�źͽ�������ѧ���б� kdid ���ű�� sbynf ��ҵ��� lys
	 */

	public List findByKdidAndSbynf(Long kdid, Integer stBynf);

	/**
	 * ���ݲ��ű�źͽ�������û�вμӱ����ѧ��
	 * @param kdid
	 *            ���ű��
	 * @param stGrade
	 *            ����
	 * @return
	 */
	public List findbyXueyuan(Long kdid, Integer stGrade);

	/**
	 * <li>������������ѯĳЩ������ĳ�꼶��ѧ���б�
	 * 
	 * @param dlist
	 *            ĳЩ����
	 * @param rid
	 *            ѧ��
	 * @param garde
	 *            ѧ���꼶
	 * @return List
	 * @author DaLei
	 */
	public List findBydeplistAndrid(List dlist, Long rid, Integer grade);

	/**
	 * <li>������������������ĳЩ������ĳ�꼶��ѧ���б�
	 * 
	 * @param dlist
	 *            ĳЩ����
	 * @param rid
	 *            ѧ��
	 * @param garde
	 *            ѧ���꼶
	 * @param tname
	 *            ����
	 * @param tno
	 *            ѧ��
	 * @return List
	 * @author DaLei
	 */
	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno);

	/**
	 * <li>������������ѯĳ���ѧ���б�
	 * 
	 * @return
	 */
	public List findByClass(String stClass);

	/**
	 * <li>��������������ѧ�Ų�ѯѧ������
	 * 
	 * @return
	 */
	public List findSnameByStid(String stId);

	/**
	 * <li>��������������ѧ�Ų�ѯѧ����Ϣ
	 * 
	 * @return
	 */
	public Student findClassByStid(String stId);

	/**
	 * <li>��������������꼶�б�
	 * 
	 * @return
	 */
	public List findGrade();

	/**
	 *<li>�������������հ༶���Ʋ�ѯ�ð༶��ѧ���б�
	 * 
	 * @param clist
	 *            �༶id�б�
	 * @return ѧ���б�
	 * @author FengXinhong 2010-7-27
	 */
	public List findByClass(List clist);

	public Long countByClass(Long clid);

	/**
	 * ��ѯĳѧ��ѧ���б�
	 * 
	 * @param sid
	 * @param kdid
	 * @return
	 */
	public List findBySid(String sid);

	public void updateStudentClass(XyClass xyClass);

	/**
	 * 
	 * <li>�������������ݰ༶��Ų�������ĳһ���ȫ��ѧ��
	 * 
	 * @author zll
	 * @param clid
	 *            �༶���
	 * @return ѧ���б�
	 */
	public List findStuByClid(Long clid);

	/**
	 * <li>�������������ݲ��ű�źͽ�ʦ�û���ţ����ҽ�ʦ������ȫ��ѧ��
	 */
	public List findStuByKdId(Long kdid);

	public List findClassByBsid(Long bsid);

	public List findMajorByBsid(String claname);
	

	/**
	 * 
	 * <li>��������������רҵid,ѧ�꣬ѧ�ڲ��Ҹ�רҵѧ�������ڵ�ѧ��
	 * @param kdid
	 * @param year
	 * @param term
	 * @return
	 */
	public List findNoMxStu(Long kdid,String year,Short term);
	
	/**
	 *  <li>����������ѧ�ꡢѧ�ڡ�ѧУ��רҵ���꼶���༶��ѧ�š��������ҷ�����ѧ���б�
	 * @param year
	 * @param term
	 * @param schid
	 * @param kdid
	 * @param grade
	 * @param cla
	 * @param sid
	 * @param sname
	 * @return
	 */
	public List findStuByYearAndTermAndSchidAndKdidAndGradeAndClassAndSidAndName(String year,Short term,Long schid,Long kdid,Integer grade,String cla,String sid ,String sname);
	
	/**
	 * <li>����������ѧ�ꡢѧ�ڡ�ѧԺid,רҵid���꼶���༶��ѧ�š��������ҷ�����ѧ���б�
	 * @param year
	 * @param term
	 * @param kdid ѧУid
	 * @param xyid
	 * @param zyid
	 * @param grade
	 * @param cla
	 * @param sid
	 * @param sname
	 * @return
	 */
	public List findStuByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndName(String year,Short term,Long kdid,Long xyid,Long zyid,Integer grade,String cla,String sid ,String sname);
	
	/**
	 * <li>����������ѧ�ꡢѧ�ڡ�ѧԺid,רҵid���꼶���༶��ѧ�š��������ҷ�����ѧ���б�
	 * @param year
	 * @param term
	 * @param kdid ѧУid
	 * @param xyid
	 * @param zyid
	 * @param grade
	 * @param cla
	 * @param sid
	 * @param sname
	 * @return
	 */
	public List findByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndName(String year,Short term,Long kdid,Long xyid,Long zyid,Integer grade,String cla,String sid ,String sname);
	/**
	 *  <li>����������ѧ�ꡢѧ�ڡ�ѧԺid,רҵid���꼶���༶��ѧ�š���������ĳ����Ա�����༶�ķ�����ѧ���б�
	 * @param year
	 * @param term
	 * @param kdid
	 * @param xyid
	 * @param zyid
	 * @param grade
	 * @param cla
	 * @param sid
	 * @param sname
	 * @param kuid
	 * @return
	 */
	public List findByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndNameFDY(String year,Short term,Long kdid,Long xyid,Long zyid,Integer grade,String cla,String sid ,String sname,Long kuid);
	/**
	 * <li>������������ѯ����ѧ���гɼ���ĳһ�������ѧ���б�
	 * @param xlid У��
 	 * @param stulist ����ѧ���б�
	 * @param small ��ٳ��ڴ��ڵĴ���
	 * @param big ��ٳ���С�ڵĴ���
	 * @param small1 �������ڵĴ���
	 * @param big1 ����С�ڵĴ���
	 * @return ����������ѧ���б�
	 */
	public List findHgStuInStulist(Long xlid,List stulist,Long small,Long big,Long small1,Long big1);
	/**
	 *<li>�������������հ༶���Ʋ�ѯ�ð༶��ѧ���б�
	 * 
	 * @param cid:�༶id
	 * @return ѧ���б�
	 * @author heya
	 */
	public List heyafindstu(Long  cid);
	//����ĳ���༶��û����ĳ����ѧ��־�Ŀ��ڱ��е�����
	public List findBysclassAndRzidAndTypeNotInKq(String sclass,Long rzid,Short type);
	/**
	 *<li>�������������ն���༶����---�༶LIST--��ѯ--ѧ���б�
	 * 
	 * @return ѧ���б�
	 * @author duqing
	 */
	public List findByclasslist(List classlist);

	/**
	 *<li>�������������ݰ༶���ƣ�������ѧ�Ų�ѯ--ѧ���б�
	 * @return ѧ���б�
	 * @author duqing
	 */
	public List findByClassandNameandXno(String classname,String xname,String xsno);
	/**
	 *<li>�������������ݰ༶����("-��ѡ��-")��������ѧ�Ų�ѯ--ѧ���б�
	 * @return ѧ���б�
	 * @author duqing
	 */
	public List findByClassandNameandXno(List classlist,String xname,String xsno);
	
	public List findByCidandSidandSname(Long clid,String sid,String sname);
	public List findBycaidAndClidAndTypeNotInXszc(Long clid,Long caid,Short type);
	 /**
	  * <li>����ĳ��ѧԺ������ѧ��
	  * @param kdid ѧԺ���
	  * @return ѧ���б�
	  * 2011-2-28
	  */
	public List findXyAllStudent(Long kdid);
	/**
	 * <li>����ĳ������Ա����ĳ��ѧУ������ѧ��
	 * @param kuid
	 * @param sid
	 * @return
	 */
	public List findByFDYKuidAndSid(Long kuid,Long sid,String sname,String sno);
	/**
	 * 
	 * @param kuid
	 * @param sid
	 * @param grade
	 * @param sname
	 * @param sno
	 * @return
	 */
	public List findByFDYKuidAndSidAndGrade(Long kuid,Long sid,Integer grade,String sname,String sno);

    /**
     * <li>����ѧ�Ų�ѯרҵ������ɼ�ʱ�ã�2011-4-22
     * @author zhangli
     * @param number
     * @return ��֯����
     */
    public WkTDept finddeptByStudentno(String number);
    /**
     * ��ҳ ����Ա��ѯ��ٳ�����Ϣ
     * @param year
     * @param term
     * @param kdid
     * @param xyid
     * @param zyid
     * @param grade
     * @param cla
     * @param sid
     * @param sname
     * @param kuid
     * @param rowNumber
     * @param dataCount
     * @return
     */
    public List  findPageByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndNameFDY(String year,Short term,Long kdid,Long xyid,Long zyid,Integer grade,String cla,String sid ,String sname,Long kuid,int rowNumber, int dataCount);
  /**
   * ��ҳ ѧУ�û���ѧԺ�쵼��ѯ������Ϣ
   * @param year
   * @param term
   * @param kdid
   * @param xyid
   * @param zyid
   * @param grade
   * @param cla
   * @param sid
   * @param sname
   * @param fromdata
   * @param todata
   * @return
   */
    public List findPageByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndName(String year, Short term, Long kdid, Long xyid, Long zyid,
			Integer grade, String cla, String sid, String sname,int fromdata,int todata);
    public List findNdsStuByYearAndTermAndSchidAndKdidAndGradeAndClassAndSidAndName(
			String year, Short term, Long schid,Long kdid, Integer grade, String cla,
			String sid, String sname, Date dsdate);
}
