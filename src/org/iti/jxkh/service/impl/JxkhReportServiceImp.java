package org.iti.jxkh.service.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.Jxkh_Report;
import org.iti.jxkh.entity.Jxkh_ReportDept;
import org.iti.jxkh.entity.Jxkh_ReportFile;
import org.iti.jxkh.entity.Jxkh_ReportMember;
import org.iti.jxkh.service.JxkhReportService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JxkhReportServiceImp extends BaseServiceImpl implements
		JxkhReportService {

	@Override
	public List<Jxkh_ReportMember> findReportMemberByReportId(Jxkh_Report report) {
		String queryString = "from Jxkh_ReportMember as rm where rm.report=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, report);
		List<Jxkh_ReportMember> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_ReportDept> findReportDeptByReportId(Jxkh_Report report) {
		String queryString = "from Jxkh_ReportDept as rm where rm.report=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, report);
		List<Jxkh_ReportDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_ReportDept> findReportDepByReportId(Jxkh_Report report) {
		String queryString = "from Jxkh_ReportDept as rm where rm.deptId!='000' and rm.report=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, report);
		List<Jxkh_ReportDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}
	
	@Override
	public List<Jxkh_Report> findReportByKuLid(String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where 1=1");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append("and a.submitId='" + kuLid + "'");
		queryString
				.append("and a.id in (select distinct m.report from Jxkh_ReportMember as m where m.personId='"
						+ kuLid + "')");
		queryString.append(" order by a.jxYear desc, a.state asc,a.date desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Report> list = query.list();
		for (Jxkh_Report report : list) {
			List<Jxkh_ReportMember> reportMembers = report.getReportMember();
			List<Jxkh_ReportDept> reportDepts = report.getReprotDept();
			Set<Jxkh_ReportFile> reportFile = report.getReportFile();
			report.setReportMember(reportMembers);
			report.setReprotDept(reportDepts);
			report.setReportFile(reportFile);
			logger.debug(reportMembers.isEmpty());
			logger.debug(reportDepts.isEmpty());
			logger.debug(reportFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findReportByKbNumber(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Report as a where a.state in (0,1,2,3) and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kbNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findReportByState() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where a.state in (1,4,5,6) ");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findReportByCondition(String nameSearch, Short stateSearch,
			String type, String depName) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.type = '" + type + "'");
		if (depName != null && !depName.equals(""))
			queryString
					.append(" and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.name='"
							+ depName + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findReportByCondition(String nameSearch, Short stateSearch,
			String type, String year, String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.type = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kbNumber + "')");
		return countQuery(queryString.toString());
	}

	@Override
	public int findReportByKbNumberAll(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Report as a where a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kbNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Report> findReportByKbNumber(String kbNumber, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Report as a where a.state in (0,1,2,3) and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kbNumber + "')");
		queryString.append(" order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Report> list = query.list();
		for (Jxkh_Report report : list) {
			List<Jxkh_ReportMember> reportMembers = report.getReportMember();
			List<Jxkh_ReportDept> reportDepts = report.getReprotDept();
			Set<Jxkh_ReportFile> reportFile = report.getReportFile();
			report.setReportMember(reportMembers);
			report.setReprotDept(reportDepts);
			report.setReportFile(reportFile);
			logger.debug(reportMembers.isEmpty());
			logger.debug(reportDepts.isEmpty());
			logger.debug(reportFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Report> findReportByKbNumberAll(String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Report as a where a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kbNumber + "')");
		queryString.append(" order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Report> list = query.list();
		for (Jxkh_Report report : list) {
			List<Jxkh_ReportMember> reportMembers = report.getReportMember();
			List<Jxkh_ReportDept> reportDepts = report.getReprotDept();
			Set<Jxkh_ReportFile> reportFile = report.getReportFile();
			report.setReportMember(reportMembers);
			report.setReprotDept(reportDepts);
			report.setReportFile(reportFile);
			logger.debug(reportMembers.isEmpty());
			logger.debug(reportDepts.isEmpty());
			logger.debug(reportFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Report> findReportByState(int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where a.state in (1,4,5,6,7) ");
		queryString.append(" order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Report> list = query.list();
		for (Jxkh_Report report : list) {
			List<Jxkh_ReportMember> reportMembers = report.getReportMember();
			List<Jxkh_ReportDept> reportDepts = report.getReprotDept();
			Set<Jxkh_ReportFile> reportFile = report.getReportFile();
			report.setReportMember(reportMembers);
			report.setReprotDept(reportDepts);
			report.setReportFile(reportFile);
			logger.debug(reportMembers.isEmpty());
			logger.debug(reportDepts.isEmpty());
			logger.debug(reportFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Report> findReportByCondition(String nameSearch,
			Short stateSearch, String type, String depName, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.type = '" + type + "'");
		if (depName != null && !depName.equals(""))
			queryString
					.append(" and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.name='"
							+ depName + "')");
		queryString.append("order by a.jxYear desc, a.state,a.submitTime");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Report> list = query.list();
		for (Jxkh_Report report : list) {
			List<Jxkh_ReportMember> reportMembers = report.getReportMember();
			List<Jxkh_ReportDept> reportDepts = report.getReprotDept();
			Set<Jxkh_ReportFile> reportFile = report.getReportFile();
			report.setReportMember(reportMembers);
			report.setReprotDept(reportDepts);
			report.setReportFile(reportFile);
			logger.debug(reportMembers.isEmpty());
			logger.debug(reportDepts.isEmpty());
			logger.debug(reportFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Report> findReportByCondition(String nameSearch,
			Short stateSearch, String type, String year, String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.type = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kbNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Report> list = query.list();
		for (Jxkh_Report report : list) {
			List<Jxkh_ReportMember> reportMembers = report.getReportMember();
			List<Jxkh_ReportDept> reportDepts = report.getReprotDept();
			Set<Jxkh_ReportFile> reportFile = report.getReportFile();
			report.setReportMember(reportMembers);
			report.setReprotDept(reportDepts);
			report.setReportFile(reportFile);
			logger.debug(reportMembers.isEmpty());
			logger.debug(reportDepts.isEmpty());
			logger.debug(reportFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findReportByKbNumber2(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Report as a where a.state in (0,1,2,3) and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kbNumber + "')");// and m.rank=1
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Report> findReportByKbNumber2(String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Report as a where a.state in (0,1,2,3) and a.id in (select distinct m.report from Jxkh_ReportDept as m where m.deptId='"
						+ kbNumber + "')");// and m.rank=1
		queryString.append(" order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Report> list = query.list();
		for (Jxkh_Report report : list) {
			List<Jxkh_ReportMember> reportMembers = report.getReportMember();
			List<Jxkh_ReportDept> reportDepts = report.getReprotDept();
			Set<Jxkh_ReportFile> reportFile = report.getReportFile();
			report.setReportMember(reportMembers);
			report.setReprotDept(reportDepts);
			report.setReportFile(reportFile);
			logger.debug(reportMembers.isEmpty());
			logger.debug(reportDepts.isEmpty());
			logger.debug(reportFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findReportByConditions(String nameSearch, Short stateSearch,
			String year, String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where 1=1");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append("and a.submitId='" + kuLid + "'");
		queryString
				.append("and a.id in (select distinct m.report from Jxkh_ReportMember as m where m.personId='"
						+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Report> findReportListByCondition(String nameSearch,
			Integer stateSearch, String year, String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Report as a where 1=1");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append("and a.submitId='" + kuLid + "'");
		queryString
				.append("and a.id in (select distinct m.report from Jxkh_ReportMember as m where m.personId='"
						+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString.append(" order by a.jxYear desc, a.state asc,a.date desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Report> list = query.list();
		for (Jxkh_Report report : list) {
			List<Jxkh_ReportMember> reportMembers = report.getReportMember();
			List<Jxkh_ReportDept> reportDepts = report.getReprotDept();
			Set<Jxkh_ReportFile> reportFile = report.getReportFile();
			report.setReportMember(reportMembers);
			report.setReprotDept(reportDepts);
			report.setReportFile(reportFile);
			logger.debug(reportMembers.isEmpty());
			logger.debug(reportDepts.isEmpty());
			logger.debug(reportFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public Jxkh_ReportDept findReportDept(Jxkh_Report m, String wktDeptId) {
		String queryString = "from Jxkh_ReportDept where report=? and deptId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		@SuppressWarnings("unchecked")
		List<Jxkh_ReportDept> resultList = query.list();
		Jxkh_ReportDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

}
