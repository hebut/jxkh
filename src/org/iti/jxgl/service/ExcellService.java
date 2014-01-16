package org.iti.jxgl.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface ExcellService extends BaseService {
	/**
	 * 将起止周转化为01形式
	 * @param week
	 * @return
	 */
	
     public String changeweekToweeks(String week);
     /**
      * 找到任务书中合班情况最大的数
      * @return
      */
     public Integer findMaxSumstate();
     /**
      * 找到学生学院任务书中合班情况最大的数
      * @return
      */
     public Integer findStudentMaxSumstate();
     /**
      * 判断任务书导入格式及表头是否正确；
      * @param filename
      * @param years
      * @param term
      * @param IsIni
      * @return
     * @throws IOException 
     * @throws FileNotFoundException 
      */
     public String is_format(String filename,List btlist) throws FileNotFoundException, IOException;
     /**
      * 将临时任务书中的数据全部删除
      */
     public void deletetemptask();
     public void deletetempSche(Long kdid);
     
    ////////////后来改的
     public void deletetemptask2();
    // public List findAllTemptask();
     /**
      * 查找临时表2中的某个学院的所有记录
      */
     public List findTemptask2Bykdid(Long kdid);
     
     /**
      * 查找临时表3中的某个学院的所有记录
      */
     public List findTemptaskBykdid(Long kdid);
     /**
      * 查找任务书中有合班的任务书列表
      * @param year
      * @param term
      * @param cid
      * @param courseid
      * @return
      */
     public List findSumByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid);
     /**
      * 查找任务书中无合班的任务书列表
      * @param year
      * @param term
      * @param cid
      * @param courseid
      * @return
      */
     public List findNoSumByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid);
     
     /**
      * 查找临时任务书4中的学生学院任务书
      * @param kdid
      * @return
      */
     
     public List findTemptask4Bykdid(Long kdid);
     /**
      * 查找学生学院任务书中授课单位为某个学院的所有课程信息
      * @param fromid
      * @param year
      * @param term
      * @param kdid
      * @return
      */
     
     public List findCoursefromTaskByfromid(String year,short term,Long kdid,Long fromid);
     /**
 	 * <li>通过年份学期部门查 找任务书
 	 * 
 	 * @author Chen Li
 	 * @param year
 	 * @param term
 	 * @param kdid
 	 * @return 任务书列表 2010-7-11
 	 */
 	public List FindTaskByYTK(String year, short term, Long kdid);
 	/**
	 * <li>功能描述：查询学生学院任务书中某学年某学期所有的课程编号cl
	 * 
	 * @param year
	 * @param term
	 * @param kdid
	 * @return
	 */
	public List FindCourseFromTask(String year, short term, Long kdid);
	/**
	 * 查找学生任务书中已有合班的列表
	 * @param year
	 * @param term
	 * @param kdid
	 * @param fromid
	 * @return
	 */
	public List findSum(String year, short term, Long kdid,Long fromid);
	/**
	 * 查找学生任务书中没有合班的列表
	 * @param year
	 * @param term
	 * @param kdid
	 * @param fromid
	 * @return
	 */
	public List findNotSum(String year, short term, Long kdid,Long fromid);
	/**
	 * <li>功能描述：查询合班情况相同的任务书列表
	 * 
	 * @param sumstate
	 * @return
	 */
	public List FindTaskBySumState(int sumstate);
	
	public List Findbkmd(Integer year,short term,Long kdid);
	public List FindbyYTKD(String year,short term,Long kdid);
	public List FindbyYTSidCname(Integer year,short term,String sid,String cname);
     

}
