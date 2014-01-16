package com.uniwin.framework.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface DocService extends BaseService {
	public List findByKdid(List deplist,List rolelist);
	public List findByKdidkrid(Long kdid,Long krid);
	public List findbykuid(Long kuid);
}
