package org.iti.xypt.service.impl;

import java.util.List;

import org.iti.xypt.service.FudaoService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class FudaoServiceImpl extends BaseServiceImpl implements FudaoService {
	public List findClassByKuIdAndSchid(Long KuId,Long Schid) {
		String queryString = "from XyFudao as fd where fd.id.kuId=? and fd.id.clId in (select cla.clId from XyClass as cla where cla.kdId in(select dept.kdId from WkTDept as dept where dept.kdPid in(select dept.kdId from WkTDept as dept where dept.kdPid=?) ) )";
		return getHibernateTemplate().find(queryString, new Object[] { KuId,Schid });
	}

	public List findClassByKdId(Long KdId) {
		String queryString = "from XyClass as cla where cla.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid=? and dept.kdType=1) order by cla.clGrade desc, cla.clId";
		return getHibernateTemplate().find(queryString, new Object[] { KdId });
	}

	public List findClassByKuIdAndKdid(Long KuId ,Long Kdid) {
		String queryString = "from XyClass as cla where cla.clId in (select fd.id.clId from XyFudao as fd where fd.id.kuId=? ) and cla.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid in(select dept.kdId from WkTDept as dept where dept.kdPid=?) ) order by cla.clGrade,cla.kdId,cla.clName";
		return getHibernateTemplate().find(queryString, new Object[] { KuId ,Kdid});
	}
	
	public List findByKuidAndSchidAndGrade(Long KuId,Long Schid,Integer grade){
		String queryString = "from XyFudao as fd where fd.id.kuId=? and fd.id.clId in (select cla.clId from XyClass as cla where cla.kdId in(select dept.kdId from WkTDept as dept where dept.kdPid in(select dept.kdId from WkTDept as dept where dept.kdPid=?) ) and cla.clGrade=? )";
		return getHibernateTemplate().find(queryString, new Object[] { KuId,Schid,grade });
	}
}
