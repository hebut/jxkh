package com.uniwin.framework.service.impl;

import java.util.List;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.service.DocService;

public class DocServiceImpl extends BaseServiceImpl implements DocService {
	public List findByKdid(List deplist,List rolelist) {
		StringBuffer queryString = new StringBuffer( "from WkTDoc as doc left join fetch doc.wktroles c where c.krId in (");
		for (int i = 0; i < rolelist.size(); i++) {
			Long role = (Long) rolelist.get(i);
			queryString.append(role);
			if (i < rolelist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") and doc.docKdid in ( ");
			for (int i = 0; i < deplist.size(); i++) {
				Long dept = (Long) deplist.get(i);
				queryString.append(dept);
				if (i < deplist.size() - 1) {
					queryString.append(",");
				}
			}
			queryString.append(") ");
		 return getHibernateTemplate().find(queryString.toString());
	}
	public List findbykuid(Long kuid){
		  String queryString="from WkTDoc as wd where wd.docKuid=? order by wd.doctime desc ";
		  return getHibernateTemplate().find(queryString, new Object[] {kuid});
		   
	   }
	public List findByKdidkrid(Long kdid, Long krid) {
		String queryString="from WkTDoc as doc left join fetch doc.wktroles c where c.krId=? and doc.docKdid = ?)";
		return getHibernateTemplate().find(queryString,new Object[]{krid,kdid});
	}
}
