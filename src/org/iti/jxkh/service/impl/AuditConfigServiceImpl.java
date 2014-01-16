package org.iti.jxkh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_AppraisalMember;
import org.iti.jxkh.entity.JXKH_AuditConfig;
import org.iti.jxkh.entity.JXKH_JFRESULT;
import org.iti.jxkh.service.AuditConfigService;
import org.springframework.stereotype.Service;
import com.uniwin.basehs.service.impl.AnnBaseServiceImpl;
import com.uniwin.framework.entity.WkTUser;

@Service("auditConfigService")
public class AuditConfigServiceImpl extends AnnBaseServiceImpl implements AuditConfigService {

	public JXKH_AuditConfig findByYear(String year) {
		String queryString = "from JXKH_AuditConfig where acYear=?";
		@SuppressWarnings("unchecked")
		List<JXKH_AuditConfig> aclist = getHibernateTemplate().find(queryString, new Object[] { year });
		if (aclist.size() != 0)
			return aclist.get(0);
		else
			return null;
	}

	public int findWorker() {
		String queryString = "select count(*) from WkTUser where kuType ="
				+ WkTUser.JOB_IN
				+ " and kuId in (select distinct id.kuId from WkTUserole where id.krId not in (select distinct krId from WkTRole where krName like '%院长%')) and kdId in (select distinct kdId from WkTDept where kdClassify = 0 and kdType = 0 and kdState = 0)";
		List<?> list = getHibernateTemplate().find(queryString, new Object[] {});
		return Integer.parseInt(list.get(0).toString());
	}

	public int findManager() {
		String queryString = "select count(*) from WkTUser where kuId in (select distinct id.kuId from WkTUserole where id.krId not in (select distinct krId from WkTRole where krName like '%院长%')) and kdId in (select distinct kdId from WkTDept where kdClassify = 1 and kdType = 0 and kdState = 0)";
		List<?> list = getHibernateTemplate().find(queryString, new Object[] {});
		return Integer.parseInt(list.get(0).toString());
	}

	public int findLeader() {
		String queryString = "select count(*) from WkTUser where kuId in (select distinct id.kuId from WkTUserole where id.krId in (select distinct krId from WkTRole where krName like '%院长%'))";
		List<?> list = getHibernateTemplate().find(queryString, new Object[] {});
		return Integer.parseInt(list.get(0).toString());
	}
	
	public int findDeptMember(Long kdId) {
		String queryString = "select count(*) from WkTUser where kuType =" + WkTUser.JOB_IN + " and kdId = ? and kuId in (select distinct id.kuId from WkTUserole where id.krId not in (select distinct krId from WkTRole where krName like '%院长%'))";
		List<?> list = getHibernateTemplate().find(queryString, new Object[] { kdId });
		return Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("unchecked")
	public List<WkTUser> findDeptMemberList(Long kdId) {
		String queryString = "from WkTUser where kdId = ? and kuType = 0 and kuId in (select distinct id.kuId from WkTUserole where id.krId not in (select distinct krId from WkTRole where krName like '%院长%'))";
		return getHibernateTemplate().find(queryString, new Object[] { kdId });
	}

	@SuppressWarnings("unchecked")
	public List<WkTUser> findLeaderList() {
		String queryString = "from WkTUser where kuId in (select distinct id.kuId from WkTUserole where id.krId in (select distinct krId from WkTRole where krName like '%院长%'))";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	@Override
	public int findDeptMemberByName(Long kdId, String Name, String StaffId) {
		String queryString = "select count(*) from WkTUser where kdId = ? and kuName like '%" + Name + "%' and staffId like '%" + StaffId
				+ "%' and kuId in (select distinct id.kuId from WkTUserole where id.krId not in (select distinct krId from WkTRole where krName like '%院长%'))";
		List<?> list = getHibernateTemplate().find(queryString, new Object[] { kdId });
		return Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WkTUser> findDeptMemberListByName(Long kdId, String Name, String StaffId) {
		String queryString = "from WkTUser where kdId = ? and kuName like '%" + Name + "%' and staffId like '%" + StaffId + "%'";
		return getHibernateTemplate().find(queryString, new Object[] { kdId });
	}

	@Override
	public int findDeptAllMember(Long kdId) {
		String queryString = "select count(*) from WkTUser where kuType =" + WkTUser.JOB_IN + " and kdId = ? and kuId in (select distinct id.kuId from WkTUserole where id.krId not in (select distinct krId from WkTRole where krName like '%院长%'))";
		List<?> list = getHibernateTemplate().find(queryString, new Object[] { kdId });
		return Integer.parseInt(list.get(0).toString());
	}


	public int findCheckPeoByDeptId(Long KdId) {
		String queryString = "select count(*) from JXKH_AppraisalMember where deptId =?";
		List<?> list = getHibernateTemplate().find(queryString, new Object[] { KdId });
		return Integer.parseInt(list.get(0).toString());
	}

	@Override
	public List<JXKH_AppraisalMember> findpeoByDept(Long deptId) {
		Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from JXKH_AppraisalMember as a where 1=1");
		if (deptId != null && !deptId.equals(""))
			queryString.append(" and a.deptId = '" + deptId + "'");
		Query query = session.createQuery(queryString.toString());
		@SuppressWarnings("unchecked")
		List<JXKH_AppraisalMember>list=query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}

	@Override
	public List<JXKH_AppraisalMember> findleaderByDept(String deptName) {
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
	public List<JXKH_AppraisalMember> findManagerPeo(String staffIdSearch,String nameSearch, Long deptSearch) {
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

	@Override
	public List<WkTUser> findUser(Long kuId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append("from WkTUser as u where 1=1");
	    queryString.append(" and u.kuId in(" + kuId + ")");
		Query query = session.createQuery(queryString.toString());
		List<WkTUser> resultList = query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return resultList;
	}

	@Override
	public List<JXKH_JFRESULT> findJfByYear(String year) {
		Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		if(year !=null && !year.equals("")){
		queryString.append("from JXKH_JFRESULT as a where a.year='"+year+"'");
		}
		Query query = session.createQuery(queryString.toString());
		@SuppressWarnings("unchecked")
		List<JXKH_JFRESULT>list=query.list();
		session.beginTransaction().commit();
		if (session.isOpen())
			session.close();
		return list;
	}
}
