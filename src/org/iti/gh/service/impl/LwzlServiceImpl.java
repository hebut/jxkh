package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.LwzlService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class LwzlServiceImpl extends BaseServiceImpl implements LwzlService {

	public List findByKuid(Long kuId, Short lx) {
		String queryString = "from GhLwzl as lwzl where lwzl.kuId = ? and lwzl.lwLx=?"; 
		return getHibernateTemplate().find(queryString,new Object[]{kuId,lx});
	}

	public List findByKdId(long kdid, Short lx) {
		String queryString="select distinct lwzl.lwMc from GhLwzl as lwzl where lwzl.lwLx=? and lwzl.kuId  in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		return getHibernateTemplate().find(queryString, new Object[] {lx,kdid,kdid});
	}

	public List findSumKdId(long kdid, Short lx, Short type,Short state) {
		String queryString="from GhLwzl as lwzl where lwzl.lwLx=? and lwzl.kuId in(select tea.kuId from Teacher as tea) and lwzl.kuId  in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(type!=null){
			queryString=queryString+" and lwzl.lwType="+type+"";
		}
		if(state!=null){
			queryString=queryString +" and lwzl.auditState="+state+" ";
		}else{
			queryString=queryString +" and lwzl.auditState is null ";
		}
		
		List list=getHibernateTemplate().find(queryString, new Object[] {lx,kdid,kdid});
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	public List findByMc(String name, Short lx) {
		String queryString = "from GhLwzl as lwzl where lwzl.lwMc = ? and lwzl.lwLx=?  order by lwzl.lwGrpm,lwzl.kuId"; 
		return getHibernateTemplate().find(queryString,new Object[]{name,lx});
	}

	public List findByKuidAndType(Long kuId, Short lx, Short type,Short jslwtyype) {
		String queryString = "from GhLwzl as lwzl where lwzl.lwLx=? and lwzl.lwId in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype=?)"; 
		if(type!=null){
			queryString=queryString+"and lwzl.lwType="+type+" ";
		}else{
			queryString=queryString+"and lwzl.lwType is null ";
		}
		return getHibernateTemplate().find(queryString,new Object[]{lx,kuId,jslwtyype});
	
	}

	public List findAllname(Long kuId, String kuname, Short lx, Short type,Short jslwtyype) {
		String queryString = "select distinct lwzl.lwMc,lwzl.lwId from GhLwzl as lwzl where lwzl.kuId <> ? and lwzl.lwAll like '%"+kuname+"%'  and lwzl.lwId not in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype=?) and lwzl.lwLx=? "; 
		if(type!=null){
			queryString=queryString+"and lwzl.lwType="+type+"";
		}else{
			queryString=queryString+"and lwzl.lwType is null";
		}
		return getHibernateTemplate().find(queryString,new Object[]{kuId,kuId,jslwtyype,lx});
	}

	public List findByKdidAndLxAndState(Long kdid, Short lx,Short type, Short state) {
		 String queryString="from GhLwzl as lwzl where lwzl.lwLx=?  and lwzl.kuId in(select xu.id.kuId from XyUserrole as xu where xu.id.krId=(select r.krId from WkTRole as r where r.krName='ΩÃ ¶' and r.kdId=(select dep.kdSchid from WkTDept as dep where dep.kdId=?)) and (xu.kdId=? or xu.kdId in( select d.kdId from WkTDept as d where d.kdPid=?)))";
		if(type!=null){
			queryString=queryString+" and lwzl.lwType="+type+"";
		}else{
			queryString=queryString+" and lwzl.lwType is null";
		}
		 if(state!=null){
			queryString=queryString +" and lwzl.auditState="+state+" ";
		}else{
			queryString=queryString +" and lwzl.auditState is null ";
		}
		return getHibernateTemplate().find(queryString,new Object[]{lx,kdid,kdid,kdid});
	}

}
