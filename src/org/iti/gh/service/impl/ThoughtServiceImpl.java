package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.TeaThought;
import org.iti.gh.service.ThoughtService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ThoughtServiceImpl extends BaseServiceImpl implements
		ThoughtService {

	public TeaThought findByKuidAndYear(Long kuid, String year) {
	     String queryString="from TeaThought as tt where tt.kuId=? and tt.ttYear=?";
	     List list=getHibernateTemplate().find(queryString, new Object[]{kuid,year});
		 if(list!=null&&list.size()>0){
			 return (TeaThought)list.get(0);
			 
		 }else {
			 return null;
		 }
	}

}
