package org.iti.jxgl.service;

import java.util.List;

import org.iti.jxgl.entity.JxScore;

import com.uniwin.basehs.service.BaseService;

public interface ScoreService extends BaseService {
	/**
	 * <li>功能描述：根据学号和课程号查找学生成绩
	 */
	public JxScore findBySidAndCid(String jsSid, String jsCid, Integer jsYear, Short jsTerm);

	/**
	 * <li>功能描述：根据学号和课程号查找教师
	 */
	public List findTeacherBySidAndCid(String sid, Long cid, String year, Short term);

	/**
	 * <li>功能描述：查找学院编号
	 */
	public List findStudentCollege(String sid);

	/**
	 * <li>功能描述：查找专业编号
	 */
	public List findStudentMajor(String sid);

	/**
	 * <li>功能描述：查询部门列表
	 */
	public List findDeptList(Long kdid);

	/**
	 * <li>功能描述：查询课程列表
	 */
	public List findLessonList(Long kdid);

	/**
	 * <li>功能描述：查询教师列表
	 */
	public List findTeacherlist(Long kdid);

	/**
	 * <li>功能描述：查询班级列表
	 */
	public List findClasslist(Long kdid);

	/**
	 * <li>功能描述：查询属于某个年级的班级列表---杜轻
	 */
	public List findClassgradelist(Long kdid, Integer grade);

	/**
	 * <li>功能描述：查询某部门某年级的学生考试人数
	 */
	public List findStudentInScoreByGrade(Integer year, Short term, Long kdid, Integer grade);

	/**
	 * <li>功能描述：查询某部门某年级的学生总数
	 */
	public List findStudentInDept(Long kdid, Integer grade);

	/**
	 * <li>功能描述：根据学号查找学生成绩
	 */
	public List findBySid(String jsSid, Integer jsYear, Short jsTerm);

	/**
	 * <li>功能描述：查询同一门课程有几位老师上课
	 */
	public List findTeacherBySameCourse(Integer year, Short term, String jcno, Long kdid);

	/**
	 * <li>功能描述：根据年份、学期、教师、课程查询班级
	 */
	public List findClassByTeacherAndCourse(String year, Short term, Long kuid, String jcno, Long kdid);

	/**
	 * <li>功能描述：查询教师教的所有班的成绩列表
	 */
	public List findScoreByClassAndCourse(Integer year, Short term, String jcno, List clalist);

	/**
	 * <li>功能描述：查询指定范围的成绩列表
	 */
	public List findScorelist(String jcno, Double max, Double min, List scorelist);

	/**
	 * <li>功能描述：查询指定教师所教的课程
	 */
	public List findCourseForTeacher(Integer year, Short term, Long kuId, Long kdid);

	/**
	 * <li>功能描述：查询指定教师所教课程的各班
	 */
	public List findClassByJcNoAndKuId(String year, Short term, String jcNo, Long kuId, Long kdid);

	/**
	 * <li>功能描述：查询班级中的某门课程的成绩
	 */
	public List findCourseScoreInClass(Integer year, Short term, String jcNo, String claname);

	/**
	 * <li>功能描述：查询任务书中的某班的年份记录
	 */
	public List findYearForClass(String claname);

	/**
	 * <li>功能描述：查询某年份某班的学期记录
	 */
	public List findTermForClass(String year, String claname);

	/**
	 * <li>功能描述：查询班级中的所有学生的成绩
	 */
	public List findAllStuScoreInClass(Integer year, Short term, String claname);

	/**
	 * <li>功能描述：按年份、学期、专业查找不及格课程（去重）
	 */
	public List findFailCourse(Integer year, Short term, String kdnumber);

	/**
	 * <li>功能描述：按年份、学期、专业、课程查找成绩列表
	 */
	public List findScoreByDeptAndCourse(Integer year, Short term, String jcno, String kdnum);

	/**
	 * <li>功能描述：按年份、学期、专业、课程查找不及格列表
	 */
	public List findFailByDpetAndCourse(Integer year, Short term, String jcno, String kdnum);

	/**
	 * <li>功能描述：查询学生的不及格成绩
	 */
	public List findFailForStudent(Integer year, Short term, String sid);

	/**
	 * <li>功能描述：按年份、学期、年级查找不及格学生（去重）
	 */
	public List findFailStudent(Integer year, Short term, Integer grade, Long kdid);

	/**
	 * <li>功能描述：按年级查询录入课程的数目
	 */
	public List findCourseNumber(Integer year, Short term, Integer grade, Long kdid);

	/**
	 * <li>功能描述：查询学生不及格的数目
	 */
	public List findStudentFailNumber(Integer year, Short term, String sid, Integer grade);

	/**
	 * <li>功能描述：查询某学院已经录入的课程
	 */
	public List findCourseKeyIn(Integer year, Short term, Long kdid);

	/**
	 * <li>功能描述：查询某课程的成绩列表
	 */
	public List findCourseScore(Integer year, Short term, String jcNo, Long kdid);

	/**
	 * <li>功能描述：查询某课程的不及格列表
	 */
	public List findCourseFail(Integer year, Short term, String jcNo, Long kdid);

	/**
	 * <li>功能描述：查询同性质下的课程列表
	 */
	public List findCourseByType(Integer year, Short term, String jctid);

	/**
	 * <li>功能描述：查询某同学同班同学成绩列表
	 */
	public List findSameClassScore(String jsCid, String jsSid);

	/**
	 * <li>功能描述：查询某年级同学成绩列表
	 */
	public List findSameGradeScore(String jsCid, List claname);

	/**
	 * <li>功能描述：查询课表中学同一门课程的班级
	 */
	public List findClassFromCourseTable(String year, Short term, String jcno);

	/**
	 * <li>功能描述：查询指定部门的所有学生
	 */
	public List findAllStudent(Integer year, Short term, String kdnum);

	/**
	 * <li>功能描述：查询某个时间段的学生成绩
	 */
	public List findScoreInPeriod(String jsSid, Integer fromTime, Integer toTime);

	/**
	 * <li>功能描述：查询某个时间段的校选课
	 */
	public List findbySidfromtimeandendtime(String jsSid, Integer fromTime, Integer toTime);
	
	public List findScoreInPeriod( Integer fromTime, Integer toTime,Long claid);
	/**
	 * <li>功能描述：根据学号查询该学生的成绩
	 */
	public List findByStid(String stid);
	/**
	 * 查询实际参加考试的
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
