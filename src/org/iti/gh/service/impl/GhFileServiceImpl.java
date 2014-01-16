package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.service.GhFileService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class GhFileServiceImpl extends BaseServiceImpl implements GhFileService {

	public List findByFxmIdandFType(Long fxmId, Integer xmType) {
		String queryString="from GhFile as file where file.fxmId = ? and file.xmType = ?";
		return getHibernateTemplate().find(queryString,new Object[]{fxmId,xmType});
	}
}
