package com.uniwin.asm.personal.service.impl;

import java.util.List;

import com.uniwin.asm.personal.entity.DocList;
import com.uniwin.asm.personal.service.DocListService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class DocListServiceImpl extends BaseServiceImpl implements
		DocListService {

	public List<DocList> findByDtid(Long dtid) {
		String sql="from DocList as model where model.dtId = ? order by model.dlId desc";
		return (List<DocList>)getHibernateTemplate().find(sql, dtid);
	}

	public List<DocList> findByDtnameAndKuid(String name, Long kuid) {
		String sql="from DocList as model where model.dlKuid = ? and model.dlName like '%"+name+"%'";
		return (List<DocList>)getHibernateTemplate().find(sql, kuid);
	}

}
