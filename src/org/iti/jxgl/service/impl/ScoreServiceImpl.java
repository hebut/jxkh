package org.iti.jxgl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.iti.jxgl.entity.JxScore;
import org.iti.jxgl.service.ScoreService;
import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;

public class ScoreServiceImpl extends BaseServiceImpl implements ScoreService {
	public JxScore findBySidAndCid(String jsSid, String jsCid, Integer jsYear, Short jsTerm) {
		String queryString = "from JxScore as score where score.jsSid=? and score.jsCid=? and score.jsYear=? and score.jsTerm=?";
		List list = getHibernateTemplate().find(queryString, new Object[] { jsSid, jsCid, jsYear, jsTerm });
		if (list.size() == 0)
			return null;
		else
			return (JxScore) list.get(0);
	}

	public List findTeacherBySidAndCid(String sid, Long cid, String year, Short term) {
		String queryString = "from JxCoursetable as ct where ct.jctYear=? and ct.jctTerm=? and ct.jcId=? and ct.jctClass in(select stClass from Student where stId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, cid, sid });
	}

	public List findStudentCollege(String sid) {
		String queryString = "from WkTDept as d where d.kdId in (select kdPid from WkTDept where kdId in (select c.kdId from XyClass as c where c.clId in (select s.clId from Student as s where s.stId=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { sid });
	}

	public List findStudentMajor(String sid) {
		String queryString = "from WkTDept as d where d.kdId in (select c.kdId from XyClass as c where c.clId in (select s.clId from Student as s where s.stId=?))";
		return getHibernateTemplate().find(queryString, new Object[] { sid });
	}

	public List findDeptList(Long kdid) {
		List deptList = new ArrayList();
		String queryString = "from WkTDept where kdId=?";
		WkTDept dept = (WkTDept) getHibernateTemplate().find(queryString, new Object[] { kdid }).get(0);
		deptList.add(new Object[] { dept.getKdId(), dept.getKdNumber(), dept.getKdName() });
		queryString = "select distinct kdId,kdNumber,kdName from WkTDept where kdNumber in (select distinct jsMajor from JxScore where jsCollege=" + dept.getKdNumber() + ")";
		deptList.addAll(getHibernateTemplate().find(queryString));
		return deptList;
	}

	public List findLessonList(Long kdid) {
		String queryString = "select distinct jcNo,jcName from JxCourse where jcId in(select distinct jcId from JxTask where kdId=? or kdId in (select kdPid from WkTDept where kdId=?)) and jcNo in (select distinct jsCid from JxScore)";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
	}

	public List findTeacherlist(Long kdid) {
		String queryString = "select distinct thName,kuId from JxTask where kuId in (select kuId from Teacher where thId in (select distinct jsThid from JxScore) and kuId in (select kuId from WkTUser where kdId=? or kdId in (select kdId from WkTDept where kdPid=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
	}

	public List findClasslist(Long kdid) {
		String queryString = "select cla.clName from XyClass as cla where cla.kdId=? or cla.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid =?) order by cla.clName";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
	}

	public List findStudentInScoreByGrade(Integer year, Short term, Long kdid, Integer grade) {
		String queryString = "select distinct s.jsSid from JxScore as s where s.jsYear=? and s.jsTerm=? and jsSid in (select stu.stId from Student as stu where stu.kuId in (select u.kuId from WkTUser as u where u.kdId=? or u.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid=?)) and stu.stGrade=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid, kdid, grade });
	}

	public List findStudentInDept(Long kdid, Integer grade) {
		String queryString = "from Student as stu where stu.kuId in (select u.kuId from WkTUser as u where u.kdId=? or u.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid=?)) and stu.stGrade=?";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid, grade });
	}

	public List findBySid(String jsSid, Integer jsYear, Short jsTerm) {
		StringBuffer queryString = new StringBuffer("from JxScore as score where score.jsSid=? and ");
		if (jsYear == 0)
			queryString.append("score.jsYear<>? and ");
		else
			queryString.append("score.jsYear=? and ");
		if (jsTerm == (short) -1)
			queryString.append("score.jsTerm<>?");
		else
			queryString.append("score.jsTerm=?");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { jsSid, jsYear, jsTerm });
	}

	public List findTeacherBySameCourse(Integer year, Short term, String jcno, Long kdid) {
		String queryString = "select distinct jsThid from JxScore where jsYear=? and jsTerm=? and jsCid=? and jsSid in (select stId from Student where clId in (select clId from XyClass where kdId=? or kdId in (select kdId from WkTDept where kdPid=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jcno, kdid, kdid });
	}

	public List findClassByTeacherAndCourse(String year, Short term, Long kuid, String jcno, Long kdid) {
		String queryString = "from JxTask where jtYear=? and jtTerm=? and kuId=? and jcId in (select jcId from JxCourse where jcNo=?) and (jtClass in (select clSname from XyClass where kdId=? or kdId in (select kdId from WkTDept where kdPid=?)) or jtClass in (select clName from XyClass where kdId=? or kdId in (select kdId from WkTDept where kdPid=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kuid, jcno, kdid, kdid, kdid, kdid });
	}

	public List findScoreByClassAndCourse(Integer year, Short term, String jcno, List clalist) {
		StringBuffer queryString = new StringBuffer("from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsCid=? and score.jsSid in (select stu.stId from Student as stu where stu.stClass in (");
		for (int i = 0; i < clalist.size(); i++) {
			queryString.append("'" + clalist.get(i).toString() + "'");
			if (i != clalist.size() - 1)
				queryString.append(",");
		}
		queryString.append("))");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, jcno });
	}

	public List findScorelist(String jcno, Double max, Double min, List scorelist) {
		StringBuffer queryString = new StringBuffer("from JxScore as score where score.jsCid =? and score.jsSid in (");
		boolean flag = false;
		for (int i = 0; i < scorelist.size(); i++) {
			JxScore js = (JxScore) scorelist.get(i);
			if (js.getJsScore() >= min && js.getJsScore() <= max) {
				queryString.append("'" + js.getJsSid() + "',");
				flag = true;
			}
		}
		if (flag) {
			queryString.deleteCharAt(queryString.length() - 1);
			queryString.append(") order by score.jsScore desc");
		} else {
			queryString.delete(42, queryString.length());
			queryString.append("and score.jsSid =-1");
		}
		return getHibernateTemplate().find(queryString.toString(), new Object[] { jcno });
	}

	public List findCourseForTeacher(Integer year, Short term, Long kuId, Long kdid) {
		String queryString = "select distinct jcNo,jcName from JxCourse where jcNo in (select distinct jsCid from JxScore where jsYear=? and jsTerm=? and jsThid in (select thId from Teacher where kuId=?) and jsCollege in (select kdNumber from WkTDept where kdId=? or kdId in (select kdPid from WkTDept where kdId=?)))";
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kuId, kdid, kdid });
	}

	public List findClassByJcNoAndKuId(String year, Short term, String jcNo, Long kuId, Long kdId) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.jcId in (select cou.jcId from JxCourse as cou where cou.jcNo=?) and task.kuId=? and (kdId=? or kdId in (select kdPid from WkTDept where kdId=?))";
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, jcNo, kuId, kdId, kdId });
	}

	public List findCourseScoreInClass(Integer year, Short term, String jcNo, String claname) {
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsCid =? and score.jsSid in (select stu.stId from Student as stu where stu.stClass=? or stu.stClass in (select cla.clSname from XyClass as cla where cla.clName=?))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jcNo, claname, claname });
	}

	public List findYearForClass(String claname) {
		String queryString = "select distinct task.jtYear from JxTask as task where task.jtClass=? or task.jtClass in (select cla.clSname from XyClass as cla where cla.clName=?)";
		return getHibernateTemplate().find(queryString, new Object[] { claname, claname });
	}

	public List findTermForClass(String year, String claname) {
		String queryString = "select distinct task.jtTerm from JxTask as task where task.jtYear=? and task.jtClass=? or task.jtClass in (select cla.clSname from XyClass as cla where cla.clName=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, claname, claname });
	}

	public List findAllStuScoreInClass(Integer year, Short term, String claname) {
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsSid in (select stu.stId from Student as stu where stu.stClass=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, claname });
	}

	public List findFailCourse(Integer year, Short term, String kdnumber) {
		String queryString = "select distinct score.jsCid from JxScore as score where score.jsYear=? and score.jsTerm=? and (score.jsCollege=? or score.jsMajor=?) and ((jsScore<60 and jsScore>0) or (jsScore=-45))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdnumber, kdnumber });
	}

	public List findScoreByDeptAndCourse(Integer year, Short term, String jcno, String kdnum) {
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsCid=? and (score.jsCollege=? or score.jsMajor=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jcno, kdnum, kdnum });
	}

	public List findFailByDpetAndCourse(Integer year, Short term, String jcno, String kdnum) {
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsCid=? and (score.jsCollege=? or score.jsMajor=?) and ((jsScore<60 and jsScore>0) or (jsScore=-45))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jcno, kdnum, kdnum });
	}

	public List findFailForStudent(Integer year, Short term, String sid) {
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=? and jsSid=? and ((jsScore<60 and jsScore>0) or (jsScore=-45))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, sid });
	}

	public List findFailStudent(Integer year, Short term, Integer grade, Long kdid) {
		String queryString = "select distinct score.jsSid from JxScore as score where score.jsYear=? and score.jsTerm=? and ((jsScore<60 and jsScore>0) or (jsScore=-45)) and score.jsSid in (select stu.stId from Student as stu where stu.stGrade=? and stu.kuId in (select u.kuId from WkTUser as u where u.kdId=? or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, grade, kdid, kdid });
	}

	public List findCourseNumber(Integer year, Short term, Integer grade, Long kdid) {
		String queryString = "select distinct score.jsCid from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsSid in (select stu.stId from Student as stu where stu.stGrade=? and stu.kuId in (select u.kuId from WkTUser as u where u.kdId=? or u.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, grade, kdid, kdid });
	}

	public List findStudentFailNumber(Integer year, Short term, String sid, Integer grade) {
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsSid=? and ((jsScore<60 and jsScore>0) or (jsScore=-45)) and score.jsSid in (select stu.stId from Student as stu where stu.stGrade=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, sid, grade });
	}

	public List findCourseKeyIn(Integer year, Short term, Long kdid) {
		String queryString = "select distinct score.jsCid from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsCollege in (select kdNumber from WkTDept where kdId=? or kdId in (select kdPid from WkTDept where kdId=?))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid, kdid });
	}

	public List findCourseScore(Integer year, Short term, String jcNo, Long kdid) {
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsCid =? and jsSid in (select stId from Student where clId in (select clId from XyClass where kdId=? or kdId in (select kdId from WkTDept where kdPid=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jcNo, kdid, kdid });
	}

	public List findCourseFail(Integer year, Short term, String jcNo, Long kdid) {
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsCid=? and ((jsScore<60 and jsScore>0) or (jsScore=-45)) and jsSid in (select stId from Student where clId in (select clId from XyClass where kdId=? or kdId in (select kdId from WkTDept where kdPid=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jcNo, kdid, kdid });
	}

	public List findCourseByType(Integer year, Short term, String jctid) {
		String queryString = "select distinct score.jsCid from JxScore as score where score.jsYear=? and score.jsTerm=? and score.jsCid in (select cou.jcNo from JxCourse as cou where cou.jcTid=? or cou.jcTid in (select jct.jctId from JxCoursetype as jct where jct.jctFid=?))";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jctid, jctid });
	}

	public List findSameClassScore(String jsCid, String jsSid) {
		String queryString = "from JxScore as sc where sc.jsCid=? and sc.jsSid in (select stId from Student as stu where stu.stClass=(select st.stClass from Student as st where st.stId=?)))";
		return getHibernateTemplate().find(queryString, new Object[] { jsCid, jsSid });
	}

	public List findSameGradeScore(String jsCid, List claname) {
		StringBuffer queryString = new StringBuffer("from JxScore as sc where sc.jsCid=? and sc.jsSid in (select stu.stId from Student as stu where stu.stClass in (");
		for (int i = 0; i < claname.size(); i++) {
			queryString.append("'" + claname.get(i).toString().trim() + "',");
		}
		queryString.deleteCharAt(queryString.length() - 1);
		queryString.append("))");
		if (claname.size() == 0)
			return null;
		return getHibernateTemplate().find(queryString.toString(), new Object[] { jsCid });
	}

	public List findClassFromCourseTable(String year, Short term, String jcno) {
		String queryString = "select distinct jctClass from JxCoursetable where jctYear=? and jctTerm=? and jcId in (select jcId from JxCourse where jcNo=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jcno });
	}

	public List findAllStudent(Integer year, Short term, String kdnum) {
		String queryString = "select distinct jsSid from JxScore where jsYear=? and jsTerm=? and jsCollege=? or jsMajor=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdnum, kdnum });
	}

	public List findClassgradelist(Long kdid, Integer grade) {
		String queryString = "select cla.clName from XyClass as cla where cla.kdId=? or cla.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid =?) and cla.clGrade=? order by cla.clName";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid, grade });
	}

	public List findScoreInPeriod(String jsSid, Integer fromTime, Integer toTime) {
		StringBuffer queryString = new StringBuffer("from JxScore where jsSid=? and jsOrder>=? and jsOrder<=?");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { jsSid, fromTime, toTime });
	}
	public List findbySidfromtimeandendtime(String jsSid, Integer fromTime, Integer toTime){
		StringBuffer queryString = new StringBuffer("select distinct jc.jcId from JxXxcourse as jx,JxCourse as jc where jx.jcId=jc.jcNo and jx.jxCredit=jc.jcCredit and jx.jxSid=? and jx.jxOrder>=? and jx.jxOrder<=?");
		
		return getHibernateTemplate().find(queryString.toString(), new Object[] { jsSid,fromTime,toTime });
	}
	
	public List findScoreInPeriod(Integer fromTime, Integer toTime,Long claid) {
		StringBuffer queryString = new StringBuffer("from JxScore where jsOrder>=? and jsOrder<=? and jsSid in(select st.stId from Student as st Where st.clId=? ) and ((jsScore >0 and jsScore<60 ) or (jsScore=-45))");
		 //System.out.println(""+queryString.toString()+""+claid);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { fromTime, toTime,claid });
	   
	}

	public List findByStid(String stid) {
		String queryString = "from JxScore as score where score.jsSid=? order by score.jsYear,score.jsTerm,score.jsCname";
		return getHibernateTemplate().find(queryString, new Object[] { stid});
	}

	public JxScore findByCidKdid(Integer year, Short term, String cid,
			String kdid,String studentno) {
		String queryString="from JxScore as js where js.jsYear=? and js.jsTerm=? and js.jsCid=? and js.jsCollege=? and js.jsSid=?";
		List jsc=getHibernateTemplate().find(queryString, new Object[]{year,term,cid,kdid,studentno});
		if (jsc == null || jsc.size()==0) {
			
			return null;
		} else {
			return (JxScore)jsc.get(0);
		}
	}
	
	public List findByYearAndTermAndJcnoAndClass(Integer year, Short term, String jcno,List jctclass){
		String queryString = "from JxScore as score where score.jsYear=? and score.jsTerm=?  and score.jsCid=? and score.jsSid in (select stu.stId from Student as stu where stu.stClass in  (";
		for(int i=0;i<jctclass.size();i++){
			String  jc=(String)jctclass.get(i);
			queryString=queryString+"'"+jc+"'";
			if(i<jctclass.size()-1){
				queryString=queryString+",";
			}
		}
		queryString=queryString+")) order by score.jsKeyindate";
		return getHibernateTemplate().find(queryString, new Object[]{year,term,jcno});
	}
}
