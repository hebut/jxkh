package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WKTHtmlSign;
import org.iti.xypt.personal.infoCollect.service.SignService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;


public class SignServiceImpl extends BaseServiceImpl implements SignService{

	public WKTHtmlSign findBySignValue(String signValue) {
		String query="from WKTHtmlSign as model where model.ksValue like '%"+signValue+"%'";
		return (WKTHtmlSign) getHibernateTemplate().find(query).get(0);
	}

	public List<WKTHtmlSign> findAll() {
		String query="from WKTHtmlSign ORDER BY ksOrderid ASC";
		return getHibernateTemplate().find(query);
	}

}
