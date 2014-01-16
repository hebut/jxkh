package org.iti.cqgl.service.impl;

import java.util.Date;
import java.util.List;

import org.iti.cqgl.service.ZccqService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.ConvertUtil;

public class ZccqServiceImpl extends BaseServiceImpl implements ZccqService {


	

	public List findBykuIdAndYearAndTermAndSchid(Long kuid,String year, short term, Long schid) {
       String queryString="select count(distinct czTime) from CqZccq as cz where cz.kuId =? and cz.xlId in (select jc.id from JxCalendar as jc where jc.CYear=? and jc.term=? and jc.kdId=?)) ";
	   return getHibernateTemplate().find(queryString, new Object[]{kuid,year,term,schid});
	}

	public List findByYearAndTermAndschidAndXyidAndZyidAndGradeAndClassAndSidAndName(
			String year, short term, Long schid, Long xyid, Long zyid,Integer grade,
			String cla, String sid, String sname) {
		String queryString="select distinct cz.kuId,cz.xlId from CqZccq as cz where cz.xlId in(select jc.id from JxCalendar as jc where  jc.kdId=? ";
		if(year!=null&&!year.equals("")&&!year.equals("-请选择-")){
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
		if(cla!=null&&!cla.equals("")&&!cla.equals("请选择")){
			queryString=queryString+" and cz.kuId in(select s.kuId from Student as s where s.stClass='"+cla+"')";
		}
		if(sid!=null&&!sid.equals("")){
			queryString=queryString+" and cz.kuId in(select s.kuId from Student as s where s.stId='%"+sid+"%')";
		}
		if(sname!=null&&!sname.equals("")){
			queryString=queryString+" and cz.kuId in(select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')";
		}
		queryString=queryString+"group by cz.xlId,cz.kuId";
		System.out.println("查询语句:"+queryString);
		return getHibernateTemplate().find(queryString,new Object[]{schid});
	}

	public Integer findByKuidAndXlid(Long kuid, Long xlid) {
		String queryString="select count(cz.czTime) from CqZccq as cz where cz.kuId=? and cz.xlId=?";
	    return((Long)getHibernateTemplate().find(queryString,new Object[]{kuid,xlid}).get(0)).intValue();
	}

	public List findByKuIdAndXlId(Long kuid, Long xlid) {
		String queryString="from CqZccq as cz where cz.kuId=? and cz.xlId=? order by cz.czTime";
		 return getHibernateTemplate().find(queryString,new Object[]{kuid,xlid});
	}


	public List findHgrenshu(String year, Short term, Long schid, Long xyid,
			Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqZccq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
				"zc.kuId in (select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and zc.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId in (select de.kdId from WkTDept as de where de.kdPid=?)) group by zc.kuId having count(*)>="+zczscs;		
		 return getHibernateTemplate().find(queryString,new Object[]{term,xyid});
	}


	/**
	  * <li>功能描述：根据用户id和日期判断该用户是否已经有出勤信息
	  * @param kuid
	  * @param time
	  * @return
	  */
	   public  boolean checkIfExist(Long kuid,Date time){
		  String queryString="select distinct cz.czTime from CqZccq as cz where cz.kuId=?";   
		 List zclist= getHibernateTemplate().find(queryString, new Object[]{kuid});
		
		 if(zclist!=null&&zclist.size()>0){
			 for(int i=0;i<zclist.size();i++){
				 Date d=(Date) zclist.get(i); 
				 if((ConvertUtil.convertString(d)).equals(ConvertUtil.convertString(time))){
					 return true;
				 }
			 }
			 return false;
			
		 }else{
			 return false;
		 }
	 }

	public List findWhgrenshu(String year, Short term, Long schid, Long xyid,
			Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqZccq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and zc.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId in (select de.kdId from WkTDept as de where de.kdPid=?)) group by zc.kuId having count(*)<"+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,xyid});
	}

	public List findZyHgrenshu(String year, Short term, Long schid, Long zyid,
			Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqZccq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and zc.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId =?) group by zc.kuId having count(*)>="+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,zyid});
	}

	public List findZyWhgrenshu(String year, Short term, Long schid, Long zyid,
			Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqZccq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")and zc.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId =?) group by zc.kuId having count(*)<"+zczscs;		

 return getHibernateTemplate().find(queryString,new Object[]{term,zyid});
	}

	public List findClassHgrenshu(String year, Short term, Long schid,
			Long classid, Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqZccq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.clId="+classid+ " and (stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+")) group by zc.kuId having count(*)>="+zczscs;	
		
 return getHibernateTemplate().find(queryString,new Object[]{term});
	}

	public List findClassWhgrenshu(String year, Short term, Long schid,
			Long classid, Integer[] grade, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqZccq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where  stu.clId=? and (stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+" )) group by zc.kuId having count(*)<"+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,classid});
	}
	public List findByClassHgrenshu(String year, Short term, Long schid,
			Long classid, Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqZccq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where stu.clId="+classid+ ") group by zc.kuId having count(*)>="+zczscs;	
		
 return getHibernateTemplate().find(queryString,new Object[]{term});
	}

	public List findByClassWhgrenshu(String year, Short term, Long schid,
			Long classid,Long zczscs) {
		String queryString="select zc.kuId ,count(*) from CqZccq as zc where zc.kdId="+schid+" and zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term=?)  and " +
		"zc.kuId in (select stu.kuId from Student as stu where  stu.clId=?  ) group by zc.kuId having count(*)<"+zczscs;		
 return getHibernateTemplate().find(queryString,new Object[]{term,classid});
	}

	public List findbykuidAndyearAndterm(String year, Short term, Long schid,
			Long kuid) {
		String queryString=" from CqZccq as cz where cz.kuId =? and cz.xlId in (select jc.id from JxCalendar as jc where jc.CYear=? and jc.term=? and jc.kdId=?)) ";
		   return getHibernateTemplate().find(queryString, new Object[]{kuid,year,term,schid});
	}

	public Long findMaxctZid() {
		String queryString="select max(bt.ctZid) as num from CqZccq as bt";
		List list=getHibernateTemplate().find(queryString, new Object[]{});
		if(list.size()==0){
			return 0l;
		}else if(list.get(0)==null){
			return 0l;
		}else{
			return Long.valueOf(list.get(0).toString());
		}
	}

	public List findbyKuidAndLiketime(Long kuid, String time) {
		String queryString=" from CqZccq as cz where cz.kuId =? and  cz.czTime like '%"+time+"%'";
		   return getHibernateTemplate().find(queryString, new Object[]{kuid});
	}

	public List findClassStuWhg(String year, Short term, Long schid,
			Long classid, Integer[] grade) {
		String queryString="from Student as stu where (stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+" )and stu.clId=? " +
				"and stu.kuId not in (select zc.kuId from CqZccq as zc where  zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +" and jc.term="+term+")) " +
						"and stu.kuId not in(select cqstu.kuId from CqNstudent as cqstu where cqstu.xlId in(select jcl.id from JxCalendar as jcl where jcl.kdId="+schid+" and jcl.CYear="+year +"and jcl.term=?) )" ;		
 return getHibernateTemplate().find(queryString,new Object[]{classid ,term});
	}
	public List findByClassStuWhg(String year, Short term, Long schid,
			Long classid) {
		String queryString="from Student as stu where stu.clId=? and  stu.kuId not in (select zc.kuId from CqZccq as zc where  zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term="+term+")) " +
						"and stu.kuId not in(select cqstu.kuId from CqNstudent as cqstu where cqstu.xlId in(select jcl.id from JxCalendar as jcl where jcl.kdId="+schid+" and jcl.CYear="+year +" and jcl.term=?) )" ;		
 return getHibernateTemplate().find(queryString,new Object[]{classid ,term});
	}

	public List findZystuWhg(String year, Short term, Long schid, Long zyid,
			Integer[] grade) {
		String queryString="from Student as stu where( stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+") and" +
				" stu.kuId in(select suser.kuId from WkTUser as suser where suser.kdId =?) and stu.kuId not in (select zc.kuId from CqZccq as zc where  zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term="+term+")) " +
						" and stu.kuId not in(select cqstu.kuId from CqNstudent as cqstu where cqstu.xlId in(select jcl.id from JxCalendar as jcl where jcl.kdId="+schid+" and jcl.CYear="+year +"and jcl.term=?) )" ;		
 return getHibernateTemplate().find(queryString,new Object[]{zyid,term});
	}

	public List findstuWhgrenshu(String year, Short term, Long schid,
			Long xyid, Integer[] grade) {
		String queryString="from Student as stu where( stu.stGrade="+grade[0]+" or stu.stGrade="+grade[1]+" or stu.stGrade="+grade[2]+"or stu.stGrade="+grade[3]+"or stu.stGrade="+grade[4]+" )and" +
				" stu.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId in (select de.kdId from WkTDept as de where de.kdPid=?)) " +
				"and stu.kuId not in (select zc.kuId from CqZccq as zc where  zc.xlId in (select jc.id from JxCalendar as jc where jc.kdId="+schid+" and jc.CYear="+year +"and jc.term="+term+"))" +
			" and stu.kuId not in(select cqstu.kuId from CqNstudent as cqstu where cqstu.xlId in(select jcl.id from JxCalendar as jcl where jcl.kdId="+schid+" and jcl.CYear="+year +"and jcl.term=?) )" ;		
		 return getHibernateTemplate().find(queryString,new Object[]{xyid,term});
	}

	public Integer findXynianzhi(Long kdid) {
		String queryString="select stu.stBynf-stu.stGrade from Student as stu where stu.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId in (select de.kdId from WkTDept as de where de.kdPid=?))";
			 List gradelist=getHibernateTemplate().find(queryString,new Object[]{kdid});
			
			 if(gradelist.size()==0){
				 return 0;
			 }else {
				 return (Integer)gradelist.get(0);
			 }
	}

	public Integer findClassnianzhi(Long clId) {
		String queryString=" select stu.stBynf-stu.stGrade from Student as stu where stu.clId=?";
		 List gradelist=getHibernateTemplate().find(queryString,new Object[]{clId});
		 if(gradelist.size()==0){
			 return 0;
		 }else {
			 return (Integer)gradelist.get(0);
		 }
	}

	public Integer findZynianzhi(Long kdid) {
		
		 String queryString="select stu.stBynf-stu.stGrade from Student as stu where stu.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId =?)";
		 List gradelist=getHibernateTemplate().find(queryString,new Object[]{kdid});
		 if(gradelist.size()==0){
			 return 0;
		 }else {
			 return (Integer)gradelist.get(0);
		 }
	}

	public List findByStartAndEndAndKdid(Date start,Date end,Long kdid){
		Long time=end.getTime()+24*3600*1000;
		Date d= new Date(time);
		String queryString="from CqZccq as cz where cz.kuId in(select wuser.kuId from WkTUser as wuser where wuser.kdId=? or wuser.kdId in (select de.kdId from WkTDept as de where de.kdPid=? or de.kdSchid=?)) and cz.czTime>=? and cz.czTime<?";
		 return getHibernateTemplate().find(queryString,new Object[]{kdid,kdid,kdid,start,d});
	}

	public List findByStartAndEndAndClid(Date Start, Date end, Long clid) {
		Long time=end.getTime()+24*3600*1000;
		Date d= new Date(time);
		String queryString="from CqZccq as cz where cz.kuId  in ( select stu.kuId from Student as stu where stu.clId=? ) and cz.czTime>=? and cz.czTime<?";
		return getHibernateTemplate().find(queryString,new Object[]{clid,Start,d});
	}

	public List findByStartAndEndAndKdidAndGrade(Date start, Date end,
			Long kdid, Integer grade) {
		Long time=end.getTime()+24*3600*1000;
		Date d= new Date(time);
		String queryString="from CqZccq as cz where cz.kuId  in ( select stu.kuId from Student as stu where stu.clId  in(select xc.clId from XyClass as xc where ( xc.kdId=?  or xc.kdId in (select de.kdId from WkTDept as de where de.kdPid=? or de.kdSchid=?))and xc.clGrade=?) ) and cz.czTime>=? and cz.czTime<?";
		return getHibernateTemplate().find(queryString,new Object[]{kdid,kdid,kdid,grade,start,d});
	}
   
	

	
}

