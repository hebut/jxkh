package com.uniwin.framework.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface WebsiteService extends BaseService {
	public List findByLidAndPassword(String lid, String pw);
	public List findByMac(String mac);
	public List findByLidAndPasswordAndMac(String lid, String pw,String mac);
}
