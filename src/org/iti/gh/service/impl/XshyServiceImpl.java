package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.XshyService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class XshyServiceImpl extends BaseServiceImpl implements XshyService {

	
	public List findByKdId(long kdid, Short ifcj) {
		String queryString="select distinct hy.hyMc from GhXshy as hy where hy.hyIfcj=? and hy.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,kdid,kdid});
	}

	public List findByKuid(long kuid, Short ifcj) {
		String queryString="from GhXshy as hy where hy.hyIfcj=? and hy.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,kuid});
	}

	public List findSumKdId(long kdid, Short ifcj,Short state) {
		String queryString="select distinct hy.hyMc from GhXshy as hy where hy.hyIfcj=? and hy.kuId in(select tea.kuId from Teacher as tea) and hy.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(state!=null){
			queryString=queryString+" and hy.auditState="+state+"";
		}else{
			queryString=queryString+" and hy.auditState is null";
		}
		List list=getHibernateTemplate().find(queryString, new Object[] {ifcj,kdid,kdid});
		if(list!=null&&list.size()>0){
			return  list;
		}else{
			return null;
		}
	}

	public List findByMc(String name, Short ifcj, Short state) {
		String queryString="from GhXshy as hy where hy.hyIfcj=? and hy.hyMc=?  ";
		if(state!=null){
			queryString=queryString+" and hy.auditState="+state+"";
		}else{
			queryString=queryString+" and hy.auditState is null";
		}
		queryString=queryString+" order by hy.kuId";
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,name});
	}

	public List findByKdidAndState(Long kdid, Short ifcj, Short state) {
		String queryString="from GhXshy as hy where hy.hyIfcj=? and hy.kuId in(select user.kuId from WkTUser as user where user.kdId=? or user.kdId in(select d.kdId from WkTDept as d where d.kdPid=?)) and hy.kuId in(select tea.kuId from Teacher as tea)";
		if(state!=null){
			queryString=queryString+" and hy.auditState="+state+"";
		}else{
			queryString=queryString+" and hy.auditState is null";
		}
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,kdid,kdid});
	}

	public boolean CheckOnlyOne(GhXshy xshy, String name, Short ifcj,Long kuid) {
		String queryString="from GhXshy as hy where hy.kuId=? and hy.hyIfcj=? and hy.hyMc=?";
		List list =getHibernateTemplate().find(queryString,new Object[]{kuid,ifcj,name});
		if(xshy!=null){
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					GhXshy hy =(GhXshy)list.get(i);
					if(hy.getHyId().intValue()!=xshy.getHyId().intValue()){
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
   public List findByKdidAndYearAndKuidAndIfcjAndLx(String year,Long kuid,Short ifcj,Short lx,String hyef){
	   String queryString="select distinct hy.hyMc from GhXshy as hy where hy.hyIfcj=? and hy.hyLx=? and hy.kuId in(select tea.kuId from Teacher as tea)  and hy.hyEffect =? ";
	   if(year!=null){
		   queryString=queryString+" and hy.hySj like '"+year+"%'";
	   }
	   if(kuid!=null){
		   queryString=queryString+" and hy.kuId ="+kuid+"";
	   }
	   return getHibernateTemplate().find(queryString, new Object[] {ifcj,lx,hyef});
   }
}
