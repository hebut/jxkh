/**
 * 
 */
package org.iti.xypt.service.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.entity.XyUserrole;
import org.iti.xypt.service.XyUserRoleService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTUserole;
import com.uniwin.framework.entity.WkTUseroleId;

/**
 * <li>项目名称: xypt <li>功能描述: 该文件的功能描述 <li>版权: Copyright (c) 2000-2007 UniWin Co. Ltd. <li>公司: 中信联信息技术有限公司
 * 
 * @author DaLei
 * @version $Id: XyUserRoleServiceImpl.java,v 1.1 2011/08/31 07:03:01 ljb Exp $
 */
public class XyUserRoleServiceImpl extends BaseServiceImpl implements XyUserRoleService {

	public List getUserRole(Long kuid, Long kdid) {
		/**
		 * String queryString="from WkTRole as r where r.kdId=? and  r.krId in ( select u_r.id.krId" + " from XyUserrole as u_r where u_r.id.kuId=? )order by r.krOrder";
		 **/
		String queryString = "from XyUserrole as u_r where u_r.id.kuId=? and u_r.id.krId in(select r.krId from WkTRole as r where r.kdId=?  )";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, kdid });
	}

	public List getUserRole(Long kuid) {
		/**
		 * String queryString="from WkTRole as r where r.krId in ( select u_r.id.krId" + " from XyUserrole as u_r where u_r.id.kuId=? )order by r.krOrder";
		 **/
		String queryString = "from XyUserrole as u_r where u_r.id.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public void saveXyUserrole(XyUserrole urole) {
		save(urole);
		WkTUseroleId uroleid = new WkTUseroleId(urole.getId().getKrId(), urole.getId().getKuId());
		WkTUserole userole = (WkTUserole) get(WkTUserole.class, uroleid);
		if (userole == null) {
			getHibernateTemplate().save(new WkTUserole(uroleid));
		}
		// 增加用户角色通知关联关系
		while (urole.getKdId().longValue() != 0L) {
			XyNUrd nurd = new XyNUrd(urole);
			getHibernateTemplate().saveOrUpdate(nurd);
			urole = new XyUserrole(urole.getId(), urole.getDept().getKdPid());
		}
	}

	public void update(XyUserrole xyu) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String deleteNR = "delete XyNUrd as xnurd where xnurd.id.kuId=? and xnurd.id.krId=?";
		Query deleteNRQ = session.createQuery(deleteNR);
		deleteNRQ.setLong(0, xyu.getId().getKuId());
		deleteNRQ.setLong(1, xyu.getId().getKrId());
		deleteNRQ.executeUpdate();
		session.update(xyu);
		session.getTransaction().commit();

		// 增加用户角色通知关联关系
		while (xyu.getKdId().longValue() != 0L) {
			XyNUrd nurd = new XyNUrd(xyu);
			getHibernateTemplate().saveOrUpdate(nurd);
			xyu = new XyUserrole(xyu.getId(), xyu.getDept().getKdPid());
		}
	}

	public void deleteXyUserrole(XyUserrole urole) {
		WkTUseroleId uroleid = new WkTUseroleId(urole.getId().getKrId(), urole.getId().getKuId());
		WkTUserole userole = (WkTUserole) get(WkTUserole.class, uroleid);
		if (userole != null) {
			getHibernateTemplate().delete(userole);
		}
		getHibernateTemplate().delete(urole);
		// 删除用户角色通知关系

		Session session = this.getHibernateTemplate().getSessionFactory().openSession();

		session.beginTransaction();

		String deleteNR = "delete XyNUrd as xnurd where xnurd.id.kuId=? and xnurd.id.krId=?";
		Query deleteNRQ = session.createQuery(deleteNR);
		deleteNRQ.setLong(0, urole.getId().getKuId());
		deleteNRQ.setLong(1, urole.getId().getKrId());
		deleteNRQ.executeUpdate();


		session.getTransaction().commit();

	}

	/*
	 * (non-Javadoc)
	 * @see org.iti.xypt.service.XyUserRoleService#getUserRoleOfTitle(java.lang.Long, java.lang.Long)
	 */
	public List getUserRoleOfTitle(Long kuid, Long tid) {
		String queryString = "from XyUserrole as u_r where u_r.id.kuId=? and u_r.id.krId in(select au.krId from WkTAuth as au" + " where au.kaRid=? and au.kaType=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, tid, WkTAuth.TYPE_TITLE });
	}

	public List getUserRoleOfTitle(Long kuid, Long tid, Long krid) {
		String queryString = "from XyUserrole as u_r where u_r.id.kuId=? and u_r.id.krId in(select r.krId from WkTRole as r" + " where r.krPid=? ) and u_r.id.krId in(select au.krId from WkTAuth as au" + " where au.kaRid=? and au.kaType=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, krid, tid, WkTAuth.TYPE_TITLE });
	}

	/**
	 * 管理组织部门中需要列出管理组织功能下具有某个学校的访问此功能的角色
	 * 
	 * @param kuid
	 * @param tid
	 * @param kdid
	 * @return
	 */
	public List getUserRoleOfTitleAndDept(Long kuid, Long tid, Long kdid) {
		String queryString = "from XyUserrole as u_r where u_r.id.kuId=? and u_r.id.krId in(select au.krId from WkTAuth as au" + " where au.kaRid=? and au.kaType=? ) and u_r.id.krId in(select r.krId from " + "WkTRole as r where r.kdId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, tid, WkTAuth.TYPE_TITLE, kdid });
	}

	public Long getRoleId(String rname, Long kdid) {
		String queryString = "select r.krId from WkTRole as r where r.krName=? and r.kdId=?";
		List rlist = getHibernateTemplate().find(queryString, new Object[] { rname, kdid });
		if (rlist.size() > 0) {
			return (Long) rlist.get(0);
		} else {
			return 0L;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.iti.xypt.service.XyUserRoleService#countByRidAndKdid(java.lang.Long, java.lang.Long)
	 */
	public Long countByRidAndKdid(Long rid, Long kdid) {
		String queryString = "select count(*) from XyUserrole as xu where xu.id.krId=? and xu.kdId=?";
		return (Long) find(queryString, new Object[] { rid, kdid }).get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see org.iti.xypt.service.XyUserRoleService#countByRidAndKdid(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public Long countByRidAndKdid(Long rid, Long kdid, Long norid) {
		if (norid == null) {
			return countByRidAndKdid(rid, kdid);
		}
		String queryString = "select count(*) from XyUserrole as xu where xu.id.krId=? and " + "xu.kdId=? and xu.id.kuId not in(select u_r.id.kuId from XyUserrole as u_r where u_r.id.krId=?)";
		return (Long) find(queryString, new Object[] { rid, kdid, norid }).get(0);
	}

	public Long countNoBsPeerview(Long rid, Long kdid, Long kuid, Long bdbId) {
		String queryString = "select count(*) from XyUserrole as xu where xu.id.krId=? and " + "xu.kdId=? and xu.id.kuId <>? and xu.id.kuId not in(select bp.kuId from BsPeerview as bp where bp.bdbId=?)";
		return (Long) find(queryString, new Object[] { rid, kdid, kuid, bdbId }).get(0);
	}

	public List findByKuidAndKdid(Long kuid, Long kdid) {
		String query = "from XyUserrole as xu where xu.id.kuId=? and xu.id.krId in(" + "select r.krId from WkTRole as r where r.kdId=?)";
		return find(query, new Object[] { kuid, kdid });
	}

	public List findByKuid(Long kuid) {
		String query = " from XyUserrole as xu where xu.id.kuId=?";
		return find(query, new Object[] { kuid });
	}

	public Long countNoBsTeacher(Long rid, Long kdid, Long buid) {
		String queryString = "select count(*) from XyUserrole as xu where xu.id.krId=? and " + "xu.kdId=? and xu.id.kuId not in(select bt.kuId from BsTeacher as bt where bt.buId=?)";
		return (Long) find(queryString, new Object[] { rid, kdid, buid }).get(0);
	}

	public List findByKridAndKuid(Long krid, Long kuid) {
		String queryString = "from XyUserrole as xu where xu.id.krId=? and xu.id.kuId=?";
		return (List) find(queryString, new Object[] { krid, kuid });
	}

	public List findByKridAndKdid(Long krid, Long kdid) {
		String query = "from XyUserrole as xu where xu.id.krId=? and xu.kdId=?";
		return find(query, new Object[] { krid, kdid });
	}

	public List findByKridAndBgidAndKPPdid(Long krid, Long bgid, Long kppdid) {
		String query = "from XyUserrole as xu where xu.id.krId=? and xu.kdId in (select de.kdId  from WkTDept as de where  de.kdId in (select unit.kdId from BsGpunit as unit where unit.bgId=?) and de.kdPid in ( select dep.kdId  from WkTDept as dep where dep.kdPid =?))order by kdId";
		return find(query, new Object[] { krid, bgid, kppdid });
	}

	public List findByKrid(Long krid) {
		String query = "from XyUserrole as xu where xu.id.krId=? ";
		return find(query, new Object[] { krid });
	}

	public List findStuHeaderRole(Long kdId) {
		String query = "from WkTRole as role where role.kdId=? and role.krArgs like '__5%'";
		return find(query, new Object[] { kdId });
	}

	public List findFDYRole(Long kuid, Long kdid) {
		String queryString = "from XyUserrole as u_r where u_r.id.kuId=? and u_r.id.krId in(select r.krId from WkTRole as r where r.kdId=? and r.krArgs like '__4%')";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, kdid });
	}

	public Long countNoCqTeacherAndschid(Long rid, Long kdid, Long schid) {
		String queryString = "select count(*) from XyUserrole as xu where xu.id.krId=? and " + "xu.kdId=? and xu.id.kuId not in(select bt.kuId from CqTeacher as bt where bt.kdId=? )";
		return (Long) find(queryString, new Object[] { rid, kdid, schid }).get(0);
	}

	public List getonlyUserRole(Long kuid, Long kdid) {
		String queryString = "from XyUserrole where id.kuId=? and kdId = ?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, kdid });
	}

	public List findByKridAndKdidAndName(Long krid, Long kdid, String name, Long wpid) {
		String query = "from XyUserrole as xu where xu.id.krId=? and xu.kdId=? and xu.id.kuId in (select u.kuId from WkTUser as u where u.kuName like '%" + name + "%') and xu.id.kuId not in(select wuser.kuId from WpUser as wuser where wuser.wpId=?)";
		return find(query, new Object[] { krid, kdid, wpid });
	}

	public List findByKridAndKdidAndNameAndThid(Long krid, Long kdid, String name, String thid) {
		String query = "from XyUserrole as xu where xu.id.krId=? and xu.kdId=? and xu.id.kuId in (select u.kuId from WkTUser as u where u.kuName like '%" + name + "%') and xu.id.kuId in (select t.kuId from Teacher as t where t.thId like '%" + thid + "%')";
		return find(query, new Object[] { krid, kdid });
	}
}
