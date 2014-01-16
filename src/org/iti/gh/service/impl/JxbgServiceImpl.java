package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhJxbg;
import org.iti.gh.entity.GhXshy;
import org.iti.gh.service.JxbgService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JxbgServiceImpl extends BaseServiceImpl implements JxbgService {

	public List findByKdIdAndCj(long kdid,Short ifcj,Short state) {
		String queryString="from GhJxbg as jx where jx.jxIfcj=? and jx.kuId in(select tea.kuId from Teacher as tea) and jx.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(state!=null){
			queryString=queryString+" and jx.auditState="+state+"";
		}else{
			queryString=queryString+" and jx.auditState is null";
		}
		return getHibernateTemplate().find(queryString, new Object[] {ifcj, kdid,kdid });
	}

	public List findByKuidAndCj(long kuid,Short ifcj) {
		String queryString="from GhJxbg as jx where jx.jxIfcj=? and jx.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,kuid });
	}

	public List findByKdidAndCjAndState(Long kdid, Short ifcj, Short state) {
		String queryString="from  GhJxbg as jx where jx.jxIfcj=? and jx.kuId in(select user.kuId from WkTUser as user where user.kdId=? or user.kdId in(select d.kdId from WkTDept as d where d.kdPid=?)) and jx.kuId in(select tea.kuId from Teacher as tea)";
		if(state!=null){
			queryString=queryString+" and jx.auditState="+state+"";
		}else{
			queryString=queryString+" and jx.auditState is null";
		}
		return getHibernateTemplate().find(queryString, new Object[] {ifcj,kdid,kdid});
	}

	public boolean CheckOnlyOne(GhJxbg jxbg, String bgname, String jxhymc,
			Short ifcj,Long kuid) {
		String queryString="from GhJxbg as jx where jx.kuId=? and jx.jxBgmc=? and jx.jxHymc=? and jx.jxIfcj=?";
		List list =getHibernateTemplate().find(queryString, new Object[]{kuid,bgname,jxhymc,ifcj});
		if(jxbg!=null){
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					GhJxbg jx =(GhJxbg)list.get(i);
					if(jx.getJxId().intValue()!=jxbg.getJxId().intValue()){
						return true;
					}
				}
			}
			return false;
		}else{
			if(list!=null&&list.size()>0){
				return true;
			}
			return false;
		}
	}
	public List findByKdidAndYearAndKuidAndIfcj(String year,Long kuid,Short ifcj,Short lx){
		String queryString="from GhJxbg as jx where jx.jxIfcj=? and jx.jxLx=? and jx.kuId in(select tea.kuId from Teacher as tea)";
		if(year!=null){
			queryString=queryString+" and jx.jxSj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString=queryString+" and jx.kuId="+kuid+"";
		}
		return getHibernateTemplate().find(queryString,new Object[]{ifcj,lx});
	}
}
