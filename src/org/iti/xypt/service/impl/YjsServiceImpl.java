package org.iti.xypt.service.impl;

import java.util.List;
import org.iti.xypt.entity.Yjs;
import org.iti.xypt.service.YjsService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;

public class YjsServiceImpl extends BaseServiceImpl implements YjsService {

	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno) {
		StringBuffer queryString = new StringBuffer("Select stu from Yjs as stu,WkTDept as wdept,WkTUser as user where user.kuId = stu.kuId and user.kuName like '%" + tname + "%'");
		if (tno != null && tno.length() > 0) {
			queryString.append(" and stu.yjsId like '%" + tno + "%'");
		}
		queryString.append(" and stu.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.kdId=wdept.kdId and role.kdId in(");
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept dept = (WkTDept) dlist.get(i);
			queryString.append(dept.getKdId());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		if (grade.intValue() != 0) {
			queryString.append(") ) and stu.yjsGrade=? order by wdept.kdNumber,stu.clId,user.kuName");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, grade });
		} else {
			queryString.append(") )order by wdept.kdNumber ,stu.clId,user.kuName");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
		}
	}

	/*
	 * public List findGrade(String kdid) { String queryString = "select distinct yj.yjsGrade from Yjs as yj where yj.yjsId=?"; return getHibernateTemplate().find(queryString.toString(), new Object[] { kdid }); }
	 */
	public List findgradeByKdid(Long kdid) {
		String queryString = "select distinct num.yjsGrade from Yjs as num where num.kuId in(select suser.kuId from WkTUser as suser where suser.kdId=? )order by num.yjsGrade desc";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public List findBygradeAndkdidAndNameAndStidNotInDb(Integer grade, Long kdid, String name, String stid) {
		String queryString = "select stu from Yjs as stu ,WkTUser as user where stu.yjsGrade=? and stu.kuId=user.kuId and user.kdId=?  and stu.kuId not in(select cho.skuId from YjsDbchoose as cho ) and stu.yjsNormal=0";
		Object[] arg;
		if (name != null && name.length() > 0) {
			queryString = queryString + "and user.kuName like '%" + name + "%'";
		}
		if (stid != null && stid.length() > 0) {
			queryString = queryString + " and stu.yjsId like '%" + stid + "%'";
		}
		queryString = queryString +"order by user.kuName";
		arg = new Object[] { grade, kdid };
		return getHibernateTemplate().find(queryString, arg);
	}

	public List findzy() {
		String queryString = "select distinct w.kdId from WkTUser as w where w.kuId in(" + "select y.kuId from Yjs as y  )";
		return getHibernateTemplate().find(queryString);
	}

	public Yjs findByKuid(Long kuid) {
		String queryString = "from Yjs as yjs where yjs.kuId=?";
		List yjslist = getHibernateTemplate().find(queryString, kuid);
		if (yjslist != null && yjslist.size() > 0) {
			return (Yjs) yjslist.get(0);
		} else {
			return null;
		}
	}

	public List findgradebykdid(Long kdid) {
		String queryString = "select distinct ys.yjsGrade from Yjs as ys where ys.kuId in(select wk.kuId from WkTUser as wk where wk.kdId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public List findByGradeAndKdid(Integer grade, Long kdid) {
		String queryString = "select ys from Yjs as ys,WkTUser as user where ys.yjsGrade=? and ys.kuId=user.kuId and user.kdId=? order by user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { grade, kdid });
	}

	public List findNotinDbByKdid(Long kdid, Integer grade ) {
		String queryString = "select ys  from Yjs as ys ,WkTUser as user  where ys.kuId=user.kuId and user.kdId=? and   ys.kuId not in(select yd.skuId from YjsDbchoose as yd )  and ys.yjsGrade=? order by user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, grade });
	}

	public List findInDbByKdid(Long kdid, Integer grade,Long kuid) {
		String queryString = "select yd from YjsDbchoose as yd,WkTUser as user,WkTUser as tu  where yd.skuId=user.kuId and tu.kuId=yd.kuId and yd.skuId in (select ys.kuId from Yjs as ys) and user.kdId=? and yd.yjsGrade=?  and  yd.kuId=? order by yd.ydbIfaccept,tu.kuName,user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, grade,kuid });
	}

	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno, String isguofang) {
		StringBuffer queryString = new StringBuffer("Select stu from Yjs as stu,WkTDept as wdept,WkTUser as user where stu.blzgnx=" + isguofang + " and stu.kuId=user.kuId and user.kuName like '%" + tname + "%'");
		if (tno != null && tno.length() > 0) {
			queryString.append(" and stu.yjsId like '%" + tno + "%'");
		}
		queryString.append(" and stu.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.kdId=wdept.kdId and role.kdId in(");
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept dept = (WkTDept) dlist.get(i);
			queryString.append(dept.getKdId());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		if (grade.intValue() != 0) {
			queryString.append(") ) and stu.yjsGrade=? order by wdept.kdNumber,stu.clId,user.kuName");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, grade });
		} else {
			queryString.append(") )order by wdept.kdNumber ,stu.clId,user.kuName");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
		}
	}
	
	public List findNoChoiceByKdidGradeAndKuid(Long kdid, Integer grade,Long kuid){
		String queryString = "select yd from YjsDbchoose as yd,WkTUser as user,WkTUser as tu  where yd.skuId=user.kuId and tu.kuId=yd.kuId and yd.skuId in (select ys.kuId from Yjs as ys) and user.kdId=? and yd.yjsGrade=?  and  yd.kuId!=? order by yd.ydbIfaccept,tu.kuName,user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, grade,kuid });
	}
}
