package org.iti.gh.service.impl;

import java.util.List;

import org.iti.gh.entity.GhCg;
import org.iti.gh.service.CgService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class CgServiceImpl extends BaseServiceImpl implements CgService {

	public List findByKdId(long kdid, Short lx) {
		String queryString="select  distinct cg.kyMc from GhCg as cg where cg.kyLx=? and cg.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		return getHibernateTemplate().find(queryString, new Object[] {lx,kdid,kdid});
	}

	public List findByKuid(Long kuId, Short lx,Integer type) {
		String queryString = "from GhCg as cg where  cg.kyLx=? and cg.kyId  in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)";
		return getHibernateTemplate().find(queryString,new Object[]{lx,kuId,type});
	}

	public List findSumKdId(long kdid, Short lx,Short state) {
		String queryString="from GhCg as cg where cg.kyLx=? and cg.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) and cg.kuId in(select tea.kuId from Teacher as tea)";
		if(state!=null){
			queryString=queryString+"and cg.auditState="+state+"";
		}else{
			queryString=queryString+"and cg.auditState is null";
		}
		List list=getHibernateTemplate().find(queryString, new Object[] {lx,kdid,kdid});
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	public List findByMc(String name, Short lx) {
		String queryString = "from GhCg as cg where cg.kyMc = ? and cg.kyLx=?  order by cg.kyGrpm";
		return getHibernateTemplate().find(queryString,new Object[]{name,lx});
	}
	public List findByKyNumAndKuId(String kynumber,Long kuId) {
		String queryString = "from GhCg as cg where cg.kyNumber = ? and cg.kuId=?";
		return getHibernateTemplate().find(queryString,new Object[]{kynumber,kuId});
	}

	public List findByKyXmidAndKuId(Long kyxmid, Long kuId) {
		String queryString = "from GhCg as cg where cg.kyXmid = ? and cg.kuId=?";
		return getHibernateTemplate().find(queryString,new Object[]{kyxmid,kuId});
	}

	public List findByKyXmid(Long kyxmid) {
		String queryString = "from GhCg as cg where cg.kyXmid = ? ";
		return getHibernateTemplate().find(queryString,new Object[]{kyxmid});
	}

	public List findByKuidAndKunameAndLxAndType(Long kuid, String uname,
			Short lx, Integer type) {
		String queryString="from GhCg as cg where  cg.kyPrizeper like'%"+uname+"%' and cg.kyLx=? and cg.kyId not in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)";
		return getHibernateTemplate().find(queryString, new Object[]{lx,kuid,type});
	}

	public List findByKdidAndLxAndState(Long kdid, Short lx, Short state) {
		String queryString="from GhCg as cg where cg.kyLx=?  and cg.kuId in(select user.kuId from WkTUser as user where (user.kdId =? or user.kdId in(select d.kdId from WkTDept as d where d.kdPid=?)) and user.kuId in(select tea.kuId from Teacher as tea))";
		if(state!=null){
			queryString=queryString+"and cg.auditState="+state+"";
		}else{
			queryString=queryString+"and cg.auditState is null";
		}
		return getHibernateTemplate().find(queryString, new Object[]{lx,kdid,kdid});
	}

	public boolean CheckByNameAndLxAndDj(GhCg cg,String name, Short lx, String ly,
			String Dj) {
		String queryString="from GhCg as cg where cg.kyMc=? and cg.kyLx=? and cg.kyLy=? and cg.kyDj like '%"+Dj+"%'";
	    List list=getHibernateTemplate().find(queryString, new Object[]{name,lx,ly});
	    if(cg!=null){
	    	//±à¼­³É¹ûÊ±
	    	 if(list!=null&&list.size()>0){
	    		for(int i=0;i<list.size();i++){
	    			GhCg ghcg=(GhCg)list.get(i);
	    			if(cg.getKyId().intValue()!=ghcg.getKyId().intValue()){
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
	public List findByNameAndLxAndDj(String name, Short lx, String ly,Long kuid,Integer type){
		String queryString="from GhCg as cg where cg.kyLx=? and cg.kyId not in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)";
		if(name!=null&&!"".equals(name)){
			queryString=queryString+" and cg.kyMc like '%"+name+"%'";
		}
		if(ly!=null&&!"".equals(ly)){
			queryString=queryString+" and cg.kyLy like '%"+ly+"%'";
		}
		return getHibernateTemplate().find(queryString, new Object[]{lx,kuid,type});
	}
	public GhCg findByNameAndLxAndly(String name, Short lx, String ly){
		String queryString="from GhCg as cg where cg.kyLx=? and cg.kyMc=? and cg.kyLy =? ";
		List list=getHibernateTemplate().find(queryString, new Object[]{lx,name,ly});
		if(list!=null&&list.size()>0){
			return (GhCg)list.get(0);
		}else{
			return null;
		}
	}

	public List findByKdidAndYearAndKuid(String year, Long kuid,Integer type,Short jb) {
		String queryString="select cg.kyDj,count(cg.kyDj) from GhCg as cg where  cg.kyJb="+jb+"";
		if(year!=null){
			queryString=queryString+" and cg.kySj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString=queryString+" and cg.kyId in(select jx.kyId from GhJsxm as jx where jx.kuId="+kuid+" and jx.jsxmType="+type+")";
		}else{
			queryString=queryString+" and cg.kyId in(select jx.kyId from GhJsxm as jx where  jx.jsxmType="+type+")";
		}
		queryString=queryString+" group by cg.kyDj order by cg.kyDj desc";
		return getHibernateTemplate().find(queryString, new Object[]{});
	}
	public List findBykdidAndYearAndKuidAndCgmcAndType(Long kdid, String year, Long kuid,Integer type,Short jb,String cjmc){
		String queryString=" from GhCg as cg where cg.kyDj=? and  cg.kuId in(select user.kuId from WkTUser as user where (user.kdId =? or user.kdId in(select d.kdId from WkTDept as d where d.kdPid=?)) and user.kuId in(select tea.kuId from Teacher as tea)) and cg.auditState="+GhCg.AUDIT_YES+" and cg.kyJb=?";
		if(year!=null){
			queryString=queryString+" and cg.kySj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString=queryString+" and cg.kyId in(select jx.kyId from GhJsxm as jx where jx.kuId="+kuid+" and jx.jsxmType=?)";
		}else{
			queryString=queryString+" and cg.kyId in(select jx.kyId from GhJsxm as jx where  jx.jsxmType=?)";
		}
		return getHibernateTemplate().find(queryString, new Object[]{cjmc,kdid,kdid,jb,type});
	}
	
	public List findCountByKuidAndCgkyAndHjky(Long kuId,Short lx,Integer type,Short fruitLevel){
		String queryString = "select count(cg.kyId)  from GhCg as cg where  cg.kyLx=? and cg.kyId  in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?) and cg.kyJb=?";
		return getHibernateTemplate().find(queryString,new Object[]{lx,kuId,type,fruitLevel});
	}
	
	public List findByYearAndKuidAndCgmcAndType(String year, Long kuid,Integer type,Short jb){
		String queryString=" from GhCg as cg where  cg.kyJb=?";
		if(year!=null){
			queryString=queryString+" and cg.kySj like '"+year+"%'";
		}
		if(kuid!=null){
			queryString=queryString+" and cg.kyId in(select jx.kyId from GhJsxm as jx where jx.kuId="+kuid+" and jx.jsxmType=?)";
		}else{
			queryString=queryString+" and cg.kyId in(select jx.kyId from GhJsxm as jx where  jx.jsxmType=?)";
		}
		return getHibernateTemplate().find(queryString, new Object[]{jb,type});
	}

	public List<GhCg> findByKyIds(String ids) {
		String queryString = "from GhCg as cg where cg.kyId in ("+ids+") order by kySj desc";
		return getHibernateTemplate().find(queryString);
	}
	
}
