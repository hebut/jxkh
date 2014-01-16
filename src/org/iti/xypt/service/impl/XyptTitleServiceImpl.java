package org.iti.xypt.service.impl;

import java.util.List;

import org.iti.xypt.entity.Title;
import org.iti.xypt.service.XyptTitleService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class XyptTitleServiceImpl extends BaseServiceImpl implements XyptTitleService {

	public Title findByTId(Integer TId) {
		String queryString = "from Title as t where t.tiId=? ";
		return (Title) getHibernateTemplate().find(queryString, TId);
		
	}
	public List findFirstTitles() {
		String queryString = "from Title as t where t.tiId like '__' order by t.tiId";
		return getHibernateTemplate().find(queryString);
	}


 
	public List findByPtid(String pid) {
		String queryString = "from Title as t where t.ptiId=? order by t.tiId desc";
		return getHibernateTemplate().find(queryString,new Object[]{pid});
	}

	public List findByTname(String tname) {
		String queryString = "from Title as t where t.tiName=?";
		return find(queryString,new Object[]{tname});
	}

	
	public List findBytid(String tid){
		String queryString = "from Title as t where t.tiId=? ";
		return  getHibernateTemplate().find(queryString,new Object[]{tid});
	}
}
