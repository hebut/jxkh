package org.iti.jxgl.service.impl;

import java.util.List;

import org.iti.jxgl.entity.JxCourse;
import org.iti.jxgl.entity.JxTask;
import org.iti.jxgl.entity.JxTeachfile;
import org.iti.jxgl.entity.JxTeachplan;
import org.iti.jxgl.service.TaskService;
import org.iti.xypt.entity.Teacher;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;

public class TaskServiceImpl extends BaseServiceImpl implements TaskService {

	public boolean CheckIfInTasklist(JxTask task, List tlist) {
		for (int k = 0; k < tlist.size(); k++) {
			JxTask jt = (JxTask) tlist.get(k);
		//	System.out.println(jt.getJtSumstate() + "");
			if (task.getJtSumstate().equals(jt.getJtSumstate())) {
				return true;
			}
		}
		return false;
	}

	public List<JxTask> FindTaskByYTK(String year, short term, Long kdid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid });
	}


	
	public List<JxTask> FindTaskByYTT(String year, short term, Long kuid, Long jcid, Long kdid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kuId=? and task.jcId=? and task.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kuid, jcid, kdid });
	}

	public List<JxTask> FindTaskByYearTermThid(String year, short term, Long kuId, Long kdId) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kuId=? and task.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kuId,kdId });
	}

	public List FindAllCourseFromTask(Long kdid) {
		String queryString = "select distinct task.jcId from JxTask as task where task.kdId=? order by task.jcId";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public List FindCourseFromTask(String year, short term, Long kdid) {

		String queryString = "select distinct task.jcId from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? order by task.jcId";
		return getHibernateTemplate().find(queryString,
				new Object[] { year, term, kdid });

	
	}


	public List FindByJcid(Long jcid,String year,short term,Long kdid) {
		String queryString = "select distinct task.kuId from JxTask as task where task.jcId=? and" +
				"  task.jtYear=? and task.jtTerm=? and task.kdId=? ";
		return getHibernateTemplate().find(queryString, new Object[] { jcid,year,term,kdid });
	}



	public List FindTeacherFromTask(Long kdid) {
		String queryString = "select distinct task.thId from JxTask as task where  task.kdId=? order by task.thId";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public List FindTaskBySumState(int sumstate) {
		String queryString = "from JxTask as task where task.jtSumstate=?";
		return getHibernateTemplate().find(queryString, new Object[] { sumstate });
	}

	public List findByYearAndTermAndTid(String year, short term, String tid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jcId in (select ct.jcId from JxCoursetable as ct) and ");
		if (year != null) {
			queryString.append("task.jtYear=?");
		}
		if (term != 2) {
			queryString.append(" and task.jtTerm=? ");
		} else {
			queryString.append(" and task.jtTerm<>? ");
		}
		if (tid != null) {
			queryString.append(" and task.thId=?");
		} else {
			queryString.append(" and task.thId<>?");
		}
		//System.out.println(queryString.toString() + "*****");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, tid });
	}

	public List findByYearTermTidCid(String year, short term, Long thid, Long jcid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=?");
		if (thid==0) {
			queryString.append("task.kuId<>?");
		} else if (jcid.equals(Long.valueOf("0"))) {
			queryString.append("and task.kuId=?");
			queryString.append("and task.jcId<>?");
		} else {
			queryString.append("and task.kuId=? and task.jcId=?");
		}
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, thid, jcid });
	}

	public List FindTask(String year, short term, Long cid, Long kuid, List deplist) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		if (cid != 0) {
			queryString.append(")and task.jcId=?");
		} else {
			queryString.append(")and task.jcId<>?");
		}
		if (kuid != 0) {
			queryString.append("and task.kuId in (select tea.kuId from Teacher as tea where tea.kuId=?)");
		} else {
			queryString.append("and task.kuId in (select tea.kuId from Teacher as tea where tea.kuId<>? )");
		}
		queryString.append(" order by task.kuId, task.jcId,task.jtSumstate");
		
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, cid, kuid });

	}

	public List<JxTask> FindTaskByYTTP(String year, short term, Long kdId, List thidlist, List planlist) {//thid改为kuid了
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.kuId in('");
		for (int i = 0; i < thidlist.size(); i++) {
			
			Teacher teacher = (Teacher) thidlist.get(i);
			
			//queryString.append(teacher.getThId());
			queryString.append(teacher.getKuId());
			if (i < thidlist.size() - 1) {
				queryString.append("','");
			}

		}
		queryString.append("')");
		queryString.append("and task.jtSumstate in('");

		for (int j = 0; j < planlist.size(); j++) {
			JxTeachplan plan = (JxTeachplan) planlist.get(j);
			queryString.append(plan.getJtSumstate());

			if (j < planlist.size() - 1) {
				queryString.append("','");
			}
		}
		queryString.append("')");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdId });

	}

	public List FindTaskByYTTF(String year, short term, Long kdId, List filelist,List filelist1) {
		//StringBuffer queryString = new StringBuffer("select distinct task.jcId from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId not in(");
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId not in(");
		for (int j = 0; j < filelist.size(); j++) {
			//String jcidString = String.valueOf(filelist.get(j));
			JxTeachfile file=(JxTeachfile) filelist.get(j);
			queryString.append(Long.valueOf(file.getJtfCid()));
			//queryString.append(jcidString);
			if (j < filelist.size() - 1) {
				queryString.append(",");
				
			}
		}
		queryString.append(")");
		queryString.append("and task.kuId not in(");
		for(int j = 0; j < filelist1.size(); j++){
			JxTeachfile file=(JxTeachfile) filelist1.get(j);
			queryString.append(file.getJtfKuid());
			if (j < filelist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")");
		// System.out.println(queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdId });
	}

	public List FindTaskByYTKd(String year, short term, Long kdId){
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=?");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdId });		
	}
	
	public List FindTaskBySum(String year, short term, Long kdid, List tasklist) {
		StringBuffer queryString = new StringBuffer("select distinct task.jtSumstate from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jtSumstate not in('");
		for (int i = 0; i < tasklist.size(); i++) {
			JxTeachplan p = (JxTeachplan) tasklist.get(i);
			queryString.append(p.getJtSumstate());
			if (i < tasklist.size() - 1) {
				queryString.append("','");
			}
		}
		queryString.append("')");
		//System.out.println(queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdid });

	}

	public List findTaskByYearTermThidKdidcourseid(String year, short term, Long kuid, Long kdid, Long courseid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kuId=? and " + "task.kdId in("+
				"select d.kdId from WkTDept as d where d.kdPid=?) ");
		if (courseid != 0) {
			queryString.append("and task.jcId=?");
		} else {
			queryString.append("and task.jcId<>?");
		}
		queryString.append(" order by task.jcId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kuid, kdid, courseid });

	}

	public List findTaskByYearTermThid(String year, short term, String thid, Long cid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.thId=? and task.jcId<>?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, thid, cid });
	}

	public List findByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? ");
		if (courseid != 0) {
			queryString.append("and task.jcId=?");
		} else {
			queryString.append("and task.jcId<>?");
		}
		//queryString.append(" order by task.jtSumstate");
		queryString.append("  order by task.jtSumstate");
		//System.out.println("==========" + queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, cid, courseid });
	}

	public List findByYearAndTermAndTeacherlistAndCourseid(String year, short term, Long cid, List teacherlist, List courseidlist) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=?" + " and task.kuId in ( ");
		// queryString.append("000030,000031,000031,000033,03029");
		for (int i = 0; i < teacherlist.size(); i++) {
			WkTUser user = (WkTUser) teacherlist.get(i);
			queryString.append(user.getKuId());
			if (i < teacherlist.size() - 1) {
				queryString.append(",");
			}
		}
		/*if (courseid != 0) {
			queryString.append(" ) and task.jcId=" + courseid);
		} else {
			queryString.append(" ) and task.jcId<>" + courseid);
		}*/
		queryString.append(" ) and task.jcId in (");
		
		for (int i = 0; i < courseidlist.size(); i++) {
			
			queryString.append(Long.valueOf(courseidlist.get(i).toString()));
			if (i < courseidlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")");
		//queryString.append(" order by task.jcId");
		//System.out.println("==========" + queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, cid });
	}

	public List findByDeplistAndYearAndTerm(String year, short term, List deplist) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where  task.jtYear=? and task.jtTerm=? and task.kdId in (");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by task.jcId,task.jtSumstate");
		// System.out.println(queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term });
	}

	public List FindTeacherBydeplist(List deplist) {
		StringBuffer queryString = new StringBuffer("select distinct task.thId from JxTask as task where task.kdId in (");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by task.thId");
		//System.out.println(queryString);
		return getHibernateTemplate().find(queryString.toString());
	}

	public List FindSumStateForTeacher(String year, short term, Long kdid, String thid) {
		String queryString = "select distinct task.jtSumstate from JxTask as task where task.jtYear=? and task.jtTerm=? and (task.kdId=? or task.kdId in (select dept.kdPid from WkTDept as dept where dept.kdId=?)) and task.thId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid, kdid, thid });
	}

	public List findByYearAndTermAndWeekAndTidAndDidAndJcbh(String year, short term, String week, String tid, List dlist, Long jcbh) {
		Integer w;
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.jtWeeks like ? and task.thId=? and task.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long did = (Long) dlist.get(i);
			queryString.append(did);

			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		w = week.indexOf("1") + 1;
	//	System.out.println(w + " ********");
		if (jcbh != null) {
			queryString.append(") and task.jcId=? and  task.jtId in (select c.jtId from JxCoursetable as c where c.jctId not in (select r.jxrzCtid from JxJxrz as r where r.jxrzWeek = ? and r.jxrzTime is not null )) ");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, week, tid, jcbh, w });
		} else {
			queryString.append(") and  task.jtId in (select c.jtId from JxCoursetable as c where c.jctId not in (select r.jxrzCtid from JxJxrz as r where r.jxrzWeek = ? and r.jxrzTime is not null ))");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, week, tid, w });
		}

	}

	public List findjcByYearAndTerm(String year, short term) {
		String querySting = " from JxTask as task Where task.jtYear=? and task.jtTerm=?";
		return getHibernateTemplate().find(querySting, new Object[] { year, term });
	}

	public List findThidByYearAndTermAndJcId(String year, short term, Long jcid) {
		String queryString = "from JxTask as jt where jt.jtYear=? and jt.jtTerm=? and jt.jcId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, jcid });
	}

	public List findByYearAndTerm(String year, short term) {

		String querySting = "from JxTask as task Where task.jtYear=? and task.jtTerm=?";
		return getHibernateTemplate().find(querySting, new Object[] { year, term });
	}

	public List findTaskByYearAndTermAndCid(String yaer, Short term, Long jcid) {
		String querySting = "select distinct task.thId,task.kdId from JxTask as task Where task.jtYea=? adn task jtTerm=? and task.jcId=? ";
		return getHibernateTemplate().find(querySting, new Object[] { yaer, term, jcid });
	}

	public JxTask FindTaskEntity(String year, short term, String thid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.thId=?";
		return (JxTask) getHibernateTemplate().find(queryString, new Object[] { year, term, thid }).get(0);

	}

	public List FindAllCourseId(String year, short term, Long thid) {
		String queryString = "select distinct task.jcId from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kuId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, thid });
	}

	public JxTask FindTaskYTTC(String year, short term, Long kuId, String jtclass) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kuId=? and task.jtClass=?";
		return (JxTask) getHibernateTemplate().find(queryString, new Object[] { year, term, kuId, jtclass }).get(0);

	}

	public JxTask FindTaskYTTS(String year, short term, String thid, Integer jtsumstate) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.thId=? and task.jtSumstates=?";
		return (JxTask) getHibernateTemplate().find(queryString, new Object[] { year, term, thid, jtsumstate }).get(0);
	}

	public List findAllTemptask() {
		String queryString = "from Jximporttask ";
		return getHibernateTemplate().find(queryString);
	}

	public List findCidinTaskNotinExecute(String year, short term, Long kdid, Long fromid, String eyear, short eterm, List deplist) {
		StringBuffer queryString = new StringBuffer("select distinct task.jcId from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId not in" + "(select distinct e.jcId from JxExectution as e where e.kdid in)");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")and e.jcId in " + "select distinct c.jcId from JxCourse as c where c.JcFromkdid=? " + "and e.JeYear=? and e.JeTerm=? ");
		queryString.append(")order by task.jcId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdid, fromid, eyear, eterm });
	}

	public List findCidinExecuteNotinTask(List deplist, String eyear, short eterm, List cidlist, Long kdid) {

		StringBuffer queryString = new StringBuffer("select distinct e.jcId from JxExectution as e where e.kdId in(");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")and e.jeYear=? and e.jeTerm=? and e.jcId in(" + "select distinct c.jcId from JxCourse as c where c.jcFromkdid=?" + ")and e.jcId not in (");
		for (int i = 0; i < cidlist.size(); i++) {
			Long cid = Long.valueOf(cidlist.get(i).toString());
			queryString.append(cid);
			if (i < cidlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")order by e.jcId");
		// System.out.println(queryString);

		return getHibernateTemplate().find(queryString.toString(), new Object[] { eyear, eterm, kdid });
	}

	public List findTaskByYearTermThidCid(String year, Short term, Long thid, Long cid,Long kdid) {
		StringBuffer querystring = new StringBuffer("from JxTask as task Where task.jtYear=? and task.jtTerm=? and task.jcId=? and task.kdId=?");
		if (thid == null)
			return getHibernateTemplate().find(querystring.toString(), new Object[] { year, term, cid ,kdid});
		else {
			querystring.append(" and task.kuId=?");
			return getHibernateTemplate().find(querystring.toString(), new Object[] { year, term, cid, kdid,thid });
		}
	}

	public List FindCourseFromTask(String year, short term, List deplist,Long kuid) {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer(" select distinct task.jcId from JxTask as task where  task.jtYear=? and task.jtTerm=?  and task.kdId in (");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")");
		if(kuid!=null&&kuid!=0L){
			queryString.append(" and task.kuId="+kuid+"");
		}
		queryString.append(" order by task.jcId");
	//	System.out.println("-----"+queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term });
	}

	public List FindSumstateFromTask(String year, short term, List deplist) {
		StringBuffer queryString = new StringBuffer(" select distinct task.jtSumstate from JxTask as task where  task.jtYear=? and task.jtTerm=? and task.kdId in (");
		for (int i = 0; i < deplist.size(); i++) {
			WkTDept dept = (WkTDept) deplist.get(i);
			queryString.append(dept.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") order by task.jtSumstate");
		//System.out.println("?????"+queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term });
	}

	public List FindJcidFromTask(String year, short term, Long kdid) {
		StringBuffer queryString = new StringBuffer(" select distinct task.jcId from JxTask as task where  task.jtYear=? and task.jtTerm=? and task.kdId=? and task.kuId is not null");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdid });
	}

	public JxTask FindTypeFromTask(Long cid) {
		String queryString = "from JxTask as task where task.jcId=? ";
		List list=getHibernateTemplate().find(queryString, new Object[] { cid });
		if(list==null||list.size()==0){
			return null;
		}else{
			return (JxTask) list.get(0);
		}
		
	}

	public List FindClassByYearAndTermAndCid(String year, short term, Long cid,Long kdid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.jcId=? and task.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, cid,kdid });
	}

	public List findByYearAndTermAndkdidAndT(String year, short term, Long kdid, Long kuid) {
		String queryString = "select distinct task.jtSumstate from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.kuId<>?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid, kuid });
	}

	public List findtaskbyyearAndtermAndtAndkdid(String year, short term, Long kuid, Long kdid) {
		String queryString = "select distinct task.jtSumstate from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kuId=? and task.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kuid, kdid });
	}

	public JxTask FindFromExectution(Long jeid) {
		String queryString = "from JxTask as task where task.jeId=?";
		List tlist = getHibernateTemplate().find(queryString, new Object[] { jeid });
		if (tlist.size() > 0) {
			return (JxTask) tlist.get(0);
		} else {
			return null;
		}
		/*
		 * public List findByYearAndTermAndT(String year,short term,String t){ StringqueryString= "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.thId<>?" ; return getHibernateTemplate().find(queryString,new Object[]{year,term,t}); }
		 */
		/*
		 * public List findtaskbyyearAndtermAndt(String year,short term,String t){ StringqueryString= "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.thId=?" ; return getHibernateTemplate().find(queryString,new Object[]{year,term,t}); }
		 */

	}

	public List findJcidByYearTern(String y, Short t) {
		String querystring = "select distinct task.jcId from JxTask as task Where task.jtYear=? and task.jtTerm=?";
		return getHibernateTemplate().find(querystring, new Object[] { y, t });
	}

	public List findTaskByYearTermJcid(String y, Short t, Long cid) {
		String querystring = " from JxTask as task Where task.jtYear=? and task.jtTerm=? and task.jcId=?";
		return getHibernateTemplate().find(querystring, new Object[] { y, t, cid });
	}

	public List<JxTask> FindTaskByKdidSumstate(Long kdid, Integer jtsumstate) {
		String queryString = "from JxTask as task where task.kdId=? and task.jtSumstate=?";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, jtsumstate });
	}

	public List<JxTask> FindTaskByJeid(Long jeId) {
		String queryString = "from JxTask as task where task.jeId=?";
		return getHibernateTemplate().find(queryString, new Object[] { jeId });
	}

	public List<JxTask> FindTaskByYTKJ(String year, short term, Long kdid, Long jcid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId=? order by task.jcId";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid, jcid });
	}

	public List FindTaskByYTKJlst(String year, short term, Long kdid, List jcidlst){
		StringBuffer queryString =new StringBuffer("select distinct task.kuId from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId in (" ) ;
			for(int i=0;i<jcidlst.size();i++){
				Long jcidLong=(Long) jcidlst.get(i);
				queryString.append(jcidLong);
				if(i<jcidlst.size()-1){
					queryString.append(",");
				}
			}
			queryString.append(")");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term ,kdid});
	}
	
	public List<JxTask> FindTaskByYTKJK(String year, short term, Long kdid, Long jcid,Long kuId){
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId=? and task.kuId=? order by task.jcId";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid, jcid ,kuId});
	}
	
	public List<JxTask> FindTaskBySumstate(Integer jtsumstate) {
		String queryString = "from JxTask as task where task.jtSumstate=?";
		return getHibernateTemplate().find(queryString, new Object[] { jtsumstate });
	}

	public List<JxTask> FindTaskByYTKJC(String year, short term, Long kdid, Long jcid, String jtClass, String jtClass1) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId=? and (task.jtClass=? or task.jtClass=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid, jcid, jtClass, jtClass1 });
	}

	public List findTaskByYearTermTidKdid(String year, short term, Long t, Long kdid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kuId=? and task.kdId=? order by task.jtSumstate ,task.jtClass ";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, t, kdid });
	}

	public List FindLeadInOrNot(String year, short term, Long kdid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid });
	}

	public List FindCourseForStu(String year, short term, Long kuid) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.jtClass in (select stu.stClass from Student as stu where stu.kuId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kuid });
	}

	public List findByYearAndTermAndkdid2(String year, short term, Long cid, Long courseid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? ");
		if (courseid != 0) {
			queryString.append("and task.jcId=?");
		} else {
			queryString.append("and task.jcId<>?");
		}
		//queryString.append(" order by task.jtSumstate");
		queryString.append(" order by task.thName,task.jtSumstate");
	//	System.out.println("==========" + queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, cid, courseid });
	}
	public List findByYearAndTermAndTKuidAndSchid(String year, short term, Long kuid, Long kdid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kuId=? and task.kdId in (select d.kdId from WkTDept as d where d.kdPid=?) ");
		
		//queryString.append(" order by task.jtSumstate");
		queryString.append(" order by task.thName,task.jtSumstate");
	//	System.out.println("==========" + queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kuid, kdid });
	}
	public List FindTeacheridFromTask(String year, short term, Long kdid) {
		String queryString = "select distinct task.kuId from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? order by task.kuId";
		return getHibernateTemplate().find(queryString,
				new Object[] { year, term, kdid });
	}

	public List findTaskByYearTermThidCid(String year, short term, String thid,
			Long cid, Long courseid) {
		return null;
	}
	public List fidjcids(String cname) {
		String queryString = " from JxCourse as b where  b.jcName=?";
		List list = getHibernateTemplate().find(queryString, new Object[] {cname });
		
		return list ;
	}
	public JxTask FindTasks(List cids,String year, short term, String thname,Long kdid) {
		if (cids != null && cids.size() > 0) {
			StringBuffer queryString = new StringBuffer("from JxTask as jt where jt.jcId in (");
			for (int i = 0; i < cids.size(); i++) {
				JxCourse jcourse=(JxCourse) cids.get(i);
				queryString.append(jcourse.getJcId());
				if (i < cids.size() - 1) {
					queryString.append(",");
				}
			}
			queryString.append(") and jt.jtYear=? and jt.jtTerm=?  and jt.thName=? and jt.kdId=?");
			List list = getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, thname,kdid });
		if(list!=null&&list.size()!=0)
		{
			JxTask task=list != null ? (JxTask)list.get(0) :null;
			return task ;	
		}
		else {
			return null;
		}
		}
		else
		{
			return null;
		}
		
	}
	public List findByYearAndTermAndTeacherlistAndCourseid1(String year, short term, Long cid, List teacherlist, Long courseid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=?" + " and task.kuId in ( ");
		// queryString.append("000030,000031,000031,000033,03029");
		for (int i = 0; i < teacherlist.size(); i++) {
			WkTUser user = (WkTUser) teacherlist.get(i);
			queryString.append(user.getKuId());
			if (i < teacherlist.size() - 1) {
				queryString.append(",");
			}
		}
		if (courseid != 0) {
			queryString.append(" ) and task.jcId=" + courseid);
		} else {
			queryString.append(" ) and task.jcId<>" + courseid);
		}
		
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, cid });
	}
	//public List findTaskByYearTermTidKdid(String year, short term, Long t,
	//		Long kdid) {
	//	return null;
//	}

	public List findTaskByYearTermCidThname(Long jcid, String tname,
			Integer jtsumstate) {
		StringBuffer queryString = new StringBuffer(" from JxTask as task where  task.jcId=? and task.thName=? and task.jtSumstate=?");
		
		return getHibernateTemplate().find(queryString.toString(), new Object[] { jcid, tname,jtsumstate });
	}
	public List<JxTask> FindTaskByYTKJC(String year, short term, Long kdid,  String jtClass, String jtClass1) {
		String queryString = "from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and (task.jtClass=? or task.jtClass=?) and jtSumstate in (select distinct jtSumstate from JxTeachplan)";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid, jtClass, jtClass1 });
	}
	public JxTask skjhfindtask(List cids,String year, short term, String classname,Long kdid) {
		if (cids != null && cids.size() > 0) {
			StringBuffer queryString = new StringBuffer("from JxTask as jt where jt.jcId in (");
			for (int i = 0; i < cids.size(); i++) {
				JxCourse jcourse=(JxCourse) cids.get(i);
				queryString.append(jcourse.getJcId());
				if (i < cids.size() - 1) {
					queryString.append(",");
				}
			}
			queryString.append(") and jt.jtYear=? and jt.jtTerm=?  and jt.jtClass=? and jt.kdId=?");
			List list = getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, classname,kdid });
		if(list!=null&&list.size()!=0)
		{
			JxTask task=list != null ? (JxTask)list.get(0) :null;
			return task ;	
		}
		else {
			return null;
		}
		}
		else
		{
			return null;
		}
	}
	public JxTask skjhfindtask(List cids,String year, short term, String classname,List kdid) {
		JxTask task=new JxTask();
		
			StringBuffer queryString = new StringBuffer("from JxTask as jt where jt.jcId in (");
			for (int i = 0; i < cids.size(); i++) {
				JxCourse jcourse=(JxCourse) cids.get(i);
				queryString.append(jcourse.getJcId());
				if (i < cids.size() - 1) {
					queryString.append(",");
				}
			}
		
			queryString.append(") and jt.jtYear=? and jt.jtTerm=?  and jt.jtClass=? and jt.kdId in ( ");
			for (int i = 0; i < kdid.size(); i++) {
				WkTDept dept = (WkTDept) kdid.get(i);
				queryString.append(dept.getKdId());
				if (i < kdid.size() - 1) {
					queryString.append(",");
				}
			}
			queryString.append(")");
			List list = getHibernateTemplate().find(queryString.toString(),new Object[]{year,term,classname});
			
			if(list!=null&&list.size()!=0)
		{
			 task= (JxTask)list.get(0) ;
				
		}
		return task;
		
	}
	public List<JxTask> skwjfindtask(String year, short term, List kdid){
		StringBuffer queryString = new StringBuffer("select task from JxTask as task ,JxCourse as jc where task.jcId=jc.jcId and  task.jtYear=? and task.jtTerm=?  and task.kuId is not null and task.kdId in(");
		for (int i = 0; i < kdid.size(); i++) {
			WkTDept dept = (WkTDept) kdid.get(i);
			queryString.append(dept.getKdId());
			if (i < kdid.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") order by task.jcId,task.kuId,task.jtSumstate");
		List list = getHibernateTemplate().find(queryString.toString(),new Object[]{year,term});
		if(list!=null&&list.size()!=0)
		{
			return list;
		}
		else{
			return null;
		}
	}
	public List<JxCourse> paperfindcourse (String year, short term, Long kdid,Long kuid){
		String queryString = "select distinct jc from JxTask as task ,JxCourse as jc where task.jcId=jc.jcId and  task.jtYear=? and task.jtTerm=? and task.kdId=? and task.kuId=? ";
		List list=getHibernateTemplate().find(queryString,new Object[]{year,term,kdid,kuid});
		if(list!=null&&list.size()!=0)
		{
			return list;
		}
		else{
			return null;
		}
		
	}
	public List findTaskByYearTermThidCidcname(String year, Short term, Long thid, Long cid,Long kdid,String claname){
		StringBuffer querystring = new StringBuffer("from JxTask as task Where task.jtYear=? and task.jtTerm=? and task.jcId=? and task.kdId=?  and task.kuId=? and task.jtClass=?  ");
		return getHibernateTemplate().find(querystring.toString(), new Object[] { year, term, cid, kdid,thid,claname });
		
	}
	public List<JxCourse> findcourselist(String year, short term,Long kdid,Long kuid){
		String queryString = "select distinct jc from JxTask as task ,JxCourse as jc where task.jcId=jc.jcId and  task.jtYear=? and task.jtTerm=? and task.kdId=? and task.kuId=?  and (jc.jcExperhours<>0 or jc.jcComhours<>0)";
		List list=getHibernateTemplate().find(queryString,new Object[]{year,term,kdid,kuid});
		if(list!=null&&list.size()!=0)
		{
			return list;
		}
		else{
			return null;
		}
		
	}
	public List findtasklist(String year, short term,Long kdid,Long kuid){
		String queryString = "select distinct task.jtSumstate from JxTask as task ,JxCourse as jc where task.jcId=jc.jcId and  task.jtYear=? and task.jtTerm=? and task.kdId=? and task.kuId=? and jc.jcName like '%实验%' order by task.jtSumstate ";
		List list=getHibernateTemplate().find(queryString,new Object[]{year,term,kdid,kuid});
		if(list!=null&&list.size()!=0)
		{
			return list;
		}
		else{
			return null;
		}
	}
	public List findtasklist(String year, short term,Long kdid,Long kuid,Long jcid){
		String queryString = "select distinct task.jtSumstate from JxTask as task ,JxCourse as jc where task.jcId=jc.jcId and task.jcId=? and  task.jtYear=? and task.jtTerm=? and task.kdId=? and task.kuId=?  order by task.jtSumstate ";
		List list=getHibernateTemplate().find(queryString,new Object[]{jcid,year,term,kdid,kuid});
		if(list!=null&&list.size()!=0)
		{
			return list;
		}
		else{
			return null;
		}
	}
	public List<JxTask> findtaskbysumstate(int sumstate){
		String queryString = " from JxTask as task  where  task.jtSumstate=? ";
		List list=getHibernateTemplate().find(queryString,new Object[]{sumstate});
		if(list!=null&&list.size()!=0)
		{
			return list;
		}
		else{
			return null;
		}
	}
	public List<JxCourse> findbyYTK(String year, short term,Long kdid){
		String queryString = "select distinct jc from JxTask as task ,JxCourse as jc where task.jcId=jc.jcId and  task.jtYear=? and task.jtTerm=? and task.kdId=? and jc.jcName like '%实验%'";
		List list=getHibernateTemplate().find(queryString,new Object[]{year,term,kdid});
		if(list!=null&&list.size()!=0)
		{
			return list;
		}
		else{
			return null;
		}
			
	}
	public List<JxCourse> findtaskbyYTKU(String year, short term,Long kuid){
		String queryString = "select distinct jc from JxTask as jt,JxCourse as jc where jt.jcId=jc.jcId and jt.jtYear=? and jt.jtTerm=? and jt.jtClass in (select stu.stClass from Student as stu where stu.kuId=?)and jc.jcName like '%实验%'";
		return getHibernateTemplate().find(queryString, new Object[] { year, term, kuid });
	}

	public JxTask findTaskByClassname(String year, short term, Long cid,
			String classname, Long kdid) {
		String quertString="from JxTask as t where t.jtYear=? and t.jtTerm=? and t.jcId=? and t.jtClass=? and t.kdId=? ";
		return (JxTask)getHibernateTemplate().find(quertString, new Object[]{year,term,cid,classname,kdid}).get(0);
	}
	
	public List findByYearAndTermAndKdidAndVAndClass(String year, short term, Long schid,String type,String claname){
		String quertString="select stu.kuId,stu.stId,xc.clSname,xc.kdId,xc.clGrade from Student as stu, JxTask as t,JxCourse as c ,XyClass as xc where stu.stClass=t.jtClass and  t.jcId=c.jcId and t.jtClass=xc.clSname and t.jtYear=? and t.jtTerm=? and t.kdId in( select d.kdId from WkTDept as d where d.kdSchid=?) and c.jcName like '%"+"软件技术基础设计"+"%'    ";
		 if(type!=null){
			 quertString=quertString+"  and t.jtClass in(select jt.jtClass from JxTask as jt,JxCourse as jc where jt.jcId=jc.jcId  and jt.kdId in( select d.kdId from WkTDept as d where d.kdSchid=?) and jc.jcName like '%"+type+"%' )";
			}else{
				quertString=quertString+" and t.jtClass in(select jt.jtClass from JxTask as jt,JxCourse as jc where jt.jcId=jc.jcId  and jt.kdId in( select d.kdId from WkTDept as d where d.kdSchid=?) and jc.jcName like '%VB%' or c.jcName like '%VC%' or c.jcName like '%VF%' )";
			}
		if(claname!=null&&!claname.equals("-所有班级-")){
			quertString=quertString+" and t.jtClass='"+claname+"'";
		}
		quertString=quertString+" order by  xc.kdId,xc.clGrade,xc.clSname,stu.stId";
		return  getHibernateTemplate().find(quertString, new Object[]{year,term,schid,schid});
	}
}
