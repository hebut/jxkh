package com.uniwin.asm.personal.service.impl;

import java.util.List;

import com.uniwin.asm.personal.service.UsrfavService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class UsrfavServiceImpl extends BaseServiceImpl implements UsrfavService {
	public List getUsrfavList(Long kuid) {
		String queryString = "from WkTUsrfav as fav where fav.kuId  in(select u.kuId from WkTUser as u where u.kuId = ?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public List getUsrfavTitleList(Long kuid) {
		String queryString = "from WkTUsrfav as fav where fav.ktId!='0' and  fav.kuId  in(select u.kuId from WkTUser as u where u.kuId = ?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public List getUsrfavDiyList(Long kuid) {
		String queryString = "from WkTUsrfav as fav where fav.ktId ='0' and  fav.kuId  in(select u.kuId from WkTUser as u where u.kuId = ?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}
}
