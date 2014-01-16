package org.iti.gh.service.impl;

import java.util.List;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.gh.entity.GhJsxm;
import org.iti.gh.entity.GhXm;
import org.iti.gh.service.XmService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;


public class XmServiceImpl extends BaseServiceImpl implements XmService {

	public List findByKuid(Long kuId,Short lx) {
		String queryString = "from GhXm as xm where xm.kuId = ? and xm.kyLx=?";
		return getHibernateTemplate().find(queryString,new Object[]{kuId,lx});
	}

	public List findByKdId(long kdid, Short lx,Short state) {
		String queryString="select distinct xm.kyMc from GhXm as xm where xm.kyLx=? and xm.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?))";
		if(state!=null){
			queryString=queryString +"and xm.auditState="+state+" ";
		}else{
			queryString=queryString +"and xm.auditState is null ";
		}
		return getHibernateTemplate().find(queryString, new Object[] {lx,kdid,kdid});
	}

	public List findSumKdId(long kdid, Short lx,String progress,Short state) {
		String queryString="from GhXm as xm where xm.kyLx=? and xm.kyProgress=? and xm.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) and xm.kuId in(select tea.kuId from Teacher as tea)";
		if(state!=null){
			queryString=queryString +"and xm.auditState="+state+" ";
		}else{
			queryString=queryString +"and xm.auditState is null ";
		}
		List list=getHibernateTemplate().find(queryString, new Object[] {lx,progress,kdid,kdid});
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	public List findByMc(String name, Short lx) {
		String queryString = "from GhXm as xm where xm.kyMc = ? and xm.kyLx=?  order by xm.kyGx,xm.kuId";
		return getHibernateTemplate().find(queryString,new Object[]{name,lx});
	}

	public List findByKyid(Long kyid) {
		String queryString = "from GhXm as xm where xm.kyId=? ";
		return getHibernateTemplate().find(queryString, new Object[] { kyid });
	}

	public List findByKuidNotZz(Long kuId, Short lx) {
		String queryString = "from GhXm as xm where xm.kuId = ? and xm.kyLx=? and xm.kyId not in (select zz.kyId from GhXmzz as zz )";
		return getHibernateTemplate().find(queryString,new Object[]{kuId,lx});
	}

	public List findByKuidAndLwidNotZz(Long kuId,Short lx,Short lwType, Integer jsxmType, Long lwid) {
		String queryString = "from GhXm as xm where  xm.kyLx=? and xm.kyId not in (select zz.kyId from GhXmzz as zz where zz.lwId = ? and zz.lwType=?) and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)";
		
		
		return getHibernateTemplate().find(queryString,new Object[]{lx,lwid,lwType,kuId,jsxmType});
	
	}

	public List findAllname(Long kuId,String kuname,Short lx,Integer type) {
		String queryString=" from GhXm as xm where  xm.kuId <>?  and (xm.kyProstaffs like '%"+kuname+"%' or xm.kyRegister like '%"+kuname+"%' or xm.kyProman like '%"+kuname+"%') and xm.kyLx=?  and xm.kyId not in( select jsxm.kyId from GhJsxm as jsxm  where jsxm.jsxmType=?  and jsxm.kuId =?)";
		return getHibernateTemplate().find(queryString,new Object[]{kuId,lx,type,kuId});
	}
	public List findAllXmname(Long kuid,String kuname, Integer lx) {
		String queryString="select distinct xm.kyMc,xm.kyId from GhXm as xm where  xm.kyProstaffs like '%"+kuname+"%' and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.jsxmType=?  and jsxm.kuId =?) and (xm.kyPrize is null or xm.kyPrize='1')))";
		return getHibernateTemplate().find(queryString,new Object[]{lx,kuid});
	}

	public List findByKuidAndType(Long kuid, Integer type) {
		String queryString="from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)";
		return getHibernateTemplate().find(queryString, new Object[]{kuid,type});
	}
	public List findByKuidAndType(Long kuid, Integer type,int pageNum, int pageSize ) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		 queryString.append("from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)");
		 Query query = session.createQuery(queryString.toString());
		 query.setParameter(0, kuid);
		 query.setParameter(1, type);
		  query.setFirstResult(pageNum*pageSize);
	        query.setMaxResults(pageSize);
	        List<GhXm> list = query.list();
	        session.close();
	        return list;
	}

	public List findByKuidAndTypeAndKyclass(Long kuid, Integer type,String kyClass) {
		String queryString="from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?) and xm.kyClass=?";
		return getHibernateTemplate().find(queryString, new Object[]{kuid,type,kyClass});
	}
	public List findByKuidAndTypeAndKyclassAndXmid(String kyClass,Long xmid){
		String queryString="from GhXm as xm where xm.kyClass=? and xm.kyId=?";
		return getHibernateTemplate().find(queryString, new Object[]{kyClass,xmid});
	}
	public List findByKdidAndLxAndTypeAndState(Long kdid, Short lx, Short State) {
		String queryString="from GhXm as xm where  xm.kyLx=? and xm.kuId in(select xu.id.kuId from XyUserrole as xu where xu.id.krId=(select r.krId from WkTRole as r where r.krName='½ÌÊ¦' and r.kdId=(select dep.kdSchid from WkTDept as dep where dep.kdId=?)) and (xu.kdId=? or xu.kdId in( select d.kdId from WkTDept as d where d.kdPid=?)))";
		if(State!=null){
			queryString=queryString +"and xm.auditState="+State+" ";
		}else{
			queryString=queryString +"and xm.auditState is null ";
		}
		return getHibernateTemplate().find(queryString, new Object[]{lx,kdid,kdid,kdid});
	}

	public boolean CheckOnlyOne(String xm, Short lx,String ly,String fzr) {
	    String queryString="from GhXm as xm where xm.kyMc=? and xm.kyLx=? and xm.kyLy=? and xm.kyProman=?";
	    List list=getHibernateTemplate().find(queryString, new Object[]{xm,lx,ly,fzr});
	    if(list!=null&&list.size()>0){
	    	return false;
	    }else{
	    	return true;
	    }
		
	}

	public boolean findByNameAndLxAndLy(GhXm xm,String mc, Short lx, String ly,String fzr) {
		 String queryString="from GhXm as xm where xm.kyMc=? and xm.kyLx=? and xm.kyLy=? and xm.kyProman=?";
		 List list=getHibernateTemplate().find(queryString, new Object[]{mc,lx,ly,fzr});
		 if(list!=null&&list.size()>0){
			 for(int i=0;i<list.size();i++){
				 GhXm ghxm=(GhXm)list.get(i);
				 if(xm.getKyId().intValue()!=ghxm.getKyId().intValue()){
					 return false;
				 }
			 }
		 }
		 return true;
	}

	public GhXm findByNameAndLyAndLxAndFzr(String mc, Short lx, String ly,
			String fzr) {
		 String queryString="from GhXm as xm where xm.kyMc=? and xm.kyLx=? and xm.kyLy=? and xm.kyProman=?";
		 List list=getHibernateTemplate().find(queryString, new Object[]{mc,lx,ly,fzr});
		 if(list!=null&&list.size()>0){
			 return (GhXm)list.get(0);
			 
		 }else{
			 return null;
		 }
	}

	public List findByMcAndLyAndFzr(Long kuid, String mc, Short lx, String ly,
			String fzr, Integer type) {
		String queryString="from GhXm as xm where  xm.kyLx=? and xm.kyId not in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)";
		if(mc!=null&&!"".equals(mc)){
			queryString=queryString+" and xm.kyMc like '%"+mc+"%'";
		}
		if(ly!=null&&!"".equals(ly)){
			queryString=queryString+" and xm.kyLy like '%"+ly+"%'";
		}
		if(fzr!=null&&!"".equals(fzr)){
			queryString=queryString+" and xm.kyProman like '%"+fzr+"%'";
		}
		
		return getHibernateTemplate().find(queryString, new Object[]{lx,kuid,type});
	}

	public List<GhXm> findByKdidAndYearAndKuidAndTypeAndJb(Long kdid, String year,
			Long kuid, Integer type, String jb,String hx) {
		String queryString="from GhXm as xm where  xm.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) and xm.kuId in(select tea.kuId from Teacher as tea) and xm.auditState="+GhXm.AUDIT_YES+" ";
		if(year!=null){
			queryString=queryString+" and xm.kyLxsj like '"+year+"%'";
		} 
		if(kuid!=null){
			queryString=queryString+" and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId="+kuid+" and jsxm.jsxmType="+type+" ) ";
		}else{
			queryString=queryString+" and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.jsxmType="+type+" ) ";
		}
		if(jb!=null){
			queryString=queryString+" and xm.kySubjetype='"+jb+"' ";
		}
		if(hx!=null){
			queryString=queryString+" and xm.kyClass='"+hx+"' ";
		}
		return getHibernateTemplate().find(queryString, new Object[]{kdid,kdid});
	}

	public List findQtByKdidAndYearAndKuid(Long kdid, String year, Long kuid,
			Integer type) {
		 
		String queryString="from GhXm as xm where  xm.kySubjetype!="+GhXm.SBJ+" and xm.kySubjetype!="+GhXm.GJJ+" and xm.kyClass!="+GhXm.HX+"  and xm.kuId in (select user.kuId from WkTUser as user where user.kdId=? or user.kdId in ( select dep.kdId from WkTDept as dep where dep.kdPid=?)) and xm.kuId in(select tea.kuId from Teacher as tea) and xm.auditState="+GhXm.AUDIT_YES+" ";
		if(year!=null){
			queryString=queryString+" and xm.kyLxsj like '"+year+"%'";
		} 
		if(kuid!=null){
			queryString=queryString+" and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId="+kuid+" and jsxm.jsxmType="+type+" ) ";
		}else{
			queryString=queryString+" and xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.jsxmType="+type+" ) ";
		}
		return getHibernateTemplate().find(queryString, new Object[]{kdid,kdid});
	}
	
	public List<GhXm> getListPageByKuidAndTypeAndKyclass(Long kuid,Integer type,String kyClass,int pageNum, int pageSize){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString="from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?) and xm.kyClass=? order by xm.kyId asc";
		Query query = session.createQuery(queryString);
        query.setParameter(0, kuid);
        query.setParameter(1, type);
        query.setParameter(2, kyClass);
        query.setFirstResult(pageNum*pageSize);
        query.setMaxResults(pageSize);
        List<GhXm>  list = query.list();
        session.close();
        return list;
	}
	
	public List<GhXm> getListPageByKuidAndType(Long kuid,Integer type,int pageNum, int pageSize){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString="from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?) order by xm.kyId asc";
		Query query = session.createQuery(queryString);
        query.setParameter(0, kuid);
        query.setParameter(1, type);
        query.setFirstResult(pageNum*pageSize);
        query.setMaxResults(pageSize);
        List<GhXm>  list = query.list();
        session.close();
        return list;
	}
	
	public List findCountByKuidAndTypeAndKyclass(Long kuid,Integer type,String kyClass){
		String queryString="select count(xm.kyId) from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?) and xm.kyClass=?";
		return getHibernateTemplate().find(queryString, new Object[]{kuid,type,kyClass});
	}
	public List findCountByKuidAndTypeAndKyScale(Long kuid,Integer type,String kyScale){
		String queryString="select count(xm.kyId) from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?) and xm.kyScale=?";
		return getHibernateTemplate().find(queryString, new Object[]{kuid,type,kyScale});
	}
	public List findCountByKuidAndType(Long kuid,Integer type){
		String queryString="select count(xm.kyId) from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)";
		return getHibernateTemplate().find(queryString, new Object[]{kuid,type});
	}
	
	public List findXmByKuidAndType(Long kuid,Integer type){
		String queryString="from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? and jsxm.jsxmType=?)";
		return getHibernateTemplate().find(queryString, new Object[]{kuid,type});
	}

	public int getHjCountByKuidGx(Long kuid,String kyScale, int gx) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select count(xm.kyId) from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? ";
		if(gx>0){
			queryString = queryString +" and jsxm.kyGx="+gx;
		}
		queryString = queryString +") and xm.kyScale = ? and xm.kyPrize = "+GhXm.PRIZE_YES +" and xm.auditState = "+GhXm.AUDIT_YES;
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuid);
		query.setParameter(1, kyScale);
		List<Long> list = query.list();
		int words = 0;
		if(null!=list&&list.size()>0)
		{
			words = list.get(0).intValue();
		}
		session.close();
		return words;
	}

	public int getCountByKuidGx(Long kuid, String kyScale, int gx) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "select count(xm.kyId) from GhXm as xm where xm.kyId in(select jsxm.kyId from GhJsxm as jsxm where jsxm.kuId=? ";
		if(gx>0){
			queryString = queryString +" and jsxm.kyGx="+gx;
		}
		queryString = queryString +") and xm.kyScale = ? and xm.auditState = "+GhXm.AUDIT_YES;
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuid);
		query.setParameter(1, kyScale);
		List<Long> list = query.list();
		int words = 0;
		if(null!=list&&list.size()>0)
		{
			words = list.get(0).intValue();
		}
		session.close();
		return words;
	}

	public List<GhXm> findByKyIdsType(String ids) {
		String queryString = "from GhXm as xm where xm.kyId in ("+ids+") order by xm.kyLxsj desc";
		return getHibernateTemplate().find(queryString);
	}
	
	
}
