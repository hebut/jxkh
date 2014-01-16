package org.iti.xypt.service.impl;

import java.util.List;
import org.iti.kyjf.entity.Zbteacher;
import org.iti.xypt.entity.Teacher;
import org.iti.xypt.service.TeacherService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class TeacherServiceImpl extends BaseServiceImpl implements TeacherService {

	public List FindTeacherByTidAndKdid(String thid, Long sid) {
		String queryString = "from Teacher as tea where tea.thId=? and tea.kdId=? order by tea.thId";
		return getHibernateTemplate().find(queryString, new Object[] { thid, sid });
	}

	public List FindTeacherByKuidAndKdid(Long kuid, Long sid) {
		String queryString = "from Teacher as tea where tea.kuId=? and tea.kdId=? order by tea.kuId";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, sid });
	}

	public Teacher findBtKuid(Long kuId) {
		String queryString = "from Teacher as tea where tea.kuId=? ";
		List teacherlist = getHibernateTemplate().find(queryString, kuId);
		if (teacherlist != null && teacherlist.size() > 0) {
			return (Teacher) teacherlist.get(0);
		} else {
			return null;
		}
	}

	public String FindTeacherByKuid(Long kuid) {
		String queryString = "select thId from Teacher as tea where  tea.kuId=?";
		return (String) getHibernateTemplate().find(queryString, new Object[] { kuid }).get(0);
	}

	public Teacher findByThid(String thid) {
		String queryString = "from Teacher as tea where tea.thId=?";
		List tlist = getHibernateTemplate().find(queryString, thid);
		if (tlist != null && tlist.size() > 0) {
			return (Teacher) tlist.get(0);
		} else {
			return null;
		}
	}

	public Teacher findBykuid(Long kuid) {
		Teacher tea = (Teacher) getHibernateTemplate().get("org.iti.xypt.entity.Teacher", kuid);
		return tea;
	}

	public List findBydeplistAndrid(List deplist, Long rid) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where  tea.kuId in(" + "select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List findBydeplistAndrid(List deplist, Long rid, Long norid) {
		if (null == norid) {
			return findBydeplistAndrid(deplist, rid);
		}
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.id.kuId not in(" + "select u_r.id.kuId from XyUserrole as u_r where u_r.id.krId=?) and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, norid });
	}

	public List findTeacherByThidAndKdid(List list) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.thId in(");
		for (int i = 0; i < list.size(); i++) {
			Object[] task = (Object[]) list.get(i);
			queryString.append(task[0]);
			if (i < list.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") " + "and tea.kdId in(");
		for (int i = 0; i < list.size(); i++) {
			Object[] task = (Object[]) list.get(i);
			queryString.append(task[1]);
			if (i < list.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")");
		return getHibernateTemplate().find(queryString.toString());
	}

	public List findNoBsTeacher(List deplist, Long rid, Long buid, String tname, String to) {
		StringBuffer queryString = new StringBuffer(" Select tea from Teacher as tea ,WkTUser as wuser ,WkTDept as wdept where tea.kuId=wuser.kuId and tea.thId like '%" + to + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=?  and role.id.kuId not in(" + "select bt.kuId from BsTeacher as bt where bt.buId=?) and role.kdId=wdept.kdId and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by wdept.kdNumber,wuser.kuName");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, buid });
	}

	public WkTDept findWktuserByKdid(Long kdid) {
		String queryString = "from WkTDept as dept where dept.kdId=?";
		return (WkTDept) getHibernateTemplate().find(queryString, new Object[] { kdid }).get(0);
	}

	public Teacher fingTeacherByTidKdid(String i, Long d) {
		String queryString = "from Teacher as t Where t.thId=? and t.kdId=?";
		return (Teacher) getHibernateTemplate().find(queryString, new Object[] { i, d }).get(0);
	}

	public WkTUser findUserByKuname(String kuname) {
		String queryString = "from WkTUser as wktuser where wktuser.kuName=?";
		return (WkTUser) getHibernateTemplate().find(queryString, new Object[] { kuname }).get(0);
	}

	public WkTUser findByUserByKuid(Long kuid) {
		String queryString = "from WkTUser as wktuser where wktuser.kuId=?";
		List ulst = getHibernateTemplate().find(queryString, kuid);
		if (ulst != null && ulst.size() != 0) {
			return (WkTUser) ulst.get(0);
		} else {
			return null;
		}
		// return (WkTUser) getHibernateTemplate().find(queryString, new Object[] { kuid }).get(0);
	}

	public List findNoBsPeerview(List deplist, Long rid, Long kuid, Long bdbId) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.kuId in(select xu.id.kuId " + "from XyUserrole as xu where xu.id.krId=? and " + "xu.id.kuId <>? and xu.id.kuId not in(select bp.kuId from BsPeerview as bp where bp.bdbId=?) and xu.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, kuid, bdbId });
	}

	public List findBydeplistNotInReply(List deplist, Long buid, Short rbatch) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.kuId not in " + "(select tg.id.kuId from BsTgroup as tg where tg.id.brpId in" + "(select rp.brpId from BsReplypacket as rp where rp.buId=? and rp.brpRbatch=?)) " + "and tea.kuId in" + "(select role.id.kuId " + "from XyUserrole as role where " + "role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.kdId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { buid, rbatch });
	}

	public List findBydeplistAndrid(List deplist, Long rid, String tname, String tno) {
		StringBuffer queryString = new StringBuffer("Select tea from Teacher as tea ,WkTUser as ktuser,WkTDept as wdept where tea.kuId=ktuser.kuId and tea.thId like '%" + tno + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(" + "select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and wdept.kdId=role.kdId and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by wdept.kdNumber,ktuser.kuName");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List findBydeplistAndrid(List deplist, Long rid, Long norid, String tname, String tno) {
		if (null == norid) {
			return findBydeplistAndrid(deplist, rid);
		}
		StringBuffer queryString = new StringBuffer("Select tea from Teacher as tea ,WkTUser as ktuser,WkTDept as wdept where tea.kuId=ktuser.kuId and  tea.thId like '%" + tno + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.id.kuId not in(" + "select u_r.id.kuId from XyUserrole as u_r where u_r.id.krId=?) and role.kdId=wdept.kdId and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by wdept.kdNumber,ktuser.kuName");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, norid });
	}

	public List findByThidAndKdid(String thid, Long kdid) {
		String queryString = "from Teacher as tea where tea.thId=? and tea.kdId=?";
		return find(queryString, new Object[] { thid, kdid });
	}

	public List findNoBsPeerview(List deplist, Long rid, Long kuid, Long bdbId, String tname, String tno) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where  tea.thId like '%" + tno + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select xu.id.kuId " + "from XyUserrole as xu where xu.id.krId=? and " + "xu.id.kuId <>? and xu.id.kuId not in(select bp.kuId from BsPeerview as bp where bp.bdbId=?) and xu.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, kuid, bdbId });
	}

	public List findBydeplistAndrid(List deplist, String tname, String tno) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.thId like '%" + tno + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString());
	}

	public List FindTeacherByThid(String thid) {
		String queryString = "from Teacher as tea where tea.thId=? order by tea.thId";
		return getHibernateTemplate().find(queryString, new Object[] { thid });
	}

	public List findNoCqTeacherAndSchid(List deplist, Long rid, Long schid, String tname, String to) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.thId like '%" + to + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.id.kuId not in(" + "select bt.kuId from CqTeacher as bt where bt.kdId= " + schid + ") and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List findNoYjsTeacherAndKdid(List deplist, Long rid, Long kdid, String tname, String to) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.thId like '%" + to + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.id.kuId not in(" + "select bt.id.kuId from YjsTeacher as bt where bt.id.kdId= " + kdid + ") and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List findBydeplistNotInYjsReply(List deplist, Long ypid, Long kdid, Integer grade) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.kuId not in " + "(select tg.id.kuId from YjsTgroup as tg where tg.id.yrpId in" + "(select rp.yrpId from YjsReplypacket as rp where rp.ypId=? and rp.kdId=? and rp.yjsGrade=?)) " + "and tea.kuId in" + "(select role.id.kuId " + "from XyUserrole as role where " + "role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.kdId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { ypid, kdid, grade });
	}

	public List findBydeplistAndridAndGradeAndKdid(List deplist, String tname, String tno, Integer grade, Long kdid, Long ypid) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.kuId not in " + "(select tg.id.kuId from YjsTgroup as tg where tg.id.yrpId in" + "(select rp.yrpId from YjsReplypacket as rp where rp.ypId=" + ypid + "and rp.kdId=" + kdid + " and rp.yjsGrade=" + grade + ")) and tea.thId like '%" + tno + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString());
	}

	public List FindBykuId(Long kuId) {
		// TODO Auto-generated method stub
		String queryString = "from Teacher as t where t.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] { kuId });
	}

	public List findBydeplistNotInGhuserdept(List deplist, String tname, String to, Long rid, Long kdid) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.thId like '%" + to + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' )and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.id.kuId not in (" + "select bt.id.kuId from GhUserdept as bt where bt.id.kdId=" + kdid + " ) and  role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List findNoFxfzrTeacher(List deplist, Long rid, String tname, String to) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where tea.thId like '%" + to + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.id.kuId not in(" + "select bt.id.kuId from GhFxfz as bt ) and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List findBykdidAndnotinUserdept(Long kdid) {
		String queryString = "from WkTUser as user where (user.kdId=? or user.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid=?)) and user.kuId in (select tea.kuId from Teacher as tea ) and user.kuId not in ( select ud.id.kuId from GhUserdept as ud where ud.id.kdId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid, kdid });
	}

	public List findBykridAndnotinpsrelation(Long kuid, Long wpid, List deptlist, Long rid, String tno, String tname) {
		StringBuffer queryString = new StringBuffer("from Teacher as tea where  tea.thId like '%" + tno + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' ) and tea.kuId in(select xu.id.kuId " + "from XyUserrole as xu where xu.id.krId=? and " + " xu.id.kuId not in(select bp.kuEid from WpRelation as bp where bp.kuId=? and bp.wpId=?) and xu.kdId in(");
		for (int i = 0; i < deptlist.size(); i++) {
			WkTDept dept = (WkTDept) deptlist.get(i);
			queryString.append(dept.getKdId());
			if (i < deptlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by tea.thId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, kuid, wpid });
	}

	public List<Zbteacher> findNoByYear(Integer year, List deplist, Long krid, String thid, String name) {
		String queryString = "from Teacher as tea where tea.thId like '%" + thid + "%'  and tea.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + name + "%' ) and tea.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.id.kuId not in (select zt.kuId from Zbteacher as zt where zt.ztYear=?) and role.kdId in(";
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString = queryString + dept.getKdId();
			if (i < deplist.size() - 1) {
				queryString = queryString + ",";
			}
		}
		queryString = queryString + "))";
		return getHibernateTemplate().find(queryString, new Object[] { krid, year });
	}

	public List findBydeplistAndrole(List deplist, Long rid, Long kdid, Long nrid, String thid, String name) {
		StringBuffer queryString = new StringBuffer("select tea from Teacher as tea,WkTUser as user  where tea.kuId=user.kuId and tea.thId like '%" + thid + "%' and tea.kuId not in (select yjstea.kuId from YjsTeacher as yjstea where yjstea.kdId="+kdid+" and yjstea.ytState=0) and user.kuName like '%" + name + "%' and tea.kuId in(select role.id.kuId from XyUserrole as role where role.id.krId=? and role.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")) and tea.kuId not in (select role.id.kuId from XyUserrole as role where role.id.krId=? and kdId =?) order by user.kuName");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, nrid, kdid });
	}
	
	public List findBytiId(String tiId) {
		String queryString = "from Title as t where t.tiId=? ";
		return getHibernateTemplate().find(queryString.toString(), new Object[] { tiId });
	}

}
