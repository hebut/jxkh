package com.uniwin.framework.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface DocTypeService extends BaseService {
	public List findByKdid(Long kdid);
}
