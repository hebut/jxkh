package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhRjzz;
import org.iti.gh.service.RjzzService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class RjzzServiceImpl extends BaseServiceImpl implements RjzzService {

	public List findByKuid(Long kuId) {
		String queryString = "from GhRjzz as rjzz where rjzz.rjId in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype="+GhJslw.RJZZ+")";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}

	public List findByKuidAndUname(Long kuid, String kuname) {
		String queryString = "from GhRjzz as rjzz where rjzz.rjPerson like '%"+kuname+"%' and rjzz.rjName not in(select rj.rjName from GhRjzz as rj where rj.kuId=? ) and rjzz.rjId not in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype="+GhJslw.RJZZ+")";
		return getHibernateTemplate().find(queryString,new Object[]{kuid,kuid});
	}

	public List findByKdidAndState(Long kdid, Short state) {
		String queryString = "from GhRjzz as rjzz where rjzz.kuId in(select xu.id.kuId from XyUserrole as xu where xu.id.krId=(select r.krId from WkTRole as r where r.krName='½ÌÊ¦' and r.kdId=(select dep.kdSchid from WkTDept as dep where dep.kdId=?)) and (xu.kdId=? or xu.kdId in( select d.kdId from WkTDept as d where d.kdPid=?)))";
		if(state!=null){
			queryString=queryString+" and rjzz.auditState="+state+"";
		}else{
			queryString=queryString+" and rjzz.auditState is null";
		}
		return  getHibernateTemplate().find(queryString,new Object[]{kdid,kdid,kdid});
	}

	public boolean CheckOnlyOne(GhRjzz rjzz, String mc, String softno) {
	   String queryString="from GhRjzz as rjzz where rjzz.rjName=? and rjzz.rjSoftno=?";
	   List list=getHibernateTemplate().find(queryString, new Object[]{mc,softno});
		if(rjzz!=null){
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					GhRjzz rj=(GhRjzz)list.get(i);
					if(rj.getRjId().intValue()!=rjzz.getRjId().intValue()){
						return true;
					}
				}
			}
			 return false;
		}else{
			if(list!=null&&list.size()>0){
				return true;
			}
			 return false;
		}
	  
	}
   public List findByRjnameAndDjh(String mc, String softno){
	   String queryString="from GhRjzz as rjzz where rjzz.rjName like '%"+mc+"%' and rjzz.rjSoftno like '%"+softno+"%'";
	   return getHibernateTemplate().find(queryString);
   }
   public GhRjzz findBynameAndSoftno(String mc, String softno){
	   String queryString="from GhRjzz as rjzz where rjzz.rjName=? and rjzz.rjSoftno=?";
	   List list=getHibernateTemplate().find(queryString, new Object[]{mc,softno});
	   if(list!=null&&list.size()>0){
		   return (GhRjzz)list.get(0);
	   }else{
		   return null;
	   }
   }

public List findBykdidAndYearAndKuid( String year, Long kuid) {
	String queryString = "from GhRjzz as rjzz where  rjzz.kuId in(select tea.kuId from Teacher as tea) ";
	if(year!=null){
		queryString=queryString+" and rjzz.rjTime like '"+year+"%' ";
	}
	if(kuid!=null){
		queryString=queryString+" and rjzz.kuId="+kuid+"";
	}
	return getHibernateTemplate().find(queryString, new Object[]{});
}

public List<GhRjzz> findByRjIds(String ids) {
	String queryString = "from GhRjzz as rj where rj.rjId in ("+ids+") order by rj.rjTime desc";
	return getHibernateTemplate().find(queryString);
}
   
}
