package org.iti.jxkh.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.JXKH_QKLW;
import org.iti.jxkh.entity.JXKH_QKLWDept;
import org.iti.jxkh.entity.JXKH_QKLWFile;
import org.iti.jxkh.entity.JXKH_QKLWMember;
import org.iti.jxkh.entity.JXKH_QklwSlMessage;
import org.iti.jxkh.service.JxkhQklwService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JxkhQklwServiceImpl extends BaseServiceImpl implements
		JxkhQklwService {

	@Override
	public List<JXKH_QKLWMember> findAwardMemberByAwardId(JXKH_QKLW meeting) {
		String queryString = "from JXKH_QKLWMember as am where am.QKLWName=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_QKLWMember> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_QKLWDept> findMeetingDeptByMeetingId(JXKH_QKLW meeting) {
		String queryString = "from JXKH_QKLWDept as ad where ad.meeting=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_QKLWDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_QKLWDept> findMeetingDepByMeetingId(JXKH_QKLW meeting) {
		String queryString = "from JXKH_QKLWDept as ad where ad.depId!='000' and ad.meeting=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_QKLWDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}
	
	@Override
	public Set<JXKH_QKLWFile> findMeetingFilesByMeetingId(JXKH_QKLW meeting) {
		String queryString = "from JXKH_QKLWFile as ad where ad.meeting=? order by ad.fileType";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_QKLWFile> flist = query.list();
		Set<JXKH_QKLWFile> fset = new HashSet<JXKH_QKLWFile>();
		fset.addAll(flist);
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return fset;
	}

	@Override
	public List<JXKH_QKLW> findMeetingByKuLidAndPaging(String name,
			Integer auditState, Long type, String kuLid, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append("and a.writerId='" + kuLid + "'");
		queryString
				.append(" and a.lwId in (select distinct m.QKLWName from JXKH_QKLWMember as m where m.personId='"
						+ kuLid + "')");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null)
			queryString.append(" and a.qkGrade = '" + type + "'");
		queryString.append(" order by a.jxYear desc, a.lwState asc,a.lwDate desc");// ,a.lwTime DESC
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append("and a.writerId='" + kuLid + "'");
		queryString
				.append(" and a.lwId in (select distinct m.QKLWName from JXKH_QKLWMember as m where m.personId='"
						+ kuLid + "')");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.qkGrade = '" + type + "'");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_QKLW> findMeetingByKdNumberAndPage(String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_QKLW as a where (a.lwState=null or a.lwState<4 ) ");
		queryString
				.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.depId='"
						+ kdNumber + "')");
		queryString.append(" order by a.lwState,a.submitTime ");// ,a.lwTime
																// DESC
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
				.append("from JXKH_QKLW as a where (a.lwState=null or a.lwState<4 ) ");
		queryString
				.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.depId='"
						+ kdNumber + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_QKLW> findMeetingByConditionAndPage(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.qkGrade = '" + type + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.depId='"
							+ kdNumber + "')");
		queryString.append(" order by a.jxYear desc,a.lwState asc,a.submitTime desc");// ,a.lwTime DESC
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.qkGrade = '" + type + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.depId='"
							+ kdNumber + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_QKLW> findMeetingsByKdNumber(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		queryString
				.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.depId='"
						+ kdNumber + "')");
		queryString.append(" order by a.lwState,a.submitTime ");// ,a.lwTime
																// DESC
		Query query = session.createQuery(queryString.toString());
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
	public List<JXKH_QKLW> findMeetingByAuditAndPaging(int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_QKLW as a where a.lwState in (1,4,5,6,7) ");
		queryString.append(" order by a.jxYear desc ,a.lwState,a.submitTime ");// ,a.lwTime
																		// DESC
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
		queryString.append("from JXKH_QKLW as a where a.lwState in (1,4,5,6) ");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_QKLW> findMeetingByKdNumberAndPages(String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_QKLW as a where (a.lwState=null or a.lwState<4 ) ");
		queryString
				.append(" and  a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where  m.depId='"
						+ kdNumber + "')");
		queryString.append(" order by a.lwState asc,a.jxYear desc,a.submitTime ");
																
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
				.append("from JXKH_QKLW as a where (a.lwState=null or a.lwState<4 ) ");
		queryString
				.append(" and  a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.depId='"
						+ kdNumber + "')");// (a.LwState=2 or
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_QKLW> findMeetingByConditionAndPages(String name,
			Integer auditState, Long type, String kdNumber, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_QKLW as a where (a.lwState=null or a.lwState<3 ) ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.qkGrade = '" + type + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.depId='"
							+ kdNumber + "')");
		queryString.append(" order by a.lwState asc,a.jxYear desc,a.submitTime desc");// ,a.lwTime
																// DESC
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
			Long type, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_QKLW as a where (a.lwState=null or a.lwState<4 ) ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.qkGrade = '" + type + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.depId='"
							+ kdNumber + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_QklwSlMessage> findQklwSlMessageByMeetingId(
			JXKH_QKLW meeting) {
		String queryString = "from JXKH_QklwSlMessage as ad where ad.meeting=? order by jxYear";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_QklwSlMessage> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_QKLW> findMeetingByBusiAduitConditionAndPages(String name,
			Integer auditState, Long type, String auditDep, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.qkGrade = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.name='"
							+ auditDep + "')");
		queryString.append(" order by a.jxYear desc, a.lwState,a.submitTime ");// ,a.lwTime
																// DESC
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.lwName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.lwState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.qkGrade = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.lwId in (select distinct m.meeting from JXKH_QKLWDept as m where m.name='"
							+ auditDep + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public JXKH_QKLWDept findQKLWDept(JXKH_QKLW m, String wktDeptId) {
		String queryString = "from JXKH_QKLWDept where meeting=? and depId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		@SuppressWarnings("unchecked")
		List<JXKH_QKLWDept> resultList = query.list();
		JXKH_QKLWDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_QKLW> findQKLWbyName(String name,String year) {
		String queryString = "from JXKH_QKLW as q where q.lwName=? and jxYear < ?";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, name);
		query.setParameter(1, year);
		List<JXKH_QKLW> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_QKLW> findQklwByKuLidAndPaging(String nameSearch,
			Integer stateSearch, String year, String kuLid, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		queryString
				.append(" and a.lwId in (select distinct m.QKLWName from JXKH_QKLWMember as m where m.personId='"
						+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.lwName like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.lwState = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString.append(" order by a.jxYear desc, a.lwState asc,a.lwDate desc");// ,a.lwTime DESC
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_QKLW> list = query.list();
		for (JXKH_QKLW award : list) {
			List<JXKH_QKLWMember> qklwMembers = award.getLwAll();
			List<JXKH_QKLWDept> qklwDepts = award.getLwDep();
			Set<JXKH_QKLWFile> qklwFile = award.getFiles();
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
	public int findQklwByKuLid(String nameSearch, Integer stateSearch,
			String year, String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_QKLW as a where 1=1 ");
		queryString
				.append(" and a.lwId in (select distinct m.QKLWName from JXKH_QKLWMember as m where m.personId='"
						+ kuLid + "')");
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
