package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhHylw;
import org.iti.gh.service.HylwService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class HylwServiceImpl extends BaseServiceImpl implements HylwService {
	
	public List<GhHylw> findByKuid(Long kuid)
	{
		String queryString = "from GhHylw as lw where lw.kuId = ?";
		return getHibernateTemplate().find(queryString,new Object[]{kuid});
	}
	
	public List findAllname(Long kuId, String kuname, Short lx, Short jslwtyype) {
		String queryString = "select distinct lwzl.lwMc,lwzl.lwId from GhHylw as lwzl where lwzl.kuId <> ? and lwzl.lwAll like '%" + kuname + "%'  and lwzl.lwId not in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype=?) and lwzl.lwLx=? ";
		return getHibernateTemplate().find(queryString, new Object[] { kuId, kuId, jslwtyype, lx });
	}

	public List findByKuidAndType(Long kuId, Short lx, Short jslwtyype) {
		String queryString = "from GhHylw as lwzl where lwzl.lwLx=? and lwzl.lwId in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype=?)";
		return getHibernateTemplate().find(queryString, new Object[] { lx, kuId, jslwtyype });
	}

	public boolean CheckOnlyOne(GhHylw hylw, String mc, Short lx, String kw) {
		String queryString = "from GhHylw as lwzl where lwzl.lwMc=? and lwzl.lwLx=? and lwzl.lwKw=?  ";
		List list = getHibernateTemplate().find(queryString, new Object[] { mc, lx, kw });
		if (hylw != null) {
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					GhHylw lw = (GhHylw) list.get(i);
					if (hylw.getLwId().intValue() != lw.getLwId().intValue()) {
						return true;
					}
				}
			}
			return false;
		} else {
			if (list != null && list.size() > 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	public List findSumKdId(long kdid, Short lx, Short state) {
		String queryString = "from GhHylw as lwzl where lwzl.lwLx=? and lwzl.kuId in(select tea.kuId from Teacher as tea) and lwzl.kuId  in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if (state != null) {
			queryString = queryString + " and lwzl.auditState=" + state + " ";
		} else {
			queryString = queryString + " and lwzl.auditState is null ";
		}
		List list = getHibernateTemplate().find(queryString, new Object[] { lx, kdid, kdid });
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public List findByMCAndKW(String lwmc, String lwkw, Short lx) {
		String queryString = "from GhHylw where lwMc =? and lwKw=? and lwLx=?";
		return getHibernateTemplate().find(queryString, new Object[] { lwmc, lwkw, lx });
	}

	public List findbyMcOrKw(Long kuId, String kuname, Short lx, Short jslwtyype, String mc, String kw) {
		String queryString = "select distinct lwzl.lwMc,lwzl.lwId from GhHylw as lwzl where lwzl.kuId <> ?   and lwzl.lwId not in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype=?) and lwzl.lwLx=? and lwzl.lwMc like '%" + mc + "%' and lwzl.lwKw like '%" + kw + "%'";
		return getHibernateTemplate().find(queryString, new Object[] { kuId, kuId, jslwtyype, lx });
	}

	public List findtjlw( String year, Short jslwtyype, String record,Long kuid) {
		String queryString="from GhHylw as lw where   lw.lwRecord="+record+" ";
		if(year!=null){
			queryString = queryString +" and lw.lwFbsj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString = queryString + " and lw.lwId in(select jl.lwzlId from GhJslw as jl where jl.kuId ="+kuid+" and jl.jslwtype="+jslwtyype+" )";
		}else{
			queryString = queryString + " and lw.lwId in(select jl.lwzlId from GhJslw as jl where jl.jslwtype="+jslwtyype+")";
		}
		List list=getHibernateTemplate().find(queryString, new Object[]{});
		if(list!=null&&list.size()>0){
			return list;
		}
		else return null;
	}
	public List findQtlw( String year, Short jslwtyype,Long kuid){
		String queryString="from GhHylw as lw where   lw.lwRecord!='"+GhHylw.SCI+"' and lw.lwRecord!='"+GhHylw.EI+"'  and lw.lwRecord!='"+GhHylw.ISTP+"' ";
		if(year!=null){
			queryString = queryString +" and lw.lwFbsj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString = queryString + " and lw.lwId in(select jl.lwzlId from GhJslw as jl where jl.kuId ="+kuid+" and jl.jslwtype="+jslwtyype+")";
		}else{
			queryString = queryString + " and lw.lwId in(select jl.lwzlId from GhJslw as jl where jl.jslwtype="+jslwtyype+")";
		}
		List list=getHibernateTemplate().find(queryString, new Object[]{});
		if(list!=null&&list.size()>0){
			return list;
		}
		else return null;
	}

	public List<GhHylw> findByLwIds(String ids) {
		String queryString = "from GhHylw as hy where hy.lwId in ("+ids+") order by hy.lwFbsj desc";
		return getHibernateTemplate().find(queryString);
	}
	
}
