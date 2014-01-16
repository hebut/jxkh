package org.iti.jxkh.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_MEETING;
import org.iti.jxkh.entity.JXKH_MeetingDept;
import org.iti.jxkh.entity.JXKH_MeetingFile;
import org.iti.jxkh.entity.JXKH_MeetingMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.service.JXKHMeetingService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class JXKHMeetingServiceImpl extends BaseServiceImpl implements
		JXKHMeetingService {

	@Override
	public List<WkTDept> findAllDep() {
		String queryString = "from WkTDept where kdPid in (1) and kdState = 0";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<JXKH_MeetingDept> findMeetingDeptByMeetingId(
			JXKH_MEETING meeting) {
		// String queryString =
		// "from JXKH_MeetingDept as ad where ad.meeting="+meeting+" order by rank";
		// return getHibernateTemplate().find(queryString);

		String queryString = "from JXKH_MeetingDept as ad where ad.meeting=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_MeetingDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_MeetingDept> findMeetingDepByMeetingId(
			JXKH_MEETING meeting) {
		String queryString = "from JXKH_MeetingDept as ad where ad.meeting=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_MeetingDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}
	
	@Override
	public Set<JXKH_MeetingFile> findMeetingFilesByMeetingId(
			JXKH_MEETING meeting) {
		String queryString = "from JXKH_MeetingFile as ad where ad.meeting=? order by fileType";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_MeetingFile> flist = query.list();
		Set<JXKH_MeetingFile> fset = new HashSet<JXKH_MeetingFile>();
		fset.addAll(flist);
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return fset;
	}

	@Override
	public List<JXKH_MEETING> findMeetingByKuLidAndPaging(String nameSearch,
			Integer stateSearch, String year, String kuLid, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_MEETING as a where 1=1 ");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append(" and a.writerId='" + kuLid + "'");
		queryString
				.append(" and a.mtId in (select distinct m.meetingName from JXKH_MeetingMember as m where m.personId='"
						+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.mtName like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.mtState = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString.append(" order by a.jxYear desc, a.mtState asc,a.mtTime desc ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_MEETING> list = query.list();
		for (JXKH_MEETING meeting : list) {
			List<JXKH_MeetingDept> meetingDepts = meeting.getMtDep();
			Set<JXKH_MeetingFile> meetingFile = meeting.getFiles();
			meeting.setMtDep(meetingDepts);
			meeting.setFiles(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByKuLid(String nameSearch, Integer stateSearch,
			String year, String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_MEETING as a where 1=1 ");
		// if (kuLid != null && !kuLid.equals(""))
		// queryString.append(" and a.writerId='" + kuLid + "'");
		queryString
				.append(" and a.mtId in (select distinct m.meetingName from JXKH_MeetingMember as m where m.personId='"
						+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.mtName like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.mtState = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_MeetingFile> findFilesByIdAndType(JXKH_MEETING meeting,
			String type) {
		String queryString = "from JXKH_MeetingFile as ad where ad.meeting=? and ad.fileType=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		query.setParameter(1, type);
		List<JXKH_MeetingFile> flist = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return flist;
	}

	@Override
	public List<JXKH_MEETING> findMeetingByKdNumberAndPaging(String KdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_MEETING as a where (a.mtState=null or a.mtState<4 ) ");
		queryString
				.append(" and a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.depId= "
						+ "'" + KdNumber + "')");
		queryString.append(" order by a.mtState,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_MEETING> list = query.list();
		for (JXKH_MEETING meeting : list) {
			List<JXKH_MeetingDept> meetingDepts = meeting.getMtDep();
			Set<JXKH_MeetingFile> meetingFile = meeting.getFiles();
			meeting.setMtDep(meetingDepts);
			meeting.setFiles(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByKdNumber(String KdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_MEETING as a where (a.mtState=null or a.mtState<4 ) ");
		queryString
				.append(" and a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.depId= "
						+ "'" + KdNumber + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_MEETING> findMeetingByConditionAndPaging(String name,
			Integer auditState, Long type, String year, String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_MEETING as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.mtName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.mtState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.mtDegree = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.depId= "
							+ "'" + kdNumber + "')");
		queryString.append(" order by a.mtState asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_MEETING> list = query.list();
		for (JXKH_MEETING meeting : list) {
			List<JXKH_MeetingDept> meetingDepts = meeting.getMtDep();
			Set<JXKH_MeetingFile> meetingFile = meeting.getFiles();
			meeting.setMtDep(meetingDepts);
			meeting.setFiles(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByCondition(String name, Integer auditState,
			Long type, String year, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_MEETING as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.mtName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.mtState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.mtDegree = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		if (kdNumber != null && !kdNumber.equals(""))
			queryString
					.append(" and a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.depId= "
							+ "'" + kdNumber + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_BusinessIndicator> findRank(Integer kbValue) {
		String queryString = "from Jxkh_BusinessIndicator as b where b.kbValue like '"
				+ kbValue + "_' order by b.kbOrdno";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<JXKH_MeetingDept> findWritingDept(JXKH_MEETING project) {
		String queryString = "from JXKH_MeetingDept as pf where pf.meeting = ? order by pf.rank";
		return getHibernateTemplate().find(queryString,
				new Object[] { project });
	}

	@Override
	public List<JXKH_MEETING> findMeetingByAudit(int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_MEETING as a where a.mtState in (1,4,5,6,7) ");
		queryString.append(" order by a.jxYear desc, a.mtState, a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_MEETING> list = query.list();
		for (JXKH_MEETING meeting : list) {
			List<JXKH_MeetingDept> meetingDepts = meeting.getMtDep();
			Set<JXKH_MeetingFile> meetingFile = meeting.getFiles();
			meeting.setMtDep(meetingDepts);
			meeting.setFiles(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
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
		queryString
				.append("from JXKH_MEETING as a where a.mtState in (1,4,5,6) ");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_MEETING> findMeetingByKdNumberAndPagings(String KdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_MEETING as a where (a.mtState=null or a.mtState<4 ) ");
		queryString
				.append(" and  a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.depId= "
						+ "'" + KdNumber + "')");
		// queryString.append(" and ( a.mtState=2 or m.rank=1)");
		queryString.append(" order by a.mtState asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_MEETING> list = query.list();
		for (JXKH_MEETING meeting : list) {
			List<JXKH_MeetingDept> meetingDepts = meeting.getMtDep();
			Set<JXKH_MeetingFile> meetingFile = meeting.getFiles();
			meeting.setMtDep(meetingDepts);
			meeting.setFiles(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findMeetingByKdNumbers(String KdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from JXKH_MEETING as a where (a.mtState=null or a.mtState<4 ) ");
		queryString
				.append(" and a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.depId= "
						+ "'" + KdNumber + "') ");
		// queryString.append(" and ( a.mtState=2 or m.rank=1)");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<JXKH_MeetingMember> findMeetingMemberByMeetingId(
			JXKH_MEETING meeting) {
		String queryString = "from JXKH_MeetingMember as am where am.meetingName=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, meeting);
		List<JXKH_MeetingMember> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<com.uniwin.framework.entity.WkTUser> findWkTUserByManId(
			String writerId) {
		String queryString = " from WkTUser as u where u.kuLid = " + "'"
				+ writerId + "'";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		List<WkTUser> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<JXKH_MEETING> findMeetingByBusiAduitConditionAndPages(
			String name, Integer auditState, Long type, String auditDep,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_MEETING as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.mtName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.mtState = '" + auditState + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.mtDegree = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.name= "
							+ "'" + auditDep + "')");
		queryString.append(" order by a.jxYear desc, a.mtState,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<JXKH_MEETING> list = query.list();
		for (JXKH_MEETING meeting : list) {
			List<JXKH_MeetingDept> meetingDepts = meeting.getMtDep();
			Set<JXKH_MeetingFile> meetingFile = meeting.getFiles();
			meeting.setMtDep(meetingDepts);
			meeting.setFiles(meetingFile);
			logger.debug(meetingDepts.isEmpty());
			logger.debug((meetingFile).isEmpty());
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
		queryString.append("from JXKH_MEETING as a where 1=1 ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.mtName like '%" + name + "%'");
		if (auditState != null && !auditState.equals(""))
			queryString.append(" and a.mtState = '" + auditState + "'");
		if (type != null)
			queryString.append(" and a.mtDegree = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.mtId in (select distinct m.meeting from JXKH_MeetingDept as m where m.name= "
							+ "'" + auditDep + "')");
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public JXKH_MeetingDept findMeetingDept(JXKH_MEETING m, String wktDeptId) {
		String queryString = "from JXKH_MeetingDept where meeting=? and depId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		@SuppressWarnings("unchecked")
		List<JXKH_MeetingDept> resultList = query.list();
		JXKH_MeetingDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

}
