package org.iti.xypt.personal.infoCollect.service.Impl;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.service.OriNewsService;
import org.zkoss.zk.ui.Session;
import org.zkoss.zkplus.hibernate.HibernateUtil;

import com.uniwin.basehs.service.impl.BaseServiceImpl;



public  class OriNewsServiceImpl extends BaseServiceImpl implements OriNewsService {

	public List getNewsOfOrinfo(Long keid) {
		String queryString = "from WkTOrinfo as i where i.keId=?";
		return getHibernateTemplate().find(queryString, new Object[]{keid});
	}

	public WkTOrinfo getOriInfo(Long koid) {
		String queryString = "from WkTOrinfo as i where i.koiId=?";
		return (WkTOrinfo) getHibernateTemplate().find(queryString, new Object[]{koid}).get(0);
	}

	public List getOriInfocnt(Long koid) {
		String queryString = "from WkTOrinfocnt as i where i.id.koiId=?";
		return getHibernateTemplate().find(queryString, new Object[]{koid});
	}
	public List getSearchInfo(Long keid,String d1,String d2,String sign,String keys,String sources)
	{   

		String queryString="from WkTOrinfo  as i where i.keId=?";
	    if(d1!=null||d1!="")
	    {
	    	queryString+=" and i.koiPtime>=?";
	    }
	    if(d2!=null||d2!="")
	    {
	    	queryString+=" and i.koiPtime<=?";
	    }
	    if(sign.equals("0"))
	    {
	    	queryString+=" and i.koiId in (select ic.koiId from WkTOrinfocnt  as ic where  ic.id.koiContent like %"+keys+"%";
	    }
	    else if(sign.equals("1"))
	    {
	    	queryString+=" and i.koiMemo  like %"+keys+"%";
	    }
	    else if(sign.equals("2"))
	    {
	    	queryString+=" and i.koiTitle  like %"+keys+"%";
	    }
	    if(sources!=null||sources!="")
	    {
	    	queryString+=" and i.koiSource  like %"+sources+"%";
	    }
		return getHibernateTemplate().find(queryString);
	}
	public List getOrifile(Long koid)
	{
		String queryString = "from WkTOrifile as i where i.id.koiId=?";
		return getHibernateTemplate().find(queryString, new Object[]{koid});
	}
	public WkTExtractask getTask(Long keid)
	{
		String queryString = "from WkTExtractask as i where i.keId=?";
		return (WkTExtractask) getHibernateTemplate().find(queryString, new Object[]{keid}).get(0);
	}

	@Override
	public List findAllInfo(Long keid) {
		// TODO Auto-generated method stub
		String queryString="from WkTOrinfo as model where model.keId=? ORDER BY model.koiId DESC";
		return getHibernateTemplate().find(queryString,new Object[]{keid});
	}

	@Override
	public List findReadInfo(Long keid,Long kuid) {
		// TODO Auto-generated method stub
		String query="from WkTNewsRead as d where d.id.keId=? and d.id.kuId =? ";
		return getHibernateTemplate().find(query,new Object[]{keid,kuid});
	}
}