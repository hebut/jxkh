package org.iti.projectmanage.science.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.projectmanage.science.member.service.ProjectMemberService;
import org.iti.xypt.entity.Teacher;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTUser;

public class ProjectMemberServiceImpl extends BaseServiceImpl implements ProjectMemberService {
	
	
	public List findByKyIdAndKuId(Long kyId, Long kuId) {
		String queryString = "from GhJsxm where kyId=? and kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] { kyId, kuId });
	}
	public List findByKyId(Long kyId,int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append( "from GhJsxm where kyId=?  order by kyGx");
		Query query = session.createQuery(queryString.toString());
		 query.setParameter(0, kyId);
		  query.setFirstResult(pageNum*pageSize);
	        query.setMaxResults(pageSize);
	        List<GhJsxm> list = query.list();
	        session.close();
	        return list;
	}

	public String findOutMember() {
		String queryString = "select max(kuId) from GhJsxm where  kuId like '%-1%'";
		return (String) getHibernateTemplate().find(queryString, new Object[] { }).get(0);
	}
	
	public  GhXm findXM(Long kyid) {
		String queryString = "from GhXm where kyId=?";
		return (GhXm) getHibernateTemplate().find(queryString, new Object[] { kyid}).get(0);
	}
	
	public  List findteacher(Long kuid)
	{
		String queryString = "from Teacher where kuId=?";
		return  getHibernateTemplate().find(queryString, new Object[] { kuid});
	}
	
	public  List findYjfx(Long gyid)
	{
		String queryString = "from GhYjfx where gyId=?";
		return  getHibernateTemplate().find(queryString, new Object[] { gyid});
	}

	public List findByKyXmId(Long kyid){
		String queryString = "from GhJsxm where kyId=? order by kyGx";
		return getHibernateTemplate().find(queryString, new Object[] { kyid});
		
	}
	public List<WkTUser> findUserForGroupByKdIdAndName1(Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate,int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		
		if (teacher) {
			queryString.append("from WkTUser as u where kuId in (select kuId from Teacher) and (u.kdId="+kdid+" or u.kdId in (select d.kdId from WkTDept as d where d.kdPid="+kdid+")) and u.kuName like '%" + name + "%' order by u.kdId");
		}
		if (student) {
			queryString .append(" union from WkTUser as u where kuId in (select kuId from Student) and (u.kdId="+kdid+" or u.kdId in (select d.kdId from WkTDept as d where d.kdPid="+kdid+")) and u.kuName like '%" + name + "%' order by u.kdId");
		}
		if (graduate) {
			queryString.append(" union from WkTUser as u where kuId in (select kuId from Yjs) and (u.kdId="+kdid+" or u.kdId in (select d.kdId from WkTDept as d where d.kdPid="+kdid+")) and u.kuName like '%" + name + "%' order by u.kdId");
		}
		
		String lastQuery = queryString.toString();
		if(lastQuery.startsWith(" union")){
			lastQuery = lastQuery.substring(7, lastQuery.length());
			
		}
		
		Query query = session.createQuery(lastQuery);
        query.setFirstResult(pageNum*pageSize);
        query.setMaxResults(pageSize);
        List<WkTUser>  list = query.list();
        return list;
	}
	public List<GhXm> findByKdidAndYearAndKuidAndTypeAndJb(Long kuid,String year,Integer type, String jb,String hx,String jz)
	{
		String queryString="from GhXm as xm where xm.kyClass='"+hx+"'";
		if(year!=null){
			queryString=queryString+" and xm.kyLxsj like '"+year+"%'";
		} 
		if(kuid!=null){
			queryString=queryString+" and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId="+kuid+" and jsxm.jsxmType="+type+" ) ";
		}
		if(jb!=null){
			queryString=queryString+" and xm.kyScale='"+jb+"' ";
		}
		if(jz!=null){
			queryString=queryString+" and xm.kyProgress='"+jz+"' ";
		}
		return getHibernateTemplate().find(queryString, new Object[]{});
	}
	public List findQtByYearAndKuid(String year, Long kuid,Integer type,String hx) {
		 
		String queryString="from GhXm as xm where  xm.kyScale!="+GhXm.SBJ+" and xm.kyScale!="+GhXm.GJJ+" and xm.kyClass='"+hx+"'";
		if(year!=null){
			queryString=queryString+" and xm.kyLxsj like '"+year+"%'";
		} 
		if(kuid!=null){
			queryString=queryString+" and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId="+kuid+" and jsxm.jsxmType="+type+" ) ";
		}else{
			queryString=queryString+" and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.jsxmType="+type+" ) ";
		}
		return getHibernateTemplate().find(queryString, new Object[]{});
	}

public GhXm findByName(String name)
{
	String queryString = "from GhXm where kyMc=?";
	return (GhXm) getHibernateTemplate().find(queryString, new Object[] {name}).get(0);
}
public List findXmByid(Long kyid)
{
	String queryString = "from GhXm where kyId=?";
	return getHibernateTemplate().find(queryString, new Object[] { kyid});
}
 public List findHyLwByXm(Long xmid,Long kuid,Short type)
 {
	 String queryString = "from GhHylw as hy where hy.lwId in(select zz.lwId from GhXmzz as zz where zz.kyId=? and zz.kuId=? and zz.lwType=?)";
		return getHibernateTemplate().find(queryString, new Object[] {xmid,kuid,type});
 }
 public List findQkLwByXm(Long xmid,Long kuid,Short type)
 {
	 String queryString = "from GhQklw as hy where hy.lwId in(select zz.lwId from GhXmzz as zz where zz.kyId=? and zz.kuId=? and zz.lwType=?)";
		return getHibernateTemplate().find(queryString, new Object[] {xmid,kuid,type});
 }
 public List findRjzzByXm(Long xmid,Long kuid,Short type)
 {
	 String queryString = "from GhRjzz as rj where rj.rjId in(select zz.lwId from GhXmzz as zz where zz.kyId=? and zz.kuId=? and zz.lwType=?)";
		return getHibernateTemplate().find(queryString, new Object[] {xmid,kuid,type});
 }
 public List findFmzlByXm(Long xmid,Long kuid,Short type)
 {
	 String queryString = "from GhFmzl as fm where fm.fmId in(select zz.lwId from GhXmzz as zz where zz.kyId=? and zz.kuId=? and zz.lwType=?)";
		return getHibernateTemplate().find(queryString, new Object[] {xmid,kuid,type});
 }
public List findOutMemberByuid(Long kuid)
{
	String queryString = "from GhOutMember as out where out.kuId=?";
	return getHibernateTemplate().find(queryString, new Object[] {kuid});
}
public List findByMid(Long mid)
{
	String queryString = "from GhOutMember as out where out.mId=?";
	return getHibernateTemplate().find(queryString, new Object[] {mid});
}

public List findZzByXm(Long xmid,Long kuid,Short type)
{
	 String queryString = "from GhZz as zz where zz.zzId in(select zz.lwId from GhXmzz as zz where zz.kyId=? and zz.kuId=? and zz.lwType=?)";
		return getHibernateTemplate().find(queryString, new Object[] {xmid,kuid,type});
}
}