package com.uniwin.framework.service.impl;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyClass;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.RoleService;

@SuppressWarnings("unchecked")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	
	public List<WkTRole> getChildRole(Long ptid) {
		String queryString = "from WkTRole as r where r.krPid=? order by r.krOrder";
		return getHibernateTemplate().find(queryString, new Object[] { ptid });
	}

	public List<WkTRole> getRoleOfUser(Long uid) {
		String queryString = "from WkTRole as r where r.krId in ( select u_r.id.krId" + " from WkTUserole as u_r where u_r.id.kuId=? )order by r.krOrder";
		return getHibernateTemplate().find(queryString, new Object[] { uid });
	}

	public List<WkTRole> getChildRole(Long ptid, List<java.lang.Long> args) {
		StringBuffer queryString = new StringBuffer("from WkTRole as r where r.krPid=? and r.kdId in (");
		for (int i = 0; i < args.size(); i++) {
			Long l = (Long) args.get(i);
			queryString.append(l.longValue());
			if (i < args.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") order by r.krOrder");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { ptid });
	}

	public void delete(WkTRole role) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		// 删除角色权限
		String deleteRole = "delete WkTAuth as auth where auth.krId=?";
		Query dRole = session.createQuery(deleteRole);
		dRole.setLong(0, role.getKrId());
		dRole.executeUpdate();
		// 删除角色用户关系
		String deleteUserRole = "delete WkTUserole as u_r where u_r.id.krId=?";
		Query dUserRole = session.createQuery(deleteUserRole);
		dUserRole.setLong(0, role.getKrId());
		dUserRole.executeUpdate();
		session.getTransaction().commit();
		getHibernateTemplate().delete(role);
	}

	public List<WkTRole> getRoleOfUser(Long uid, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("from WkTRole as r where r.krId in ( select u_r.id.krId" + " from WkTUserole as u_r where u_r.id.kuId=?) and r.kdId in(");
		for (int i = 0; i < dlist.size(); i++) {
			Long l = (Long) dlist.get(i);
			queryString.append(l.longValue());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(" )order by r.krOrder");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { uid });
	}

	public List<WkTRole> getChildRoleOrDefault(Long ptid, List<Long> args) {
		StringBuffer queryString = new StringBuffer("from WkTRole as r where r.krPid=? and (r.kdId in (");
		for (int i = 0; i < args.size(); i++) {
			Long l = (Long) args.get(i);
			queryString.append(l.longValue());
			if (i < args.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") or r.krShare=?) order by r.krOrder");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { ptid, "1" });
	}

	public WkTRole findByRid(Long krId) {
		String queryString = "from WkTRole as r where r.krId=? ";
		List<WkTRole> list = getHibernateTemplate().find(queryString, new Object[] { krId });
		if (list.size() == 0) {
			return null;
		} else {
			return (WkTRole) list.get(0);
		}
	}

	public List<WkTRole> findByName(String rname) {
		String queryString = "select r.krId from WkTRole as r where r.krName=? ";
		return getHibernateTemplate().find(queryString, new Object[] { rname });
	}

	public List<WkTRole> findSelectAdmins(Long schid, char grade) {
		String queryString = "from WkTRole as r where r.kdId=? and substring(r.krArgs,2,1)>=? order by r.krOrder";
		return getHibernateTemplate().find(queryString, new Object[] { schid, String.valueOf(grade) });
	}

	public List<WkTRole> findDudaoRole(Long kdid) {
		String query = "from WkTRole as r where r.kdId=? and substring(r.krArgs,1,1)=?";
		return getHibernateTemplate().find(query, new Object[] { kdid, String.valueOf(WkTRole.ISDD_YES) });
	}

	public WkTRole findByGzl(Long kdid) {
		String query = "from WkTRole as r where r.kdId=? and substring(r.krArgs,4,1)=?";
		List<WkTRole> glist = getHibernateTemplate().find(query, new Object[] { kdid, String.valueOf(WkTRole.GZL_YES) });
		if (glist.size() > 0) {
			return (WkTRole) glist.get(0);
		} else {
			return null;
		}
	}

	/**
	 * SELECT *, SUBSTRING(KR_ARGS, 2, 1) AS Expr1 FROM WK_T_ROLE WHERE (KD_ID = 19) AND (SUBSTRING(KR_ARGS, 2, 1) <> 0) AND (SUBSTRING(KR_ARGS, 3, 1) = 2) ORDER BY SUBSTRING(KR_ARGS, 2, 1), LEN(KR_ADMINS) DESC
	 */
	public WkTRole getShcAmdinRole(Long kdid) {
		String query = "from WkTRole as r where r.kdId=? and substring(r.krArgs,2,1)!=? and substring(r.krArgs,3,1)=? order by substring(r.krArgs,2,1),len(r.krAdmins) desc";
		List<WkTRole> listf = getHibernateTemplate().find(query, new Object[] { kdid, "0", String.valueOf(WkTRole.TYPE_LD) });
		if (listf.size() == 0) {
			return null;
		} else {
			return (WkTRole) listf.get(0);
		}
	}

	public List<WkTRole> getProleOfUser(Long kuId) {
		String queryString = "from WkTRole as pr where pr.krId in(select r.krPid from " + "WkTRole as r where r.krId in(" + "select ur.id.krId from WkTUserole as ur where ur.id.kuId = ?)) ";
		return getHibernateTemplate().find(queryString, new Object[] { kuId });
	}

	public List<WkTRole> getChildRoleByKuid(Long krId, Long kuId) {
		String queryString = "from WkTRole as r where r.krPid=? and r.krId in(" + "select ur.id.krId from WkTUserole as ur where ur.id.kuId = ?) order by r.krOrder";
		return getHibernateTemplate().find(queryString, new Object[] { krId, kuId });
	}

	public List<WkTRole> getProleOfUser(Long kuId, Long tid) {
		String queryString = "from WkTRole as pr where pr.krId in(select r.krPid from " + "WkTRole as r where r.krId in(" + "select ur.id.krId from WkTUserole as ur where ur.id.kuId = ? and ur.id.krId in(select au.krId from WkTAuth as au" + " where au.kaRid=? and au.kaType=?) )) ";
		return getHibernateTemplate().find(queryString, new Object[] { kuId, tid, WkTAuth.TYPE_TITLE });
	}

	public List<WkTRole> findByXmid(Long xmid) {
		String query = "from WkTRole as r where r.krId in(select xnr.id.krId from XyNReceiver as xnr where xnr.id.xmId=?)";
		return getHibernateTemplate().find(query, new Object[] { xmid });
	}

	public List<WkTRole> findByTidAndKdid(Long tid, Long kdid) {
		String queryString = "from WkTRole as r where r.krId in(" + "select auth.krId from WkTAuth as auth where auth.kaRid = ? and auth.kaType = ?" + "and auth.kaCode1 = ?) and r.kdId = ?";
		return getHibernateTemplate().find(queryString, new Object[] { tid, WkTAuth.TYPE_TITLE, WkTAuth.KACODE1_OK, kdid });
	}

	public List<Student> findStuByRole(List<Object> deplist, Long krid, String sname, String sno) {
		StringBuffer queryString = new StringBuffer("select stu from Student as stu ,WkTDept as wdept where stu.stId like '%" + sno + "%'  and stu.kuId in (select u.kuId from WkTUser as u where " + "u.kuName like '%" + sname + "%' ) and stu.kuId in (" + "select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and wdept.kdId=role.kdId and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			if (deplist.get(i) instanceof WkTDept) {
				WkTDept dept = (WkTDept) deplist.get(i);
				queryString.append(dept.getKdId());
			} else if (deplist.get(i) instanceof XyClass) {
				XyClass cla = (XyClass) deplist.get(i);
				queryString.append(cla.getKdId());
			}
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") ) order by wdept.kdNumber,stu.stClass, stu.stId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { krid });
	}
	public List<Student> findStuByRole(Long kdid,Long kuid, Long krid, String sname, String sno) {
		String queryString = "from Student as stu where stu.clId in (select c.clId from XyClass as c where (c.kdId=? or c.kdId in (select d.kdId from WkTDept as d where d.kdPid=?))and c.clId in (select f.id.clId from XyFudao as f where f.id.kuId=?)) and stu.stId like '%" + sno + "%'  and stu.kuId in (select u.kuId from WkTUser as u where " + "u.kuName like '%" + sname + "%' ) and stu.kuId in (select role.id.kuId from XyUserrole as role where role.id.krId=?)) order by stu.clId";
		return getHibernateTemplate().find(queryString.toString(), new Object[] { kdid,kdid,kuid, krid });
	}
	

	public List<Student> findStuByRole(Long clid, Long krid, String sname, String sno) {
		String queryString = "from Student as stu where stu.clId=? and stu.stId like '%" + sno + "%'  and stu.kuId in (select u.kuId from WkTUser as u where " + "u.kuName like '%" + sname + "%' ) and stu.kuId in (select role.id.kuId from XyUserrole as role where role.id.krId=?)) order by stu.stId";
		return getHibernateTemplate().find(queryString.toString(), new Object[] { clid, krid });
	}

	public List<WkTRole> FindByName(String rname) {
		String queryString = "from WkTRole as r where r.krName=? ";
		return getHibernateTemplate().find(queryString, new Object[] { rname });
	}

	public List<WkTRole> findAllRole(Long kdid) {
		String queryString = "from WkTRole where kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public List<WkTRole> findAllRoleWithout(Long kdid) {
		String queryString = "from WkTRole where kdId= ? and krPid <> 0";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public List<WkTRole> findbyKrAdmins(String KrAdmins) {
		String queryString = "from WkTRole where krId in ("+KrAdmins+")";
		return getHibernateTemplate().find(queryString);
	}
	public List findrolebykuid(Long kuid) {
		String queryString = "select distinct urole.id.krId from WkTUserole as urole where urole.id.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}
	public List findByKdidAndKrname(Long kdid, String krname) {
		String queryString = "from WkTRole where kdId=? and krName=?";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, krname });
	}
	public List getUserRoleId(Long uid)
	{
		String queryString="from WkTUserole as ur where ur.id.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[]{uid});
	}
}
