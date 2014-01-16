package org.iti.cqgl.service.impl;

import java.util.Date;
import java.util.List;

import org.iti.cqgl.service.KyhdcqService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.ConvertUtil;

public class KyhdcqServiceImpl extends BaseServiceImpl implements KyhdcqService {

	public List findByYearAndTermAndschidAndXyidAndZyidAndGradeAndClassAndSidAndName(
			String year, short term, Long schid, Long xyid, Long zyid,
			Integer grade, String cla, String sid, String sname) {
		String queryString="select distinct cz.kuId,cz.xlId from CqKyhdcq as cz where cz.xlId in(select jc.id from JxCalendar as jc where  jc.kdId=? ";
		if(year!=null&&!year.equals("")&&!year.equals("-ÇëÑ¡Ôñ-")){
			queryString=queryString+" and jc.CYear='"+year+"'";
		}
		if(term!=2){
			queryString=queryString+" and jc.term="+term+" )";
		}else{
			queryString=queryString+")";
		}
		if(xyid!=null&&xyid!=0L){
			queryString=queryString+" and cz.kuId in(select u.kuId from WkTUser as u where u.kdId in(select d.kdId from WkTDept as d where d.kdPid="+xyid+"))";
		}
		if(zyid!=null&&zyid!=0L){
			queryString=queryString+" and cz.kuId in(select u.kuId from WkTUser as u where u.kdId="+zyid+")";
		}
		if(grade!=null&&grade!=0){
			queryString=queryString+" and cz.kuId in(select s.kuId from Student as s where s.stGrade="+grade+")";
		}
		if(cla!=null&&!cla.equals("")&&!cla.equals("ÇëÑ¡Ôñ")){
			queryString=queryString+" and cz.kuId in(select s.kuId from Student as s where s.stClass='"+cla+"')";
		}
		if(sid!=null&&!sid.equals("")){
			queryString=queryString+" and cz.kuId in(select s.kuId from Student as s where s.stId='%"+sid+"%')";
		}
		if(sname!=null&&!sname.equals("")){
			queryString=queryString+" and cz.kuId in(select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')";
		}
		queryString=queryString+"group by cz.xlId,cz.kuId";
		System.out.println("²éÑ¯Óï¾ä:"+queryString);
		return getHibernateTemplate().find(queryString,new Object[]{schid});
	}

	public List findByKuIdAndXlId(Long kuid, Long xlid) {
		String queryString="from CqKyhdcq as cz where cz.kuId=? and cz.xlId=? order by cz.ckStart";
		 return getHibernateTemplate().find(queryString,new Object[]{kuid,xlid});
	}

	public boolean checkIfExsit(Long kuid, Date time) {
		String queryString="select distinct ck.ckStart from CqKyhdcq as ck where ck.kuId=? ";
		List kylist=getHibernateTemplate().find(queryString,new Object[]{kuid});
		
		
		 if(kylist!=null&&kylist.size()>0){
			 for(int i=0;i<kylist.size();i++){
				 Date d=(Date) kylist.get(i); 
				 if((ConvertUtil.convertString(d)).equals(ConvertUtil.convertString(time))){
					 return true;
				 }
			 }
			 return false;
			
		 }else{
			 return false;
		 }
		
	}

	public List findClassHgrenshu(String year, Short term, Long schid,
			Long classid, Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqKyhdcq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.clId="+classid+ " and (stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")) group by zc.kuId having count(*)>="+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term});
	}
	public List findByClassHgrenshu(String year, Short term, Long schid,
			Long classid, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqKyhdcq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.clId="+classid+ ") group by zc.kuId having count(*)>="+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term});
	}

	public List findClassWhgrenshu(String year, Short term, Long schid,
			Long classid, Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqKyhdcq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where  stu.clId=? and (stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+" )) group by zc.kuId having count(*)<"+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,classid});
	}
	public List findByClassWhgrenshu(String year, Short term, Long schid,
			Long classid, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqKyhdcq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where  stu.clId=? ) group by zc.kuId having count(*)<"+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,classid});
	}

	public List findHgrenshu(String year, Short term, Long schid, Long xyid,
			Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqKyhdcq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and zc.kuId in(select suser.kuId from WkTUser as suser where suser.kdId in (select de.kdId from WkTDept as de where de.kdPid=?)) group by zc.kuId having count(*)>="+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,xyid});
	}

	public List findWhgrenshu(String year, Short term, Long schid, Long xyid,
			Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqKyhdcq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and zc.kuId in(select suser.kuId from WkTUser as suser where suser.kdId in (select de.kdId from WkTDept as de where de.kdPid=?)) group by zc.kuId having count(*)<"+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,xyid});
	}

	public List findZyHgrenshu(String year, Short term, Long schid, Long zyid,
			Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqKyhdcq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and zc.kuId in(select suser.kuId from WkTUser as suser where suser.kdId =?) group by zc.kuId having count(*)>="+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,zyid});
	}

	public List findZyWhgrenshu(String year, Short term, Long schid, Long zyid,
			Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqKyhdcq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and zc.kuId in(select suser.kuId from WkTUser as suser where suser.kdId =?) group by zc.kuId having count(*)<"+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,zyid});
	}

	public List findKybykuidAndyearAndterm(String year, Short term, Long schid,
			Long kuid) {
		String queryString=" from CqKyhdcq as cz where cz.kuId =? and cz.xlId in (select jc.id from JxCalendar as jc where jc.CYear=? and jc.term=? and jc.kdId=?)) ";
		   return getHibernateTemplate().find(queryString, new Object[]{kuid,year,term,schid});
	}

	public Long findMaxctZid() {
		String queryString="select max(bt.ctKid) as num from CqKyhdcq as bt";
		List list=getHibernateTemplate().find(queryString, new Object[]{});
		if(list.size()==0){
			return 0l;
		}else if(list.get(0)==null){
			return 0l;
		}else{
			return Long.valueOf(list.get(0).toString());
		}
	}
	
	public List findbyKuidAndLiketime(Long kuid, String time, String end) {
		String queryString=" from CqKyhdcq as ckyhd where ckyhd.kuId =? and  ckyhd.ckStart like '%"+time+"%' and ckyhd.ckEnd like '%"+end+"%'";
		   return getHibernateTemplate().find(queryString, new Object[]{kuid});
	}	
	public List findClassStuWhg(String year, Short term, Long schid,
			Long classid, Integer[] grade) {
		String queryString="from Student as stu  where (stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+" )and stu.clId=? " +
				"and stu.kuId not in (select zc.kuId from CqKyhdcq as zc where  zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term="+term+")) " +
						"and stu.kuId not in(select cqstu.kuId from CqNstudent as cqstu where cqstu.xlId in(select jcl.id from JxCalendar as jcl where jcl.kdId="+schid+" and jcl.CYear="+year +"and jcl.term=?) )" ;		
 return getHibernateTemplate().find(queryString,new Object[]{classid,term});
	}
	public List findByClassStuWhg(String year, Short term, Long schid,
			Long classid) {
		String queryString="from Student as stu  where stu.clId=? " +
				"and stu.kuId not in (select zc.kuId from CqKyhdcq as zc where  zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term="+term+")) " +
						"and stu.kuId not in(select cqstu.kuId from CqNstudent as cqstu where cqstu.xlId in(select jcl.id from JxCalendar as jcl where jcl.kdId="+schid+" and jcl.CYear="+year +"and jcl.term=?) )" ;		
 return getHibernateTemplate().find(queryString,new Object[]{classid,term});
	}

	public List findZystuWhg(String year, Short term, Long schid, Long zyid,
			Integer[] grade) {
		String queryString="from Student as stu where( stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+") and" +
				" stu.kuId in(select user.kuId from WkTUser as user where user.kdId =?) and stu.kuId not in (select zc.kuId from CqKyhdcq as zc where  zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term="+term+")) " +
						" and stu.kuId not in(select cqstu.kuId from CqNstudent as cqstu where cqstu.xlId in(select jcl.id from JxCalendar as jcl where jcl.kdId="+schid+" and jcl.CYear="+year +"and jcl.term=?) )" ;		
 return getHibernateTemplate().find(queryString,new Object[]{zyid,term});
	}

	public List findstuWhgrenshu(String year, Short term, Long schid,
			Long xyid, Integer[] grade) {
		String queryString="from Student as stu  where( stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+" )and" +
				" stu.kuId in(select user.kuId from WkTUser as user where user.kdId in (select de.kdId from WkTDept as de where de.kdPid=?)) " +
				"and stu.kuId not in (select zc.kuId from CqKyhdcq as zc where  zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term="+term+"))" +
			" and stu.kuId not in(select cqstu.kuId from CqNstudent as cqstu where cqstu.xlId in(select jcl.id from JxCalendar as jcl where jcl.kdId="+schid+" and jcl.CYear="+year +"and jcl.term=?) )" ;		
		 return getHibernateTemplate().find(queryString,new Object[]{xyid,term});
	}
	
	public List findByStartAndEndAndKdid(Date start,Date end,Long kdid){
		String queryString="from CqKyhdcq as ckyhd where ckyhd.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId=? or wuser.kdId in (select de.kdId from WkTDept as de where de.kdPid=?)) and ckyhd.ckStart>=? and ckyhd.ckEnd<=?";
		return getHibernateTemplate().find(queryString,new Object[]{kdid,kdid,start,end});
	
	}
	public List findByStartAndEndAndClid(Date Start, Date end, Long clid) {
		String queryString="from CqKyhdcq as ckyhd where ckyhd.kuId  in ( select stu.kuId from Student as stu where stu.clId=? )  and ckyhd.ckStart>=? and ckyhd.ckEnd<=?";
		return getHibernateTemplate().find(queryString,new Object[]{clid,Start,end});
	}

	public List findByStartAndEndAndKdidAndGrade(Date start, Date end,
			Long kdid, Integer grade) {
		String queryString="from CqKyhdcq as ckyhd where ckyhd.kuId   in ( select stu.kuId from Student as stu where stu.clId  in(select xc.clId from XyClass as xc where ( xc.kdId=?  or xc.kdId in (select de.kdId from WkTDept as de where de.kdPid=?))and xc.clGrade=?) ) and ckyhd.ckStart>=? and ckyhd.ckEnd<=?";
		return getHibernateTemplate().find(queryString,new Object[]{kdid,kdid,grade,start,end});
	}
}
