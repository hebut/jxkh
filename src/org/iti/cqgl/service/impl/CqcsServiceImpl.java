package org.iti.cqgl.service.impl;
import java.util.List;


import org.iti.bysj.entity.BsBatch;
import org.iti.cqgl.entity.CqCqcs;

import org.iti.cqgl.entity.CqCqcs;

import org.iti.cqgl.service.CqcsService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class CqcsServiceImpl extends BaseServiceImpl implements CqcsService {

	public CqCqcs findByXlid(Long xlId) {
		String queryString="from CqCqcs as cc where cc.xlId =?";
		List cslist=getHibernateTemplate().find(queryString, new Object[]{xlId});
		if(cslist!=null&&cslist.size()>0){
			CqCqcs cc=(CqCqcs)cslist.get(0);
			return cc;
		}else{
			return null;
		}
		
	}
	public List findByYearAndTerm(String year, Short term, Long schid) {
		String queryString="from CqCqcs as cn where cn.xlId in(select jc.id from JxCalendar as jc where jc.kdId=? ";
	    if(year!=null&&year.length()!=0){
	    	queryString=queryString+" and jc.CYear=? ";
	    }else{
	    	queryString=queryString+" and jc.CYear is not null ";
	    }
	    if(term!=null&&term!=2){
	    	queryString=queryString+" and jc.term=? ) ";
	    	if(year!=null&&year.length()!=0){
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,year,term});
	    	}else{
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,term});
	    	}
	    }else{
	    	queryString=queryString+" and jc.term is not null )";
	    	if(year!=null&&year.length()!=0){
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid,year});
	    	}else{
	    		return  getHibernateTemplate().find(queryString, new Object[]{schid});
	    	}
	    }
	}

	public CqCqcs findByXlId(Long xlId) {
		String queryString = "from  CqCqcs as cn where cn.xlId=?";
		List bblist = getHibernateTemplate().find(queryString, new Object[] {xlId });
		if (bblist.size() > 0) {
			return (CqCqcs) bblist.get(0);
		} else {
			return null;
		}
	}
	public Long findByxlYearAndTerm(String year,Short term,Long schid) {
		String queryString = "select cn.ccZczscs from  CqCqcs as cn where cn.xlId in (select jc.id from JxCalendar as jc where jc.kdId=? and jc.CYear=?and jc.term=?)";
		List bblist = getHibernateTemplate().find(queryString, new Object[] {schid,year,term });
		if (bblist.size() > 0) {
			return (Long) bblist.get(0);
		} else {
			return Long.parseLong("0");
		}
	}
	public Long findKyByxlYearAndTerm(String year, Short term, Long schid) {
		String queryString = "select cn.ccKyhdzscs from  CqCqcs as cn where cn.xlId in (select jc.id from JxCalendar as jc where jc.kdId=? and jc.CYear=?and jc.term=?)";
		List bblist = getHibernateTemplate().find(queryString, new Object[] {schid,year,term });
		if (bblist.size() > 0) {
			return (Long) bblist.get(0);
		} else {
			return Long.parseLong("0");
		}
	}
	
	}
