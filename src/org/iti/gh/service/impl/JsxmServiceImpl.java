package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhYjfx;
import org.iti.gh.service.JsxmService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JsxmServiceImpl extends BaseServiceImpl implements JsxmService {

	public GhJsxm findByXmidAndKuidAndType(Long xmId, Long kuId, Integer Type) {
		String queryString="from GhJsxm as jx where jx.kyId=? and jx.kuId=? and jx.jsxmType=?";
		List jxlist= getHibernateTemplate().find(queryString, new Object[]{xmId,kuId,Type});
		if(jxlist!=null&&jxlist.size()>0){
			return (GhJsxm)jxlist.get(0);
		}else{
			return null;
		}
		
	}

	public List findByXmidAndtype(Long xmid,Integer Type){
		String queryString="from GhJsxm as jx where jx.kyId=?  and jx.jsxmType=?";
		return getHibernateTemplate().find(queryString, new Object[]{xmid,Type});
	}
	
	public GhYjfx getYjfx(Long yjfx)
	{
		String queryString="from GhYjfx as fx where fx.yjId=?";
		return (GhYjfx) getHibernateTemplate().find(queryString, new Object[]{yjfx}).get(0);
	}
}
