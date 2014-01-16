package org.iti.jxkh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_PatentDept;
import org.iti.jxkh.entity.Jxkh_PatentFile;
import org.iti.jxkh.entity.Jxkh_PatentInventor;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.entity.Jxkh_ProjectDept;
import org.iti.jxkh.entity.Jxkh_ProjectFile;
import org.iti.jxkh.entity.Jxkh_ProjectFund;
import org.iti.jxkh.entity.Jxkh_ProjectMember;
import org.iti.jxkh.entity.Jxkh_Writer;
import org.iti.jxkh.entity.Jxkh_Writing;
import org.iti.jxkh.entity.Jxkh_WritingDept;
import org.iti.jxkh.service.JxkhProjectService;
import org.iti.jxkh.entity.Jxkh_Patent;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

@SuppressWarnings("unchecked")
public class JxkhProjectServiceImpl extends BaseServiceImpl implements
		JxkhProjectService {
	@Override
	public List<Jxkh_Project> findAllZP() {
		String queryString = "from Jxkh_Project as zp where zp.sort = 0";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Project> findAllHP() {
		String queryString = "from Jxkh_Project as zp where zp.sort = 1";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Project> findAllCP() {
		String queryString = "from Jxkh_Project as zp where zp.sort = 2";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Patent> findAllPatent() {
		String queryString = "from Jxkh_Patent";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Writing> findAllWriting() {
		String queryString = "from Jxkh_Writing";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Project> findZPBymemberId(String memberId) {
		String queryString = "from Jxkh_Project as zp where zp.sort = 0 "
				+ " and zp.id in (select distinct mt.project from Jxkh_ProjectMember as mt where mt.personId='"
				+ memberId + "') order by zp.jxYear desc,zp.state asc,zp.beginDate desc";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Project> findHPBymemberId(String memberId) {
		String queryString = "from Jxkh_Project as zp where zp.sort = 1 "
				+ " and zp.id in (select distinct mt.project from Jxkh_ProjectMember as mt where mt.personId='"
				+ memberId + "') order by zp.jxYear desc,zp.state asc,zp.beginDate desc";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Project> findCPBymemberId(String memberId) {
		String queryString = "from Jxkh_Project as zp where zp.sort = 2 "
				+ " and zp.id in (select distinct mt.project from Jxkh_ProjectMember as mt where mt.personId='"
				+ memberId + "') order by zp.jxYear desc, zp.state asc,zp.beginDate desc";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Patent> findPatentBymemberId(String memberId) {
		String queryString = "from Jxkh_Patent as zp where"
				+ " zp.id in (select distinct mt.patent from Jxkh_PatentInventor as mt where mt.personId='"
				+ memberId + "') order by zp.jxYear desc, zp.state asc,zp.grantDate desc ";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_Writing> findWritingBymemberId(String memberId) {
		String queryString = "from Jxkh_Writing as zp where "
				+ "zp.id in (select distinct mt.writing from Jxkh_Writer as mt where mt.personId='"
				+ memberId + "') order by zp.jxYear desc, zp.state asc,zp.publishDate desc ";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_BusinessIndicator> findRank() {
		String queryString = "from Jxkh_BusinessIndicator as zp where zp.kbPid=44 order by zp.kbOrdno";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_BusinessIndicator> findSort() {
		String queryString = "from Jxkh_BusinessIndicator as zp where zp.kbPid=46 order by zp.kbOrdno";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_BusinessIndicator> findWSort() {
		String queryString = "from Jxkh_BusinessIndicator as zp where zp.kbPid=31 order by zp.kbOrdno";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public List<Jxkh_ProjectFund> findFunds(Jxkh_Project project, Short sort) {
		String queryString = "from Jxkh_ProjectFund as pf where pf.project=?"
				;
		if(sort != null) {
			queryString += " and pf.sort="+ sort.shortValue() + "";
		}
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<?> sumOut(Jxkh_Project project, String year) {
		String queryString = "select sum(money) from Jxkh_ProjectFund as pf where pf.sort= 0 and pf.type = 3 and pf.jxYear like '%"
				+ year + "%' and pf.project = ?";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<?> sumIn(Jxkh_Project project, String year) {
		String queryString = "select sum(money) from Jxkh_ProjectFund as pf where pf.sort= 0 and pf.type = 0 and pf.jxYear like '%"
				+ year + "%' and pf.project = ?";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public WkTUser findWktUserByMemberUserId(String kuLid) {
		String queryString = "from WkTUser as u where u.kuLid=?";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuLid);
		List<WkTUser> resultList = query.list();
		WkTUser result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
			WkTDept dept = result.getDept();
			logger.debug(dept.getKdName());
			result.setDept(dept);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public WkTDept findWkTDeptByDept(String deptId) {
		String queryString = "from WkTDept where kdNumber=?";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, deptId);
		List<WkTDept> resultList = query.list();
		WkTDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_ProjectMember> findProjectMember(Jxkh_Project project) {
		String queryString = "from Jxkh_ProjectMember as pf where pf.project = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<Jxkh_ProjectDept> findProjectDept(Jxkh_Project project) {
		String queryString = "from Jxkh_ProjectDept as pf where pf.project = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<Jxkh_ProjectDept> findProjectDep(Jxkh_Project project) {
		String queryString = "from Jxkh_ProjectDept as pf where pf.deptId!='000' and pf.project = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}
	
	@Override
	public List<Jxkh_PatentInventor> findPatentMember(Jxkh_Patent project) {
		String queryString = "from Jxkh_PatentInventor as pf where pf.patent = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<Jxkh_PatentDept> findPatentDept(Jxkh_Patent project) {
		String queryString = "from Jxkh_PatentDept as pf where pf.patent = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<Jxkh_PatentDept> findPatentDep(Jxkh_Patent project) {
		String queryString = "from Jxkh_PatentDept as pf where pf.deptId!='000' and pf.patent = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}
	
	@Override
	public List<Jxkh_Writer> findWritingMember(Jxkh_Writing project) {
		String queryString = "from Jxkh_Writer as pf where pf.writing = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<Jxkh_WritingDept> findWritingDept(Jxkh_Writing project) {
		String queryString = "from Jxkh_WritingDept as pf where pf.writing = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<Jxkh_WritingDept> findWritingDep(Jxkh_Writing project) {
		String queryString = "from Jxkh_WritingDept as pf where pf.deptId!='000' and pf.writing = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}
	@Override
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");// and
																			// d.rank
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	public List<Jxkh_Project> findZPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findZPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	public List<Jxkh_Project> findHPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findHPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findCPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findCPByCondition2(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear asc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.sort = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");// and
																			// d.rank
																			// =1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.sort = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.sort = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");// and
																			// d.rank
																			// =1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.sort = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.name='"
							+ auditDep + "')");
		queryString.append("order by a.jxYear desc, a.state, a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.name='"
							+ auditDep + "')");
		queryString.append("order by a.jxYear desc, a.state, a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.name='"
							+ auditDep + "')");
		queryString.append("order by a.jxYear desc, a.state, a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.sort = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append("and a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.name='"
							+ auditDep + "')");
		queryString.append(" order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.sort = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.name='"
							+ auditDep + "')");
		queryString.append("order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		queryString.append("order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		queryString.append("order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		queryString.append("order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		queryString.append(" order by a.id");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Short auditSearch, int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		queryString.append("order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllZPByDept(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 0 and a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllZPByDept(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 0 and a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllHPByDept(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 1 and a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllHPByDept(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 1 and a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllCPByDept(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 2 and a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.id");// and d.rank = 1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllCPByDept(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 2 and a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =
																			// 1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findAllPatentByDept(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.deptId='"
						+ kdNumber + "') order by a.state,a.submitTime ");// and
																			// d.rank
																			// =1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findAllPatentByDept(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findAllWritingByDept(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.deptId='"
						+ kdNumber + "' ) order by a.state,a.submitTime ");// and
																			// d.rank
																			// =1
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findAllWritingByDept(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where a.state in(0,1,2,3)");
		queryString
				.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");// and
																			// d.rank
																			// =1
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllZPByBusi() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 0 and a.state in(1,4,5,6,7)");
		queryString.append("order by a.jxYear desc, a.state, a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllHPByBusi() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 1 and a.state in(1,4,5,6,7)");
		queryString.append("order by a.jxYear desc, a.state, a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllCPByBusi() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 2 and a.state in(1,4,5,6,7)");
		queryString.append("order by a.jxYear desc , a.state, a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findAllPatentByBusi() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where a.state in(1,4,5,6,7)");
		queryString.append(" order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findAllWritingByBusi() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where a.state in(1,4,5,6,7)");
		queryString.append("order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllZPByBusi(int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 0 and a.state in(1,4,5,6)");
		queryString.append("order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllHPByBusi(int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 1 and a.state in(1,4,5,6)");
		queryString.append("order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllCPByBusi(int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Project as a where a.sort = 2 and a.state in(1,4,5,6)");
		queryString.append("order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findAllPatentByBusi(int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where a.state in(1,4,5,6)");
		queryString.append(" order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findAllWritingByBusi(int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where a.state in(1,4,5,6)");
		queryString.append("order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllZPByDept1(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0 ");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllZPByDept1(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0 ");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllHPByDept1(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1 ");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllHPByDept1(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1 ");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllCPByDept1(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2 ");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findAllCPByDept1(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2 ");
		queryString
				.append("and a.id in (select distinct d.project from Jxkh_ProjectDept as d where d.deptId='"
						+ kdNumber + "') order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findAllPatentByDept1(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Patent as a where a.state in(0,1,2,3,4,5,6,7,8)");
		queryString
				.append("and a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.deptId='"
						+ kdNumber + "') order by a.jxYear desc,a.state asc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findAllPatentByDept1(String kdNumber, int pageNo,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Patent as a where a.state in(0,1,2,3,4,5,6)");
		queryString
				.append("and a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.deptId='"
						+ kdNumber + "') order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findAllWritingByDept1(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Writing as a where a.state in(0,1,2,3,4,5,6,7,8)");
		queryString
				.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.deptId='"
						+ kdNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");
		// queryString.append("from Jxkh_Writing as a where a.id in (select distinct d.patent from Jxkh_PatentDept as d where d.deptId='"+kdNumber+"') order by a.id")
		// ;
		// queryString.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.deptId='"+kdNumber+"') order by a.id");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findAllWritingByDept1(String kdNumber,
			int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Writing as a where a.state in(0,1,2,3,4,5,6)");
		queryString
				.append("and a.id in (select distinct d.writing from Jxkh_WritingDept as d where d.deptId='"
						+ kdNumber + "') order by a.id");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findMultiDeptProject(String year) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Jxkh_Project");
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
	public JXKH_MultiDept findMultiDeptByPrId(Long prId) {
		String queryString = "from JXKH_MultiDept where prId=?";
		List<JXKH_MultiDept> list = getHibernateTemplate().find(queryString,
				new Object[] { prId });
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Jxkh_PatentFile> findFilesByIdAndType(Jxkh_Patent patent,
			String type) {
		String queryString = "from Jxkh_PatentFile as ad where ad.patent=? and ad.belongType=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, patent);
		query.setParameter(1, type);
		List<Jxkh_PatentFile> flist = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return flist;
	}

	@Override
	public List<Jxkh_Project> findZPByCondition(String nameSearch,
			Integer stateSearch, String year, String memberId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 0");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append(" and a.id in (select distinct mt.project from Jxkh_ProjectMember as mt where mt.personId='"
						+ memberId + "') order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findHPByCondition(String nameSearch,
			Integer auditSearch, String year, String memberId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct mt.project from Jxkh_ProjectMember as mt where mt.personId='"
						+ memberId + "') order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Project> findCPByCondition(String nameSearch,
			Integer auditSearch, String year, String memberId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where a.sort = 2");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct mt.project from Jxkh_ProjectMember as mt where mt.personId='"
						+ memberId + "') order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Patent> findPatentByCondition(String nameSearch,
			Integer auditSearch, String year, String memberId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Patent as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and  a.id in (select distinct mt.patent from Jxkh_PatentInventor as mt where mt.personId='"
						+ memberId + "') order by a.jxYear desc, a.state asc,a.grantDate desc ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Patent> list = query.list();
		for (Jxkh_Patent patent : list) {
			List<Jxkh_PatentDept> meetingDepts = patent.getPatentDept();
			Set<Jxkh_PatentFile> meetingFile = patent.getPatentFile();
			patent.setPatentDept(meetingDepts);
			patent.setPatentFile(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Writing> findWritingByCondition(String nameSearch,
			Integer auditSearch, String year, String memberId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Writing as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct mt.writing from Jxkh_Writer as mt where mt.personId='"
						+ memberId + "') order by a.jxYear desc, a.state asc,a.publishDate desc  ");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Writing> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public Jxkh_ProjectDept findProjectDept(Jxkh_Project m, String wktDeptId) {
		String queryString = "from Jxkh_ProjectDept where project=? and deptId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		List<Jxkh_ProjectDept> resultList = query.list();
		Jxkh_ProjectDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public Jxkh_PatentDept findPatentDept(Jxkh_Patent m, String wktDeptId) {
		String queryString = "from Jxkh_PatentDept where patent=? and deptId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		List<Jxkh_PatentDept> resultList = query.list();
		Jxkh_PatentDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public Jxkh_WritingDept findWritingDept(Jxkh_Writing m, String wktDeptId) {
		String queryString = "from Jxkh_WritingDept where writing=? and deptId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		List<Jxkh_WritingDept> resultList = query.list();
		Jxkh_WritingDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_ProjectFund> getFundByYearAndProject(Jxkh_Project project, String year) {
		String queryString = "from Jxkh_ProjectFund as pf where pf.sort= 0 and pf.jxYear like '%"
				+ year + "%' and pf.project = ?";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<Jxkh_ProjectFile> getProjectFile(Jxkh_Project project) {
		String queryString = "from Jxkh_ProjectFile as f where f.project = ?";
		return getHibernateTemplate().find(queryString, new Object[] {project});
	}

	@Override
	public List<Jxkh_Project> findProjectBySortAndMemberIdAndPaging(int pageNo, int pageSize, Short sort, String memberId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		String queryString = "from Jxkh_Project as zp where zp.sort = " +sort.shortValue()
				+ " and zp.id in (select distinct mt.project from Jxkh_ProjectMember as mt where mt.personId='"
				+ memberId + "') order by zp.jxYear desc,zp.state asc,zp.beginDate desc";
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Project> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}
}
