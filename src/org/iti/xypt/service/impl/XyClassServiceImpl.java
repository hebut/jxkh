package org.iti.xypt.service.impl;

import java.util.List;

import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.service.XyClassService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;

public class XyClassServiceImpl extends BaseServiceImpl implements XyClassService {
	public List getGradeList(List deptList) {
		StringBuffer queryString = new StringBuffer("select xc.clGrade from XyClass as xc where xc.kdId in(");
		for (int i = 0; i < deptList.size(); i++) {
			WkTDept d = (WkTDept) deptList.get(i);
			queryString.append(d.getKdId());
			if (i < (deptList.size() - 1)) {
				queryString.append(",");
			}
		}
		queryString.append(") group by xc.clGrade order by xc.clGrade desc");
		return find(queryString.toString());
	}

	public List findByKdidAndGrade(Long kdid, Integer grade) {
		String queryString = "from XyClass as xc where (xc.kdId=? or xc.kdId in (select kdId from WkTDept where kdPid=?))";
		if (grade == null) {
			return find(queryString, new Object[] { kdid, kdid });
		} else {
			queryString = queryString + " and xc.clGrade=?";
			return find(queryString, new Object[] { kdid, kdid, grade });
		}
	}

	public XyClass findByKdidAndCname(Long kdid, String Cname) {
		StringBuffer queryString = new StringBuffer("from XyClass as xc where xc.clSname=?");
		if (kdid != 0) {
			queryString.append(" and xc.kdId=? ");
			List clist = getHibernateTemplate().find(queryString.toString(), new Object[] { Cname, kdid });
			if (clist != null && clist.size() > 0) {
				return (XyClass) clist.get(0);
			} else {
				return null;
			}
		} else {
			List tlist = getHibernateTemplate().find(queryString.toString(), new Object[] { Cname });
			if (tlist != null && tlist.size() > 0) {
				return (XyClass) tlist.get(0);
			} else {
				return null;
			}
		}
	}

	public XyClass findByKdidAndCName(Long kdid, String cName) {
		String queryString = "from XyClass as c Where c.kdId=? and c.clName=?";
		List list = getHibernateTemplate().find(queryString, new Object[] { kdid, cName });
		if (list.size() == 0) {
			return null;
		} else {
			return (XyClass) list.get(0);
		}
	}

	public XyClass findByKuid(Long ku) {
		String queryString = "from XyClass as c Where c.clId in(select s.clId from Student as s Where s.kuId=?)";
		return (XyClass) getHibernateTemplate().find(queryString, new Object[] { ku }).get(0);
	}

	public List findByGradeAndName(Integer grade, String name, List deptList) {
		StringBuffer query = new StringBuffer("from XyClass as xc where xc.kdId in(");
		for (int i = 0; i < deptList.size(); i++) {
			WkTDept d = (WkTDept) deptList.get(i);
			query.append(d.getKdId());
			if (i < (deptList.size() - 1)) {
				query.append(",");
			}
		}
		query.append(")");
		if (grade.intValue() != 0) {
			query.append(" and xc.clGrade=? and xc.clName like '%" + name + "%' order by xc.clGrade desc ,xc.kdId");
			return find(query.toString(), new Object[] { grade });
		}
		query.append(" order by xc.clGrade desc ,xc.kdId");
		return find(query.toString());
	}

	public List findByCnameAndKdid(String cname, Long kdid) {
		String query = "from XyClass as xc where xc.clName=? and xc.kdId in(select d.kdId from WkTDept as d where d.kdSchid=? and d.kdLevel=?)";
		return find(query, new Object[] { cname, kdid, WkTDept.GRADE_XI });
	}

	public List findByScnameAndKdid(String cname, Long kdid) {
		String query = "from XyClass as xc where xc.clSname=? and xc.kdId in(select d.kdId from WkTDept as d where d.kdSchid=? and d.kdLevel=?)";
		return find(query, new Object[] { cname, kdid, WkTDept.GRADE_XI });
	}

	public List findClassByKdid(List kdid) {
		StringBuffer queryString = new StringBuffer("from XyClass as cc Where cc.kdId in(");
		if (kdid != null && kdid.size() > 0) {
			for (int i = 0; i < kdid.size(); i++) {
				Long a = (Long) kdid.get(i);
				queryString.append(a);
				if (i < kdid.size() - 1) {
					queryString.append(",");
				}
			}
			queryString.append(")");
			return getHibernateTemplate().find(queryString.toString());
		} else {
			return null;
		}
	}

	public List findByKdid(Long kdid) {
		String queryString = "from XyClass as cc Where cc.kdId=? or cc.kdId in( select d.kdId from WkTDept as d where d.kdPid=?) order by cc.clGrade desc,cc.kdId";
		return find(queryString, new Object[] { kdid,kdid});
	}

	public List findByKdid(Long kdid, int year) {
		String queryString = "from XyClass as cc Where cc.kdId=? and cc.clGrade=? order by cc.clName";
		return find(queryString, new Object[] { kdid, year });
	}

	public List findClassByCnameAndDeplist(String cname, String ccname, List deplist) {
		StringBuffer queryString = new StringBuffer("from XyClass as c Where (c.clName=? or c.clSname=?) and " + "c.kdId in(");
		if (deplist != null && deplist.size() > 0) {
			for (int i = 0; i < deplist.size(); i++) {
				Long a = (Long) deplist.get(i);
				queryString.append(a);
				if (i < deplist.size() - 1) {
					queryString.append(",");
				}
			}
			queryString.append(")");
			return find(queryString.toString(), new Object[] { cname, ccname });
		} else {
			return null;
		}
	}

	public List findClassByCid(Long cid) {
		String query = "from XyClass as xc where xc.clId=?";
		return find(query, new Object[] { cid });
	}

	public List findClasByCsn(List sname) {
		StringBuffer queryString = new StringBuffer("from XyClass as ys Where ys.clSname in(");
		if (sname.size() > 0) {
			for (int i = 0; i < sname.size(); i++) {
				String nn = (String) sname.get(i);
				queryString.append("'" + nn + "'");
				if (i < sname.size() - 1)
					queryString.append(",");
			}
			queryString.append(")");
			List studentt = getHibernateTemplate().find(queryString.toString());
			if (studentt != null && studentt.size() > 0) {
				return studentt;
			} else {
				return null;
			}
		} else
			return null;
	}

	public List findClassForFDY(Long kdid, Long kuid) {
		String query = "from XyClass as c where (c.kdId=? or c.kdId in (select d.kdId from WkTDept as d where d.kdPid=?))and c.clId in (select f.id.clId from XyFudao as f where f.id.kuId=?) order by c.clName";
		return find(query, new Object[] { kdid, kdid, kuid });
	}

	public List findClassForCollege(Long kdid, Integer max, Integer min) {
		String queryString = "from XyClass as cla Where cla.kdId in (select d.kdId from WkTDept as d where d.kdPid=?) and (cla.clGrade<=? and cla.clGrade>=?) order by cla.clGrade,cla.clName";
		return find(queryString, new Object[] { kdid, max, min });
	}

	public List findByKdidAndSname(Long kdid, String Cname) {
		StringBuffer queryString = new StringBuffer("from XyClass as xc where xc.clSname=?");
		if (kdid != 0) {
			queryString.append(" and xc.kdId in(select d.kdId from WkTDept as d where d.kdSchid=?) ");
			List clist = getHibernateTemplate().find(queryString.toString(), new Object[] { Cname, kdid });
			if (clist != null && clist.size() > 0) {
				return clist;
			} else {
				return null;
			}
		} else {
			List tlist = getHibernateTemplate().find(queryString.toString(), new Object[] { Cname });
			if (tlist != null && tlist.size() > 0) {
				return tlist;
			} else {
				return null;
			}
		}
	}

	public boolean CheckIfIn(XyClass xc, List clist) {
		for (int i = 0; i < clist.size(); i++) {
			XyClass c = (XyClass) clist.get(i);
			if (xc.getClId().intValue() == c.getClId().intValue()) {
				return true;
			}
		}
		return false;
	}

	public List findgradeByKdid(Long kdid) {
		String queryString = "select distinct xc.clGrade from XyClass as xc where xc.kdId=? or xc.kdId in(select d.kdId from WkTDept as d where d.kdPid=? ) order by xc.clGrade desc";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
	}

	public List findByKdIdAndGrade(Long kdid, Integer grade) {
		String queryString = "from XyClass as xc where (xc.kdId=? or xc.kdId in(select d.kdId from WkTDept as d where d.kdPid=?))";
		if (grade == null) {
			return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
		} else {
			queryString = queryString + "  and xc.clGrade=?";
			return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid, grade });
		}
	}

	public List findgradeByScKdid(Long kdid) {
		String queryString = "select distinct xc.clGrade from XyClass as xc where xc.kdId in(select dept.kdId from WkTDept as dept where dept.kdPid in (select deptl.kdId from WkTDept as deptl where deptl.kdPid=?)) order by xc.clGrade desc";
		return getHibernateTemplate().find(queryString, kdid);
	}

	public List findClass(Long kdid) {
		String queryString = "from XyClass as xclass where xclass.kdId in (select d.kdId from WkTDept as d where d.kdType=" + WkTDept.DANWEI + " and d.kdPid in (select de.kdId from WkTDept as de where de.kdPid=?) )";
		return getHibernateTemplate().find(queryString, kdid);
	}

	public XyClass hyfindclass(Long clid) {
		String queryString = "from XyClass as cl where cl.clId=? ";
		XyClass cl = (XyClass) getHibernateTemplate().find(queryString, clid).get(0);
		if (cl != null)
			return cl;
		else
			return null;
	}

	public XyClass hyfindclassbykdidclname(Long kdid, String clname) {
		String queryString = "from XyClass as cl where cl.kdId in (select d.kdId from WkTDept as d where d.kdPid=?) and cl.clName=? ";
		XyClass cl = (XyClass) getHibernateTemplate().find(queryString, new Object[] { kdid, clname }).get(0);
		if (cl != null)
			return cl;
		else
			return null;
	}

	public XyClass hyfindclassbycid(Long cid) {
		String queryString = "from XyClass as xycla where xycla.clId=? ";
		return (XyClass) getHibernateTemplate().find(queryString, new Object[] { cid }).get(0);
	}

	public List findBykdIdorkdPid(Long kdid) {
		String queryString = "from XyClass as xycla where  xycla.clId in (select stu.clId from Student as stu)  and (xycla.kdId=? or xycla.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid=? or dept.kdPid in (select dept.kdId from WkTDept as dept where dept.kdPid=? ))) order by xycla.clGrade desc,xycla.kdId,xycla.clName";
		return getHibernateTemplate().find(queryString, new Object[] { kdid,kdid,kdid });
	}
	
	public List findByKuidAndSid(Long kuid,Long sid){
		String queryString = "select distinct xc.clGrade from XyClass as xc where xc.clId in(select xf.id.clId from XyFudao as xf where xf.id.kuId=? ) and xc.kdId in( select kdId from WkTDept as d where d.kdSchid=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid,sid});
	}
	public List findClassByKuidAndSid(Long kuid,Long sid){
		String queryString = "from XyClass as xc where xc.clId in(select xf.id.clId from XyFudao as xf where xf.id.kuId=? ) and xc.kdId in( select kdId from WkTDept as d where d.kdSchid=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid,sid});
	}
	public List findClassByKuidAndSidAndGrade(Long kuid,Long sid,Integer grade){
		String queryString = "from XyClass as xc where xc.clId in(select xf.id.clId from XyFudao as xf where xf.id.kuId=? ) and xc.kdId in( select kdId from WkTDept as d where d.kdSchid=?) and xc.clGrade=?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid,sid,grade});
	}
	public List findbyKdidAndGradeList(Long kdid,List gradelist){
		String queryString = "from XyClass as cc Where (cc.kdId=? or cc.kdId in( select d.kdId from WkTDept as d where d.kdPid=? or d.kdSchid=?)) and cc.clGrade in(";
		for(int i=0;i<gradelist.size();i++){
			Integer grade=(Integer)gradelist.get(i);
			queryString=queryString+grade;
			if(i<gradelist.size()-1){
				queryString=queryString+",";
			}
		
		}
		queryString=queryString+") order by cc.clGrade desc,cc.kdId";
		return find(queryString, new Object[] { kdid,kdid,kdid});
	}
}
