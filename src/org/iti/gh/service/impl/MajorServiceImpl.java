package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.MajorService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class MajorServiceImpl extends BaseServiceImpl implements MajorService{

	public List findFirstmajor(){
		String queryString ="from GhZy as zy where zy.zyId like'__'";
		return getHibernateTemplate().find(queryString);
	}
	public List findByPtid(String pzyid){
		String queryString ="from GhZy as zy where zy.zyPid=?";
		return getHibernateTemplate().find(queryString, new Object[] {pzyid});
	}
	public List findByZyid(String zyid){
		String queryString ="from GhZy as zy where zy.zyId=?";
		return getHibernateTemplate().find(queryString, new Object[] {zyid});
	}
}
