package org.iti.jxgl.service;

import org.iti.jxgl.entity.JxCourse;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface CourseService extends BaseService {
	/**
	 * <li>通过开课单位查找课程列表
	 * 
	 * @author Chen Li
	 * @param year
	 * @param term
	 * @param kdid
	 * @return 课程列表 2010-7-11
	 */
	public List<JxCourse> FindCourseBykdId(Long kdid);

	/**
	 * <li>功能描述：根据课程ID号查找课程
	 * 
	 * @author zhanghlili
	 * @param kdid
	 * @return 课程名称
	 */
	public JxCourse FindCourseBykcbh(String kcbh);

	public JxCourse FindCourseBycid(Long cid);

	public JxCourse findByJcId(Long jcId);

	public JxCourse findByJcNo(String jcNo);

	public JxCourse findByJcbh(String jcbh);

	/**
	 * <li>功能描述：根据课程编号jc_id列表查找课程列表
	 * 
	 * @param idlist
	 * @return
	 */
	public List findByIdList(List idlist);
	public List findBySYIdList(List idlist);

	public Integer getMaxNum();

	public String getNum();

	/**
	 * <li>查找数据库表JX_COURSE中字段JC_BH中代表顺序号的最大号
	 * 
	 * @param kdid
	 * @return
	 */
	public Long checkDuplicate(JxCourse course);

	/**
	 * <li>查询课程表里是否有重复信息，若有则返回课程主键，若没有则返回0
	 * 
	 * @param jcid
	 * @return
	 */
	public JxCourse findByJcid(Long jcid);

	/**
	 * <li>根据导入课程表中的课程号查询课程编号
	 * 
	 * @param jcno
	 * @return
	 */
	public List findJcid();

	/**
	 * <li>将临时tablename数据库表中的课程信息导入到course数据库表，同时得到相应的培养计划记录
	 * 
	 * @return
	 */
	public List ImportCourseGetsch(Long schoolkdid, String rxyear, Long kdid);

	/**
	 * <li>功能描述：根据课程编号（列表）查找课程实体
	 * 
	 * @author zhangli
	 * @param jcid
	 * @return 课程实体列表
	 */
	public List findCourseByJcid(List jcid);

	/**
	 * <li>chenli <li>功能描述：根据任务书中的课程信息查找课程表中的课程id
	 */
	public Long findcidBytaskcourse(JxCourse course);

	public Long findcidBytaskcourseentity(JxCourse course);

	/**
	 * chenli <li>功能描述：根据任务书中的课程信息查找执行计划中相同的课程信息
	 * 
	 * @param course
	 * @return
	 */
	public List findsameCourse(JxCourse course);

	/**
	 * <li>功能描述：根据课程名称查找课程号
	 */
	public JxCourse findjcIdByjcName(String jcName);

	/**
	 * <li>功能描述：根据执行计划中的年份，学期，年级，部门编号，课程大类编号统计各类课程学分比例分配
	 * 
	 * @param year
	 * @param term
	 * @param Rxyear
	 * @param kdid
	 * @param ctype
	 * @return 课程性质，总学时，总学分，总上机学时，总授课学时，总实验学时
	 * @author FengXinghong 2010-8-8
	 */
	public List findStaByYearAndTermAndRxyearAndDidAndType(String year, short term, String Rxyear, Long kdid, String ctype);

	/**
	 * <li>功能描述：根据培养计划中的年级，部门编号，课程大类编号统计各类课程学分比例分配
	 * 
	 * @param year
	 *            年级
	 * @param kdid
	 *            部门编号
	 * @param ctype
	 *            课程大类编号
	 * @return 课程性质，总学时，总学分，总上机学时，总授课学时，总实验学时
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
	 * 通过课程名称查找任务书中存在的课程id
	 * 
	 * @param c
	 * @return
	 */
	public Long findcidBycname(String name);

	/**
	 * 通过课程名称jcid列表
	 * 
	 * @param c
	 * @return
	 */
	public List findJcidByCname(String name);
	public List findJcidByCname(String name,short type);

	/**
	 * <li>功能描述：根据课程编号（任务书中的课程编号）查找课程
	 * 
	 * @author zhangli
	 * @param taskcid
	 * @return 课程实体列表
	 */
	public List findCourseByTask(List taskcid);

	/**
	 * <li>功能描述：根据课程号和课程名查找课程
	 */
	public JxCourse findCourseByJcNoAndJcName(String jcNo, String jcName);
	/**
	 * 通过课程名称查找任务书中存在的课程id
	 * 
	 * @param c
	 * @return杜轻
	 */
	public List findcidlistBycname(String name);
	public List findBylistcid(List listcid);
	
	/**
	 * <li>功能描述：根据课程编号列表查找课程，排除实验课
	 * @author  lili
	 * @param jcid 课程编号列表
	 * @return 课程列表
	 */
	public List findCourByJcid(List jcid);
	
	/**
	 * <li>功能描述：根据学年，学期，组织部门编号，课程编号（可以为空）找试卷中的课程
	 * @param year学年，
	 * @param term 学期
	 * @param kdid 组织部门编号
	 * @param cid 课程编号（可以为空）
	 * @return 课程列表
	 */
	public List<JxCourse> findBypapercid(String year, Short term, Long kdid,Long  cid); 
 
	public List findCourByJcnoJcnameCrediType(String jcno,String jcname,Float xufen,short type);
 
}
