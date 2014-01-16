package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhZz;
import org.iti.gh.service.ZzService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ZzServiceImpl extends BaseServiceImpl implements ZzService {

	public List findAllname(Long kuId, String kuname, Short lx, Short jszztyype) {
	String queryString=" from GhZz as zz where zz.kuId <> ? and (zz.zzZb like '%"+kuname+"%' or zz.zzFzb like '%"+kuname+"%' or zz.zzBz like '%"+kuname+"%' ) and zz.zzLx=? and zz.zzId not in(select jszz.zzId from GhJszz as jszz where jszz.kuId=? and jszz.jszzType=?)";
	return getHibernateTemplate().find(queryString, new Object[]{kuId,lx,kuId,jszztyype});
	}

	public List findByKuidAndType(Long kuId, Short lx, Short jszztyype) {
	String queryString="from GhZz as zz where zz.zzLx=? and zz.zzId in( select jszz.zzId from GhJszz as jszz where jszz.kuId=? and jszz.jszzType=?)";
	return  getHibernateTemplate().find(queryString, new Object[]{lx,kuId,jszztyype});
	}

	public boolean CheckOnlyOne(GhZz zz, String name, Short lx, String zb) {
		String queryString="from GhZz as zz where zz.zzMc=? and zz.zzLx=? and zz.zzZb=?";
		List list=getHibernateTemplate().find(queryString, new Object[]{name,lx,zb});
		if(zz!=null){
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					GhZz z=(GhZz)list.get(i);
					if(z.getZzId().intValue()!=zz.getZzId().intValue()){
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
	public List findSumKdId(long kdid, Short lx, Short state) {
		String queryString="from GhZz as zz where zz.zzLx=? and zz.kuId in(select tea.kuId from Teacher as tea) and zz.kuId  in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(state!=null){
			queryString=queryString +" and zz.auditState="+state+" ";
		}else{
			queryString=queryString +" and zz.auditState is null ";
		}
		
		List list=getHibernateTemplate().find(queryString, new Object[] {lx,kdid,kdid});
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	public GhZz findByJcnameAndZbAndLx(String name, Short lx, String zb){
		String queryString="from GhZz as zz where zz.zzMc=? and zz.zzLx=? and zz.zzZb=? ";
		List list=getHibernateTemplate().find(queryString, new Object[]{name,lx,zb});
		if(list!=null&&list.size()>0){
			return (GhZz)list.get(0);
		}else{
			return null;
		}
	}

	public List findByKuidAndJcmcAndZbAndLxAndType(Long kuid, String jcname,
			String zb, Short lx, Short type) {
		String queryString="from GhZz as zz where zz.zzMc like '%"+jcname+"%'  and zz.zzLx=? and zz.zzId not in(select jszz.zzId from GhJszz as jszz where jszz.kuId=? and  jszz.jszzType=?) ";
	
		return getHibernateTemplate().find(queryString, new Object[]{zb,lx,kuid,type});
	}
	public List findtjzj(Long kdid, String year, Short lx,Long kuid) {
		String queryString="from GhZz as zz where zz.kuId  in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) and zz.auditState="+GhZz.AUDIT_YES+"";
		if(year!=null){
			queryString = queryString +" and zz.zzPublitime like '"+year+"%'";
		}
		if(kuid!=null){
			queryString = queryString + " and zz.zzId in(select jz.zzId from GhJszz as jz where jz.kuId ="+kuid+" and jz.jszzType=?)";
		}else{
			queryString = queryString + " and zz.zzId in(select jz.zzId from GhJszz as jz where jz.jszzType=?)";
		}
		return getHibernateTemplate().find(queryString, new Object[]{kdid,kdid,lx});
		/*if(list!=null&&list.size()>0){
			return list;
		}
		else return null;*/
	}

	public List<GhZz> findByZzIds(String ids) {
		String queryString = "from GhZz as zz where zz.zzId in ("+ids+") order by zz.zzPublitime";
		return getHibernateTemplate().find(queryString);
	}
}
