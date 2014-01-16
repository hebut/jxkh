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
	 * <li>通过用户编号查找学生
	 * 
	 * @author zhangxue
	 * @param kuid
	 * @return 学生 2010-7-12
	 */
	public Student findByKuid(Long kuid);

	/*
	 * 根据部门编号和届数查找学生列表 kdid 部门编号 sgrade 届数 lys
	 */

	public List findByKdidAndSgrade(Long kdid, Integer stGrade);

	/*
	 * 根据部门编号和届数查找学生列表 kdid 部门编号 sbynf 毕业年份 lys
	 */

	public List findByKdidAndSbynf(Long kdid, Integer stBynf);

	/**
	 * 根据部门编号和届数查找没有参加毕设的学生
	 * @param kdid
	 *            部门编号
	 * @param stGrade
	 *            届数
	 * @return
	 */
	public List findbyXueyuan(Long kdid, Integer stGrade);

	/**
	 * <li>功能描述：查询某些部门下某年级的学生列表。
	 * 
	 * @param dlist
	 *            某些部门
	 * @param rid
	 *            学生
	 * @param garde
	 *            学生年级
	 * @return List
	 * @author DaLei
	 */
	public List findBydeplistAndrid(List dlist, Long rid, Integer grade);

	/**
	 * <li>功能描述：按照条件某些部门下某年级的学生列表。
	 * 
	 * @param dlist
	 *            某些部门
	 * @param rid
	 *            学生
	 * @param garde
	 *            学生年级
	 * @param tname
	 *            姓名
	 * @param tno
	 *            学号
	 * @return List
	 * @author DaLei
	 */
	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno);

	/**
	 * <li>功能描述：查询某班的学生列表
	 * 
	 * @return
	 */
	public List findByClass(String stClass);

	/**
	 * <li>功能描述：根据学号查询学生姓名
	 * 
	 * @return
	 */
	public List findSnameByStid(String stId);

	/**
	 * <li>功能描述：根据学号查询学生信息
	 * 
	 * @return
	 */
	public Student findClassByStid(String stId);

	/**
	 * <li>功能描述：获得年级列表
	 * 
	 * @return
	 */
	public List findGrade();

	/**
	 *<li>功能描述：按照班级名称查询该班级的学生列表
	 * 
	 * @param clist
	 *            班级id列表
	 * @return 学生列表
	 * @author FengXinhong 2010-7-27
	 */
	public List findByClass(List clist);

	public Long countByClass(Long clid);

	/**
	 * 查询某学号学生列表
	 * 
	 * @param sid
	 * @param kdid
	 * @return
	 */
	public List findBySid(String sid);

	public void updateStudentClass(XyClass xyClass);

	/**
	 * 
	 * <li>功能描述：根据班级编号查找属于某一班的全部学生
	 * 
	 * @author zll
	 * @param clid
	 *            班级编号
	 * @return 学生列表
	 */
	public List findStuByClid(Long clid);

	/**
	 * <li>功能描述：根据部门编号和教师用户编号，查找教师所带的全部学生
	 */
	public List findStuByKdId(Long kdid);

	public List findClassByBsid(Long bsid);

	public List findMajorByBsid(String claname);
	

	/**
	 * 
	 * <li>功能描述：根据专业id,学年，学期查找该专业学生非免勤的学生
	 * @param kdid
	 * @param year
	 * @param term
	 * @return
	 */
	public List findNoMxStu(Long kdid,String year,Short term);
	
	/**
	 *  <li>功能描述：学年、学期、学校、专业、年级、班级、学号、姓名查找非免勤学生列表
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
	 * <li>功能描述：学年、学期、学院id,专业id、年级、班级、学号、姓名查找非免勤学生列表
	 * @param year
	 * @param term
	 * @param kdid 学校id
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
	 * <li>功能描述：学年、学期、学院id,专业id、年级、班级、学号、姓名查找非免勤学生列表
	 * @param year
	 * @param term
	 * @param kdid 学校id
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
	 *  <li>功能描述：学年、学期、学院id,专业id、年级、班级、学号、姓名查找某辅导员所带班级的非免勤学生列表
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
	 * <li>功能描述：查询免修学生中成绩在某一个区间的学生列表
	 * @param xlid 校历
 	 * @param stulist 免修学生列表
	 * @param small 早操出勤大于的次数
	 * @param big 早操出勤小于的次数
	 * @param small1 课余活动大于的次数
	 * @param big1 课余活动小于的次数
	 * @return 满足条件的学生列表
	 */
	public List findHgStuInStulist(Long xlid,List stulist,Long small,Long big,Long small1,Long big1);
	/**
	 *<li>功能描述：按照班级名称查询该班级的学生列表
	 * 
	 * @param cid:班级id
	 * @return 学生列表
	 * @author heya
	 */
	public List heyafindstu(Long  cid);
	//查找某个班级内没有在某个教学日志的考勤表中的数据
	public List findBysclassAndRzidAndTypeNotInKq(String sclass,Long rzid,Short type);
	/**
	 *<li>功能描述：按照多个班级名称---班级LIST--查询--学生列表
	 * 
	 * @return 学生列表
	 * @author duqing
	 */
	public List findByclasslist(List classlist);

	/**
	 *<li>功能描述：根据班级名称，姓名，学号查询--学生列表
	 * @return 学生列表
	 * @author duqing
	 */
	public List findByClassandNameandXno(String classname,String xname,String xsno);
	/**
	 *<li>功能描述：根据班级名称("-请选择-")，姓名，学号查询--学生列表
	 * @return 学生列表
	 * @author duqing
	 */
	public List findByClassandNameandXno(List classlist,String xname,String xsno);
	
	public List findByCidandSidandSname(Long clid,String sid,String sname);
	public List findBycaidAndClidAndTypeNotInXszc(Long clid,Long caid,Short type);
	 /**
	  * <li>查找某个学院的所有学生
	  * @param kdid 学院编号
	  * @return 学生列表
	  * 2011-2-28
	  */
	public List findXyAllStudent(Long kdid);
	/**
	 * <li>查找某个辅导员所带某个学校的所有学生
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
     * <li>根据学号查询专业，导入成绩时用，2011-4-22
     * @author zhangli
     * @param number
     * @return 组织部门
     */
    public WkTDept finddeptByStudentno(String number);
    /**
     * 分页 辅导员查询早操出勤信息
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
   * 分页 学校用户和学院领导查询出勤信息
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
