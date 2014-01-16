package org.iti.jxkh.service.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.Jxkh_Fruit;
import org.iti.jxkh.entity.Jxkh_FruitDept;
import org.iti.jxkh.entity.Jxkh_FruitFile;
import org.iti.jxkh.entity.Jxkh_FruitMember;
import org.iti.jxkh.service.JxkhFruitService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class JxkhFruitServiceImp extends BaseServiceImpl implements
		JxkhFruitService {

	@Override
	public List<Jxkh_Fruit> findFruitByKuLid(String nameSearch,
			Integer stateSearch, String year, String kuLid) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Fruit as a where 1=1");
		// if(kuLid!=null&&!kuLid.equals(""))
		// queryString.append("and a.submitId='"+kuLid+"'");
		queryString
				.append(" and a.id in (select distinct m.fruit from Jxkh_FruitMember as m where 1=1");
		if (kuLid != null && !kuLid.equals("")) {
			queryString.append(" and m.personId='" + kuLid + "'");
		}
		queryString.append(")");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString.append(" order by a.jxYear desc, a.state,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		List<Jxkh_Fruit> list = query.list();
		for (Jxkh_Fruit fruit : list) {
			List<Jxkh_FruitMember> fruitMembers = fruit.getFruitMember();
			List<Jxkh_FruitDept> fruitDepts = fruit.getFruitDept();
			Set<Jxkh_FruitFile> fruitFile = fruit.getFruitFile();
			fruit.setFruitMember(fruitMembers);
			fruit.setFruitDept(fruitDepts);
			fruit.setFruitFile(fruitFile);
			logger.debug(fruitMembers.isEmpty());
			logger.debug(fruitDepts.isEmpty());
			logger.debug(fruitFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findFruitByKbNumber(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Fruit as a where a.state in (0,1,2,3) and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kbNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findFruitByState() {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Fruit as a where a.state in (1,4,5,6) ");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findFruitByCondition(String nameSearch, Short stateSearch,
			Long type, String auditDep) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Fruit as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null)
			queryString.append(" and a.appraiseRank = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.name='"
							+ auditDep + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public int findFruitByCondition(String nameSearch, Short stateSearch,
			Long type, String year, String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Fruit as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.appraiseRank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kbNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_FruitMember> findFruitMemberByFruitId(Jxkh_Fruit fruit) {
		String queryString = "from Jxkh_FruitMember as am where am.fruit=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, fruit);
		List<Jxkh_FruitMember> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_FruitDept> findFruitDeptByFruitId(Jxkh_Fruit fruit) {
		String queryString = "from Jxkh_FruitDept as ad where ad.fruit=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, fruit);
		List<Jxkh_FruitDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public List<Jxkh_FruitDept> findFruitDepByFruitId(Jxkh_Fruit fruit) {
		String queryString = "from Jxkh_FruitDept as ad where ad.deptId!='000' and ad.fruit=? order by rank";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, fruit);
		List<Jxkh_FruitDept> result = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}

	@Override
	public int findFruitByKbNumberAll(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Fruit as a where a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kbNumber + "')");
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Fruit> findFruitByKbNumberAndPaging(String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Fruit as a where a.state in (0,1,2,3) and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kbNumber + "')");
		queryString.append(" order by a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Fruit> list = query.list();
		for (Jxkh_Fruit fruit : list) {
			List<Jxkh_FruitMember> fruitMembers = fruit.getFruitMember();
			List<Jxkh_FruitDept> fruitDepts = fruit.getFruitDept();
			Set<Jxkh_FruitFile> fruitFile = fruit.getFruitFile();
			fruit.setFruitMember(fruitMembers);
			fruit.setFruitDept(fruitDepts);
			fruit.setFruitFile(fruitFile);
			logger.debug(fruitMembers.isEmpty());
			logger.debug(fruitDepts.isEmpty());
			logger.debug(fruitFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Fruit> findFruitByConditionAndPaging(String nameSearch,
			Short stateSearch, Long type, String year, String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Fruit as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.appraiseRank = '" + type + "'");
		if (year != null && !year.equals(""))
			queryString.append(" and a.jxYear = '" + year + "'");
		queryString
				.append("and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kbNumber + "') order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Fruit> list = query.list();
		for (Jxkh_Fruit fruit : list) {
			List<Jxkh_FruitMember> fruitMembers = fruit.getFruitMember();
			List<Jxkh_FruitDept> fruitDepts = fruit.getFruitDept();
			Set<Jxkh_FruitFile> fruitFile = fruit.getFruitFile();
			fruit.setFruitMember(fruitMembers);
			fruit.setFruitDept(fruitDepts);
			fruit.setFruitFile(fruitFile);
			logger.debug(fruitMembers.isEmpty());
			logger.debug(fruitDepts.isEmpty());
			logger.debug(fruitFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Fruit> findFruitByKbNumberAll(String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Fruit as a where a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kbNumber + "')");
		queryString.append(" order by a.jxYear desc, a.state asc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Fruit> list = query.list();
		for (Jxkh_Fruit fruit : list) {
			List<Jxkh_FruitMember> fruitMembers = fruit.getFruitMember();
			List<Jxkh_FruitDept> fruitDepts = fruit.getFruitDept();
			Set<Jxkh_FruitFile> fruitFile = fruit.getFruitFile();
			fruit.setFruitMember(fruitMembers);
			fruit.setFruitDept(fruitDepts);
			fruit.setFruitFile(fruitFile);
			logger.debug(fruitMembers.isEmpty());
			logger.debug(fruitDepts.isEmpty());
			logger.debug(fruitFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Fruit> findFruitByState(int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Fruit as a where a.state in (1,4,5,6,7) ");
		queryString.append(" order by a.jxYear desc, a.state,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Fruit> list = query.list();
		for (Jxkh_Fruit fruit : list) {
			List<Jxkh_FruitMember> fruitMembers = fruit.getFruitMember();
			List<Jxkh_FruitDept> fruitDepts = fruit.getFruitDept();
			Set<Jxkh_FruitFile> fruitFile = fruit.getFruitFile();
			fruit.setFruitMember(fruitMembers);
			fruit.setFruitDept(fruitDepts);
			fruit.setFruitFile(fruitFile);
			logger.debug(fruitMembers.isEmpty());
			logger.debug(fruitDepts.isEmpty());
			logger.debug(fruitFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<Jxkh_Fruit> findFruitByCondition(String nameSearch,
			Short stateSearch, Long type, String auditDep, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from Jxkh_Fruit as a where 1=1");
		if (nameSearch != null && !nameSearch.equals(""))
			queryString.append(" and a.name like '%" + nameSearch + "%'");
		if (stateSearch != null && !stateSearch.equals(""))
			queryString.append(" and a.state = '" + stateSearch + "'");
		if (type != null && !type.equals(""))
			queryString.append(" and a.appraiseRank = '" + type + "'");
		if (auditDep != null && !auditDep.equals(""))
			queryString
					.append(" and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.name='"
							+ auditDep + "')");
		queryString.append("order by a.jxYear desc, a.state ,a.submitTime ");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Fruit> list = query.list();
		for (Jxkh_Fruit fruit : list) {
			List<Jxkh_FruitMember> fruitMembers = fruit.getFruitMember();
			List<Jxkh_FruitDept> fruitDepts = fruit.getFruitDept();
			Set<Jxkh_FruitFile> fruitFile = fruit.getFruitFile();
			fruit.setFruitMember(fruitMembers);
			fruit.setFruitDept(fruitDepts);
			fruit.setFruitFile(fruitFile);
			logger.debug(fruitMembers.isEmpty());
			logger.debug(fruitDepts.isEmpty());
			logger.debug(fruitFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public int findFruitByKbNumber2(String kbNumber) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Fruit as a where a.state in (0,1,2,3) and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kbNumber + "')");// and m.rank=1
		session.close();
		return countQuery(queryString.toString());
	}

	@Override
	public List<Jxkh_Fruit> findFruitByKbNumberAndPaging2(String kbNumber,
			int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("from Jxkh_Fruit as a where a.state in (0,1,2,3) and a.id in (select distinct m.fruit from Jxkh_FruitDept as m where m.deptId='"
						+ kbNumber + "')");// and m.rank=1
		queryString.append(" order by a.state asc,a.jxYear desc,a.submitTime desc");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		List<Jxkh_Fruit> list = query.list();
		for (Jxkh_Fruit fruit : list) {
			List<Jxkh_FruitMember> fruitMembers = fruit.getFruitMember();
			List<Jxkh_FruitDept> fruitDepts = fruit.getFruitDept();
			Set<Jxkh_FruitFile> fruitFile = fruit.getFruitFile();
			fruit.setFruitMember(fruitMembers);
			fruit.setFruitDept(fruitDepts);
			fruit.setFruitFile(fruitFile);
			logger.debug(fruitMembers.isEmpty());
			logger.debug(fruitDepts.isEmpty());
			logger.debug(fruitFile.isEmpty());
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public Jxkh_FruitDept findFruitDept(Jxkh_Fruit m, String wktDeptId) {
		String queryString = "from Jxkh_FruitDept where fruit=? and deptId=? ";
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(queryString);
		query.setParameter(0, m);
		query.setParameter(1, wktDeptId);
		@SuppressWarnings("unchecked")
		List<Jxkh_FruitDept> resultList = query.list();
		Jxkh_FruitDept result = null;
		if (resultList.size() != 0) {
			result = resultList.get(0);
		}
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return result;
	}
}
