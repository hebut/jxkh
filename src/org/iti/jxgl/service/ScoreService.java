package org.iti.jxgl.service;

import java.util.List;

import org.iti.jxgl.entity.JxScore;

import com.uniwin.basehs.service.BaseService;

public interface ScoreService extends BaseService {
	/**
	 * <li>��������������ѧ�źͿγ̺Ų���ѧ���ɼ�
	 */
	public JxScore findBySidAndCid(String jsSid, String jsCid, Integer jsYear, Short jsTerm);

	/**
	 * <li>��������������ѧ�źͿγ̺Ų��ҽ�ʦ
	 */
	public List findTeacherBySidAndCid(String sid, Long cid, String year, Short term);

	/**
	 * <li>��������������ѧԺ���
	 */
	public List findStudentCollege(String sid);

	/**
	 * <li>��������������רҵ���
	 */
	public List findStudentMajor(String sid);

	/**
	 * <li>������������ѯ�����б�
	 */
	public List findDeptList(Long kdid);

	/**
	 * <li>������������ѯ�γ��б�
	 */
	public List findLessonList(Long kdid);

	/**
	 * <li>������������ѯ��ʦ�б�
	 */
	public List findTeacherlist(Long kdid);

	/**
	 * <li>������������ѯ�༶�б�
	 */
	public List findClasslist(Long kdid);

	/**
	 * <li>������������ѯ����ĳ���꼶�İ༶�б�---����
	 */
	public List findClassgradelist(Long kdid, Integer grade);

	/**
	 * <li>������������ѯĳ����ĳ�꼶��ѧ����������
	 */
	public List findStudentInScoreByGrade(Integer year, Short term, Long kdid, Integer grade);

	/**
	 * <li>������������ѯĳ����ĳ�꼶��ѧ������
	 */
	public List findStudentInDept(Long kdid, Integer grade);

	/**
	 * <li>��������������ѧ�Ų���ѧ���ɼ�
	 */
	public List findBySid(String jsSid, Integer jsYear, Short jsTerm);

	/**
	 * <li>������������ѯͬһ�ſγ��м�λ��ʦ�Ͽ�
	 */
	public List findTeacherBySameCourse(Integer year, Short term, String jcno, Long kdid);

	/**
	 * <li>����������������ݡ�ѧ�ڡ���ʦ���γ̲�ѯ�༶
	 */
	public List findClassByTeacherAndCourse(String year, Short term, Long kuid, String jcno, Long kdid);

	/**
	 * <li>������������ѯ��ʦ�̵����а�ĳɼ��б�
	 */
	public List findScoreByClassAndCourse(Integer year, Short term, String jcno, List clalist);

	/**
	 * <li>������������ѯָ����Χ�ĳɼ��б�
	 */
	public List findScorelist(String jcno, Double max, Double min, List scorelist);

	/**
	 * <li>������������ѯָ����ʦ���̵Ŀγ�
	 */
	public List findCourseForTeacher(Integer year, Short term, Long kuId, Long kdid);

	/**
	 * <li>������������ѯָ����ʦ���̿γ̵ĸ���
	 */
	public List findClassByJcNoAndKuId(String year, Short term, String jcNo, Long kuId, Long kdid);

	/**
	 * <li>������������ѯ�༶�е�ĳ�ſγ̵ĳɼ�
	 */
	public List findCourseScoreInClass(Integer year, Short term, String jcNo, String claname);

	/**
	 * <li>������������ѯ�������е�ĳ�����ݼ�¼
	 */
	public List findYearForClass(String claname);

	/**
	 * <li>������������ѯĳ���ĳ���ѧ�ڼ�¼
	 */
	public List findTermForClass(String year, String claname);

	/**
	 * <li>������������ѯ�༶�е�����ѧ���ĳɼ�
	 */
	public List findAllStuScoreInClass(Integer year, Short term, String claname);

	/**
	 * <li>��������������ݡ�ѧ�ڡ�רҵ���Ҳ�����γ̣�ȥ�أ�
	 */
	public List findFailCourse(Integer year, Short term, String kdnumber);

	/**
	 * <li>��������������ݡ�ѧ�ڡ�רҵ���γ̲��ҳɼ��б�
	 */
	public List findScoreByDeptAndCourse(Integer year, Short term, String jcno, String kdnum);

	/**
	 * <li>��������������ݡ�ѧ�ڡ�רҵ���γ̲��Ҳ������б�
	 */
	public List findFailByDpetAndCourse(Integer year, Short term, String jcno, String kdnum);

	/**
	 * <li>������������ѯѧ���Ĳ�����ɼ�
	 */
	public List findFailForStudent(Integer year, Short term, String sid);

	/**
	 * <li>��������������ݡ�ѧ�ڡ��꼶���Ҳ�����ѧ����ȥ�أ�
	 */
	public List findFailStudent(Integer year, Short term, Integer grade, Long kdid);

	/**
	 * <li>�������������꼶��ѯ¼��γ̵���Ŀ
	 */
	public List findCourseNumber(Integer year, Short term, Integer grade, Long kdid);

	/**
	 * <li>������������ѯѧ�����������Ŀ
	 */
	public List findStudentFailNumber(Integer year, Short term, String sid, Integer grade);

	/**
	 * <li>������������ѯĳѧԺ�Ѿ�¼��Ŀγ�
	 */
	public List findCourseKeyIn(Integer year, Short term, Long kdid);

	/**
	 * <li>������������ѯĳ�γ̵ĳɼ��б�
	 */
	public List findCourseScore(Integer year, Short term, String jcNo, Long kdid);

	/**
	 * <li>������������ѯĳ�γ̵Ĳ������б�
	 */
	public List findCourseFail(Integer year, Short term, String jcNo, Long kdid);

	/**
	 * <li>������������ѯͬ�����µĿγ��б�
	 */
	public List findCourseByType(Integer year, Short term, String jctid);

	/**
	 * <li>������������ѯĳͬѧͬ��ͬѧ�ɼ��б�
	 */
	public List findSameClassScore(String jsCid, String jsSid);

	/**
	 * <li>������������ѯĳ�꼶ͬѧ�ɼ��б�
	 */
	public List findSameGradeScore(String jsCid, List claname);

	/**
	 * <li>������������ѯ�α���ѧͬһ�ſγ̵İ༶
	 */
	public List findClassFromCourseTable(String year, Short term, String jcno);

	/**
	 * <li>������������ѯָ�����ŵ�����ѧ��
	 */
	public List findAllStudent(Integer year, Short term, String kdnum);

	/**
	 * <li>������������ѯĳ��ʱ��ε�ѧ���ɼ�
	 */
	public List findScoreInPeriod(String jsSid, Integer fromTime, Integer toTime);

	/**
	 * <li>������������ѯĳ��ʱ��ε�Уѡ��
	 */
	public List findbySidfromtimeandendtime(String jsSid, Integer fromTime, Integer toTime);
	
	public List findScoreInPeriod( Integer fromTime, Integer toTime,Long claid);
	/**
	 * <li>��������������ѧ�Ų�ѯ��ѧ���ĳɼ�
	 */
	public List findByStid(String stid);
	/**
	 * ��ѯʵ�ʲμӿ��Ե�
	 * @param year
	 * @param term
	 * @param jcno
	 * @param jctclass
	 * @return
	 */
	public List findByYearAndTermAndJcnoAndClass(Integer year, Short term, String jcno,List jctclass);
	
	/**2011-4-22
	 * @param year
	 * @param term
	 * @param cid
	 * @param kdid
	 * @param studentno 
	 * @return
	 */
	public JxScore findByCidKdid(Integer year,Short term,String cid,String kdid, String studentno);
}
