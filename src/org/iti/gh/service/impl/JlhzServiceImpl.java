package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhJlhz;
import org.iti.gh.service.JlhzService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JlhzServiceImpl extends BaseServiceImpl implements JlhzService {

	public List findByKdId(long kdid,Short ifcj,Short state) {
		String queryString="select distinct jl.hzMc from GhJlhz as jl where jl.hzIfcj=? and jl.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(state!=null){
			queryString=queryString+" and jl.auditState="+state+"";
		}else{
			queryString=queryString+" and jl.auditState is null";
		}
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,kdid,kdid});
	}

	public List findByKuid(long kuid,Short ifcj) {
		String queryString="from GhJlhz as jl where jl.hzIfcj=? and jl.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,kuid});
	}

	public Long findSumKdId(long kdid, Short ifcj,Short state) {
		String queryString="select count(distinct jl.hzMc) from GhJlhz as jl where jl.hzIfcj=? and jl.kuId in(select tea.kuId from Teacher as tea) and jl.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(state!=null){
			queryString=queryString+" and jl.auditState="+state+"";
		}else{
			queryString=queryString+" and jl.auditState is null";
		}
		List list=getHibernateTemplate().find(queryString, new Object[] {ifcj,kdid,kdid});
		if(list!=null&&list.size()>0){
			return (Long)list.get(0);
		}else{
			return 0l;
		}
	}

	public List findByMc(String name, Short ifcj, Short state) {
		// TODO Auto-generated method stub order by jl.hzMc,jl.kuId
		String queryString="from GhJlhz as jl where jl.hzIfcj=? and jl.hzMc=? order by jl.kuId";
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,name});
	}

	public List findByKdidAndCjAndState(Long kdid, Short ifcj, Short state) {
		String queryString="from GhJlhz as jl where jl.hzIfcj=? and jl.kuId in(select user.kuId from WkTUser as user where user.kdId=? or user.kdId in(select d.kdId from WkTDept as d where d.kdPid=?)) and jl.kuId in(select tea.kuId from Teacher as tea)";
		if(state!=null){
			queryString=queryString+" and jl.auditState="+state+"";
		}else{
			queryString=queryString+" and jl.auditState is null";
		}
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,kdid,kdid});
	}

	public boolean CheckOnlyOne(GhJlhz jlhz, String name,String hzdx, Short ifcj,Long kuid) {
	    String queryString="from GhJlhz as jl  where  jl.hzIfcj=? and jl.hzMc=? and jl.hzDx=? and jl.kuId=?";
	    List list=getHibernateTemplate().find(queryString, new Object[]{ifcj,name,hzdx,kuid});
	    if(jlhz!=null){
	    	if(list!=null&&list.size()>0){
	    		for(int i=0;i<list.size();i++){
	    			GhJlhz jl=(GhJlhz)list.get(i);
	    			if(jl.getHzId().intValue()!=jlhz.getHzId().intValue()){
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
	public List findBykdidAndYearAndKuidAndifcj(String year,Long kuid,Short ifcj,Short lx){
		String queryString="select distinct jl.hzMc from GhJlhz as jl where jl.hzIfcj=?  and jl.hzLx = ? and jl.kuId in(select tea.kuId from Teacher as tea)";
		if(year!=null){
			queryString=queryString+" and jl.hzKssj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString=queryString+" and jl.kuId="+kuid+"";
		}
		return getHibernateTemplate().find(queryString,new Object[]{ifcj,lx});
	}

}

