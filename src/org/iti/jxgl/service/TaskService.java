package org.iti.jxgl.service;

import java.util.List;

import org.iti.jxgl.entity.JxTask;
import org.iti.jxgl.entity.JxCourse;
import org.iti.jxgl.entity.JxTeachplan;

import com.uniwin.basehs.service.BaseService;

public interface TaskService extends BaseService {

	/**
	 * <li>功能描述：根据年份，学期，教师登录号、课程编号，学院编号查询任务书实体列表
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTT(String year, short term, Long kuid, Long jcid, Long kdid);

	/**
	 * <li>功能描述：根据年份，学期，教师号列表、授课计划列表查询任务书实体列表
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTTP(String year, short term, Long kdId, List thidlist, List planlist);

	/**
	 * <li>功能描述：根据年份，学期，kdid、filelist,授课计划列表查询任务书实体列表
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param kdid
	 * @param jcid
	 *            list不为空时
	 * @return
	 */
	public List FindTaskByYTTF(String year, short term, Long kdId, List filelist,List filelist1);

	/**
	 * <li>功能描述：根据年份，学期，kdid、filelist,授课计划列表查询任务书实体列表
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param kdid
	 * @param jcid
	 *            list为空时
	 * @return
	 */
	public List FindTaskByYTKd(String year, short term, Long kdId);
	
	/**
	 * <li>功能描述：根据年份，学期，教师号、班级查询任务书实体（想得到唯一的课程id）
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @param jtclass
	 * @return
	 */
	public JxTask FindTaskYTTC(String year, short term, Long kuId, String jtclass);

	/**
	 * <li>功能描述：根据年份，学期，教师号、合班情况查询任务书实体
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @param jtsumstate
	 * @return
	 */
	public JxTask FindTaskYTTS(String year, short term, String thid, Integer jtsumstate);

	/**
	 * <li>功能描述：根据年份，学期，教师号查询任务书实体列表
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public List<JxTask> FindTaskByYearTermThid(String year, short term, Long kuId, Long kdId);

	/**
	 * <li>功能描述：根据年份，学期，教师号查询任务书实体
	 * 
	 * @author XiaoMa
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public JxTask FindTaskEntity(String year, short term, String thid);

	/**
	 * <li>功能描述：根据年份，学期，教师登录号查询课程编号（不重复）
	 * 
	 * @author XiaoMa
	 * @param year 年份
	 * @param term 学期
	 * @param tid 教师登录号
	 * @return
	 */
	public List FindAllCourseId(String year, short term, Long thid);

	/**
	 * <li>功能描述：根据课程号查询教师编号（不重复）
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List FindByJcid(Long jcid, String year, short term, Long kdid);

	/**
	 * <li>功能描述：根据年份、学期、课程号、开课单位查询任务书列表 学生查看授课计划
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTKJ(String year, short term, Long kdid, Long jcid);

	/**
	 * <li>功能描述：根据年份、学期、课程号、开课单位查询任务书列表 学生查看授课计划
	 * 
	 * @author XiaoMa
	 * @param year,term,kdid,jcidlst
	 * @return
	 */
	public List FindTaskByYTKJlst(String year, short term, Long kdid, List jcidlst);
	/**
	 * <li>功能描述：根据年份、学期、课程号、开课单位\教师号 查询任务书列表 授课文件按课程合班
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTKJK(String year, short term, Long kdid, Long jcid,Long kuId);
	
	/**
	 * <li>功能描述：根据年份、学期、课程号、开课单位、班级名称查询任务书列表 学生查看授课计划
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTKJC(String year, short term, Long kdid, Long jcid, String jtClass, String jtClass1);

	/**
	 * <li>功能描述：根据执行计划号查询任务书列表
	 * 
	 * @author XiaoMa
	 * @param jeid
	 * @return
	 */
	public List<JxTask> FindTaskByJeid(Long jeId);

	/**
	 * <li>通过年份学期部门查 找任务书
	 * 
	 * @author Chen Li
	 * @param year
	 * @param term
	 * @param kdid
	 * @return 任务书列表 2010-7-11
	 */
	public List<JxTask> FindTaskByYTK(String year, short term, Long kdid);

	/**
	 *<li>功能描述： 查询任务书中所有的课程编号
	 * 
	 * @param kdid
	 * @return
	 */
	public List FindAllCourseFromTask(Long kdid);

	/**
	 * <li>功能描述：查询任务书中某学年某学期所有的课程编号
	 * 
	 * @param year
	 * @param term
	 * @param kdid
	 * @return
	 */
	public List FindCourseFromTask(String year, short term, Long kdid);
	/**
	 * <li>功能描述：查询任务书中某学年某学期所有的教师编号
	 * 杜轻
	 * @param year
	 * @param term
	 * @param kdid
	 * @return
	 */
	public List FindTeacheridFromTask(String year, short term, Long kdid);
	/**
	 * <li>功能描述：查询任务书中所有的教师编号
	 * 
	 * @param kdid
	 * @return
	 */
	public List FindTeacherFromTask(Long kdid);

	/**
	 * <li>功能描述：组合条件查询任务书
	 * 
	 * @param year
	 * @param term
	 * @param cid
	 * @param kuid
	 * @param deplist
	 * @return
	 */
	public List FindTask(String year, short term, Long cid, Long kuid, List deplist);

	/**
	 * 组合添加查询，如果用户不为空 ，cl
	 * 
	 * @param year
	 * @param term
	 * @param thid
	 * @param cid
	 * @param courseid
	 * @return
	 */
	public List findTaskByYearTermThidKdidcourseid(String year, short term, Long kuid, Long cid, Long courseid);

	/**
	 * 组合添加查询，如果用户不为空 ，课程编号为空xiaoma
	 * 
	 * @param year
	 * @param term
	 * @param thid
	 * 
	 * @param courseid
	 * @return
	 */
	public List findTaskByYearTermThid(String year, short term, String thid, Long cid);

	/**
	 * 组合条件查询，用户为空，选择全学院教师
	 * 
	 * @param year
	 * @param term
	 * @param cid
	 * @param courseid
	 * @return
	 */
	public List findByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid);
	
	
	/**
	 * 
	 * 
	 * @param year
	 * @param term
	 * @param cid
	 * @param courseid
	 * @return杜轻--管理工作量用到
	 */
	public List findByYearAndTermAndkdid2(String year, short term, Long cid, Long courseid);
	/**
	 * 组合条件查询，用户为空，选择某系教师
	 * 
	 * @param year
	 * @param term
	 * @param teacherlist
	 * @param cid
	 * @param courseid
	 * @return
	 */
	public List findByYearAndTermAndTKuidAndSchid(String year, short term, Long kuid, Long kdid);
	public List findByYearAndTermAndTeacherlistAndCourseid(String year, short term, Long cid, List teacherlist, List courseidlist);

	/**
	 * <li>功能描述：组合条件查询任务书
	 * 
	 * @param year
	 * @param term
	 * @param cid
	 * @param kuid
	 * @param deplist
	 * @return
	 */
	public List FindTaskBySum(String year, short term, Long kdid, List tasklist);

	/**
	 * <li>功能描述：查询合班情况相同的任务书列表
	 * 
	 * @param sumstate
	 * @return
	 */
	public List FindTaskBySumState(int sumstate);

	

	/**
	 * <li>功能描述:根据年份学期教师编号查询该教师任务书
	 * 
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public List findByYearAndTermAndTid(String year, short term, String tid);

	/**
	 * <li>功能描述:根据年份、学期、教师登录号、课程编号查询该教师任务书
	 * 
	 * @param year
	 *            XiaoMa
	 * @param term
	 * @param tid
	 * @param jcid
	 * @return
	 */
	public List findByYearTermTidCid(String year, short term, Long thid, Long jcid);

	/**
	 * <li>功能描述： 根据年份、学期、专业列表查找任务书
	 * 
	 * @param year
	 * @param term
	 * @param deplist
	 * @return
	 */
	public List findByDeplistAndYearAndTerm(String year, short term, List deplist);

	/**
	 * <li>功能描述： 根据专业列表查找教师编号列表
	 * 
	 * @param deplist
	 * @return
	 */
	public List FindTeacherBydeplist(List deplist);

	/**
	 * 
	 * <li>功能描述：根据学年、学期查询任务书 所有的 课程编号
	 * 
	 * @author zhangli
	 * @param year
	 *            学年
	 * @param term
	 *            学期
	 * @return 课程编号列表
	 */
	public List findjcByYearAndTerm(String year, short term);

	/**
	 * <li>功能描述:根据年份、学期、教师编号、学院编号查询除该教师以外的教师任务书的合班情况
	 * 
	 * @author liwei
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public List findByYearAndTermAndkdidAndT(String year, short term, Long kdid, Long kuid);

	/**
	 * <li>功能描述:根据年份、学期、教师编号、学院编号查询该教师任务书的合班情况
	 * 
	 * @author liwei
	 * @param year
	 * @param term
	 * @param tid
	 * @return
	 */
	public List findtaskbyyearAndtermAndtAndkdid(String year, short term, Long kuid, Long kdid);

	/**
	 * <li>功能描述：根据学年、学期、课程编号查找任务书
	 * 
	 * @author zhangli
	 * @param year学年
	 * @param term学期
	 *            *@param Cid课程编
	 * @return 任务书（教师编号和组织单位）列表
	 */
	public List findTaskByYearAndTermAndCid(String yaer, Short term, Long jcid);

	/**
	 * <li>功能描述：根据学年，学期，课程id查询教这门课的任务书
	 * 
	 * @param year
	 *            学年
	 * @param term
	 *            学期
	 * @param jcid
	 *            课程id
	 * @return 任务书列表
	 * @author FengXinhong 2010-7-18
	 */
	public List findThidByYearAndTermAndJcId(String year, short term, Long jcid);

	/**
	 * <li>功能描述：根据学年，学期、jcid、kdid查询任务书 XiaoMa
	 * 
	 * @param year
	 * @param term
	 * @return
	 */
	// public List findThidByYearTermJcIdKdid(String year, short term, Long jcid,Long kdid);
	/**
	 * <li>功能描述：根据学年，学期查询任务书
	 * 
	 * @param year
	 * @param term
	 * @return
	 */
	public List findByYearAndTerm(String year, short term);

	/**
	 * <li>功能描述：查找临时任务表中所有记录
	 * 
	 * @param year
	 * @param term
	 * @return
	 */
	public List findAllTemptask();

	/**
	 * <li>功能描述： 根据年份、学期、专业列表查找课程编号 * * @author duqing
	 * 
	 * @param year
	 * @param term
	 * @param deplist
	 * @return
	 */
	public List FindCourseFromTask(String year, short term, List deplist,Long kuid);

	/**
	 * <li>功能描述： 根据年份、学期、专业列表查找不同的合班 *
	 * 
	 * @author XiaoMa
	 * 
	 * @param year
	 * @param term
	 * @param deplist
	 * @return
	 */
	public List FindSumstateFromTask(String year, short term, List deplist);

	/**
	 * <li>功能描述： 根据年份、学期、专业列表查找不同的课程 *统计授课课件上传情况用
	 * 
	 * @author XiaoMa
	 * 
	 * @param year
	 * @param term
	 * @param deplist
	 * @return
	 */
	public List FindJcidFromTask(String year, short term, Long kdid);

	/**
	 * <li>功能描述：根据课程编号查找任务书中课程的考试类别（考试/考查） * @author duqing
	 * 
	 * @param year
	 *            课程编号
	 * @return 任务书列表
	 */

	public List findCidinTaskNotinExecute(String year, short term, Long kdid, Long fromid, String eyear, short eterm, List deplist);

	/**
	 * <li>功能描述：查询任务书中存在但在学校所有系执行计划中开课单位为本学院不存在的课程信息
	 */
	public List findCidinExecuteNotinTask(List deplist, String eyear, short eterm, List cidlist, Long kdid);

	/**
	 * <li>功能描述：根据学年、学期、教师登录号、课程编号、学院编号查找任务书实体
	 * 
	 * @author zhangli
	 * @param year
	 *            学年
	 * @param term
	 *            学期
	 * @param thid
	 *            教师登录号(可以为空)
	 * @param cid
	 *            课程编号
	 * @return 任务书实体列表
	 */
	public List findTaskByYearTermThidCid(String year, Short term, Long thid, Long cid,Long kdid);

	/**
	 * <li>功能描述：根据学年 学期查找任务书（找课程编号）
	 * 
	 * @author zhangli
	 * @param y
	 *            学年
	 * @param t
	 *            学期
	 * @return 课程编号列表
	 */

	public List findJcidByYearTern(String y, Short t);

	/**
	 * <li>功能描述：根据课程编号查找任务书中课程的考试类别（考试/考查） * @author duqing
	 * 
	 * @param year
	 *            课程编号
	 * @return 任务书列表
	 */

	/**
	 * <li>功能描述：根据执行计划编号查询任务书
	 * 
	 * @param jeid
	 *            执行计划编号
	 * @return 任务书列表
	 */
	public JxTask FindFromExectution(Long jeid);

	/**
	 * <li>功能描述：根据学年 学期 课程编号查找任务书
	 * 
	 * @param y
	 *            学年
	 * @param t
	 *            学期
	 * @param cid
	 *            课程编号
	 * @return 任务书实体列表
	 */

	public List findTaskByYearTermJcid(String y, Short t, Long cid);

	public List FindClassByYearAndTermAndCid(String year, short term, Long cid,Long kdid);

	/**
	 * <li>功能描述：根据课程编号查找任务书中课程的考试类别（考试/考查） * @author duqing
	 * 
	 * @param year
	 *            课程编号
	 * @return 任务书列表
	 */
	public JxTask FindTypeFromTask(Long cid);

	/**
	 * <li>功能描述：根据kdid、sumstate查询任务书
	 * 
	 * @param kdid
	 *            sumstate XiaoMa
	 * 
	 * @return 任务书列表
	 */
	public List<JxTask> FindTaskByKdidSumstate(Long kdid, Integer jtsumstate);

	/**
	 * <li>功能描述：判断与某个任务书中合班情况一致的任务书是否已在列表中
	 * 
	 * @param task
	 * @param tlist
	 * @return
	 * @author FengXinhong
	 */
	public boolean CheckIfInTasklist(JxTask task, List tlist);

	/**
	 * <li>功能描述：根据kdid、sumstate查询任务书
	 * 
	 * @param kdid
	 *            sumstate XiaoMa
	 * 
	 * @return 任务书列表
	 */
	public List<JxTask> FindTaskBySumstate(Integer jtsumstate);

	/**
	 * <li>功能描述：根据学年、学期、教师登录号、学院编号查找任务书
	 * 
	 * @author zhangli 2010-8-3
	 * @param year
	 *            学年
	 * @param term
	 *            学期
	 * @param t
	 *            教师登录号
	 * @param kdid
	 *            学院编号
	 * @return 课程编号列表
	 */
	public List findTaskByYearTermTidKdid(String year, short term, Long t, Long kdid);

	/**
	 * <li>功能描述：查询是否已经导入过这个专业的任务书
	 */
	public List FindLeadInOrNot(String year, short term, Long kdid);

	/**
	 * <li>功能描述：查询某个学生上的课程
	 */
	public List FindCourseForStu(String year, short term, Long kuid);
	
	
	/**
	 * <li>功能描述：根据年份，学期，教师号,课程名称查询任务书实体
	 * 
	 * @author 贺亚
	 * @param year
	 * @param term
	 * @param tid
	 * * @param cid
	 * @return
	 */
	public JxTask FindTasks(List cids,String year, short term, String thname,Long kdid);
	/**
	 * <li>功能描述：根据课程名称查询课程id
	 * 
	 * @author 贺亚
	 
	 * * @param cname
	 * @return
	 */
	public  List fidjcids(String cname);
	/**
	 * <li>功能描述：根据年份，学期，课程id串，班级查询任务书实体
	 * 
	 * @author 贺亚
	 * @param year
	 * @param term
	 * @param class
	 * * @param cids
	 * @return
	 */
	public JxTask skjhfindtask(List cids,String year, short term, String classname,Long kdid);
	/**
	 * <li>功能描述：根据年份，学期，课程id串，班级,学校所含学院查询任务书实体
	 * 
	 * @author 贺亚
	 * @param year
	 * @param term
	 * @param class
	 * * @param cids
	 * @return
	 */
	public JxTask skjhfindtask(List cids,String year, short term, String classname,List kdid);
	/**
	 * <li>功能描述：根据年份，学期，学院查询任务书实体
	 * 
	 * @author 贺亚
	 * @param year
	 * @param term
	 * @param class
	 * * @param cids
	 * @return
	 */
	public List<JxTask> skwjfindtask(String year, short term, List kdid);
	/**
	 * <li>功能描述：根据年份，学期，学院,教师编号查询教师课程列表
	 * 
	 * @author 贺亚
	 * @param year
	 * @param term
	 * @param kdid
	 * * @param  kuid
	 * @return
	 */
	public List<JxCourse> paperfindcourse (String year, short term, Long kdid,Long kuid);
	/**
	 * <li>功能描述：根据学年、学期、教师登录号、课程编号、学院编号查找任务书实体
	 * 
	 * @author zhangli
	 * @param year
	 *            学年
	 * @param term
	 *            学期
	 * @param thid
	 *            教师登录号(可以为空)
	 * @param cid
	 *            课程编号
	 * @return 任务书实体列表
	 */
	public List findTaskByYearTermThidCidcname(String year, Short term, Long thid, Long cid,Long kdid,String claname);

	
	/**
	/**
	 * <li>功能描述：根据年份、学期、课程号、开课单位、班级名称查询任务书列表 学生查看授课计划
	 * 
	 * @author XiaoMa
	 * @param jcid
	 * @return
	 */
	public List<JxTask> FindTaskByYTKJC(String year, short term, Long kdid,  String jtClass, String jtClass1);
	
	/**
	/**
	 * <li>功能描述：根据年份、学期、授课单位、教师id查询课程实体
	 
	 */
	public List<JxCourse> findcourselist(String year, short term,Long kdid,Long kuid);
	/**
	/**
	 * <li>功能描述：根据年份、学期、授课单位、教师id查询课程实体
	 
	 */
	public List<JxCourse> findbyYTK(String year, short term,Long kdid);
	/**
	/**
	 * <li>功能描述：根据年份、学期、授课单位、教师id查询合班状态
	 
	 */
	public List findtasklist(String year, short term,Long kdid,Long kuid);
	/**
	/**
	 * <li>功能描述：根据年份、学期、授课单位、教师id、课程编号查询合班状态
	 
	 */
	public List findtasklist(String year, short term,Long kdid,Long kuid,Long jcid);
	/**
	/**
	 * <li>功能描述：根据合班状况查询任务书实体
	 
	 */
	public List<JxTask> findtaskbysumstate(int sumstate);
	
	public List findByYearAndTermAndTeacherlistAndCourseid1(String year, short term, Long cid, List teacherlist, Long courseid);

	public List findTaskByYearTermCidThname(Long jcid,String tname,Integer jtsumstate );
	/**
	/**
	 * <li>功能描述：查询学生查询应上实验课程列表
	 
	 */
	public List<JxCourse> findtaskbyYTKU(String year, short term,Long kuid);

    /**
     * <li>根据学年学期课程号班级名称学院编号2011-4-22
     * @param year 学年
     * @param term 学期
     * @param cid 课程号
     * @param classname 班级名称
     * @param kdid 学院编号
     * @return 任务书对象
     */
    public  JxTask findTaskByClassname(String year, short term,Long cid, String classname,Long kdid);
    /**
     * 
     * @param year
     * @param term
     * @param schid
     * @param type
     * @param claname
     * @return
     */
    public List findByYearAndTermAndKdidAndVAndClass(String year, short term, Long schid,String type,String claname);
}
