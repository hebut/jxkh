package com.uniwin.framework.service.impl;


import java.util.List;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDocType;
import com.uniwin.framework.service.DocTypeService;

public class DocTypeServiceImpl extends BaseServiceImpl implements DocTypeService {

	public List<WkTDocType> findByKdid(Long kdid) {
		String sql="from WkTDocType as model where model.doctKdid = ?";
		return getHibernateTemplate().find(sql, kdid);
	}
	
}
