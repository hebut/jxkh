package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.List;

import org.iti.xypt.personal.infoCollect.service.LinkService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;


public class LinkServiceImpl extends BaseServiceImpl implements LinkService{

	public List findbyUrl(String url) {
		String queryString ="from WkTLinkurl as model where model.klUrl=?";
		return getHibernateTemplate().find(queryString, url);
	}

	public List findByKeId(Long id) {
		String queryString ="from WkTLinkurl as model where model.keId=?";
		return getHibernateTemplate().find(queryString, id);
	}

	public List findByIdAndUrl(String url, Long id) {
		// TODO Auto-generated method stub
		String queryString="from WkTLinkurl as model where model.klUrl=? and model.keId=?";
		return getHibernateTemplate().find(queryString,new Object[]{url,id});
	}

	public List findByIdAndStatus(Long id, Long id2) {
		// TODO Auto-generated method stub
		String queryString="from WkTLinkurl as model where model.keId=? and model.klStatus=?";
		return getHibernateTemplate().find(queryString,new Object[]{id,id2});
	}

}
