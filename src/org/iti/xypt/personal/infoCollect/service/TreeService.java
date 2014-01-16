package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;


public interface TreeService extends BaseService{
	public List findAll();
	public List findById(Long id);
}
