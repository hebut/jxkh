package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WKTHtmlSign;

import com.uniwin.basehs.service.BaseService;


public interface SignService extends BaseService{
	
	public WKTHtmlSign findBySignValue(String signValue);
	public List<WKTHtmlSign> findAll();
}
