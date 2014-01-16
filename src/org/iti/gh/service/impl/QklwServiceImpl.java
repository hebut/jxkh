package org.iti.gh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GhJslw;
import org.iti.gh.entity.GhQklw;
import org.iti.gh.service.QklwService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class QklwServiceImpl extends BaseServiceImpl implements QklwService {
	
	public List<GhQklw> findByKuid(Long kuid)
	{
		String queryString ="from GhQklw as lw where lw.kuId=?";
		return getHibernateTemplate().find(queryString,new Object[]{kuid});
	}
	public List findAllname(Long kuId, String kuname, Short lx, Short jslwtyype) {
		String queryString = "select distinct lwzl.lwMc,lwzl.lwId from GhQklw as lwzl where lwzl.kuId <> ? and lwzl.lwAll like '%" + kuname + "%'  and lwzl.lwId not in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype=?) and lwzl.lwLx=? ";
		return getHibernateTemplate().find(queryString, new Object[] { kuId, kuId, jslwtyype, lx });
	}

	public List findByKuidAndType(Long kuId, Short lx, Short jslwtyype) {
		String queryString = "from GhQklw as lwzl where lwzl.lwLx=? and lwzl.lwId in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype=?)";
		return getHibernateTemplate().find(queryString, new Object[] { lx, kuId, jslwtyype });
	}

	public boolean CheckByNameAndLxAndIssn(GhQklw qklw, String name, Short lx, String Issn) {
		String queryString = "from GhQklw as qklw where qklw.lwMc =? and qklw.lwLx=? and qklw.lwIssn=?";
		List list = getHibernateTemplate().find(queryString, new Object[] { name, lx, Issn });
		if (qklw != null) {
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					GhQklw lw = (GhQklw) list.get(i);
					if (lw.getLwId().intValue() != qklw.getLwId().intValue()) {
						return true;
					}
				}
			}
			return false;
		} else {
			if (list != null && list.size() > 0) {
				return true;
			}
			return false;
		}
	}

	public List findSumKdId(long kdid, Short lx, Short state) {
		String queryString = "from GhQklw as lwzl where lwzl.lwLx=? and lwzl.kuId in(select tea.kuId from Teacher as tea) and lwzl.kuId  in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
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

	public List findByMCAndKW(String lwmc, String lwkw) {
		String queryString = "from GhQklw where lwMc =? and lwKw=?";
		return getHibernateTemplate().find(queryString, new Object[] { lwmc, lwkw });
	}

	public List findbyMcOrKw(Long kuId, String kuname, Short lx, Short jslwtyype, String mc, String kw) {
		String queryString = "select distinct lwzl.lwMc,lwzl.lwId from GhQklw as lwzl where lwzl.kuId <> ?   and lwzl.lwId not in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype=?) and lwzl.lwLx=? and lwzl.lwMc like '%" + mc + "%' and lwzl.lwKw like '%" + kw + "%'";
		return getHibernateTemplate().find(queryString, new Object[] { kuId, kuId, jslwtyype, lx });
	}
	
	public List findByKdidAndYearAndKuidAndTypeAndRecordAndHx(String year,Long kuid,String record,Short hx,  Short jslwtyype){
		
		String queryString = "from GhQklw as lwzl where lwzl.lwLx="+GhQklw.KYLW+"";
		if (year != null) {
			queryString=queryString+" and lwzl.lwFbsj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString=queryString+" and lwzl.lwId in( select jl.lwzlId from GhJslw as jl where jl.kuId="+kuid+" and jl.jslwtype="+jslwtyype+" )";
		}else {
			queryString=queryString+" and lwzl.lwId in( select jl.lwzlId from GhJslw as jl where   jl.jslwtype="+jslwtyype+" )";
		}
		if(record!=null){
			queryString=queryString+" and lwzl.lwRecord='"+record+"'";
		}
		
		if(hx!=null){
			queryString=queryString+" and lwzl.lwCenter="+hx+"";
		}
		return getHibernateTemplate().find(queryString, new Object[]{});
	}
	
	public List findQtlw(String year,Long kuid,Short jslwtyype){
		String queryString = "from GhQklw as lwzl where  lwzl.lwRecord not in('"+GhQklw.SCI+"','"+GhQklw.EI+"','"+GhQklw.ISTP+"') and lwzl.lwCenter="+GhQklw.LWHX_NO+"";
		if (year != null) {
			queryString=queryString+" and lwzl.lwFbsj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString=queryString+" and lwzl.lwId in( select jl.lwzlId from GhJslw as jl where jl.kuId="+kuid+" and jl.jslwtype="+jslwtyype+"   )";
		}else {
			queryString=queryString+" and lwzl.lwId in( select jl.lwzlId from GhJslw as jl where   jl.jslwtype="+jslwtyype+"  )";
		}
		return getHibernateTemplate().find(queryString, new Object[]{});
		
	}
	public List findByKuidAndType(Long kuId,String name) {
		String queryString = "from GhQklw as lwzl where lwzl.lwId in(select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? ) and lwzl.lwAll  like '%"+name+"%'";
		return getHibernateTemplate().find(queryString, new Object[] {kuId });
	}

	public List<Long> findCountByKuidRecordSelfs(Long kuid, String record,int selfs) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select count(lw.lwId) from GhQklw as lw where lw.lwRecord in ("+record+") and lw.auditState = "+GhQklw.AUDIT_YES+
			" and lw.lwId in (select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype in ("+GhJslw.JYQK+","+GhJslw.KYQK+")";
		if(selfs>0)
		{
			queryString = queryString +" and jslw.lwSelfs = "+selfs;
		}
		queryString = queryString +")";
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuid);
		List<Long> list = query.list();
		session.close();
		return list;
	}

	public List<Long> findCountByKuidCenterSelfs(Long kuid, Short center,int selfs) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select count(lw.lwId) from GhQklw as lw where lw.lwCenter = ? and lw.auditState = "+GhQklw.AUDIT_YES+
			" and lw.lwId in (select jslw.lwzlId from GhJslw as jslw where jslw.kuId=? and jslw.jslwtype in ("+GhJslw.JYQK+","+GhJslw.KYQK+")";
		if(selfs>0)
		{
			queryString = queryString +" and jslw.lwSelfs = "+selfs;
		}
		queryString = queryString +")";
		Query query = session.createQuery(queryString);
		query.setShort(0, center);
		query.setParameter(1, kuid);
		List<Long> list = query.list();
		session.close();
		return list;
	}

	public int getWordsByKuidRecord(Long kuId, String record) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select sum(cast(jslw.lwWords as int)) from GhJslw as jslw where jslw.lwzlId in " +
				"(select lw.lwId from GhQklw as lw where lw.lwRecord in ("+record+") and lw.kuId = ? and jslw.jslwtype in ("+GhJslw.JYQK+","+GhJslw.KYQK+")" +
						" and lw.auditState = "+GhQklw.AUDIT_YES+" )";
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuId);
		List<Long> list = query.list();
		int words = 0;
		if(null!=list&&list.size()>0)
		{
			Long num = list.get(0);
			if(null!=num&&!"".equals(num))
				words = num.intValue();
		}
		session.close();
		return words;
	}

	public int getWordsByKuidIsCenter(Long kuid, Short center) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select sum(cast(jslw.lwWords as int)) from GhJslw as jslw where jslw.lwzlId in " +
				"(select lw.lwId from GhQklw as lw where lw.kuId = ? and lwCenter = ? and lw.auditState = "+GhQklw.AUDIT_YES+" )" +
				"and jslw.jslwtype in ("+GhJslw.JYQK+","+GhJslw.KYQK+")";
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuid);
		query.setParameter(1, center);
		List<Long> list = query.list();
		int words = 0;
		if(null!=list&&list.size()>0)
		{
			Long num = list.get(0);
			if(null!=num&&!"".equals(num))
				words = num.intValue();
		}
		session.close();
		return words;
	}
	
	public List<GhQklw> findByLwIds(String ids) {
		String queryString = "from GhQklw as qk where qk.lwId in ("+ids+") order by qk.lwFbsj desc";
		return getHibernateTemplate().find(queryString);
	}

}
