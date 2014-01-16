package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhPkpy;
import org.iti.gh.service.PkpyService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class PkpyServiceImpl extends BaseServiceImpl implements PkpyService {

	public List findByKuid(Long kuId) {
		String queryString = "from GhPkpy as pkpy where pkpy.kuId = ? ";
		return getHibernateTemplate().find(queryString,new Object[]{kuId});
	}

	public GhPkpy findBykuidAndMc(Long kuId, String mc) {
		String queryString = "from GhPkpy as pkpy where pkpy.kuId = ? and pkpy.pkName=? ";
		List list= getHibernateTemplate().find(queryString,new Object[]{kuId,mc});
		if(list!=null&&list.size()>0){
			return (GhPkpy)list.get(0);
		}else{
			return null;
		}
		 
	}

	
}
