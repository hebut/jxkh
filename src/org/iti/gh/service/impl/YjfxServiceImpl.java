package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.YjfxService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class YjfxServiceImpl extends BaseServiceImpl implements YjfxService {

	public List findByKdid(Long kdId) {
		String queryString = "from GhYjfx as yjfx where yjfx.kdId=?";
		return getHibernateTemplate().find(queryString,new Object[]{kdId});
	}

	public List<GhYjfx> findByKdidAndGyname(Long kdid,String gyname) {
		String queryString = "from GhYjfx as yjfx where yjfx.kdId=? and  yjfx.gyName=?";
		return getHibernateTemplate().find(queryString,new Object[]{kdid,gyname});
	}

	public List<GhYjfx> findByKdidAndGynameAndNotGyid(Long kdid, String gyname,
			Long gyid) {
		String queryString = "from GhYjfx as yjfx where yjfx.kdId=? and  yjfx.gyName=? and yjfx.gyId<>?";
		return getHibernateTemplate().find(queryString,new Object[]{kdid,gyname,gyid});
	}
	public List<GhYjfx> findByKuid(Long kuId) {
		String queryString = "from GhYjfx as yjfx where yjfx.gyId in(select gu.id.gyId from GhUseryjfx as gu where gu.id.kuId=?)";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}
}
