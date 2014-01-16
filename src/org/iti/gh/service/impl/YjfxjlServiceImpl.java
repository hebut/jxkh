package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.YjfxjlService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class YjfxjlServiceImpl extends BaseServiceImpl implements YjfxjlService {

	public List findByGyidAndGyType(Long gyid,Short type) {
		String queryString="select distinct jl.gyId from GhYjfxjl as jl where jl.gyId=? and jl.type=?";
		return getHibernateTemplate().find(queryString, new Object[]{gyid,type});
	}
	public List findTypeByGyid(Long gyid) {
		String queryString="select distinct jl.gjType from GhYjfxjl as jl where jl.gyId=? ";
		return getHibernateTemplate().find(queryString, new Object[]{gyid});
	}
	public List findByGyidAndtypeAndDjAndJbAndYear(Long gyid,Short type,Short dj,Short jb,Integer year) {
		String queryString=" from GhYjfxjl as jl where jl.gyId=? and jl.gjType=? and gjDj=? and gjJb=? and gjYear=?";
		return getHibernateTemplate().find(queryString, new Object[]{gyid,type,dj,jb,year});
	}
	public List findGjDjByGyidAndTypeAndGjjb(Long gyid,Short type,Short gjjb) {
		String queryString="select distinct jl.gjDj from GhYjfxjl as jl where jl.gyId=? and jl.gjType=? and gjJb=?";
		return getHibernateTemplate().find(queryString, new Object[]{gyid,type,gjjb});
	}
	public List findGjjbByGyidAndtype(Long gyid,Short type) {
		String queryString="select distinct jl.gjJb from GhYjfxjl as jl where jl.gyId=? and jl.gjType=? ";
		return getHibernateTemplate().find(queryString, new Object[]{gyid,type});
	}
}
