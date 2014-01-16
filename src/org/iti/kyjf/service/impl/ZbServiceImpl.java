package org.iti.kyjf.service.impl;

import java.util.List;

import org.iti.kyjf.entity.Kyzb;
import org.iti.kyjf.service.ZbService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ZbServiceImpl extends BaseServiceImpl implements ZbService {

	public Kyzb findByYear(Integer year,Long kdid) {
	 String queryString="from Kyzb as k where k.Äê·Ý=? and k.kdId=?";
	 List list=getHibernateTemplate().find(queryString, new Object[]{year,kdid});
	 if(list!=null&&list.size()>0){
		return  (Kyzb)list.get(0);
	 }else{
		 return null;
	 }
	}
	public List findByKdid(Long kdid){
		 String queryString="from Kyzb as k where  k.kdId=?";
		 return getHibernateTemplate().find(queryString, new Object[]{kdid});
	}

}
