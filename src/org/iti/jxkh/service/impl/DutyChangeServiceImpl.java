package org.iti.jxkh.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.jxkh.entity.JXKH_MultiDept;
import org.iti.jxkh.entity.JXKH_PerTrans;
import org.iti.jxkh.service.DutyChangeService;
import org.springframework.stereotype.Service;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class DutyChangeServiceImpl extends BaseServiceImpl implements DutyChangeService{

	@Override
	public List<JXKH_PerTrans> findDutyChangeByDept(Long dept) {
		String queryString="from JXKH_PerTrans as jp where jp.oldKdId = "+dept+" or jp.newKdId = "+dept+" order by jp.ptDate"; 
		return getHibernateTemplate().find(queryString, new Object[]{});
	}

	@Override
	public List<JXKH_PerTrans> findChangeByPage(Long dept, int pageNum,
			int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append( "from JXKH_PerTrans as jp where jp.oldKdId = "+dept+" or jp.newKdId = "+dept+" order by jp.ptDate");
		Query query = session.createQuery(queryString.toString());
//		query.setParameter(0, dept);
		query.setFirstResult(pageNum*pageSize);
	    query.setMaxResults(pageSize);
	    List<JXKH_PerTrans> list = query.list();
	    
	    session.beginTransaction().commit();
	    
		if (session.isOpen())
			session.close();
	   
	    return list;
	}

	@Override
	public List<JXKH_PerTrans> findChangeByKuid(Long kuId) {
		String queryString="from JXKH_PerTrans as jp where jp.kuId = ? order by jp.ptDate"; 
		return getHibernateTemplate().find(queryString, new Object[]{kuId});
	}

	@Override
	public List<WkTUser> findUser(String kuName,String staffId,Long kdId) {
		String queryString;	
		if(staffId != null) {
			queryString = "from WkTUser as u where u.kuName like '%"+kuName+"%' and u.staffId like '%"+staffId+"%' and u.kdId ="+kdId+"";
		}else {
			queryString = "from WkTUser as u where u.kuName like '%"+kuName+"%' and u.kdId not in (0)";
		}
		
		return getHibernateTemplate().find(queryString);
			
		
	}

	@Override
	public List<WkTUser> findUserByPage(int pageNum, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		session.beginTransaction();
		StringBuffer queryString = new StringBuffer();
		queryString.append( "from WkTUser where kdId not in (0)");
		Query query = session.createQuery(queryString.toString());
		query.setFirstResult(pageNum*pageSize);
	    query.setMaxResults(pageSize);
	    List<WkTUser> list = query.list();	    
	    session.beginTransaction().commit();	    
		if (session.isOpen())
			session.close();	   
	    return list;
	}

	@Override
	public List<WkTDept> findAllDept(String deptName) {
//		if(deptName == null || "".equals(deptName)) {
//			String queryString = "from WkTDept as d where d.kdPid=1)";
//			return getHibernateTemplate().find(queryString);
//		}else {
			String queryString = "from WkTDept as d where d.kdName like '%"+deptName+"%' and d.kdPid=1 and d.kdState="+WkTDept.USE_YES+")";
			return getHibernateTemplate().find(queryString); 
//		}
		
	}

	@Override
	public float getAllScoreByDeptAndUserAndYear(String deptNum, String userNum, String year, short type) {
		String sql = "";
		float score = 0f;
		switch (type) {
		case JXKH_MultiDept.HYLW:
			sql = "select SUM(mem.score)  from JXKH_HULWMember mem ,JXKH_HYLW a  where a.lwId=mem.lwId and mem.assignDep=? and a. jxYear=? and mem.personId=? and a.lwState in (4,6)";
			break;
		case JXKH_MultiDept.QKLW:
			sql = "select SUM(mem.score)  from JXKH_QKLWMember mem ,JXKH_QKLW a  where a.lwId=mem.lwId and mem.assignDep=? and a. jxYear=? and mem.personId=? and a.lwState in (4,6)";
			break;
		case JXKH_MultiDept.ZZ:
			sql = "select SUM(mem.score)  from Jxkh_Writer mem ,Jxkh_Writing a  where a.id=mem.WRITING and mem.assignDep=? and a. jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.JL:
			sql = "select SUM(mem.score)  from Jxkh_AwardMember mem ,Jxkh_Award a  where a.id=mem.AWARD and mem.assignDep=? and a. jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.SP:
			sql = "select SUM(mem.score)  from Jxkh_VideoMember mem ,Jxkh_Video a  where a.id=mem.VIDEO and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.XM:
			sql = "select SUM(mem.score)  from Jxkh_ProjectMember mem ,Jxkh_Project a  where a.id=mem.PROJECT and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.ZL:
			sql = "select SUM(mem.score)  from Jxkh_PatentInventor mem ,Jxkh_Patent a  where a.id=mem.PATENT and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.CG:
			sql = "select SUM(mem.score)  from Jxkh_FruitMember mem ,Jxkh_Fruit a  where a.id=mem.FRUIT and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.HY:
			sql = "select SUM(mem.score)  from Jxkh_MeetingMember mem ,Jxkh_MEETING a  where a.mtId=mem.lwId and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.mtState in (4,6)";
			break;
		case JXKH_MultiDept.BG:
			sql = "select SUM(mem.score)  from Jxkh_ReportMember mem ,Jxkh_Report a  where a.id=mem.REPORT and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		}
		score = (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
				deptNum, year, userNum }, java.lang.Float.class) == null ? 0
				: (Float) this.jdbcTemplate.queryForObject(sql, new Object[] {
						deptNum, year, userNum }, java.lang.Float.class);
		return score;
	}

	@Override
	public List<Object> getAllMemberByDeptAndUserAndYear(String deptNum, String userNum, String year, short type) {
		String sql = "";
		switch (type) {
		case JXKH_MultiDept.HYLW:
			sql = "from JXKH_HULWMember as mem ,JXKH_HYLW as a  where a.lwId=mem.HYLWName and mem.assignDep=? and a. jxYear=? and mem.personId=? and a.lwState in (4,6)";
			break;
		case JXKH_MultiDept.QKLW:
			sql = "from JXKH_QKLWMember as mem ,JXKH_QKLW as a  where a.lwId=mem.QKLWName and mem.assignDep=? and a. jxYear=? and mem.personId=? and a.lwState in (4,6)";
			break;
		case JXKH_MultiDept.ZZ:
			sql = "from Jxkh_Writer as mem ,Jxkh_Writing as a  where a.id=mem.writing and mem.assignDep=? and a. jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.JL:
			sql = "from Jxkh_AwardMember as mem ,Jxkh_Award as a  where a.id=mem.award and mem.assignDep=? and a. jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.SP:
			sql = "from Jxkh_VideoMember as mem ,Jxkh_Video as a  where a.id=mem.video and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.XM:
			sql = "from Jxkh_ProjectMember as mem ,Jxkh_Project as a  where a.id=mem.project and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.ZL:
			sql = "from Jxkh_PatentInventor as mem ,Jxkh_Patent as a  where a.id=mem.patent and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.CG:
			sql = "from Jxkh_FruitMember as mem ,Jxkh_Fruit as a  where a.id=mem.fruit and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		case JXKH_MultiDept.HY:
			sql = "from JXKH_MeetingMember as mem ,JXKH_MEETING as a  where a.mtId=mem.meetingName and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.mtState in (4,6)";
			break;
		case JXKH_MultiDept.BG:
			sql = "from Jxkh_ReportMember as mem ,Jxkh_Report as a  where a.id=mem.report and mem.assignDep=? and a.jxYear=? and mem.personId=? and a.state in (4,6)";
			break;
		}
		return getHibernateTemplate().find(sql, new Object[]{deptNum, year, userNum});
	}
}
