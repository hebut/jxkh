package org.iti.wp.service.impl;
 
import java.util.List;
 
import org.iti.wp.service.WpPhaseService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class WpPhaseServiceImpl extends BaseServiceImpl implements
		WpPhaseService {
 
	public List findbywtid(Long wtid) {
		String queryString="from WpPhase as wp where wp.wtId=?";
		return getHibernateTemplate().find(queryString, new Object[]{wtid});
	}

	  
	public List findphasebykdid(Long kdId) {
		String queryString="from WpPhase as wpphase where wpphase.wtId in (select type.wtId from WpType as type where type.kdId=?) order by wpphase.wpPsstart desc";
		return getHibernateTemplate().find(queryString, new Object[]{kdId});
		
	}


	public List findphasebykuid(Long kuId) {
		String queryString="from WpPhase as wpphase where wpphase.wpId in (select wfp.wpId from WpFilephase as wfp where wfp.wfpId in (select wf.wfpId from WpFile as wf where wf.kuId=?) ) order by wpphase.wpPsstart desc";
		return getHibernateTemplate().find(queryString, new Object[]{kuId});
	}


	public List findphasebykdidAndkuid(Long kdid,Long kuid) {
		String queryString="from WpPhase as wpphase where wpphase.wtId in (select type.wtId from WpType as type where type.kdId=?) and wpphase.wpId in(select wr.wpId from WpRelation as wr where wr.kuEid=?)order by wpphase.wpPsstart desc";
		return getHibernateTemplate().find(queryString, new Object[]{kdid,kuid});
		
	}

	 
}
