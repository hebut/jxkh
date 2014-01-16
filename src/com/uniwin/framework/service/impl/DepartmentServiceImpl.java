package com.uniwin.framework.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTRole;
import com.uniwin.framework.service.DepartmentService;

@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends BaseServiceImpl implements DepartmentService {

	public List<WkTDept> findByKuidAndKtname(Long kuid, String ktname) {
		String queryString = " from WkTDept as d  where d.kdId in( select dd.kdSchid  from WkTDept as dd where dd.kdId in( select u_r.kdId from XyUserrole as u_r where u_r.id.kuId=? and u_r.id.krId in(select au.krId from WkTAuth as au"
				+ " where au.kaRid in ( select t.ktId from WkTTitle as t where t.ktName=?)))) ";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, ktname });
	}

	public List<WkTDept> getChildDepartment(Long ptid, String kdtype) {
		String queryString = null;
		if (kdtype.equals(WkTDept.QUANBU)) {
			queryString = "from WkTDept as d where d.kdPid=?  order by d.kdOrder";
		} else {
			queryString = "from WkTDept as d where d.kdPid=?  and d.kdType=" + kdtype + " order by d.kdOrder";
		}
		return getHibernateTemplate().find(queryString, new Object[] { ptid });
	}

	public List<WkTDept> getChildDepartment(Long ptid, Long notdid) {
		String queryString = "from WkTDept as d where d.kdPid=? and d.kdId!=? order by d.kdNumber";
		return getHibernateTemplate().find(queryString, new Object[] { ptid, notdid });
	}

	// 获取下级单位部门
	public List<WkTDept> findDanweiAndBumenByKpid(Long kdid) {
		String queryString = "from WkTDept as d where d.kdPid=? " + "and (d.kdType=" + WkTDept.DANWEI + " or d.kdType =" + WkTDept.BUMEN + ")";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public void delete(WkTDept dept) {
		// String queryString="from WkTAuth as auth where auth.kdId=?";
		// List alist=getHibernateTemplate().find(queryString, new
		// Object[]{dept.getKdId()});
		// getHibernateTemplate().deleteAll(alist);
		// 首先删除权限
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hqlDelete = "delete WkTAuth as auth where auth.kdId=?";
		Query dq = session.createQuery(hqlDelete);
		dq.setLong(0, dept.getKdId());
		dq.executeUpdate();
		// 然后处理部门所属用户
		String updateUser = "update WkTUser as user set user.kdId=? where user.kdId=?";
		Query uUser = session.createQuery(updateUser);
		uUser.setLong(0, dept.getKdPid());
		uUser.setLong(1, dept.getKdId());
		uUser.executeUpdate();
		session.flush();
		session.clear();
		// 处理部门所有角色
		String roleString = "from WkTRole as r where r.kdId=?";
		List<WkTRole> rlist = getHibernateTemplate().find(roleString, new Object[] { dept.getKdId() });
		for (int i = 0; i < rlist.size(); i++) {
			WkTRole r = (WkTRole) rlist.get(i);
			// 删除角色权限
			String deleteRole = "delete WkTAuth as auth where auth.krId=?";
			Query dRole = session.createQuery(deleteRole);
			dRole.setLong(0, r.getKrId());
			dRole.executeUpdate();
			// 删除角色用户关系
			String deleteUserRole = "delete WkTUserole as u_r where u_r.id.krId=?";
			Query dUserRole = session.createQuery(deleteUserRole);
			dUserRole.setLong(0, r.getKrId());
			dUserRole.executeUpdate();
			// 删除角色用户
			String deletexyUserRole = "delete XyUserrole as u_r where u_r.id.krId=?";
			Query xyUserRole = session.createQuery(deletexyUserRole);
			xyUserRole.setLong(0, r.getKrId());
			xyUserRole.executeUpdate();
			session.flush();
			session.clear();
		}
		// 删除角色
		String deleteRole = "delete WkTRole as r where r.kdId=?";
		Query dRole = session.createQuery(deleteRole);
		dRole.setLong(0, dept.getKdId());
		dRole.executeUpdate();
		session.flush();
		session.clear();
		session.getTransaction().commit();
		// 最后删除部门
		getHibernateTemplate().delete(dept);
	}

	public Long getUserCount(Long did) {
		String queryString = "select count(*) from WkTUser as u where u.kdId=?";
		List<Long> rlist = getHibernateTemplate().find(queryString, new Object[] { did });
		return (Long) rlist.get(0);
	}

	public WkTDept findByDid(Long kdid) {
		String queryString = "from WkTDept as d where d.kdId=? ";
		if (getHibernateTemplate().find(queryString, new Object[] { kdid }).size() == 0) {
			WkTDept dept = new WkTDept();
			dept.setKdName("");
			dept.setKdNumber("");
			return dept;
		} else {
			return (WkTDept) getHibernateTemplate().find(queryString, new Object[] { kdid }).get(0);
		}
	}

	public List<WkTDept> getDepByuser(List<Long> rlist) {
		StringBuffer queryString = new StringBuffer("from WkTDept as d where d.kdId in (select u.kdId from WkTUser as u where u.kuId in (select ur.id.kuId from WkTUserole as ur where ur.id.krId in(");
		for (int i = 0; i < rlist.size(); i++) {
			Long l = (Long) rlist.get(i);
			queryString.append(l.longValue());
			if (i < rlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append("))) order by d.kdId");
		return getHibernateTemplate().find(queryString.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uniwin.framework.service.DepartmentService#getRootList(java.lang.
	 * Long)
	 */
	public List<WkTDept> getRootList(Long kuid) {
		String queryString = "from WkTDept as d where d.kdId in (select r.kdId from WkTRole as r where r.krId in(select u_r.id.krId from XyUserrole as u_r where u_r.id.kuId=?))";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public List<WkTDept> findByPpKdid(Long kdid) {
		String queryString = "from WkTDept as d where d.kdType=? and d.kdPid in (select d1.kdId from WkTDept as d1 where d1.kdPid=?)";
		return getHibernateTemplate().find(queryString, new Object[] { WkTDept.DANWEI, kdid });
	}

	public WkTDept findByKdname(String kdname) {
		String queryString = "from WkTDept as d where d.kdName=?";
		List<WkTDept> dlist = getHibernateTemplate().find(queryString, new Object[] { kdname });
		if (dlist.size() > 0) {
			return (WkTDept) dlist.get(0);
		} else {
			return null;
		}
	}

	public WkTDept findByKdnameandschid(String kdname, Long kdpid) {
		String queryString = "from WkTDept as d where d.kdName=? and d.kdPid=?";
		List<WkTDept> dlist = getHibernateTemplate().find(queryString, new Object[] { kdname, kdpid });
		if (dlist.size() > 0) {
			return (WkTDept) dlist.get(0);
		} else {
			return null;
		}
	}

	public List<WkTDept> findKkDep() {
		String queryString = "from WkTDept as d where d.kdId in (select jc.jcFromkdid from JxCourse as jc )";
		return getHibernateTemplate().find(queryString);
	}

	public WkTDept findByKdnumberAndKdSchid(String kdNum, Long kdid) {
		String query = "from WkTDept as d where d.kdNumber=? and d.kdSchid=?";
		List<WkTDept> dlist = getHibernateTemplate().find(query, new Object[] { kdNum, kdid });
		if (dlist.size() == 0) {
			return null;
		} else {
			return (WkTDept) dlist.get(0);
		}
	}

	public List<WkTDept> findByLevelAndSchid(Integer level, Long kdid) {
		String query = "from WkTDept as d where d.kdLevel=? and d.kdSchid=?";
		return getHibernateTemplate().find(query, new Object[] { level, kdid });
	}

	public List<WkTDept> findByBuid(Long buid) {
		String queryString = "from WkTDept as d where d.kdId in (select gpu.kdId from BsGpunit as gpu where gpu.buId=?)";
		return getHibernateTemplate().find(queryString, buid);
	}

	public List<WkTDept> getChildDepdw(Long ptid) {
		String queryString = "from WkTDept as d where d.kdPid=? and d.kdType=? order by d.kdOrder";
		return getHibernateTemplate().find(queryString, new Object[] { ptid, WkTDept.DANWEI });
	}

	public List<WkTDept> findDeptForFDY(Long kipid, Long kuid) {
		String query = "from WkTDept as d where d.kdPid=? and d.kdId in (select c.kdId from XyClass as c where c.clId in (select f.id.clId from XyFudao as f where f.id.kuId=?))";
		return getHibernateTemplate().find(query, new Object[] { kipid, kuid });
	}

	public List<WkTDept> findDeptByKdidAndType(Long kdid, String type) {
		String queryString = "from WkTDept as d where d.kdPid=? and d.kdType=? order by d.kdNumber";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, type });
	}

	public List<WkTDept> findbykdidkdpid(Long kdid, Long kdpid) {
		String queryString = "from WkTDept as d where d.kdId =? and d.kdPid=? ";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdpid });
	}

	public List<WkTDept> getChildrenbyPtid(Long ptid) {
		String queryString = "from WkTDept as d where d.kdPid=? and d.kdState = 0";
		return getHibernateTemplate().find(queryString, ptid);
	}

	public List<WkTDept> getChildrenbyPtidForWork(Long ptid) {
		String queryString = "from WkTDept as d where d.kdPid=? and d.kdState = 0 and d.kdClassify =" + WkTDept.WORK;
		return getHibernateTemplate().find(queryString, ptid);
	}

	public List getChildDepartment(Long ptid) {
		String queryString = "from WkTDept as d where d.kdPid=? order by d.kdNumber";
		return getHibernateTemplate().find(queryString, new Object[] { ptid });
	}

	@Override
	public List<WkTDept> getDeptByNum(String num) {
		String query = "from WkTDept as d where d.kdNumber = ?";
		return getHibernateTemplate().find(query, new Object[] { num });
	}
}
