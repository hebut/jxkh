package com.uniwin.framework.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.AuthService;
@SuppressWarnings("unchecked")
public class AuthServiceImpl extends BaseServiceImpl implements AuthService {
	public List<WkTAuth> findByType(String katype) {
		String queryString = "from WkTAuth as a where a.kaType=?";
		return getHibernateTemplate().find(queryString, new Object[] { katype });
	}
	public List<WkTAuth> findByKrid(Long krid) {
		String queryString = "from WkTAuth as auth where auth.krId=? ";
		return getHibernateTemplate().find(queryString.toString(), new Object[] {krid});
	}
	public List<WkTAuth> getChildAuth(Long aid) {
		String queryString = "from WkTAuth as a where a.kaPid=? ";
		return getHibernateTemplate().find(queryString, new Object[] { aid });
	}

	// public WkTChanel findchanel(Long karid) {
	// String queryString="from WkTChanel as c where c.kcId=?";
	// return (WkTChanel)getHibernateTemplate().find(queryString,new Object[]{karid}).get(0);
	// }
	/**
	 * 返回部门对象
	 */
	public WkTDept finddep(Long kdid) {
		String queryString = "from WkTDept as d where d.kdid=? ";
		return (WkTDept) getHibernateTemplate().find(queryString, new Object[] { kdid }).get(0);
	}

	/**
	 * 返回角色对象
	 */
	public WkTRole findrole(Long krid) {
		String queryString = "from WkTRole as r where r.krid=?";
		return (WkTRole) getHibernateTemplate().find(queryString, new Object[] { krid }).get(0);
	}

	public List<WkTAuth> getAuthOfTitle(Long tid, List<Long> rlist, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("from WkTAuth as au where " + " au.kaRid=?" + " and au.kaType=? and au.krId in (");
		for (int i = 0; i < rlist.size(); i++) {
			Long rid = (Long) rlist.get(i);
			queryString.append(rid);
			if (i < rlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") and au.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long rid = (Long) dlist.get(i);
			queryString.append(rid);
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") ");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { tid, WkTAuth.TYPE_TITLE });
	}

	public void deleteAuthOfTitle(Long tid, List<Long> rlist, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("delete WkTAuth as au where  au.kaRid=? and au.kaType=? and au.krId in (");
		for (int i = 0; i < rlist.size(); i++) {
			Long rid = (Long) rlist.get(i);
			queryString.append(rid);
			if (i < rlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") and au.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long rid = (Long) dlist.get(i);
			queryString.append(rid);
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") ");
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query dAuth = session.createQuery(queryString.toString());
		dAuth.setLong(0, tid);
		dAuth.setString(1, WkTAuth.TYPE_TITLE);
		dAuth.executeUpdate();
		session.getTransaction().commit();
		//session.clear();
	}

	public List<WkTAuth> findByExample(WkTAuth auth) {
		String queryString = "from WkTAuth as au where au.kaRid=? and au.krId=? and au.kdId=? and au.kaType=? and au.kaCode=? ";
		return getHibernateTemplate().find(queryString, new Object[] { auth.getKaRid(), auth.getKrId(), auth.getKdId(), auth.getKaType(), auth.getKaCode() });
	}

	
	public List<WkTAuth> getAuthOfTitle(List<Long> deptList, Long tid) {
		// deptList当前用户及下级部门列表,要求权限所在的部门及角色所在部门在此列表中
		StringBuffer queryString = new StringBuffer("from WkTAuth as auth where auth.kaType=1 " + "and auth.kaRid=? and (auth.kdId=0 or auth.kdId in (");
		for (int i = 0; i < deptList.size(); i++) {
			Long l = (Long) deptList.get(i);
			queryString.append(l);
			if (i < deptList.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")) and (auth.krId=0 or auth.krId in (select r.krId from WkTRole as r where " + "r.kdId in (");
		for (int i = 0; i < deptList.size(); i++) {
			Long l = (Long) deptList.get(i);
			queryString.append(l);
			if (i < deptList.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")))");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { tid });
	}

	public List<WkTAuth> getAuthOfChanel(List<Long> depList, Long cid) {
		StringBuffer queryString = new StringBuffer("from WkTAuth as auth where auth.kaType=2" + " and auth.kaRid=? and (auth.kdId=0 or auth.kdId in (");
		for (int i = 0; i < depList.size(); i++) {
			Long l = (Long) depList.get(i);
			queryString.append(l);
			if (i < depList.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")) and (auth.krId=0 or auth.krId in (select r.krId from WkTRole as r where " + "r.kdId in (");
		for (int i = 0; i < depList.size(); i++) {
			Long l = (Long) depList.get(i);
			queryString.append(l);
			if (i < depList.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")))");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { cid });
	}

	public List<WkTAuth> findAuthOfChanel(Long kcid) {
		String queryString = "from WkTAuth as au where au.kaType=2 and au.kaRid=? order by au.kaRid";
		return getHibernateTemplate().find(queryString, new Object[] { kcid });
	}

	public List<WkTAuth> getAuthOfChanel(Long cid, List<Long> rlist, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("from WkTAuth as au where " + " au.kaRid=?" + " and au.kaType=? and au.krId in (");
		for (int i = 0; i < rlist.size(); i++) {
			Long rid = (Long) rlist.get(i);
			queryString.append(rid);
			if (i < rlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") and au.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long rid = (Long) dlist.get(i);
			queryString.append(rid);
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") ");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { cid, WkTAuth.TYPE_Chanel });
	}

	public void deleteAuthOfChanel(Long cid, List<Long> rlist, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("delete WkTAuth as au where " + " au.kaRid=?" + " and au.kaType=? and au.krId in (");
		for (int i = 0; i < rlist.size(); i++) {
			Long rid = (Long) rlist.get(i);
			queryString.append(rid);
			if (i < rlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") and au.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long rid = (Long) dlist.get(i);
			queryString.append(rid);
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") ");
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query dAuth = session.createQuery(queryString.toString());
		dAuth.setLong(0, cid);
		dAuth.setString(1, WkTAuth.TYPE_Chanel);
		dAuth.executeUpdate();
		session.getTransaction().commit();
		session.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniwin.framework.service.AuthService#deleteByRole(com.uniwin.framework.entity.WkTRole)
	 */
	public void deleteByRole(WkTRole role) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		// 删除角色权限
		session.beginTransaction();
		String deleteRole = "delete WkTAuth as auth where auth.krId=?";
		Query dRole = session.createQuery(deleteRole);
		dRole.setLong(0, role.getKrId());
		dRole.executeUpdate();
		session.getTransaction().commit();
		session.clear();
	}

	public void copyAuthByRole(WkTRole frole, WkTRole trole) {
		if (frole.getKrId().intValue() == trole.getKrId().intValue()) {
			return;
		}
		String selectAuth = "from WkTAuth as auth where auth.krId=?";
		List<WkTAuth> slist = getHibernateTemplate().find(selectAuth, frole.getKrId());
		for (int i = 0; i < slist.size(); i++) {
			WkTAuth auth = (WkTAuth) slist.get(i);
			auth.setKrId(trole.getKrId());
			save(auth);
		}
	}

	public List<WkTAuth> findByKridAndTypeAndCode(Long krid, String type, Short code) {
		String queryString = "from WkTAuth as auth where auth.krId=? and auth.kaType=? " + "and auth.kaCode1=? ";
		return getHibernateTemplate().find(queryString.toString(), new Object[] { krid, type, code });
	}
	
	public void deleteAuthOfTask(Long wid, List rlist, List dlist) {
		
		StringBuffer queryString=new StringBuffer("delete WkTAuth as au where "+
				" au.kaRid=?"+
			    " and au.kaType=? and au.krId in (");
			for(int i=0;i<rlist.size();i++){
				Long rid=(Long)rlist.get(i);
				queryString.append(rid);
				if(i<rlist.size()-1){
					queryString.append(",");
				}
			}
			queryString.append(") and au.kdId in (");
			for(int i=0;i<dlist.size();i++){
				Long rid=(Long)dlist.get(i);
				queryString.append(rid);
				if(i<dlist.size()-1){
					queryString.append(",");
				}
			}
			queryString.append(") ");	
		 Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();		
	     session.beginTransaction();
		 Query dAuth=session.createQuery(queryString.toString());	     
	     dAuth.setLong(0,wid);
	     dAuth.setString(1, WkTAuth.TYPE_TASK);
	     dAuth.executeUpdate();	
	     session.getTransaction().commit();
	     session.clear();
	}
	
public List<WkTAuth> getAuthOfTask(List depList, Long wid) {
		
		StringBuffer queryString=new StringBuffer("from WkTAuth as auth where auth.kaType=3"+
			" and auth.kaRid=? and (auth.kdId=0 or auth.kdId in (");
		 for(int i=0;i<depList.size();i++){
				Long l=(Long)depList.get(i);
				queryString.append(l);
				if(i<depList.size()-1){
					queryString.append(",");
		  }	
		 }
				queryString.append(")) and (auth.krId=0 or auth.krId in (select r.krId from WkTRole as r where "+
				"r.kdId in (");
			for(int i=0;i<depList.size();i++){
					Long l=(Long)depList.get(i);
					queryString.append(l);
				if(i<depList.size()-1){
					queryString.append(",");
				}
			}
			queryString.append(")))"); 
			return getHibernateTemplate().find(queryString.toString(), new Object[]{wid});
	}

}
