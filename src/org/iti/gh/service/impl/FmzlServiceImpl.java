package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GhFmzl;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.service.FmzlService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class FmzlServiceImpl extends BaseServiceImpl implements FmzlService {

	public List findByKdId(long kdid) {
		String queryString="from GhFmzl as fm where fm.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		return getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
	}

	public List findByKuid(Long kuId) {
		String queryString = "from GhFmzl as fm where fm.fmId  in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype="+GhJslw.FMZL+" ) ";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}

	public List findSumKdId(long kdid,Short state) {
		String queryString="from GhFmzl as fm where  fm.kuId in(select tea.kuId from Teacher as tea) and fm.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(state!=null){
			queryString=queryString+" and fm.auditState="+state+"";
		}else{
			queryString=queryString+" and fm.auditState is null ";
		}
		List list=getHibernateTemplate().find(queryString, new Object[] {kdid,kdid});
		if(list!=null&&list.size()>0){
			return  list;
		}else{
			return null;
		}
		
	}

	public List findByMc(String name) {
		String queryString = "from GhFmzl as fm where fm.fmMc = ? order by fm.kuId";
		return getHibernateTemplate().find(queryString,new Object[]{name});
	}

	public List findByKuidAndUname(Long kuid, String name) {
		String queryString = "from GhFmzl as fm where fm.fmInventor like '%"+name+"%' and fm.fmMc not in(select fmzl.fmMc from GhFmzl as fmzl where fmzl.kuId=? ) and fm.fmId not in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype="+GhJslw.FMZL+" ) ";
		return getHibernateTemplate().find(queryString,new Object[]{kuid,kuid});
	}

	public List findByKdidAndState(Long kdid, Short state) {
		String queryString="from GhFmzl as fm where  kuId in(select user.kuId from WkTUser as user where user.kdId=? or user.kdId in( select d.kdId from WkTDept as d where d.kdPid=?)) and fm.kuId in(select tea.kuId from Teacher as tea)";
		if(state!=null){
			queryString=queryString+" and fm.auditState="+state+"";
		}else{
			queryString=queryString+" and fm.auditState is null ";
		}
		return getHibernateTemplate().find(queryString, new Object[]{kdid,kdid});
	}

	public boolean CheckOnlyOne(GhFmzl fmzl, String mc, String zlh) {
		String queryString="from GhFmzl as fm where fm.fmMc=?";
		if(zlh!=null&&!"".equals(zlh)){
			queryString=queryString+" and fm.fmSqh='"+zlh+"'";
		}
		List list=getHibernateTemplate().find(queryString, new Object[]{mc});
		if(fmzl!=null){
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					GhFmzl fm=(GhFmzl)list.get(i);
					if(fm.getFmId().intValue()!=fmzl.getFmId().intValue()){
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
	
	public List findByFmmcAndFmrname(String fmmc,String uname,Long kuid){
		String queryString="from GhFmzl as fm where fm.fmMc like '%"+fmmc+"%' and fm.fmInventor like '"+uname+"%' and fm.fmId not in(select jx.lwzlId from GhJslw as jx where jx.kuId=? and jx.jslwtype="+GhJslw.FMZL+")";
		return getHibernateTemplate().find(queryString,kuid);
	}
	
    public GhFmzl finByFmmc(String fmmc){
    	String queryString="from GhFmzl as fm where fm.fmMc=? ";
    	List list=getHibernateTemplate().find(queryString,fmmc);
    	if(list!=null&&list.size()>0){
    		return (GhFmzl)list.get(0);
    	}else {
    		return null;	
    	}
    }
    public List findBykdidAndYearAndKuid(String year,Long kuid,String type){
    	String queryString="from GhFmzl as fm where  fm.kuId in(select tea.kuId from Teacher as tea)";
    	if(year!=null){
    		queryString=queryString+" and fm.fmSj like '"+year+"%'";
    	}
    	if(kuid!=null){
    		queryString=queryString+" and fm.fmId in(select jx.lwzlId from GhJslw as jx where jx.kuId="+kuid+" and jx.jslwtype="+GhJslw.FMZL+")";
    	}
    	if(type!=null){
    		queryString=queryString+" and fm.fmTypes="+type+"";
    	}
    	return getHibernateTemplate().find(queryString, new Object[]{});
    	
    }

	public int getCountBykuidSelfs(Long kuid, int selfs) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select count(zl.fmId) from GhFmzl as zl where zl.fmId in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? ";
		if(selfs>0)
			queryString = queryString + " and jslw.lwSelfs = "+selfs;
		queryString = queryString + " and jslw.jslwtype ="+GhJslw.FMZL+") and zl.fmStatus = "+GhFmzl.STA_YES;
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuid);
		List<Long> list = query.list();
		int words = 0;
		if(null!=list&&list.size()>0)
		{
			words = list.get(0).intValue();
		}
		session.close();
		return words;
	}

	public List<GhFmzl> findByFmIds(String ids) {
		String queryString = "from GhFmzl as fm where fm.fmId in ("+ids+") order by fm.fmSj desc";
		return getHibernateTemplate().find(queryString);
	}
}
