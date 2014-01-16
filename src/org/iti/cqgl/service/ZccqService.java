package org.iti.cqgl.service;

import java.util.Date;
import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface ZccqService extends BaseService {

	

	/**
	 * <li>功能描述:根据学年学期学校id查询
	 * @param kuid
	 * @param year
	 * @param term
	 * @param schid
	 * @return
	 */
	public List findBykuIdAndYearAndTermAndSchid(Long kuid,String year,short term,Long schid);
	/**
	 * <li>功能描述:根据学年学期学校id,学院、专业、班级、学号、姓名等条件查询出勤信息
	 * @param year
	 * @param term
	 * @param schid
	 * @param xyid
	 * @param zyid
	 * @param cla
	 * @param sid
	 * @param sname
	 * @return
	 */
	public List findByYearAndTermAndschidAndXyidAndZyidAndGradeAndClassAndSidAndName(String year,short term,Long schid,Long xyid,Long zyid,Integer grade,String cla,String sid,String sname);
	
	/**
	 * <li>功能描述:查询某一个学生用户本学期出勤次数
	 * @param kuid
	 * @param xlid
	 * @return
	 */
	public Integer findByKuidAndXlid(Long kuid,Long xlid);
	
	/**
	 * <li>功能描述:查询某一个学生用户本学期出勤详情
	 * @param kuid
	 * @param xlid
	 * @return
	 */
	public List findByKuIdAndXlId(Long kuid,Long xlid);

	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param xyid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findHgrenshu(String year,Short term,Long schid,Long xyid,Integer[] grade,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param zyid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findZyHgrenshu(String year,Short term,Long schid,Long zyid,Integer[] grade,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param classid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findClassHgrenshu(String year,Short term,Long schid,Long classid,Integer[] grade,Long zczscs);
	public List findByClassHgrenshu(String year,Short term,Long schid,Long classid,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param xyid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findWhgrenshu(String year,Short term,Long schid,Long xyid,Integer[] grade,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param zyid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findZyWhgrenshu(String year,Short term,Long schid,Long zyid,Integer[] grade,Long zczscs);
	/**
	 * 
	 * @param year
	 * @param term
	 * @param schid
	 * @param classid
	 * @param grade
	 * @param zczscs
	 * @return
	 */
	public List findClassWhgrenshu(String year,Short term,Long schid,Long classid,Integer[] grade,Long zczscs);
	public List findByClassWhgrenshu(String year,Short term,Long schid,Long classid,Long zczscs);
	
	/**
	  * <li>功能描述：根据用户id和日期判断该用户是否已经有出勤信息
	  * @param kuid
	  * @param time
	  * @return
	  */
	   public  boolean checkIfExist(Long kuid,Date time);
	   /**
	    * 
	    * @param year
	    * @param term
	    * @param schid
	    * @param kuid
	    * @return
	    */
	   public List findbykuidAndyearAndterm(String year,Short term,Long schid,Long kuid);
	   /**
	    * 
	    * @return
	    */
	   public Long findMaxctZid();
	   /**
	    * 
	    * @param kuid
	    * @param time
	    * @return
	    */
	   public List findbyKuidAndLiketime(Long kuid,String time);
	   /**
	    * 
	    * @param year
	    * @param term
	    * @param schid
	    * @param classid
	    * @param grade
	    * @return
	    */
	   public List findClassStuWhg(String year,Short term,Long schid,Long classid,Integer[] grade);
	   public List findByClassStuWhg(String year,Short term,Long schid,Long classid);
	   /**
	    * 
	    * @param year
	    * @param term
	    * @param schid
	    * @param zyid
	    * @param grade
	    * @return
	    */
	   public List findZystuWhg(String year,Short term,Long schid,Long zyid,Integer[] grade);
	   /**
	    * 
	    * @param year
	    * @param term
	    * @param schid
	    * @param xyid
	    * @param grade
	    * @return
	    */
	   public List findstuWhgrenshu(String year,Short term,Long schid,Long xyid,Integer[] grade);
	   /**
	    * 
	    * @param kdid
	    * @return
	    */
	   public Integer findXynianzhi(Long kdid);
	   /**
	    * 
	    * @param kdid
	    * @return
	    */
	   public Integer findZynianzhi(Long kdid);
	   /**
	    * 
	    * @param kdid
	    * @return
	    */
	   public Integer findClassnianzhi(Long clId);
	   /**
	    * 查询某一时间区间内出勤情况
	    * @param start starttime 开始时间
	    * @param end endtime 结束时间
	    * @param kdid 学院id或专业id
	    * @return
	    */
	   public List findByStartAndEndAndKdid(Date start,Date end,Long kdid);
	   /**
	    * 查询某一时间区间内某以班级学生出勤情况
	    * @param Start
	    * @param end
	    * @param clid
	    * @return
	    */
	   public List findByStartAndEndAndClid(Date Start,Date end,Long clid);
	   /**
	    * 查询某一时间区间内某一年级的学生出勤情况
	    * @param start
	    * @param end
	    * @param kdid
	    * @param grade
	    * @return
	    */
	   public List findByStartAndEndAndKdidAndGrade(Date start,Date end,Long kdid,Integer grade);
	   
}
