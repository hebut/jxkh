package org.iti.jxkh.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_AuditResult;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.JXKH_PointNumber;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_Patent;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectFund;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_Video;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.service.AuditResultService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.iti.common.util.PropertiesLoader;
import com.uniwin.basehs.service.impl.AnnBaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

@Service("auditResultService")
public class AuditResultServiceImpl extends AnnBaseServiceImpl implements
		AuditResultService {

	Logger logger = Logger.getLogger(VoteResultServiceImpl.class);
	Properties property = PropertiesLoader.loader("title", "title.properties");

	@SuppressWarnings("unchecked")
	public List<WkTDept> findWorkDept() {
		String queryString = "from WkTDept where kdClassify = 0 and kdType = 0 and kdState = 0 and kdPid = 1";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_HYLW> findHyLw(String kdName, String year) {
		String queryString = "from JXKH_HYLW as lw inner join fetch lw.lwDep as dept where lw.lwTime like '%"
				+ year + "%' and dept.depId=? and lwState>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, JXKH_HYLW.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_QKLW> findQkLw(String kdName, String year) {
		String queryString = "from JXKH_QKLW as lw inner join fetch lw.lwDep as dept where lw.lwDate like '%"
				+ year + "%' and dept.depId=? and lwState>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, JXKH_QKLW.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<Jxkh_Writing> findZz(String kdName, String year) {
		String queryString = "from Jxkh_Writing as wt inner join fetch wt.writingDept as dept where wt.publishDate like '%"
				+ year + "%' and dept.deptId=? and state>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, Jxkh_Writing.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<Jxkh_Award> findJl(String kdName, String year) {
		String queryString = "from Jxkh_Award as aw inner join fetch aw.awardDept as dept where aw.date like '%"
				+ year + "%' and dept.deptId=? and state>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, Jxkh_Award.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<Jxkh_Video> findSp(String kdName, String year) {
		String queryString = "from Jxkh_Video as vd inner join fetch vd.videoDept as dept where vd.playTime like '%"
				+ year + "%' and dept.deptId=? and state>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, Jxkh_Video.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<Jxkh_Project> findJf(String kdName, String year) {
		String queryString = "from Jxkh_Project as pj inner join fetch pj.projectDept as dept where pj.id in (select distinct project from Jxkh_ProjectFund where date like '"
				+ year + "%' and pj.id=project) and dept.deptId=? and state>=?";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, kdName);
		query.setParameter(1, Jxkh_Project.BUSINESS_PASS);
		List<Jxkh_Project> pjlist = query.list();
		for (Jxkh_Project pj : pjlist) {
			List<Jxkh_ProjectFund> flist = new ArrayList<Jxkh_ProjectFund>();
			for (Jxkh_ProjectFund fund : pj.getProjectFund()) {
				logger.debug(fund.getId());
				flist.add(fund);
				logger.debug(fund.getId());
			}
			pj.setProjectFund(flist);
		}
		return pjlist;
	}

	@SuppressWarnings("unchecked")
	public List<Jxkh_Patent> findZl(String kdName, String year) {
		String queryString = "from Jxkh_Patent as pt inner join fetch pt.patentDept as dept where pt.grantDate like '%"
				+ year + "%' and dept.deptId=? and state>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, Jxkh_Patent.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<Jxkh_Fruit> findCg(String kdName, String year) {
		String queryString = "from Jxkh_Fruit as fr inner join fetch fr.fruitDept as dept where (fr.appraiseDate like '"
				+ year
				+ "%' or fr.acceptDate like '"
				+ year
				+ "%') and dept.deptId=? and state>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, Jxkh_Fruit.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_MEETING> findHy(String kdName, String year) {
		String queryString = "from JXKH_MEETING as mt inner join fetch mt.mtDep as dept where mt.mtTime like '%"
				+ year + "%' and dept.depId=? and mtState>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, JXKH_MEETING.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<Jxkh_Report> findBg(String kdName, String year) {
		String queryString = "from Jxkh_Report as rp inner join fetch rp.reprotDept as dept where rp.date like '%"
				+ year + "%' and dept.deptId=? and state>=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdName, Jxkh_Report.BUSINESS_PASS });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_AuditResult> findDeptByYear(String year) {
		String queryString = "from JXKH_AuditResult where arYear=? and arType=? order by arIndex desc";
		return getHibernateTemplate().find(queryString,
				new Object[] { year, JXKH_AuditResult.AR_DEPT });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_AuditResult> findWorkDeptByYear(String year) {
		String queryString = "from JXKH_AuditResult where arYear=? and arType=? order by arIndex desc";
		return getHibernateTemplate().find(queryString,
				new Object[] { year, JXKH_AuditResult.AR_WORK });
	}

	@SuppressWarnings("unchecked")
	public JXKH_AuditResult findWorkDeptByKdIdAndYear(Long kdId, String year) {
		String queryString = "from JXKH_AuditResult where kdId=? and arYear=? and arType=?";
		List<JXKH_AuditResult> arlist = getHibernateTemplate().find(
				queryString,
				new Object[] { kdId, year, JXKH_AuditResult.AR_DEPT });
		if (arlist.size() != 0)
			return arlist.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_AuditResult> findWorkByLevel(Long kdId, String year,
			Short level) {
		String queryString = "from JXKH_AuditResult where kdId=? and arYear=? and arType=? and arLevel=? order by arScore desc";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdId, year, JXKH_AuditResult.AR_WORK, level });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_AuditResult> findWorkByKdId(Long kdId, String year) {
		String queryString = "from JXKH_AuditResult where kdId=? and arYear=? and arType=? order by arScore desc";
		return getHibernateTemplate().find(queryString,
				new Object[] { kdId, year, JXKH_AuditResult.AR_WORK });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_AuditResult> findManageByYear(String year) {
		String queryString = "from JXKH_AuditResult where arYear=? and arType=? order by arScore desc";
		return getHibernateTemplate().find(queryString,
				new Object[] { year, JXKH_AuditResult.AR_MANAGE });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_AuditResult> findLeaderByYear(Long kuId, String year) {
		String queryString = "from JXKH_AuditResult where kuId=? and arYear=? and arType=? order by arScore desc";
		return getHibernateTemplate().find(queryString,
				new Object[] { kuId, year, JXKH_AuditResult.AR_LEADER });
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_AuditResult> findLeaderByYear(String year) {
		String queryString = "from JXKH_AuditResult where arYear=? and arType=? order by arScore desc";
		return getHibernateTemplate().find(queryString,
				new Object[] { year, JXKH_AuditResult.AR_LEADER });
	}

	public JXKH_AuditResult findPersonal(String year, Long kuId) {
		String queryString = "from JXKH_AuditResult where arYear=? and kuId=?";
		@SuppressWarnings("unchecked")
		List<JXKH_AuditResult> arlist = getHibernateTemplate().find(
				queryString, new Object[] { year, kuId });
		if (arlist.size() == 0) {
			return null;
		} else {
			return arlist.get(0);
		}
	}

	public JXKH_AuditResult findDepartment(String year, Long kdId) {
		String queryString = "from JXKH_AuditResult where arYear=? and kdId=? and arType=?";
		@SuppressWarnings("unchecked")
		List<JXKH_AuditResult> arlist = getHibernateTemplate().find(
				queryString,
				new Object[] { year, kdId, JXKH_AuditResult.AR_DEPT });
		if (arlist.size() == 0) {
			return null;
		} else {
			return arlist.get(0);
		}
	}

	public WkTDept findDepname(Long kdId) {
		String queryString = "from WkTDept where kdId=?";
		@SuppressWarnings("unchecked")
		List<WkTDept> arlist = getHibernateTemplate().find(queryString,
				new Object[] { kdId });
		if (arlist.size() == 0) {
			return null;
		} else {
			return arlist.get(0);
		}
	}

	public WkTUser findUser(Long kuId) {
		String queryString = "from WkTUser where kuId=?";
		@SuppressWarnings("unchecked")
		List<WkTUser> arlist = getHibernateTemplate().find(queryString,
				new Object[] { kuId });
		if (arlist.size() == 0) {
			return null;
		} else {
			return arlist.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<JXKH_AuditResult> findAll(String year, Long kdId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_AuditResult where arYear like '%" + year
				+ "%'");
		if (kdId != null)
			queryString.append(" and kdId ='" + kdId + "'");
		queryString.append(" and arType !=3");
		queryString.append(" order by kuId");
		return getHibernateTemplate().find(queryString.toString(),
				new Object[] {});
	}

	@Override
	public JXKH_MultiDept findMultiDeptByPrIdAndType(Long prId, Short type) {
		String queryString = "from JXKH_MultiDept where prId=? and type=?";
		List<JXKH_MultiDept> list = getHibernateTemplate().find(queryString,
				new Object[] { prId, type });
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<JXKH_HYLW> findMultiDeptHYLW(String year, String kdNumber) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where a.lwTime like '" + year
				+ "%' and a.lwState>=?");
		queryString
				.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.rank=1 and m.depId=?)");
		queryString.append(" order by a.lwTime DESC");
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString.toString());
		query.setParameter(0, JXKH_HYLW.BUSINESS_PASS);
		query.setParameter(1, kdNumber);
		List<JXKH_HYLW> plist = query.list();
		List<JXKH_HYLW> prlist = new ArrayList<JXKH_HYLW>();
		for (JXKH_HYLW p : plist) {
			Hibernate.initialize(p);
			if (p.getLwDep().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<JXKH_QKLW> findMultiDeptQKLW(String year, String kdNumber) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_QKLW as a where a.lwDate like '%" + year
				+ "%' and a.lwState>=" + JXKH_QKLW.BUSINESS_PASS);
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.rank=1 and m.depId='"
							+ kdNumber + "')");
		queryString.append(" order by a.lwState,a.lwDate DESC");
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString.toString());
		List<JXKH_QKLW> plist = query.list();
		List<JXKH_QKLW> prlist = new ArrayList<JXKH_QKLW>();
		for (JXKH_QKLW p : plist) {
			Hibernate.initialize(p);
			if (p.getLwDep().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<Jxkh_Writing> findMultiDeptZZ(String year, String kdNumber) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where a.publishDate like '"
				+ year + "%' and a.state>=" + Jxkh_Writing.BUSINESS_PASS);
		queryString
				.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.deptId='"
						+ kdNumber
						+ "' and d.rank =1) order by a.state,a.publishDate desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Writing> plist = query.list();
		List<Jxkh_Writing> prlist = new ArrayList<Jxkh_Writing>();
		for (Jxkh_Writing p : plist) {
			Hibernate.initialize(p);
			if (p.getWritingDept().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<Jxkh_Award> findMultiDeptJL(String year, String kdNumber) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Award as a where a.date like '"
						+ year
						+ "%' and a.state>="
						+ Jxkh_Award.BUSINESS_PASS
						+ " and a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "' and d.rank=1)");
		queryString.append(" order by a.state,a.date desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Award> plist = query.list();
		List<Jxkh_Award> prlist = new ArrayList<Jxkh_Award>();
		for (Jxkh_Award p : plist) {
			Hibernate.initialize(p);
			if (p.getAwardDept().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<Jxkh_Video> findMultiDeptSP(String year, String kdNumber) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Video as a where a.playTime like '"
						+ year
						+ "%' and  a.state>="
						+ Jxkh_Video.BUSINESS_PASS
						+ " and a.id in (select distinct m.video from Jxkh_VideoDept as m where m.deptId='"
						+ kdNumber + "'and m.rank=1)");
		queryString.append(" order by a.state,a.shootTime desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Video> plist = query.list();
		List<Jxkh_Video> prlist = new ArrayList<Jxkh_Video>();
		for (Jxkh_Video p : plist) {
			Hibernate.initialize(p);
			if (p.getVideoDept().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<Jxkh_Project> findMultiDeptXM(String year, String kdNumber) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.id in (select distinct project from Jxkh_ProjectFund where date like '"
						+ year
						+ "%' and a.id=project) and a.sort in (0,1) and a.state>="
						+ Jxkh_Project.BUSINESS_PASS);
		queryString
				.append(" and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber
						+ "' and d.rank = 1) order by a.state,a.beginDate desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> plist = query.list();
		List<Jxkh_Project> prlist = new ArrayList<Jxkh_Project>();
		for (Jxkh_Project p : plist) {
			Hibernate.initialize(p);
			if (p.getProjectDept().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<Jxkh_Patent> findMultiDeptZL(String year, String kdNumber) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where a.grantDate like '"
				+ year + "%' and a.state>=" + Jxkh_Patent.BUSINESS_PASS);
		queryString
				.append(" and a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.deptId='"
						+ kdNumber
						+ "' and d.rank =1) order by a.state,a.grantDate desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Patent> plist = query.list();
		List<Jxkh_Patent> prlist = new ArrayList<Jxkh_Patent>();
		for (Jxkh_Patent p : plist) {
			Hibernate.initialize(p);
			if (p.getPatentDept().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<Jxkh_Fruit> findMultiDeptCG(String year, String kdNumber) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Fruit as a where (a.appraiseDate like '"
						+ year
						+ "%' or a.acceptDate like '"
						+ year
						+ "%') and a.state>="
						+ Jxkh_Fruit.BUSINESS_PASS
						+ " and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kdNumber + "' and m.rank=1)");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Fruit> plist = query.list();
		List<Jxkh_Fruit> prlist = new ArrayList<Jxkh_Fruit>();
		for (Jxkh_Fruit p : plist) {
			Hibernate.initialize(p);
			if (p.getFruitDept().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<JXKH_MEETING> findMultiDeptHY(String year, String kdNumber) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_MEETING as a where a.mtTime like '"
				+ year + "%' and a.mtState>=" + JXKH_MEETING.BUSINESS_PASS);
		queryString
				.append(" and a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.rank=1 and m.depId= "
						+ "'" + kdNumber + "')");
		queryString.append(" order by a.mtState,a.mtTime DESC");
		Query query = session.createQuery(queryString.toString());
		List<JXKH_MEETING> plist = query.list();
		List<JXKH_MEETING> prlist = new ArrayList<JXKH_MEETING>();
		for (JXKH_MEETING p : plist) {
			Hibernate.initialize(p);
			if (p.getMtDep().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@Override
	public List<Jxkh_Report> findMultiDeptBG(String year, String kdNumber) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Report as a where a.date like '"
						+ year
						+ "%' and a.state>="
						+ Jxkh_Report.BUSINESS_PASS
						+ " and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kdNumber + "'and m.rank=1)");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Report> plist = query.list();
		List<Jxkh_Report> prlist = new ArrayList<Jxkh_Report>();
		for (Jxkh_Report p : plist) {
			Hibernate.initialize(p);
			if (p.getReprotDept().size() > 1) {
				prlist.add(p);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return prlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JXKH_AuditResult> findDept(String year) {
		String queryString = "from JXKH_AuditResult where arYear = ? and arType = ? order by arIndex desc";
		return getHibernateTemplate().find(queryString,
				new Object[] { year, JXKH_AuditResult.AR_DEPT });
	}

	@Override
	public JXKH_MultiDept findMultiDept(String kdNumber, Long prId, Short type) {
		String queryString = "from JXKH_MultiDept where prId=? and type=? and depts like '%"
				+ kdNumber + "%'";
		@SuppressWarnings("unchecked")
		List<JXKH_MultiDept> list = getHibernateTemplate().find(queryString,
				new Object[] { prId, type });
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public JXKH_PointNumber findPointNumber(String year, Long kdId) {
		String queryString = "from JXKH_PointNumber where kdId=? and pnYear=?";
		List<JXKH_PointNumber> arlist = getHibernateTemplate().find(
				queryString, new Object[] { kdId, year });
		if (arlist.size() != 0)
			return arlist.get(0);
		else
			return null;
	}

	@Override
	public List<?> findListForShow(String year, String kdName, String kbName,
			Integer type) {
		System.out.println(year + "-" + kdName + "-" + kbName + "-" + type);
		List<?> reslist = new ArrayList();
		String queryString = "";
		switch (type) {
		case 0:
			if (kbName.equals(property.getProperty("core"))) {
				queryString = "from JXKH_HYLW as lw inner join fetch lw.lwDep as dept where (lw.lwType.kbName=? or lw.lwType.kbName=?) and lw.lwTime like '%"
						+ year + "%' and dept.depId=? and lwState>=?";
				reslist.addAll(getHibernateTemplate().find(
						queryString,
						new Object[] { kbName,
								property.getProperty("international"), kdName,
								Jxkh_Report.BUSINESS_PASS }));
				queryString = "from JXKH_QKLW as lw inner join fetch lw.lwDep as dept where lw.qkGrade.kbName=? and lw.lwTime like '%"
						+ year + "%' and dept.depId=? and lwState>=?";
				reslist.addAll(getHibernateTemplate().find(
						queryString,
						new Object[] { kbName, kdName,
								Jxkh_Report.BUSINESS_PASS }));
			} else if (kbName.equals(property.getProperty("sci"))
					|| kbName.equals(property.getProperty("ei"))
					|| kbName.equals(property.getProperty("istp"))) {
				queryString = "from JXKH_HYLW as lw inner join fetch lw.lwDep as dept where lw.lwType.kbName=? and lw.lwTime like '%"
						+ year + "%' and dept.depId=? and lwState>=?";
				reslist.addAll(getHibernateTemplate().find(
						queryString,
						new Object[] { kbName, kdName,
								Jxkh_Report.BUSINESS_PASS }));
				queryString = "from JXKH_QKLW as lw inner join fetch lw.lwDep as dept where lw.lwType.kbName=? and lw.lwTime like '%"
						+ year + "%' and dept.depId=? and lwState>=?";
				reslist.addAll(getHibernateTemplate().find(
						queryString,
						new Object[] { kbName, kdName,
								Jxkh_Report.BUSINESS_PASS }));
			} else {
				queryString = "from JXKH_QKLW as lw inner join fetch lw.lwDep as dept where lw.lwType.kbName=? and lw.lwTime like '%"
						+ year + "%' and dept.depId=? and lwState>=?";
				reslist = getHibernateTemplate().find(
						queryString,
						new Object[] { kbName, kdName,
								Jxkh_Report.BUSINESS_PASS });
			}
			break;
		case 1:
			queryString = "from Jxkh_Writing as wt inner join fetch wt.writingDept as dept where wt.sort.kbName=? and wt.publishDate like '%"
					+ year + "%' and dept.deptId=? and state>=?";
			reslist = getHibernateTemplate().find(queryString,
					new Object[] { kbName, kdName, Jxkh_Report.BUSINESS_PASS });
			break;
		case 2:
			queryString = "from Jxkh_Award as aw inner join fetch aw.awardDept as dept where aw.rank.kbName=? and aw.date like '%"
					+ year + "%' and dept.deptId=? and state>=?";
			reslist = getHibernateTemplate().find(queryString,
					new Object[] { kbName, kdName, Jxkh_Report.BUSINESS_PASS });
			break;
		case 3:
			queryString = "from Jxkh_Video as vd inner join fetch vd.videoDept as dept where and vd.rank.kbName=? and vd.playTime like '%"
					+ year + "%' and dept.deptId=? and state>=?";
			reslist = getHibernateTemplate().find(queryString,
					new Object[] { kbName, kdName, Jxkh_Report.BUSINESS_PASS });
			break;
		case 4:
			queryString = "from Jxkh_Project as pj inner join fetch pj.projectDept as dept where pj.rank.kbName=? and pj.standYear like '%"
					+ year + "%' and dept.deptId=? and state>=?";
			reslist = getHibernateTemplate().find(queryString,
					new Object[] { kbName, kdName, Jxkh_Report.BUSINESS_PASS });
			break;
		case 5:
			queryString = "from Jxkh_Patent as pt inner join fetch pt.patentDept as dept where pt.sort.kbName=? and pt.grantDate like '%"
					+ year + "%' and dept.deptId=? and state>=?";
			reslist = getHibernateTemplate().find(queryString,
					new Object[] { kbName, kdName, Jxkh_Report.BUSINESS_PASS });
			break;
		case 6:
			queryString = "from Jxkh_Fruit as fr inner join fetch fr.fruitDept as dept where fr.appraiseRank.kbName=? and fr.acceptDate like '%"
					+ year + "%' and dept.deptId=? and state>=?";
			reslist = getHibernateTemplate().find(queryString,
					new Object[] { kbName, kdName, Jxkh_Report.BUSINESS_PASS });
			break;
		case 7:
			queryString = "from JXKH_MEETING as mt inner join fetch mt.mtDep as dept where mt.mtDegree.kbName=? and mt.mtTime like '%"
					+ year + "%' and dept.depId=? and mtState>=?";
			reslist = getHibernateTemplate().find(queryString,
					new Object[] { kbName, kdName, Jxkh_Report.BUSINESS_PASS });
			break;
		case 8:
			queryString = "from Jxkh_Report as rp inner join fetch rp.reprotDept as dept where rp.leader.kbName=? and rp.date like '%"
					+ year + "%' and dept.deptId=? and state>=?";
			reslist = getHibernateTemplate().find(queryString,
					new Object[] { kbName, kdName, Jxkh_Report.BUSINESS_PASS });
			break;
		}
		return reslist;
	}

	@Override
	public Float countDeptPro(String kdNum, String year) {
		String sql = "select sum(dep.score) from Jxkh_ProjectDept dep, Jxkh_Project pj where pj.id = dep.PROJECT and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6)";
		Float a = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdNum, year }, java.lang.Float.class);
		if (a == null)
			a = 0F;
		return a;
	}

	@Override
	public int countDeptProNum(String kdNum, String year, String type) {
		String sql = "select count(pj.id) from Jxkh_Project pj, Jxkh_ProjectDept dep, Jxkh_BusinessIndicator bi where pj.id = dep.PROJECT and bi.kbId = pj.kbId1 and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%'";
		return this.jdbcTemplate.queryForInt(sql, new Object[] { kdNum, year });
	}

	@Override
	public Float countDeptProType(String kdNum, String year, String type) {
		String sql = "select sum(dep.score) from Jxkh_Project pj, Jxkh_ProjectDept dep, Jxkh_BusinessIndicator bi where pj.id = dep.PROJECT and bi.kbId = pj.kbId1 and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%'";
		Float a = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdNum, year }, java.lang.Float.class);
		if (a == null)
			a = 0F;
		return a;
	}

	@Override
	public List<String> countDeptProType1(String kdNum, String year, String type) {
		String sql = "select pj.id,sum(dep.score) as score from Jxkh_Project pj, Jxkh_ProjectDept dep, Jxkh_BusinessIndicator bi where pj.id = dep.PROJECT and bi.kbId = pj.kbId1 and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%' group by pj.id";
		@SuppressWarnings("unchecked")
		List<String> list = this.jdbcTemplate.query(sql, new Object[] { kdNum,
				year }, new RowMapper() {

			@Override
			public String mapRow(final ResultSet arg0, final int arg1)
					throws SQLException {
				final Long id = arg0.getLong("id");
				Float score = arg0.getFloat("score");
				if (score == null) {
					score = 0f;
				}
				String o = new String();
				o = id + "-" + score;
				return o;
			}
		});
		return list;

	}

	@Override
	public Float countDeptWri(String kdNum, String year) {
		String sql = "select sum(dep.score) from Jxkh_WritingDept dep, Jxkh_Writing pj where pj.id = dep.WRITING and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6)";
		Float a = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdNum, year }, java.lang.Float.class);
		if (a == null)
			a = 0F;
		return a;
	}
	
	@Override
	public int countDeptWriNum(String kdNum, String year, String type) {
		String sql = "select count(pj.id) from Jxkh_Writing pj, Jxkh_WritingDept dep, Jxkh_BusinessIndicator bi where pj.id = dep.WRITING and bi.kbId = pj.kbId3 and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%'";
		return this.jdbcTemplate.queryForInt(sql, new Object[] { kdNum, year });
	}

	@Override
	public Float countDeptWriType(String kdNum, String year, String type) {
		String sql = "select sum(dep.score) from Jxkh_Writing pj, Jxkh_WritingDept dep, Jxkh_BusinessIndicator bi where pj.id = dep.WRITING and bi.kbId = pj.kbId3 and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%'";
		Float a = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdNum, year }, java.lang.Float.class);
		if (a == null)
			a = 0F;
		return a;
	}
	@Override
	public List<String> countDeptWriType1(String kdNum, String year, String type) {
		String sql = "select pj.id, sum(dep.score) as score from Jxkh_WritingDept dep, Jxkh_Writing pj, Jxkh_BusinessIndicator bi where bi.kbid=pj.kbId3 and pj.id = dep.WRITING and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%' group by pj.id";
		List  list = this.jdbcTemplate.query(sql, new Object[] { kdNum,
				year }, new RowMapper() {

			@Override
			public String mapRow(final ResultSet arg0, final int arg1)
					throws SQLException {
				final Long id = arg0.getLong("id");
				Float score = arg0.getFloat("score");
				if (score == null) {
					score = 0f;
				}
				String o = new String();
				o = id + "-" + score;
				return o;
			}
		});
		return list;
	}
	@Override
	public Float countDeptPat(String kdNum, String year) {
		String sql = "select sum(dep.score) from Jxkh_PatentDept dep, Jxkh_Patent pj where pj.id = dep.PATENT and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6)";
		Float a = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdNum, year }, java.lang.Float.class);
		if (a == null)
			a = 0F;
		return a;
	}

	@Override
	public int countDeptPatNum(String kdNum, String year, String type) {
		String sql = "select count(pj.id) from Jxkh_Patent pj, Jxkh_PatentDept dep, Jxkh_BusinessIndicator bi where pj.id = dep.PATENT and bi.kbId = pj.kbId2 and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%'";
		return this.jdbcTemplate.queryForInt(sql, new Object[] { kdNum, year });
	}

	@Override
	public Float countDeptPatType(String kdNum, String year, String type) {
		String sql = "select sum(dep.score) from Jxkh_Patent pj, Jxkh_PatentDept dep, Jxkh_BusinessIndicator bi where pj.id = dep.PATENT and bi.kbId = pj.kbId2 and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%'";
		Float a = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdNum, year }, java.lang.Float.class);
		if (a == null)
			a = 0F;
		return a;
	}
	@Override
	public List<String> countDeptPatType1(String kdNum, String year, String type) {
		String sql = "select pj.id, sum(dep.score) as score from Jxkh_Patent pj, Jxkh_PatentDept dep, Jxkh_BusinessIndicator bi where pj.id = dep.PATENT and bi.kbId = pj.kbId2 and dep.deptId = ? and pj.jxYear = ? and pj.state in (4,6) and bi.kbName like '%"
				+ type + "%' group by pj.id";
		List  list = this.jdbcTemplate.query(sql, new Object[] { kdNum,
				year }, new RowMapper() {

			@Override
			public String mapRow(final ResultSet arg0, final int arg1)
					throws SQLException {
				final Long id = arg0.getLong("id");
				Float score = arg0.getFloat("score");
				if (score == null) {
					score = 0f;
				}
				String o = new String();
				o = id + "-" + score;
				return o;
			}
		});
		return list;
	}
	@Override
	public Float countDeptMee(String kdNum, String year) {
		String sql = "select sum(dep.score) from JXKH_MeetingDept dep, JXKH_MEETING pj where pj.mtId = dep.mtId and dep.depId = ? and pj.jxYear = ? and pj.mtState in (4,6)";
		Float a = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdNum, year }, java.lang.Float.class);
		if (a == null)
			a = 0F;
		return a;
	}

	@Override
	public int countDeptMeeNum(String kdNum, String year, String type) {
		String sql = "select count(pj.mtId) from JXKH_MEETING pj, JXKH_MeetingDept dep, Jxkh_BusinessIndicator bi where pj.mtId = dep.mtId and bi.kbId = pj.mtDegree and dep.depId = ? and pj.jxYear = ? and pj.mtState in (4,6) and bi.kbName like '%"
				+ type + "%'";
		return this.jdbcTemplate.queryForInt(sql, new Object[] { kdNum, year });
	}

	@Override
	public Float countDeptMeeType(String kdNum, String year, String type) {
		String sql = "select sum(dep.score) from JXKH_MEETING pj, JXKH_MeetingDept dep, Jxkh_BusinessIndicator bi where pj.mtId = dep.mtId and bi.kbId = pj.mtDegree and dep.depId = ? and pj.jxYear = ? and pj.mtState in (4,6) and bi.kbName like '%"
				+ type + "%'";
		Float a = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdNum, year }, java.lang.Float.class);
		if (a == null)
			a = 0F;
		return a;
	}

	@Override
	public List<String> countDeptMeeType1(String kdNum, String year, String type) {
		String sql = "select pj.mtid,sum(dep.score) as score from JXKH_MEETING pj, JXKH_MeetingDept dep, Jxkh_BusinessIndicator bi where pj.mtId = dep.mtId and bi.kbId = pj.mtDegree and dep.depId = ? and pj.jxYear = ? and pj.mtState in (4,6) and bi.kbName like '%"
				+ type + "%' group by pj.mtid";
		List  list = this.jdbcTemplate.query(sql, new Object[] { kdNum,
				year }, new RowMapper() {
			@Override
			public String mapRow(final ResultSet arg0, final int arg1)
					throws SQLException {
				final Long id = arg0.getLong("mtid");
				Float score = arg0.getFloat("score");
				if (score == null) {
					score = 0f;
				}
				String o = new String();
				o = id + "-" + score;
				return o;
			}
		});
		return list;
	}
	
	@Override
	public float findSumScoreByKdNumberYearAndState(String kdnumber,
			String year, Short type) {
		String sql = "";
		float score = 0f;
		switch (type) {
		case 0:
			sql = "select SUM(dept.score)  from Jxkh_HYLWDept dept ,Jxkh_HYLW a  where a.lwId=dept.lwId and dept.depId=? and a. jxYear=? and a.lwState in (4,6)";
			break;
		case 1:
			sql = "select SUM(dept.score)  from Jxkh_QKLWDept dept ,Jxkh_QKLW a  where a.lwId=dept.lwId and dept.depId=? and a. jxYear=? and a.lwState in (4,6)";
			break;
		case 3:
			sql = "select SUM(dept.score)  from Jxkh_AwardDept dept ,Jxkh_Award a  where a.id=dept.AWARD and dept.deptId=? and a. jxYear=? and a.state in (4,6)";
			break;
		case 4:
			sql = "select SUM(dept.score)  from Jxkh_VideoDept dept ,Jxkh_Video a  where a.id=dept.Video and dept.deptId=? and a.jxYear=? and a.state in (4,6)";
			break;
		case 7:
			sql = "select SUM(dept.score)  from Jxkh_FruitDept dept ,Jxkh_Fruit a  where a.id=dept.Fruit and dept.deptId=? and a.jxYear=? and a.state in (4,6)";
			break;
		case 9:
			sql = "select SUM(dept.score)  from Jxkh_ReportDept dept ,Jxkh_Report a  where a.id=dept.Report and dept.deptId=? and a.jxYear=? and a.state in (4,6)";
			break;
		}
		score = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				kdnumber, year }, java.lang.Float.class) == null ? 0
				: (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
						kdnumber, year }, java.lang.Float.class);
		return score;
	}

	@Override
	public Object[] findBiSumScore(String kdnumber, String year, Short type,
			Long id) {
		String sql = "";
		switch (type) {
		case 0:
			sql = "select count(*) as num,SUM(dept.score) as score  from Jxkh_HYLWDept dept ,Jxkh_HYLW a  where a.lwId=dept.lwId and dept.depId=? and a. jxYear=? and computeType=?  and a.lwState in (4,6)";
			break;
		case 1:
			sql = "select count(*) as num,SUM(dept.score) as score  from Jxkh_QKLWDept dept ,Jxkh_QKLW a  where a.lwId=dept.lwId and dept.depId=? and a. jxYear=? and computeType=? and a.lwState in (4,6)";
			break;
		case 3:
			sql = "select count(*) as num,SUM(dept.score) as score from Jxkh_AwardDept dept ,Jxkh_Award a  where a.id=dept.AWARD and dept.deptId=? and a. jxYear=? and rank_id=? and a.state in (4,6)";
			break;
		case 4:
			sql = "select count(*) as num,SUM(dept.score) as score  from Jxkh_VideoDept dept ,Jxkh_Video a  where a.id=dept.Video and dept.deptId=? and a.jxYear=? and computeType=?  and a.state in (4,6)";
			break;
		case 7:
			sql = "select count(*) as num,SUM(dept.score) as score  from Jxkh_FruitDept dept ,Jxkh_Fruit a  where a.id=dept.Fruit and dept.deptId=? and a.jxYear=? and computeType=? and a.state in (4,6)";
			break;
		case 9:
			sql = "select count(*) as num,SUM(dept.score) as score  from Jxkh_ReportDept dept ,Jxkh_Report a  where a.id=dept.Report and dept.deptId=? and a.jxYear=? and leader=? and  a.state in (4,6)";
			break;
		}
		List list = this.jdbcTemplate.query(sql, new Object[] { kdnumber, year,
				id }, new RowMapper() {

			@Override
			public Object[] mapRow(ResultSet arg0, int arg1)
					throws SQLException {
				int num = arg0.getInt("num");
				Float score = arg0.getFloat("score");
				if (score == null) {
					score = 0f;
				}
				Object[] o = new Object[2];
				o[0] = num;
				o[1] = score;
				return o;
			}
		});
		return (Object[]) list.get(0);
	}

	@Override
	public List<String> countDeptDetail(String kdNum, String year,
			String biName, Short type) {
		String sql = "";
		switch (type) {
		case 0:
			sql = "select a.lwid as id,dept.score  from Jxkh_HYLWDept dept ,Jxkh_HYLW a,Jxkh_BusinessIndicator bi  where a.lwId=dept.lwId and bi.kbId=a.computeType and dept.depId=? and a. jxYear=? and bi.kbName=?  and a.lwState in (4,6)";
			List hylist = this.jdbcTemplate.query(sql, new Object[] { kdNum, year,
					biName }, new RowMapper() {
				@Override
				public String mapRow(ResultSet arg0, int arg1)
						throws SQLException {
					final Long id = arg0.getLong("id");
					Float score = arg0.getFloat("score");
					if (score == null) {
						score = 0f;
					}
					String o = new String();
					o = id + "-" + score+"-hy";
					return o;
				}
			});
			return hylist;
		case 1:
			sql = "select a.lwid as id,dept.score   from Jxkh_QKLWDept dept ,Jxkh_QKLW a,Jxkh_BusinessIndicator bi  where a.lwId=dept.lwId and bi.kbId=a.computeType and dept.depId=? and a. jxYear=? and bi.kbName=? and a.lwState in (4,6)";
			List qklist = this.jdbcTemplate.query(sql, new Object[] { kdNum, year,
					biName }, new RowMapper() {

				@Override
				public String mapRow(ResultSet arg0, int arg1)
						throws SQLException {
					final Long id = arg0.getLong("id");
					Float score = arg0.getFloat("score");
					if (score == null) {
						score = 0f;
					}
					String o = new String();
					o = id + "-" + score+"-qk";
					return o;
				}
			});
			return qklist;
		case 3:
			sql = "select a.id,dept.score  from Jxkh_AwardDept dept ,Jxkh_Award a,Jxkh_BusinessIndicator bi  where bi.kbId=a.rank_id and a.id=dept.AWARD and dept.deptId=? and a. jxYear=? and bi.kbName=? and a.state in (4,6)";
			break;
		case 4:
			sql = "select a.id,dept.score   from Jxkh_VideoDept dept ,Jxkh_Video a,Jxkh_BusinessIndicator bi  where bi.kbid=a.computeType and a.id=dept.Video and dept.deptId=? and a.jxYear=? and bi.kbName=?  and a.state in (4,6)";
			break;
		case 7:
			sql = "select a.id,dept.score   from Jxkh_FruitDept dept ,Jxkh_Fruit a,Jxkh_BusinessIndicator bi  where bi.kbid=a.computeType and a.id=dept.Fruit and dept.deptId=? and a.jxYear=? and bi.kbName=? and a.state in (4,6)";
			break;
		case 9:
			sql = "select a.id,dept.score   from Jxkh_ReportDept dept ,Jxkh_Report a,Jxkh_BusinessIndicator bi  where bi.kbid=a.leader and a.id=dept.Report and dept.deptId=? and a.jxYear=? and bi.kbName=? and  a.state in (4,6)";
			break;
		}
		List list = this.jdbcTemplate.query(sql, new Object[] { kdNum, year,
				biName }, new RowMapper() {

			@Override
			public String mapRow(ResultSet arg0, int arg1)
					throws SQLException {
				final Long id = arg0.getLong("id");
				Float score = arg0.getFloat("score");
				if (score == null) {
					score = 0f;
				}
				String o = new String();
				o = id + "-" + score;
				return o;
			}
		});
		return list;
	}

	@Override
	public List<WkTDept> findManageDept() {
		String queryString = "from WkTDept where kdClassify = 1 and kdType = 0 and kdState = 0";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<WkTUser> findUserByDept(String deptString) {
		String queryString = "from  WkTUser where kdId in (" + deptString +") and kuType = " + WkTUser.JOB_IN;
		return getHibernateTemplate().find(queryString);
	}

}