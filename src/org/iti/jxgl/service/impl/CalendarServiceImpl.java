package org.iti.jxgl.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.iti.jxgl.entity.JxCalendar;

import org.iti.jxgl.service.CalendarService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.DateUtil;

public class CalendarServiceImpl extends BaseServiceImpl implements CalendarService {

	public List findCalendar(String year, Short term,Long kdid) {
		String queryString = "from JxCalendar where   CYear=? and term=? and kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term,kdid });
		// return (Date)getHibernateTemplate().find(queryString, new Object[]{year,term});
	}

	public JxCalendar findByCyearAndTermAndKdid(String cyear, Short term,Long kdid) {
		String queryString = "from JxCalendar as jc where jc.CYear=? and jc.term=? and jc.kdId=?";
		List clist = getHibernateTemplate().find(queryString, new Object[] { cyear, term,kdid });
		if (clist != null && clist.size() > 0) {
			return (JxCalendar) clist.get(0);
		} else {
			return null;
		}
	}
	public List findByYearAndTerm(String year, Short term, Long schid) {
		String queryString=" from JxCalendar as jc where  jc.kdId=? ";
	    if(year!=null&&year.length()!=0){
	    	queryString=queryString+" and jc.CYear=? ";
	    }else{
	    	queryString=queryString+" and jc.CYear is not null ";
	    }
	    if(term!=null&&term!=2){
	    	queryString=queryString+" and jc.term=? ) ";
	    	if(year!=null&&year.length()!=0){
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,year,term});
	    	}else{
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,term});
	    	}
	    }else{
	    	queryString=queryString+" and jc.term is not null )";
	    	if(year!=null&&year.length()!=0){
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,year});
	    	}else{
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid});
	    	}
	    }
	}

	public String[] getNowYearTerm() {
		String[] time = { "", "0" };
		String queryString = " from JxCalendar order by CYear,term";
		List list = getHibernateTemplate().find(queryString);
		Date endtimelast = null;
		for (int i = 0; i < list.size(); i++) {

			JxCalendar cal = (JxCalendar) list.get(i);
			Date starttime = cal.getStarttime();
			Date endtime = null;
			try {
				endtime = DateUtil.addDate(starttime, 7 * Integer.valueOf(cal.getWeeks()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				dateFormat.format(endtime);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date date = new Date();// 获取当前时间
			if (i == 0 && list.size() == 1) {
				endtimelast = date;
			}

			// System.out.println("starttime=="+starttime+",endtime=="+endtime+",now=="+date);
			if (date.compareTo(starttime) >= 0 && date.compareTo(endtime) <= 0) {
				time[0] = cal.getCYear();
				time[1] = String.valueOf(cal.getTerm());
				// System.out.println("进去1,"+"year=="+time[0]+",term=="+time[1]);
				break;
			} else if (endtimelast != null && date.compareTo(endtimelast) >= 0 && date.compareTo(endtime) <= 0) {
				time[0] = cal.getCYear();
				time[1] = String.valueOf(cal.getTerm());
				// System.out.println("进去2,"+"year=="+time[0]+",term=="+time[1]);
				break;
			}
			endtimelast = endtime;
		}
		return time;
	}
	public Long  findByKdidAndTime(Long kdid,Date time) {
		Long c=0l;
		String queryString = " from JxCalendar as jc where jc.kdId="+kdid+" order by jc.CYear,jc.term";
		List list = getHibernateTemplate().find(queryString);
		Date endtimelast = null;
		for (int i = 0; i < list.size(); i++) {

			JxCalendar cal = (JxCalendar) list.get(i);
			Date starttime = cal.getStarttime();
			Date endtime = null;
			try {
				endtime = DateUtil.addDate(starttime, 7 * Integer.valueOf(cal.getWeeks()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				dateFormat.format(endtime);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date date = time;// 获取当前时间
			if (i == 0 && list.size() == 1) {
				endtimelast = date;
			}

			// System.out.println("starttime=="+starttime+",endtime=="+endtime+",now=="+date);
			if (date.compareTo(starttime) >= 0 && date.compareTo(endtime) <= 0) {
				c=cal.getId();
				// System.out.println("进去1,"+"year=="+time[0]+",term=="+time[1]);
				break;
			} else if (endtimelast != null && date.compareTo(endtimelast) >= 0 && date.compareTo(endtime) <= 0) {
				c=cal.getId();
				// System.out.println("进去2,"+"year=="+time[0]+",term=="+time[1]);
				break;
			}
			endtimelast = endtime;
		}
		return c;
	}

	public List findWeekCalendar(String year, Short term, Long kdid) {
		String queryString = "from JxWeekcalendar where jwcYear=? and jwcTerm=? and kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid });
	}
	
	public List findWeekCalendarByClassid(String year, Short term,Long classid){
		String queryString = "from JxWeekcalendar where jwcYear=? and jwcTerm=? and jwcClass=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, classid });
	}

	public List findBySchid(Long kdid) {
		String queryString="from JxCalendar as jc where jc.kdId=? order by jc.CYear desc,jc.term desc";
		return getHibernateTemplate().find(queryString, kdid);
	}

	public List findyearByschid(Long kdid) {
		String queryString="select distinct jc.CYear from JxCalendar as jc where jc.kdId=? order by jc.CYear desc";
		return getHibernateTemplate().find(queryString, kdid);
	}

	public List findByYearAndSchid(String year, Long schid) {
		String queryString="from JxCalendar as jc where jc.CYear=? and jc.kdId=? order by jc.term";
		return getHibernateTemplate().find(queryString, new Object[]{year,schid});
	}
}
