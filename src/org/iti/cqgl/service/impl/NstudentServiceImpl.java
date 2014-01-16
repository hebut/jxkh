package org.iti.cqgl.service.impl;

import java.util.List;

import org.iti.cqgl.service.NstudentService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class NstudentServiceImpl extends BaseServiceImpl implements
		NstudentService {

	public List findByYearAndTerm(String year, Short term,Long schid,String name, String xuehao) {
		String queryString="from CqNstudent as cn where cn.xlId in(select jc.id from JxCalendar as jc where jc.kdId=? ";
	    if(year!=null&&year.length()!=0){
	    	queryString=queryString+" and jc.CYear=? ";
	    }else{
	    	queryString=queryString+" and jc.CYear is not null ";
	    }
	    if(term!=null&&term!=2){
	    	queryString=queryString+" and jc.term=? )and cn.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + name + "%' ) and cn.kuId in(select stu.kuId from Student as stu where stu.stId like  '%" + xuehao + "%') order by cn.cnsType";
	    	if(year!=null&&year.length()!=0){
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,year,term});
	    	}else{
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,term});
	    	}
	    }else{
	    	queryString=queryString+" and jc.term is not null )and cn.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + name + "%' ) and cn.kuId in(select stu.kuId from Student as stu where stu.stId like  '%" + xuehao + "%')order by cn.cnsType";
	    	if(year!=null&&year.length()!=0){
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,year});
	    	}else{
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid});
	    	}
	    }
	}

	public List findByYearAndTermAndgrade(String year, Short term, Long schid, Long xykdid,
			Integer[] grade) {
		String queryString="from CqNstudent as cn where cn.xlId in(select jc.id from JxCalendar as jc where jc.kdId=? and jc.CYear=?" +
				"and jc.term=?)and cn.kuId in(select stu.kuId from Student as stu  where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and cn.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId in (select de.kdId from WkTDept as de where de.kdPid=?)) order by cn.cnsType";
		return  getHibernateTemplate().find(queryString, new Object[]{schid,year,term,xykdid});
	}

	public List findZyByYearAndTermAndgrade(String year, Short term,
			Long schid, Long zykdid, Integer[] grade) {
		String queryString="from CqNstudent as cn where cn.xlId in(select jc.id from JxCalendar as jc where jc.kdId=? and jc.CYear=?" +
		"and jc.term=?)and cn.kuId in(select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and cn.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId =?) order by cn.cnsType";
return  getHibernateTemplate().find(queryString, new Object[]{schid,year,term,zykdid});
	}

	public List findClassByYearAndTermAndgrade(String year, Short term,
			Long schid, Long classid, Integer[] grade) {
		String queryString="from CqNstudent as cn where cn.xlId in(select jc.id from JxCalendar as jc where jc.kdId=? and jc.CYear=?" +
		"and jc.term=?)and cn.kuId in(select stu.kuId from Student as stu where (stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+") and stu.clId=?) order by cn.cnsType";
return  getHibernateTemplate().find(queryString, new Object[]{schid,year,term,classid});
	}

	public List findClassByYearAndTerm(String year, Short term, Long schid,
			Long classid) {
		String queryString="from CqNstudent as cn where cn.xlId in(select jc.id from JxCalendar as jc where jc.kdId=? and jc.CYear=?" +
		"and jc.term=?)and cn.kuId in(select stu.kuId from Student as stu where (stu.clId=?)) order by cn.cnsType";
return  getHibernateTemplate().find(queryString, new Object[]{schid,year,term,classid});
	}

	public boolean CheckIf(Long xlid, Long kuid) {
		String queryString="from CqNstudent as cn where cn.kuId=? and cn.xlId=? and cn.kuId in(select stu.kuId from Student as stu)";
		List list= getHibernateTemplate().find(queryString, new Object[]{kuid,xlid});
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
		
	}	
	
	
}
