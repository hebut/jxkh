package org.iti.jxkh.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_HULWMember;
import org.iti.jxkh.entity.JXKH_HYLW;
import org.iti.jxkh.entity.JXKH_HYLWDept;
import org.iti.jxkh.entity.JXKH_HYLWFile;
import org.iti.jxkh.entity.JXKH_HYlwSlMessage;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.JXKH_QKLWFile;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.service.JxkhHylwService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JxkhHylwServiceImpl extends BaseServiceImpl implements
		JxkhHylwService {

	@Override
	public List<JXKH_HYLW> findIndexListbox() {
		String queryString = "from JXKH_HYLW";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<JXKH_HULWMember> findAwardMemberByAwardId(JXKH_HYLW meeting) {
		String queryString = "from JXKH_HULWMember as am where am.HYLWName=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_HULWMember> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_HYLWDept> findMeetingDeptByMeetingId(JXKH_HYLW meeting) {
		String queryString = "from JXKH_HYLWDept as ad where ad.meeting=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_HYLWDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_HYLWDept> findMeetingDepByMeetingId(JXKH_HYLW meeting) {
		String queryString = "from JXKH_HYLWDept as ad where ad.depId!='000' and ad.meeting=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_HYLWDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}
	@Override
	public Set<JXKH_HYLWFile> findMeetingFilesByMeetingId(JXKH_HYLW meeting) {
		String queryString = "from JXKH_HYLWFile as ad where ad.meeting=? order by fileType";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_HYLWFile> flist = query.list();
		Set<JXKH_HYLWFile> fset = new HashSet<JXKH_HYLWFile>();
		fset.addAll(flist);
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return fset;
	}

	@Override
	public List<JXKH_HYLW> findMeetingByKuLidAndpPaging(String name,
			Integer auditState, Long type, String kuLid, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where 1=1 ");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append("and a.writerId='" + kuLid + "'");
		queryString
				.append(" and a.lwId in (select distinct m.HYLWName from JXKH_HULWMember as m where m.personId='"
						+ kuLid + "')");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null)
			queryString.append(" and a.lwGrade = '" + type + "'");
		queryString.append(" order by a.jxYear desc, a.lwState asc,a.lwTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_HYLW> list = query.list();
		for (JXKH_HYLW award : list) {
			List<JXKH_HULWMember> qklwMembers = award.getLwAll();
			List<JXKH_HYLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_HYLWFile> qklwFile = award.getFiles();
			award.setLwAll(qklwMembers);
			award.setLwDep(qklwDepts);
			award.setFiles(qklwFile);
			logger.debug(qklwMembers.isEmpty());
			logger.debug(qklwDepts.isEmpty());
			logger.debug((qklwFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByKuLid(String name, Integer auditState, Long type,
			String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where 1=1 ");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append("and a.writerId='" + kuLid + "'");
		queryString
				.append(" and a.lwId in (select distinct m.HYLWName from JXKH_HULWMember as m where m.personId='"
						+ kuLid + "')");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null)
			queryString.append(" and a.lwGrade = '" + type + "'");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_HYLW> findMeetingByKdNumberAndPaging(String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_HYLW as a where (a.lwState=null or a.lwState<4 ) ");
		queryString
				.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.depId='"
						+ kdNumber + "')");
		queryString.append(" order by a.lwState,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_HYLW> list = query.list();
		for (JXKH_HYLW award : list) {
			List<JXKH_HULWMember> qklwMembers = award.getLwAll();
			List<JXKH_HYLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_HYLWFile> qklwFile = award.getFiles();
			award.setLwAll(qklwMembers);
			award.setLwDep(qklwDepts);
			award.setFiles(qklwFile);
			logger.debug(qklwMembers.isEmpty());
			logger.debug(qklwDepts.isEmpty());
			logger.debug((qklwFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByKdNumber(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_HYLW as a where (a.lwState=null or a.lwState<4 ) ");
		queryString
				.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.depId='"
						+ kdNumber + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_HYLW> findMeetingByConditionAndPage(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null)
			queryString.append(" and a.lwGrade = '" + type + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.depId='"
							+ kdNumber + "')");
		queryString.append(" order by a.lwState asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_HYLW> list = query.list();
		for (JXKH_HYLW award : list) {
			List<JXKH_HULWMember> qklwMembers = award.getLwAll();
			List<JXKH_HYLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_HYLWFile> qklwFile = award.getFiles();
			award.setLwAll(qklwMembers);
			award.setLwDep(qklwDepts);
			award.setFiles(qklwFile);
			logger.debug(qklwMembers.isEmpty());
			logger.debug(qklwDepts.isEmpty());
			logger.debug((qklwFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByCondition(String name, Integer auditState,
			Long type, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null)
			queryString.append(" and a.lwGrade = '" + type + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.depId='"
							+ kdNumber + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_HYLW> findMeetingByAuditAndPaging(int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where a.lwState in (1,4,5,6,7) ");
		queryString.append(" order by a.jxYear desc, a.lwState ,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_HYLW> list = query.list();
		for (JXKH_HYLW award : list) {
			List<JXKH_HULWMember> qklwMembers = award.getLwAll();
			List<JXKH_HYLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_HYLWFile> qklwFile = award.getFiles();
			award.setLwAll(qklwMembers);
			award.setLwDep(qklwDepts);
			award.setFiles(qklwFile);
			logger.debug(qklwMembers.isEmpty());
			logger.debug(qklwDepts.isEmpty());
			logger.debug((qklwFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByAudit() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where a.lwState in (1,4,5,6) ");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_HYLW> findMeetingByKdNumberAndPagings(String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_HYLW as a where (a.lwState=null or a.lwState<4 ) ");
		queryString
				.append(" and  a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where  m.depId='"
						+ kdNumber + "')");// (a.LwState=2 or
		queryString.append(" order by a.lwState asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_HYLW> list = query.list();
		for (JXKH_HYLW award : list) {
			List<JXKH_HULWMember> qklwMembers = award.getLwAll();
			List<JXKH_HYLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_HYLWFile> qklwFile = award.getFiles();
			award.setLwAll(qklwMembers);
			award.setLwDep(qklwDepts);
			award.setFiles(qklwFile);
			logger.debug(qklwMembers.isEmpty());
			logger.debug(qklwDepts.isEmpty());
			logger.debug((qklwFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByKdNumbers(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_HYLW as a where (a.lwState=null or a.lwState<4 ) ");
		queryString
				.append(" and  a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.depId='"
						+ kdNumber + "')");// (a.LwState=2 or
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_HYLW> findMeetingByConditionAndPages(String name,
			Integer auditState, String kdNumber, int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_HYLW as a where (a.lwState=null or a.lwState<4 ) ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where  m.depId='"
							+ kdNumber + "')");
		queryString.append(" order by a.lwState,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_HYLW> list = query.list();
		for (JXKH_HYLW award : list) {
			List<JXKH_HULWMember> qklwMembers = award.getLwAll();
			List<JXKH_HYLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_HYLWFile> qklwFile = award.getFiles();
			award.setLwAll(qklwMembers);
			award.setLwDep(qklwDepts);
			award.setFiles(qklwFile);
			logger.debug(qklwMembers.isEmpty());
			logger.debug(qklwDepts.isEmpty());
			logger.debug((qklwFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByConditions(String name, Integer auditState,
			String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_HYLW as a where (a.lwState=null or a.lwState<4 ) ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.depId='"
							+ kdNumber + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_HYlwSlMessage> findHylwSlMessageByMeetingId(
			JXKH_HYLW meeting) {
		String queryString = "from JXKH_HYlwSlMessage as ad where ad.meeting=? order by jxYear";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_HYlwSlMessage> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_HYLW> findMeetingByBusiAduitConditionAndPages(String name,
			Integer auditState, Long type, String auditDep, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null)
			queryString.append(" and a.lwGrade = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.name='"
							+ auditDep + "')");
		queryString.append(" order by a.jxYear desc, a.lwState,a.submitTime");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_HYLW> list = query.list();
		for (JXKH_HYLW award : list) {
			List<JXKH_HULWMember> qklwMembers = award.getLwAll();
			List<JXKH_HYLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_HYLWFile> qklwFile = award.getFiles();
			award.setLwAll(qklwMembers);
			award.setLwDep(qklwDepts);
			award.setFiles(qklwFile);
			logger.debug(qklwMembers.isEmpty());
			logger.debug(qklwDepts.isEmpty());
			logger.debug((qklwFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByBusiAduitConditions(String name,
			Integer auditState, Long type, String auditDep) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null)
			queryString.append(" and a.lwGrade = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_HYLWDept as m where m.name='"
							+ auditDep + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public JXKH_HYLWDept findHYLWDept(JXKH_HYLW m, String wktDeptId) {
		String queryString = "from JXKH_HYLWDept where meeting=? and depId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		@SuppressWarnings("unchecked")
		List<JXKH_HYLWDept> resultList = query.list();
		JXKH_HYLWDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_HYLW> findHYLWbyName(String name, String year) {
		String queryString = "from JXKH_HYLW as q where q.lwName=? and jxYear < ?";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, name);
		query.setParameter(1, year);
		List<JXKH_HYLW> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_HYLW> findHylwByKuLidAndPaging(String nameSearch,
			Integer stateSearch, String year, String kuLid, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where 1=1 ");
		queryString.append(" and a.lwId in (select distinct m.HYLWName from JXKH_HULWMember as m where m.personId='"+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.lwName like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.lwState = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString.append(" order by a.jxYear desc, a.lwState asc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_HYLW> list = query.list();
		for (JXKH_HYLW award : list) {
			List<JXKH_HULWMember> qklwMembers = award.getLwAll();
			List<JXKH_HYLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_HYLWFile> qklwFile = award.getFiles();
			award.setLwAll(qklwMembers);
			award.setLwDep(qklwDepts);
			award.setFiles(qklwFile);
			logger.debug(qklwMembers.isEmpty());
			logger.debug(qklwDepts.isEmpty());
			logger.debug((qklwFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}
	@Override
	public int findHylwByKuLid(String nameSearch, Integer stateSearch,
			String year, String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_HYLW as a where 1=1 ");
		queryString.append(" and a.lwId in (select distinct m.HYLWName from JXKH_HULWMember as m where m.personId='"+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.lwName like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.lwState = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

}
