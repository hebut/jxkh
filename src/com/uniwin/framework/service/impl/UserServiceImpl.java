package com.uniwin.framework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.xypt.entity.Teacher;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.UserService;
@SuppressWarnings("unchecked")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	public List<WkTUser> searchUserInfo(String lname, String tname, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("");
		Object[] args;
		if (lname == null || lname.length() == 0) {
			if (tname == null || tname.length() == 0) // 登录名和真实姓名都为空，返回空值
			{
				return null;
			} else // 登录名为空，但真实姓名不为空，则通过真实姓名搜索用户基本信息
			{
				queryString.append("from WkTUser as u where  u.kuName=?");
				args = new Object[] { tname };
			}
		} else // 登录名不为空，真实姓名为空，则通过登录名搜索用户基本信息
		{
			if (tname == null || tname.length() == 0) {
				queryString.append("from WkTUser as u where u.kuLid=?");
				args = new Object[] { lname };
			} else // 登录名和真实姓名均不为空，则可通过登录名和真实姓名一起进行搜索
			{
				queryString.append("from WkTUser as u where u.kuLid=? and u.kuName=?");
				args = new Object[] { lname, tname };
			}
		}
		queryString.append("and u.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long l = (Long) dlist.get(i);
			queryString.append(l.longValue());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")");
		return getHibernateTemplate().find(queryString.toString(), args);
	}
	public WkTUser getUserByuid(Long uid) {
		String queryString = "from WkTUser as u where u.kuId=?";
		return (WkTUser) getHibernateTemplate().find(queryString, new Object[] { uid }).get(0);
	}

	public List<WkTUser> getUsersNotRole(Long rid) {
		String queryString = "from WkTUser as u where u.kuId not in (select u_r.id.kuId " + "from WkTUserole as u_r where u_r.id.krId=?) order by u.kuLevel desc";
		return getHibernateTemplate().find(queryString, new Object[] { rid });
	}

	public List<WkTUser> getUsersOfRole(Long rid) {
		String queryString = "from WkTUser as u where u.kuId in (select u_r.id.kuId " + "from WkTUserole as u_r where u_r.id.krId=?) order by u.kuLevel desc";
		return getHibernateTemplate().find(queryString, new Object[] { rid });
	}

	public List<WkTUser> getUsersOfDept(Long did) {
		String queryString = "from WkTUser as u where u.kdId=? order by u.kuLevel desc";
		return getHibernateTemplate().find(queryString, new Object[] { did });
	}

	public void deleteUser(WkTUser user) {
		// 首先删除用户角色关系
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String deleteUserRole = "delete WkTUserole as u_r where u_r.id.kuId=?";
		Query dUserRole = session.createQuery(deleteUserRole);
		dUserRole.setLong(0, user.getKuId());
		dUserRole.executeUpdate();
		// 还需要删除用户发布信息、消息等
		String deleteNR = "delete XyNUrd as xnurd where xnurd.id.kuId=?";
		Query updateNRQ = session.createQuery(deleteNR);
		updateNRQ.setLong(0, user.getKuId());
		updateNRQ.executeUpdate();
		String deleteM = "delete XyMReceiver as xmr where xmr.id.kuId=?";
		Query updateM = session.createQuery(deleteM);
		updateM.setLong(0, user.getKuId());
		updateM.executeUpdate();
		String deleteN = "delete XyMessage as xm where xm.kuId=?";
		Query updateN = session.createQuery(deleteN);
		updateN.setLong(0, user.getKuId());
		updateN.executeUpdate();
		session.getTransaction().commit();
		// 删除用户
		getHibernateTemplate().delete(user);
	}

	public List<WkTUser> loginUser(String username, String pasword) {
		String queryString = "from WkTUser as u where u.kuLid=? and u.kuPasswd=?";
		return getHibernateTemplate().find(queryString, new Object[] { username, pasword });
	}

	public List<WkTUser> getUsersNotRole(Long rid, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("from WkTUser as u where u.kuId not in (select u_r.id.kuId " + "from WkTUserole as u_r where u_r.id.krId=?) and u.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long l = (Long) dlist.get(i);
			queryString.append(l.longValue());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")  order by u.kuLevel desc");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List<WkTUser> getUsersOfRole(Long rid, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("from WkTUser as u where u.kuId in (select u_r.id.kuId " + "from WkTUserole as u_r where u_r.id.krId=?) and u.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long l = (Long) dlist.get(i);
			queryString.append(l.longValue());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") order by u.kuLevel desc");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List<WkTUser> getUsersByLogin(String loginName) {
		String queryString = "from WkTUser as u where u.kuLid=?)";
		return getHibernateTemplate().find(queryString, new Object[] { loginName });
	}

	public List<WkTUser> getUsersOfDept(Long did, String key) {
		String queryString = "from WkTUser as u where u.kdId=? and u.kuName like '%" + key + "%'  order by u.kuLevel desc";
		return getHibernateTemplate().find(queryString, new Object[] { did });
	}

	public List<WkTUser> getUSersByRole(Long kdid, String rname) {
		String queryString = "from WkTUser as u where u.kuId in (select ur.id.kuId from WkTUserole as ur where ur.id.krId=(select r.krId from WkTRole as r where r.kdId=? and r.krName=?)) ";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, rname });
	}

	public List<WkTUser> getUsersByRAndD(List<Long> rlist, List<Long> dlist) {
		StringBuffer queryString = new StringBuffer("from WkTUser as u where u.kuId in (select u_r.id.kuId " + "from WkTUserole as u_r where u_r.id.krId in(");
		for (int j = 0; j < rlist.size(); j++) {
			Long r = (Long) rlist.get(j);
			queryString.append(r.longValue());
			if (j < rlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(" ) )and u.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long l = (Long) dlist.get(i);
			queryString.append(l.longValue());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") order by u.kuLevel desc");
		return getHibernateTemplate().find(queryString.toString());
	}

	public List<WkTUser> getUsersByrlist(List<Long> rlist) {
		StringBuffer queryString = new StringBuffer("from WkTUser as u where u.kuId in (select u_r.id.kuId " + "from WkTUserole as u_r where u_r.id.krId in(");
		for (int j = 0; j < rlist.size(); j++) {
			Long r = (Long) rlist.get(j);
			queryString.append(r.longValue());
			if (j < rlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by u.kuLevel desc");
		return getHibernateTemplate().find(queryString.toString());
	}

	public List<WkTUser> getUserBytlist(List<Teacher> tlist) {
		StringBuffer queryString = new StringBuffer("from WkTUser as u where u.kuId in (");
		for (int i = 0; i < tlist.size(); i++) {
			Teacher tea = (Teacher) tlist.get(i);
			queryString.append(tea.getKuId());
			if (i < tlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by u.kuLevel desc");
		return getHibernateTemplate().find(queryString.toString());
	}

	public WkTUser getUserBykuid(Long kuid) {
		String queryString = "from WkTUser as u where u.kuId=?";
		List<WkTUser> ulist = getHibernateTemplate().find(queryString, new Object[] { kuid });
		if (ulist.size() > 0) {
			return (WkTUser) ulist.get(0);
		} else {
			return null;
		}
	}

	public List<WkTUser> FindTnameForStudent(Long kuid) {
		String queryString = "from WkTUser as u where u.kuId in (select tea.kuId from Teacher as tea where tea.thId in (select task.thId from JxTask as task where task.jtClass in (select stu.stClass from Student as stu where stu.kuId=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public WkTUser findTnameByThid(String thid) {
		String queryString = "from WkTUser as u where u.kuId in (select tea.kuId from Teacher as tea where tea.thId=?)";
		List<WkTUser> tlist = getHibernateTemplate().find(queryString, new Object[] { thid });
		if (tlist != null && tlist.size() > 0) {
			return (WkTUser) tlist.get(0);
		} else {
			return null;
		}
	}

	public WkTUser findSnameBybsid(Long bsid) {
		String queryString = "from WkTUser as u where u.kuId in (select s.kuId from BsStudent as s where s.bsId=?)";
		List<WkTUser> ulist = getHibernateTemplate().find(queryString, new Object[] { bsid });
		if (ulist.size() > 0) {
			return (WkTUser) ulist.get(0);
		} else {
			return null;
		}
	}

	public WkTUser findTnameByBtid(Long btid) {
		String queryString = "from WkTUser as u where u.kuId in (select bt.kuId from BsTeacher as bt where bt.btId=?)";
		List<WkTUser> tlist = getHibernateTemplate().find(queryString, new Object[] { btid });
		if (tlist != null && tlist.size() > 0) {
			return (WkTUser) tlist.get(0);
		} else {
			return null;
		}
	}

	public List<WkTUser> findByTeacherTrueName(String trueName, List<WkTDept> dlist) {
		String query = "from WkTUser as u where u.kuName like '%" + trueName + "%' and u.kdId in (";
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept d = (WkTDept) dlist.get(i);
			sb.append(d.getKdId() + "");
			if (i < (dlist.size() - 1)) {
				sb.append(",");
			}
		}
		query = query + sb.toString() + ") and u.kuId in (select t.kuId from Teacher as t)";
		return getHibernateTemplate().find(query);
	}

	public List<WkTUser> findByStudentTrueName(String trueName, List<WkTDept> dlist) {
		String query = "from WkTUser as u where u.kuName like '%" + trueName + "%' and u.kdId in (";
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept d = (WkTDept) dlist.get(i);
			sb.append(d.getKdId() + "");
			if (i < (dlist.size() - 1)) {
				sb.append(",");
			}
		}
		query = query + sb.toString() + ") and u.kuId in (select s.kuId from Student as s)";
		return getHibernateTemplate().find(query);
	}

	public List<WkTUser> findByTrueName(String trueName, List<WkTDept> dlist) {
		String query = "from WkTUser as u where u.kuName like '%" + trueName + "%' and u.kdId in (";
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept d = (WkTDept) dlist.get(i);
			sb.append(d.getKdId() + "");
			if (i < (dlist.size() - 1)) {
				sb.append(",");
			}
		}
		query = query + sb.toString() + ")";
		return getHibernateTemplate().find(query);
	}

	public List<WkTUser> findSnameBystid(String stid) {
		String queryString = "from WkTUser as u where u.kuId in (select stu.kuId from Student as stu where stu.stId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { stid });
	}

	public List<WkTUser> findNameByTeacher(List<Teacher> telist) {
		StringBuffer queryString = new StringBuffer("from WkTUser as u where u.kuId in (");
		for (int i = 0; i < telist.size(); i++) {
			Teacher tea = (Teacher) telist.get(i);
			queryString.append(tea.getKuId());
			if (i < telist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") order by u.kuName ");
		return getHibernateTemplate().find(queryString.toString());
	}

	public List<WkTUser> findUserBykuid(Long kuid) {
		String queryString = "from WkTUser as u where u.kuId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public List<WkTUser> findBykrIdOrkrid(Long krId, Long krid) {
		String queryString = "from WkTUser as u where u.kuId  in (select xu.id.kuId from XyUserrole as xu where xu.id.krId=? or xu.id.krId=? ) )";
		return getHibernateTemplate().find(queryString, new Object[] { krId, krid });
	}

	public List<WkTUser> findUserForGroupByKdIdAndName(List<com.uniwin.asm.personal.entity.QzMember> ilist,List<com.uniwin.asm.personal.entity.QzMember> qlist,Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate) {
		String queryString;
		List<WkTUser> record = new ArrayList<WkTUser>();
		Map<Long,WkTUser> recordMap=new HashMap<Long,WkTUser>();
		if (teacher) {
			queryString = "from WkTUser as u where kuId in (select kuId from Teacher) and (u.kdId=? or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)) and u.kuName like '%" + name + "%' order by u.kdId";
			List<WkTUser> list=getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
			for(WkTUser u:list){
				recordMap.put(u.getKuId(), u);
			}
		}
		if (student) {
			queryString = "from WkTUser as u where kuId in (select kuId from Student) and (u.kdId=? or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)) and u.kuName like '%" + name + "%' order by u.kdId";
			List<WkTUser> list=getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
			for(WkTUser u:list){
				recordMap.put(u.getKuId(), u);
			}
		}
		if (graduate) {
			queryString = "from WkTUser as u where kuId in (select kuId from Yjs) and (u.kdId=? or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)) and u.kuName like '%" + name + "%' order by u.kdId";
			List<WkTUser> list=getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
			for(WkTUser u:list){
				recordMap.put(u.getKuId(), u);
			}
		}
		for(com.uniwin.asm.personal.entity.QzMember m:qlist){
			recordMap.remove(m.getMbMember());
		}
		for(com.uniwin.asm.personal.entity.QzMember m:ilist){
			recordMap.remove(m.getMbMember());
		}
		record.addAll(recordMap.values());
		return record;
	}
	public List<WkTUser> findUserForGroupByKdIdAndName(Long kdid, String name, Boolean teacher, Boolean student, Boolean graduate) {
		String queryString;
		List<WkTUser> record = new ArrayList<WkTUser>();
		
		if (teacher) {
			queryString = "from WkTUser as u where u.kuId in (select distinct ur.id.kuId from WkTUserole as ur where ur.id.krId in (122,123)) and (u.kdId=? or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)) and u.kuName like '%" + name + "%' order by u.kdId";
			record.addAll(getHibernateTemplate().find(queryString, new Object[] { kdid, kdid }));
		}
//		
//		                   from WkTUser as u where u.kuId in (select wkTUser.kuId from Teacher)                                 and (u.wktDept.kdId=? or u.wktDept.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)) and u.kuName like '%" + name + "%' order by u.wktDept.kdId";
		if (student) {
			queryString = "from WkTUser as u where u.kuId in (select distinct ur.id.kuId from WkTUserole as ur where ur.id.krId =121) and (u.kdId=? or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)) and u.kuName like '%" + name + "%' order by u.kdId";
			record.addAll(getHibernateTemplate().find(queryString, new Object[] { kdid, kdid }));
		}
//		  
		if (graduate) {
			queryString = "from WkTUser as u where u.kuId in (select distinct ur.id.kuId from WkTUserole as ur where ur.id.krId =120) and (u.kdId=? or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)) and u.kuName like '%" + name + "%' order by u.kdId";
			record.addAll(getHibernateTemplate().find(queryString, new Object[] { kdid, kdid }));
//			 
		}
		return record;
	}
	

	public List<WkTUser> findByDlistAndTnameAndTno(List<WkTDept> deplist, String tname, String tno) {
		StringBuffer queryString = new StringBuffer("from WkTUser as u where u.kuName like '%" + tname + "%' and u.kuLid like '%" + tno + "%' and u.kuId in(select tea.kuId from Teacher as tea )and u.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(" )order by u.kdId,u.kuLid");
		return getHibernateTemplate().find(queryString.toString());
	}

	public List<WkTUser> findBykrId(Long krId) {
		String queryString = "from WkTUser as u where u.kuId  in (select xu.id.kuId from XyUserrole as xu where xu.id.krId=?  ) )";
		return getHibernateTemplate().find(queryString, new Object[] { krId });
	}

	public List<WkTUser> loginUser(String username) {
		String queryString = "from WkTUser as u where u.kuLid = ? ";
		return getHibernateTemplate().find(queryString, new Object[]{username});
	}
	public List FindBykuId(Long kuId) {
		String queryString="from WkTUser as u where u.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[]{kuId});
	}
	public List<WkTUser> findstudentBykdid(Long kdid) {
		String queryString = "from WkTUser as u where (u.kdId = ? or u.kdId in (select kdId from WkTDept where (kdPid=? or kdPid in (select kdId from WkTDept where kdPid=?))) )and u.kuId in (select kuId from Student)";
		return getHibernateTemplate().find(queryString, new Object[]{kdid,kdid,kdid});
	}
	public List<WkTUser> findUserBykdid(Long kdid) {
		String queryString = "from WkTUser as u where (u.kdId = ? or u.kdId in (select kdId from WkTDept where (kdPid=? or kdPid in (select kdId from WkTDept where kdPid=?))) )";
		return getHibernateTemplate().find(queryString, new Object[]{kdid,kdid,kdid});
	}
	public List<WkTUser> finduserbykunameandkdid(String name,Long kdid) {
		String queryString = "from WkTUser as u where u.kuName=? and u.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { name, kdid});
	}
	public List<WkTUser> finduserbykuLnameandkdid(String name,Long kdid) {
		String queryString = "from WkTUser as u where u.kuLid=? and u.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { name, kdid});
	}
	@Override
	public List<WkTUser> getUserByStaffid(String staffId) {
		String queryString = "from WkTUser as u where u.staffId=?)";
		return getHibernateTemplate().find(queryString, new Object[] {staffId});
	}
	@Override
	public List<WkTUser> findByKdId(Long kdId) {
		String queryString = "from WkTUser as u where u.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] {kdId});
	}
}
