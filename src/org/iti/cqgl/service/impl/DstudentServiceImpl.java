package org.iti.cqgl.service.impl;

import java.util.Date;
import java.util.List;

import org.iti.cqgl.service.DstudentService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class DstudentServiceImpl extends BaseServiceImpl implements
		DstudentService {
	public List findByYearAndTermAndgradeAndXy(String year, Short term, Long schid, Long xykdid,
			Integer grade,String tname,String tno,Date start,Date end) {
		Long time=end.getTime()+24*3600*1000;
		String queryString="from CqDstudent as cn where cn.dsDate>="+start.getTime()+" and cn.dsDate<"+time+" and cn.xlId in(select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear=" +year+
				"and jc.term="+term+")and cn.kuId in(select stu.kuId from Student as stu  where  stu.stId like '%" + tno + "%'" ;
		if(grade!=0){
			queryString=queryString+ "and stu.stGrade="+grade ;
		}
		queryString=queryString+") and cn.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kuName like '%" + tname + "%'" ;
		if(xykdid!=0){
			queryString=queryString+"and wuser.kdId in (select de.kdId from WkTDept as de where de.kdPid="+xykdid+")";	
		}
		queryString=queryString+") order by cn.cdsType";
		return  getHibernateTemplate().find(queryString);
	}
	public List findByYearAndTermAndclass(String year, Short term, Long schid, String cla ,Date start,Date end) {
		Long time=end.getTime()+24*3600*1000;
		String queryString="from CqDstudent as cn where cn.dsDate>="+start.getTime()+"and cn.dsDate<"+time+" and cn.xlId in(select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear=" +year+
				"and jc.term="+term+")and cn.kuId in(select stu.kuId from Student as stu  " ;
		if(cla!=null&&!cla.equals("-ÇëÑ¡Ôñ-")&&!cla.equals("")){
			queryString=queryString+" where stu.stClass = '"+cla+"'";
		}
		
		queryString=queryString+") order by cn.cdsType";
		return  getHibernateTemplate().find(queryString);
	}

}
