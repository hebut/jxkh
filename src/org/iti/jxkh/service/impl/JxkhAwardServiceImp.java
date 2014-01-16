package org.iti.jxkh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.Jxkh_Award;
import org.iti.jxkh.entity.Jxkh_AwardDept;
import org.iti.jxkh.entity.Jxkh_AwardFile;
import org.iti.jxkh.entity.Jxkh_AwardMember;
import org.iti.jxkh.entity.Jxkh_BusinessIndicator;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_Project;
import org.iti.jxkh.service.JxkhAwardService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class JxkhAwardServiceImp extends BaseServiceImpl implements
		JxkhAwardService {

	private final static Log logger = LogFactory
			.getLog(JxkhAwardServiceImp.class);

	public List<Jxkh_Award> findAllAward() {
		String queryString = "from Jxkh_Award";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<Jxkh_Award> findAwardBysubmitId(String submintId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Award as a where a.submitId='"
				+ submintId + "'order by a.date");
		Query query = session.createQuery(queryString.toString());
		// query.setFirstResult(pageNum*pageSize);
		// query.setMaxResults(pageSize);
		List<Jxkh_Award> list = query.list();
		// for(SRDMS_Award award : list){
		// List<SRDMS_AwardMember> awardMembers = award.getMembers();
		// List<SRDMS_FruitAward> fruitAward = award.getFruitAwards();
		// award.setMembers(awardMembers);
		// award.setFruitAwards(fruitAward);
		// logger.debug(awardMembers.isEmpty());
		// logger.debug(fruitAward.isEmpty());
		// }
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_BusinessIndicator> findRank(Long kbPid) {
		String queryString = "from Jxkh_BusinessIndicator as b where b.kbPid="
				+ kbPid + " order by b.kbOrdno";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<Jxkh_Fruit> findAllFruit() {
		String queryString = "from Jxkh_Fruit as f order by f.appraiseDate";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<Jxkh_AwardMember> findAwardMemberByAwardId(Jxkh_Award award) {
		String queryString = "from Jxkh_AwardMember as am where am.award=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, award);
		List<Jxkh_AwardMember> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_AwardDept> findAwardDeptByAwardId(Jxkh_Award award) {
		String queryString = "from Jxkh_AwardDept as ad where ad.award=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, award);
		List<Jxkh_AwardDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_AwardDept> findAwardDepByAwardId(Jxkh_Award award) {
		String queryString = "from Jxkh_AwardDept as ad where ad.deptId!='000' and ad.award=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, award);
		List<Jxkh_AwardDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}
	
	@Override
	public List<Jxkh_BusinessIndicator> findAllBusinessIndicator() {
		String queryString = "from Jxkh_BusinessIndicator";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<WkTUser> findUser() {
		String queryString = "from WkTUser where kdId not in (0)";
		return getHibernateTemplate().find(queryString);
	}

	// String kuidList = "";
	//
	// if (kuidList.length() > 0)
	// kuidList = kuidList.substring(0, kuidList.length() - 1);
	// final String queryString = "from WkTUser as u where u.kuId not in (" +
	// kuidList + ") and u.wktDept.kdId=" + kdid +
	// " or u.wktDept.kdId in (select kdId from WkTDept where (kdPid=" + kdid +
	// " or kdPid in (select kdId from WkTDept where kdPid=" + kdid + ")))";
	// Session session =
	// this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	// session.beginTransaction();
	// @SuppressWarnings("unchecked")
	// List<WkTUser> result =
	// session.createQuery(queryString).setMaxResults(pSize).setFirstResult(pNo
	// * pSize).list();
	// List<WkTUser> resultList = new ArrayList<WkTUser>();
	// for (WkTUser user : result) {
	// WkTDept dept = user.getWktDept();
	// user.setWktDept(dept);
	// logger.debug(dept.getKdName());
	// resultList.add(user);
	// }
	// session.beginTransaction().commit();
	// if (session.isOpen())
	// session.close();
	// // return resultList;
	// }

	@Override
	public List<WkTDept> findDept() {
		String queryString = "from WkTDept where kdPid in (1) and kdState = 0";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<WkTDept> findDept1() {
		String queryString = "from WkTDept where kdState=0";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<Jxkh_Award> findAwardByKuLid(String nameSearch,
			Integer auditSearch, String year, String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Award as a where 1=1");
		// if(kuLid!=null&&!kuLid.equals(""))
		// queryString.append("and a.submitId='"+kuLid+"'");
		queryString
				.append("and a.id in (select distinct m.award from Jxkh_AwardMember as m where m.personId='"
						+ kuLid + "')");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString.append(" order by a.jxYear desc, a.state asc,a.date desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Award> list = query.list();
		for (Jxkh_Award award : list) {
			List<Jxkh_AwardMember> awardMembers = award.getAwardMember();
			List<Jxkh_AwardDept> awardDepts = award.getAwardDept();
			Set<Jxkh_AwardFile> awardFile = award.getAwardFile();
			award.setAwardMember(awardMembers);
			award.setAwardDept(awardDepts);
			award.setAwardFile(awardFile);
			logger.debug(awardMembers.isEmpty());
			logger.debug(awardDepts.isEmpty());
			logger.debug((awardFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public WkTUser findWktUserByMemberUserId(String kuLid) {
		String queryString = "from WkTUser as u where u.kuLid=?";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, kuLid);
		@SuppressWarnings("unchecked")
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
		@SuppressWarnings("unchecked")
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
	public List<Jxkh_Award> findAwardByDept(String memberSearch) {

		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Award as a where 1=1");
		queryString
				.append("and a.Id in (select distinct d.award from Jxkh_AwardDept as d)"
						+ "where d.deptId in (" + memberSearch + ")");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Award> list = query.list();
		for (Jxkh_Award award : list) {
			List<Jxkh_AwardMember> awardMembers = award.getAwardMember();
			List<Jxkh_AwardDept> awardDepts = award.getAwardDept();
			Set<Jxkh_AwardFile> awardFile = award.getAwardFile();
			award.setAwardMember(awardMembers);
			award.setAwardDept(awardDepts);
			award.setAwardFile(awardFile);
			logger.debug(awardMembers.isEmpty());
			logger.debug(awardDepts.isEmpty());
			logger.debug((awardFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;

	}

	@Override
	public List<WkTDept> findWktDeptNotInListBox2(String dlist) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		// String deptidList="";
		// for(WkTDept dept : dlist){
		// deptidList+=dept.getKdNumber()+",";
		// }
		// if(deptidList.length() > 0)
		// deptidList=deptidList.substring(0, deptidList.length()-1);
		queryString.append("from WkTDept as d where d.kdType ='"
				+ WkTDept.BUMEN + "' and d.kdState=" + WkTDept.USE_YES + "");
		if (dlist != null && !dlist.equals(""))
			queryString.append(" and d.kdNumber not in (" + dlist + ")");
		Query query = session.createQuery(queryString.toString());
		List<WkTDept> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<WkTUser> findWkTUserNotInListBox2(String dlist) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from WkTUser as u where u.kdId not in (0)");
		if (dlist != null && !dlist.equals(""))
			queryString.append(" and u.kuLid not in (" + dlist + ")");
		queryString.append("order by u.kdId,u.kuName");
		Query query = session.createQuery(queryString.toString());
		List<WkTUser> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findAwardByKdNumber(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Award as a where a.state in (0,1,2,3) and a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findAwardByAudit() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Award as a where a.state in (1,4,5,6)");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findAwardByCondition(String nameSearch, Short auditSearch,
			Long type, String auditDep) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Award as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null)
			queryString.append(" and a.rank = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.id in (select distinct m.award from Jxkh_AwardDept as m where m.name='"
							+ auditDep + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findAwardByCondition(String nameSearch, Short auditSearch,
			Long type, String year, String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append(" from Jxkh_Award as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Project> findAllPByConditon(String name, Short sort) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Project as a where name!=null ");
		if (name != null && !name.equals(""))
			queryString.append(" and a.name like '%" + name + "%'");
		if (sort != null && !sort.equals(""))
			queryString.append(" and a.sort = '" + sort + "'");
		queryString.append(" order by a.id");
//		return getHibernateTemplate().find(queryString.toString(), new Object[] {});
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Project> list = query.list();
		List<Jxkh_Project> rList = new ArrayList<Jxkh_Project>();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				Jxkh_Project result = list.get(i);
				Jxkh_BusinessIndicator dept = result.getRank();
//				logger.debug(dept.getKbName());
				result.setRank(dept);
				rList.add(i, result);
			}
		}
		
		
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
//		return list;
		return rList;
	}

	@Override
	public List<WkTUser> findWkTUserByCondition(String nameSearch,
			Long deptSearch, String dlist) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from WkTUser as u where u.kdId not in (0)");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and u.kuName like '%" + nameSearch + "%'");
		if (deptSearch != null && !deptSearch.equals(""))
			queryString.append(" and u.kdId in(" + deptSearch + ")");
		if (dlist != null && !dlist.equals(""))
			queryString.append(" and u.kuLid not in (" + dlist + ")");
		Query query = session.createQuery(queryString.toString());
		// List<WkTUser> list=query.list();
		// session.beginTransaction().commit();
		// if (session.isOpen())
		// session.close();
		// return list;
		List<WkTUser> resultList = query.list();
		List rList = new ArrayList();
		WkTUser result = null;
		if (resultList.size() != 0) {
			for (int i = 0; i < resultList.size(); i++) {
				result = resultList.get(i);
				WkTDept dept = result.getDept();
				logger.debug(dept.getKdName());
				result.setDept(dept);
				rList.add(i, result);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return rList;
	}

	@Override
	public List<WkTDept> findWktDeptByCondition(String nameSearch,
			String numberSearch, String dlist) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from WkTDept as d where d.kdState=0 and d.kdPid == 1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and d.kdName like '%" + nameSearch + "%'");
		if (dlist != null && !dlist.equals(""))
			queryString.append(" and d.kdNumber like '%" + numberSearch + "%'");
		if (dlist != null && !dlist.equals(""))
			queryString.append(" and d.kdNumber not in (" + dlist + ")");
		Query query = session.createQuery(queryString.toString());
		List<WkTDept> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findAwardByKdNumberAll(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Award as a where a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Award> findAwardByKdNumberAndPaging(String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Award as a where a.state in (0,1,2,3) and a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "')");
		queryString.append(" order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Award> list = query.list();
		for (Jxkh_Award award : list) {
			List<Jxkh_AwardMember> awardMembers = award.getAwardMember();
			List<Jxkh_AwardDept> awardDepts = award.getAwardDept();
			Set<Jxkh_AwardFile> awardFile = award.getAwardFile();
			award.setAwardMember(awardMembers);
			award.setAwardDept(awardDepts);
			award.setAwardFile(awardFile);
			logger.debug(awardMembers.isEmpty());
			logger.debug(awardDepts.isEmpty());
			logger.debug((awardFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Award> findAwardByConditionAndPaging(String nameSearch,
			Short auditSearch, Long type, String year, String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Award as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "') order by a.jxYear desc, a.state asc,a.date desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Award> list = query.list();
		for (Jxkh_Award award : list) {
			List<Jxkh_AwardMember> awardMembers = award.getAwardMember();
			List<Jxkh_AwardDept> awardDepts = award.getAwardDept();
			Set<Jxkh_AwardFile> awardFile = award.getAwardFile();
			award.setAwardMember(awardMembers);
			award.setAwardDept(awardDepts);
			award.setAwardFile(awardFile);
			logger.debug(awardMembers.isEmpty());
			logger.debug(awardDepts.isEmpty());
			logger.debug((awardFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Award> findAwardByKdNumberAll(String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Award as a where a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "')");
		queryString.append(" order by a.jxYear desc,a.state asc,a.date desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Award> list = query.list();
		for (Jxkh_Award award : list) {
			List<Jxkh_AwardMember> awardMembers = award.getAwardMember();
			List<Jxkh_AwardDept> awardDepts = award.getAwardDept();
			Set<Jxkh_AwardFile> awardFile = award.getAwardFile();
			award.setAwardMember(awardMembers);
			award.setAwardDept(awardDepts);
			award.setAwardFile(awardFile);
			logger.debug(awardMembers.isEmpty());
			logger.debug(awardDepts.isEmpty());
			logger.debug((awardFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Award> findAwardByAudit(int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Award as a where a.state in (1,4,5,6,7)  order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Award> list = query.list();
		for (Jxkh_Award award : list) {
			List<Jxkh_AwardMember> awardMembers = award.getAwardMember();
			List<Jxkh_AwardDept> awardDepts = award.getAwardDept();
			Set<Jxkh_AwardFile> awardFile = award.getAwardFile();
			award.setAwardMember(awardMembers);
			award.setAwardDept(awardDepts);
			award.setAwardFile(awardFile);
			logger.debug(awardMembers.isEmpty());
			logger.debug(awardDepts.isEmpty());
			logger.debug((awardFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Award> findAwardByCondition(String nameSearch,
			Short auditSearch, Long type, String auditDep, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Award as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (auditSearch != null && !auditSearch.equals(""))
			queryString.append(" and a.state = '" + auditSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.rank = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.id in (select distinct m.award from Jxkh_AwardDept as m where m.name='"
							+ auditDep + "')");
		queryString.append("order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Award> list = query.list();
		for (Jxkh_Award award : list) {
			List<Jxkh_AwardMember> awardMembers = award.getAwardMember();
			List<Jxkh_AwardDept> awardDepts = award.getAwardDept();
			Set<Jxkh_AwardFile> awardFile = award.getAwardFile();
			award.setAwardMember(awardMembers);
			award.setAwardDept(awardDepts);
			award.setAwardFile(awardFile);
			logger.debug(awardMembers.isEmpty());
			logger.debug(awardDepts.isEmpty());
			logger.debug((awardFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Award> findAwardByKdNumberAndPaging2(String kdNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Award as a where a.state in (0,1,2,3) and a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "')");// and d.rank=1
		queryString.append(" order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Award> list = query.list();
		for (Jxkh_Award award : list) {
			List<Jxkh_AwardMember> awardMembers = award.getAwardMember();
			List<Jxkh_AwardDept> awardDepts = award.getAwardDept();
			Set<Jxkh_AwardFile> awardFile = award.getAwardFile();
			award.setAwardMember(awardMembers);
			award.setAwardDept(awardDepts);
			award.setAwardFile(awardFile);
			logger.debug(awardMembers.isEmpty());
			logger.debug(awardDepts.isEmpty());
			logger.debug((awardFile).isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findAwardByKdNumber2(String kdNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Award as a where a.state in (0,1,2,3) and a.id in (select distinct d.award from Jxkh_AwardDept as d where d.deptId='"
						+ kdNumber + "')");// and d.rank=1
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_AwardDept> findAwardDeptByAwardId2(Jxkh_Award award) {
		String queryString = "from Jxkh_AwardDept as ad where ad.award=? and ad.rank=1";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, award);
		List<Jxkh_AwardDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<WkTUser> findWkTUserNotInListBox2ByDept(String dlist, Long kdId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from WkTUser as u where u.kdId not in (0) and u.kdId = "
						+ kdId + "");
		if (dlist != null && !dlist.equals(""))
			queryString.append(" and u.kuLid not in (" + dlist + ")");
		queryString.append("order by u.kdId,u.kuName");
		Query query = session.createQuery(queryString.toString());
		List<WkTUser> list = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<WkTUser> findWkTUserByConditions(String staffIdSearch,
			String nameSearch, Long deptSearch, String dlist) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from WkTUser as u where u.kdId not in (0)");
		if (staffIdSearch != null && !staffIdSearch.equals(""))
			queryString.append(" and u.staffId = '" + staffIdSearch + "'");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and u.kuName like '%" + nameSearch + "%'");
		if (deptSearch != null && !deptSearch.equals(""))
			queryString.append(" and u.kdId in(" + deptSearch + ")");
		if (dlist != null && !dlist.equals(""))
			queryString.append(" and u.kuLid not in (" + dlist + ")");
		Query query = session.createQuery(queryString.toString());
		List<WkTUser> resultList = query.list();
		List rList = new ArrayList();
		WkTUser result = null;
		if (resultList.size() != 0) {
			for (int i = 0; i < resultList.size(); i++) {
				result = resultList.get(i);
				WkTDept dept = result.getDept();
				logger.debug(dept.getKdName());
				result.setDept(dept);
				rList.add(i, result);
			}
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return rList;
	}

	@Override
	public List<JXKH_AppraisalMember> findAllAppraisalMember() {
		String queryString = "from JXKH_AppraisalMember";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public Jxkh_AwardDept findAwardDept(Jxkh_Award m, String wktDeptId) {
		String queryString = "from Jxkh_AwardDept where award=? and deptId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		@SuppressWarnings("unchecked")
		List<Jxkh_AwardDept> resultList = query.list();
		Jxkh_AwardDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	        @Override
		    public List<JXKH_AppraisalMember> findpeoByDept(String deptName) {
	    	Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
			session.beginTransaction();
			StringBuffer queryString = new StringBuffer();
			queryString.append("from JXKH_AppraisalMember as a where 1=1");
			if (deptName != null && !deptName.equals(""))
				queryString.append(" and a.dept = '" + deptName + "'");
			Query query = session.createQuery(queryString.toString());
			@SuppressWarnings("unchecked")
			List<JXKH_AppraisalMember>list=query.list();
			session.beginTransaction().commit();
			if (session.isOpen())
				session.close();
			return list;
	}

			@Override
			public List<WkTDept> findDeptAll() {
				String queryString = "from WkTDept where kdPid in (0,1) and kdState = 0";
				return getHibernateTemplate().find(queryString);
			}

			@Override
			public List<JXKH_AppraisalMember> findCheckedUserByConditions(String staffIdSearch, String nameSearch, Long deptSearch,String dlist) {
				Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				session.beginTransaction();
				StringBuffer queryString = new StringBuffer();
				queryString.append("from JXKH_AppraisalMember as u where 1=1");
				if (staffIdSearch != null && !staffIdSearch.equals(""))
					queryString.append(" and u.personId = '" + staffIdSearch + "'");
				if (nameSearch != null && !nameSearch.equals(""))
					queryString.append(" and u.name like '%" + nameSearch + "%'");
				if (deptSearch != null && !deptSearch.equals(""))
					queryString.append(" and u.deptId in(" + deptSearch + ")");
				Query query = session.createQuery(queryString.toString());
				List<JXKH_AppraisalMember> resultList = query.list();
				session.beginTransaction().commit();
				if (session.isOpen())
					session.close();
				return resultList;
			}
}
