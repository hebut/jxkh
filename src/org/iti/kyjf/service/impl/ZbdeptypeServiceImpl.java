package org.iti.kyjf.service.impl;

import java.util.List;

import org.iti.kyjf.entity.Zbdeptype;
import org.iti.kyjf.service.ZbdeptypeService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ZbdeptypeServiceImpl extends BaseServiceImpl implements
		ZbdeptypeService {
	public List queryByKdid(Long Kdid){
		 String queryString="from Zbdeptype as k where  k.kdId=?";
		 return getHibernateTemplate().find(queryString, new Object[]{Kdid});
	}

	public Zbdeptype queryByYearAndKdid(Integer year, Long kdid) {
		 String queryString="from Zbdeptype as k where  k.Zxyear=? and k.kdId=?";
		 List list= getHibernateTemplate().find(queryString, new Object[]{year,kdid});
		 if(list!=null&&list.size()>0){
			 return (Zbdeptype)list.get(0);
		 }else {
			 return null;
		 }
	}

	 
}
