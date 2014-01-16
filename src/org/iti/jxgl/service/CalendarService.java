package org.iti.jxgl.service;

import java.text.ParseException;
import java.util.List;

import org.iti.jxgl.entity.JxCalendar;

import java.util.Date;
import org.iti.jxgl.entity.JxCalendar;
import com.uniwin.basehs.service.BaseService;

public interface CalendarService extends BaseService {
	/**
	 * <li>功能描述：查询校历
	 * 
	 * @param year
	 * @param term
	 *            * @author 李伟
	 * @return starttime(开始时间)
	 */
	public List findCalendar(String year, Short term,Long kdid);

	/**
	 * <li>功能描述：根据学年学期查找校历
	 * 
	 * @param cyear
	 *            学年
	 * @param term
	 *            学期编号（1代表春季 2 代表秋季）
	 * @return
	 */
	public JxCalendar findByCyearAndTermAndKdid(String cyear, Short term,Long kdid);

	/**
	 * <li>功能描述：根据校历判断当前时间属于校历中的哪年份和学期
	 * 
	 * @return 年份、学期
	 */
	public String[] getNowYearTerm();

	/**
	 * <li>功能描述：查询周历
	 */
	public List findWeekCalendar(String year, Short term, Long kdid);
	/**
	 * 查询某个班级的周历
	 * @param year
	 * @param term
	 * @param classid
	 * @return
	 */
	
	public List findWeekCalendarByClassid(String year, Short term,Long classid);
	
	/**
	 * 根据学校id查询某一个学校的校历
	 * @param kdid
	 * @return
	 */
	public List findBySchid(Long kdid);
	/**
	 * 根据学校id查询某一个学校的校历中学年
	 * @param kdid
	 * @return
	 */
	public List findyearByschid(Long kdid);
	public List findByYearAndTerm(String year, Short term, Long schid);
	
	/**
	 * <li>功能描述：根据学年和学校id查某一年校历
	 * @param year
	 * @param schid
	 * @return
	 */
	public List findByYearAndSchid(String year, Long schid);
	public Long  findByKdidAndTime(Long kdid,Date time);
	}
