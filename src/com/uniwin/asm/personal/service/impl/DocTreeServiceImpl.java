package com.uniwin.asm.personal.service.impl;

import java.util.List;

import com.uniwin.asm.personal.entity.DocTree;
import com.uniwin.asm.personal.service.DocTreeService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class DocTreeServiceImpl extends BaseServiceImpl implements
		DocTreeService {

	public DocTree getRoot(Long dtkuid) {
		String queryString = "from DocTree as d where d.dtKuid = ? ";
		List list=getHibernateTemplate().find(queryString, new Object[] { dtkuid });
		if(list.size()==0)return new DocTree();
		else return (DocTree) list.get(0);
	}

	public List<DocTree> getChildernDocTree(Long dtpid) {
		String queryString="from DocTree as d where d.dtpId = ? order by d.dtId ";
		return getHibernateTemplate().find(queryString, new Object[]{dtpid});
	}

}
