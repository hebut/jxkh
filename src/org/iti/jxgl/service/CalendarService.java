package org.iti.jxgl.service;

import java.text.ParseException;
import java.util.List;

import org.iti.jxgl.entity.JxCalendar;

import java.util.Date;
import org.iti.jxgl.entity.JxCalendar;
import com.uniwin.basehs.service.BaseService;

public interface CalendarService extends BaseService {
	/**
	 * <li>������������ѯУ��
	 * 
	 * @param year
	 * @param term
	 *            * @author ��ΰ
	 * @return starttime(��ʼʱ��)
	 */
	public List findCalendar(String year, Short term,Long kdid);

	/**
	 * <li>��������������ѧ��ѧ�ڲ���У��
	 * 
	 * @param cyear
	 *            ѧ��
	 * @param term
	 *            ѧ�ڱ�ţ�1������ 2 �����＾��
	 * @return
	 */
	public JxCalendar findByCyearAndTermAndKdid(String cyear, Short term,Long kdid);

	/**
	 * <li>��������������У���жϵ�ǰʱ������У���е�����ݺ�ѧ��
	 * 
	 * @return ��ݡ�ѧ��
	 */
	public String[] getNowYearTerm();

	/**
	 * <li>������������ѯ����
	 */
	public List findWeekCalendar(String year, Short term, Long kdid);
	/**
	 * ��ѯĳ���༶������
	 * @param year
	 * @param term
	 * @param classid
	 * @return
	 */
	
	public List findWeekCalendarByClassid(String year, Short term,Long classid);
	
	/**
	 * ����ѧУid��ѯĳһ��ѧУ��У��
	 * @param kdid
	 * @return
	 */
	public List findBySchid(Long kdid);
	/**
	 * ����ѧУid��ѯĳһ��ѧУ��У����ѧ��
	 * @param kdid
	 * @return
	 */
	public List findyearByschid(Long kdid);
	public List findByYearAndTerm(String year, Short term, Long schid);
	
	/**
	 * <li>��������������ѧ���ѧУid��ĳһ��У��
	 * @param year
	 * @param schid
	 * @return
	 */
	public List findByYearAndSchid(String year, Long schid);
	public Long  findByKdidAndTime(Long kdid,Date time);
	}
