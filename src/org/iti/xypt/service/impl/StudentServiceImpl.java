package org.iti.xypt.service.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.xypt.entity.Student;
import org.iti.xypt.entity.XyClass;
import org.iti.xypt.entity.XyNUrd;
import org.iti.xypt.service.StudentService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.framework.entity.WkTDept;

public class StudentServiceImpl extends BaseServiceImpl implements StudentService {

	public Student findByKuid(Long kuid) {
		Student stu = (Student) getHibernateTemplate().get("org.iti.xypt.entity.Student", kuid);
		return stu;

	}

	public List findByKdidAndSgrade(Long kdid, Integer sgrade) {
		String queryString = "from Student as stu where stu.kuId in (select user.kuId  from WkTUser as user where user.kdId=?) and stu.stBynf=?";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, sgrade });
	}

	public List findbyXueyuan(Long kdid, Integer stGrade) {
		String queryString = "from Student as stu where stu.kuId in (select user.kuId  from WkTUser as user where user.kdId=? and " + "user.kuId not in (select bst.kuId from BsStudent as bst)) and stu.stBynf=? order by stu.stClass,stu.stId ";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, stGrade });

	}

	public List findBydeplistAndrid(List dlist, Long rid) {
		StringBuffer queryString = new StringBuffer("from Student as stu where stu.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.kdId in(");
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept dept = (WkTDept) dlist.get(i);
			queryString.append(dept.getKdId());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") )order by stu.stClass,stu.stId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List findBydeplistAndrid(List dlist, Long rid, Integer grade) {
		StringBuffer queryString = new StringBuffer("from Student as stu where stu.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.kdId in(");
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept dept = (WkTDept) dlist.get(i);
			queryString.append(dept.getKdId());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") ) ");
		if (grade.intValue() != 0) {
			queryString.append(" and stu.stGrade=? order by stu.stClass,stu.stId");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { grade, rid });
		}
		queryString.append("order by stu.stClass,stu.stId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });
	}

	public List findBydeplistAndrid(List dlist, Long rid, Integer grade, String tname, String tno) {
		StringBuffer queryString = new StringBuffer("select stu from Student as stu ,WkTDept as wdept  where stu.kuId in(select u.kuId from WkTUser as u where" + " u.kuName like '%" + tname + "%')");
		if (tno != null && tno.length() > 0) {
			queryString.append(" and stu.stId like '%" + tno + "%'");
		}
		queryString.append(" and stu.kuId in(select role.id.kuId " + "from XyUserrole as role where role.id.krId=? and role.kdId=wdept.kdId and role.kdId in(");
		for (int i = 0; i < dlist.size(); i++) {
			WkTDept dept = (WkTDept) dlist.get(i);
			queryString.append(dept.getKdId());
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		if (grade.intValue() != 0) {
			queryString.append(") ) and stu.stGrade=? order by wdept.kdNumber,stu.stClass,stu.stId");
			return getHibernateTemplate().find(queryString.toString(), new Object[] { rid, grade });
		}else{
		queryString.append(") )order by wdept.kdNumber,stu.stClass,stu.stId");
		//System.out.println("----"+queryString);
		return getHibernateTemplate().find(queryString.toString(), new Object[] { rid });}
	}

	public List findByClass(String stClass) {
		String queryString = "from Student as stu where stu.stNormal=0 and  (stu.stClass=? or stu.stClass in (select cla.clSname from XyClass as cla where cla.clName=?))";
		return getHibernateTemplate().find(queryString, new Object[] { stClass, stClass });
	}
	public List findByClass(List clist) {
		StringBuffer queryString = new StringBuffer("from Student as st where st.clId in (");
		for (int i = 0; i < clist.size(); i++) {
			Long clid = (Long) clist.get(i);
			queryString.append(clid);
			if (i < clist.size() - 1) {
				queryString.append(",");
			}

		}
		queryString.append(") order by st.clId,st.stId");
		return getHibernateTemplate().find(queryString.toString());
	}

	public List findSnameByStid(String stId) {
		String queryString = "from WkTUser as u where u.kuId in (select stu.kuId from Student as stu where stu.stId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { stId });
	}

	public Student findClassByStid(String stId) {
		String queryString = "from Student as stu where stu.stId=?";
		return (Student) getHibernateTemplate().find(queryString, new Object[] { stId }).get(0);
	}

	public List findGrade() {
		String queryString = "select distinct stu.stGrade from Student as stu)";
		return getHibernateTemplate().find(queryString, new Object[] {});
	}

	public Long countByClass(Long clid) {
		String query = "select count(*) from Student as st where st.clId=?";
		return (Long) find(query, new Object[] { clid }).get(0);
	}

	public List findBySid(String sid) {
		String query = "from Student as st where st.stId=?";
		return find(query, new Object[] { sid });
	}

	public void updateStudentClass(XyClass xyClass) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String updateStudent = "update Student as st set st.stGrade=?,st.stClass=? where st.clId=?";
		Query dUserRole = session.createQuery(updateStudent);
		dUserRole.setInteger(0, xyClass.getClGrade());
		dUserRole.setString(1, xyClass.getClSname());
		dUserRole.setLong(2, xyClass.getClId());
		dUserRole.executeUpdate();
	}

	public List findStuByClid(Long clid) {
		if (clid != null) {
			String queryString = "from Student as st Where st.clId=? and st.stNormal=0 order by st.stId ";
			return getHibernateTemplate().find(queryString, new Object[] { clid });
		} else {
			return null;
		}
	}

	public List findClassByBsid(Long bsid) {
		String query = "from Student as stu where stu.kuId in (select bs.kuId from BsStudent as bs where bs.bsId=?)";
		return find(query, new Object[] { bsid });
	}

	public List findMajorByBsid(String claname) {
		String query = "from WkTDept as dept where dept.kdId in (select cla.kdId from XyClass as cla where cla.clName=? or cla.clSname=?)";
		return find(query, new Object[] { claname, claname });
	}

	public List findByKdidAndSbynf(Long kdid, Integer stBynf) {
		String queryString = "from Student as stu where stu.kuId in (select user.kuId  from WkTUser as user where user.kdId=?) and stu.stBynf=?";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, stBynf });
	}

	public void update(Student student) {
		String query = "update XyNUrd as xnurd set xnurd.id.kdId=? where xnurd.id.kuId=? and xnurd.id.xnuType=?";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query updateNRQ = session.createQuery(query);
		updateNRQ.setLong(0, student.getClId());
		updateNRQ.setLong(1, student.getKuId());
		updateNRQ.setLong(2, XyNUrd.TYPE_CLID);
		updateNRQ.executeUpdate();
		getHibernateTemplate().update(student);
	}

	public List findStuByKdId(Long kdid) {
		String queryString = "from Student as s where s.clId in (select c.clId from XyClass as c where c.kdId=? or c.kdId in (select d.kdId from WkTDept as d where d.kdPid=?))";
		return getHibernateTemplate().find(queryString, new Object[] { kdid, kdid });
	}

	public List findNoMxStu(Long kdid,String year,Short term) {	
		String queryString = "from Student as s where s.clId in (select c.clId from XyClass as c where c.kdId=? or c.kdId in (select d.kdId from WkTDept as d where d.kdPid=?)) and s.kuId not in(select cn.kuId from CqNstudent as cn where cn.xlId in(select c.id from JxCalendar as c where c.CYear=? and c.term=?))";
		return  getHibernateTemplate().find(queryString, new Object[] { kdid, kdid,year,term });
	}

	public List findStuByYearAndTermAndSchidAndKdidAndGradeAndClassAndSidAndName(
			String year, Short term, Long schid,Long kdid, Integer grade, String cla,
			String sid, String sname) {
		StringBuffer queryString=new StringBuffer("from Student as s where s.stNormal=0 and s.kuId not in(select cn.kuId from CqNstudent as cn where cn.xlId in(select c.id from JxCalendar as c where c.CYear=? and c.term=? and c.kdId=?))  and s.clId in (select c.clId from XyClass as c where (c.kdId=? or c.kdId in (select d.kdId from WkTDept as d where d.kdPid=? or d.kdSchid=?))");
	    if(grade!=0){
	    	queryString.append(" and c.clGrade="+grade+" )");
	    }else{
	    	queryString.append(" )");
	    }
	    if(cla!=null&&!cla.equals("-ÇëÑ¡Ôñ-")&&!cla.equals("")){
	    	queryString.append(" and s.stClass = '"+cla+"' ");
	    }
	    if(sid!=null&&!sid.equals("")){
	    	queryString.append(" and s.stId like '%" + sid+ "%'");
	    }
	    if(sname!=null&&!sname.equals("")){
	    	queryString.append(" and s.kuId in( select u.kuId from WkTUser as u where u.kuName like '"+sname+"')");
	  
	    }
	    return getHibernateTemplate().find(queryString.toString(), new Object[]{year,term,schid,kdid,kdid,kdid});
	}

	public List findStuByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndName(
			String year, Short term, Long kdid, Long xyid, Long zyid,
			Integer grade, String cla, String sid, String sname) {
		StringBuffer queryString=new StringBuffer("from Student as s where s.stNormal=0 and s.clId in(select c.clId from XyClass as c where c.kdId in(select d.kdId from WkTDept as d where d.kdSchid="+kdid+" ) ) and s.kuId not in(select cn.kuId from CqNstudent as cn where cn.xlId in(select c.id from JxCalendar as c where c.CYear=? and c.term=? and c.kdId=?)) ");
		if(xyid!=null&&xyid!=0L){
			queryString.append(" and s.kuId in(select u.kuId from WkTUser as u where u.kdId in(select d.kdId from WkTDept as d where d.kdPid="+xyid+"))");
		}
		if(zyid!=null&&zyid!=0L){
			queryString.append(" and s.clId in (select c.clId from XyClass as c where c.kdId="+zyid+")");
		}
		if(grade!=null&&grade!=0){
	    	queryString.append(" and s.stGrade="+grade+" ");
	    }
	    if(cla!=null&&!cla.equals("-ÇëÑ¡Ôñ-")&&!cla.equals("")){
	    	queryString.append(" and s.stClass = '"+cla+"'");
	    }
	    if(sid!=null&&!sid.equals("")){
	    	queryString.append(" and s.stId like '%" + sid+ "%'");
	    }
	    if(sname!=null&&!sname.equals("")){
	    	queryString.append(" and s.kuId in( select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')");
	  
	    }
	    queryString.append(" order by s.stClass,s.stId ");
	    return getHibernateTemplate().find(queryString.toString(), new Object[]{year,term,kdid});
	}
	public List findPageByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndName(String year, Short term, Long kdid, Long xyid, Long zyid,
			Integer grade, String cla, String sid, String sname,int fromdata,int todata) {
		StringBuffer queryString=new StringBuffer("from Student as s where s.stNormal=0 and s.clId in(select c.clId from XyClass as c where c.kdId in(select d.kdId from WkTDept as d where d.kdSchid="+kdid+" ) ) and s.kuId not in(select cn.kuId from CqNstudent as cn where cn.xlId in(select c.id from JxCalendar as c where c.CYear="+year+" and c.term="+term+" and c.kdId="+kdid+")) ");
		if(xyid!=null&&xyid!=0L){
			queryString.append(" and s.kuId in(select u.kuId from WkTUser as u where u.kdId in(select d.kdId from WkTDept as d where d.kdPid="+xyid+"))");
		}
		if(zyid!=null&&zyid!=0L){
			queryString.append(" and s.clId in (select c.clId from XyClass as c where c.kdId="+zyid+")");
		}
		if(grade!=null&&grade!=0){
	    	queryString.append(" and s.stGrade="+grade+" ");
	    }
	    if(cla!=null&&!cla.equals("-ÇëÑ¡Ôñ-")&&!cla.equals("")){
	    	queryString.append(" and s.stClass ='"+cla+"' ");
	    }
	    if(sid!=null&&!sid.equals("")){
	    	queryString.append(" and s.stId like '%" + sid+ "%'");
	    }
	    if(sname!=null&&!sname.equals("")){
	    	queryString.append(" and s.kuId in( select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')");
	  
	    }
	    queryString.append(" order by s.stClass,s.stId");
	    Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	    s.beginTransaction();
        Query q = s.createQuery(queryString.toString());
        q.setFirstResult(fromdata);
        q.setMaxResults(todata);
        List stulist = q.list();
        s.getTransaction().commit();
        return stulist;
		
	}
	public List findByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndName(
			String year, Short term, Long kdid, Long xyid, Long zyid,
			Integer grade, String cla, String sid, String sname) {
		StringBuffer queryString=new StringBuffer("from Student as s where s.stNormal=0 and s.clId in(select c.clId from XyClass as c where c.kdId in(select d.kdId from WkTDept as d where d.kdSchid="+kdid+" ) )and s.kuId not in(select cn.kuId from CqNstudent as cn where cn.xlId in(select c.id from JxCalendar as c where c.CYear=? and c.term=? and c.kdId=?)) ");
		if(xyid!=null&&xyid!=0L){
			queryString.append(" and s.kuId in(select u.kuId from WkTUser as u where u.kdId in(select d.kdId from WkTDept as d where d.kdPid="+xyid+"))");
		}
		if(zyid!=null&&zyid!=0L){
			queryString.append(" and s.clId in (select c.clId from XyClass as c where c.kdId="+zyid+")");
		}
		if(grade!=null&&grade!=0){
	    	queryString.append(" and s.stGrade="+grade+" ");
	    }
	    if(cla!=null&&!cla.equals("-ÇëÑ¡Ôñ-")&&!cla.equals("")){
	    	queryString.append(" and s.stClass = '"+cla+"' ");
	    }
	    if(sid!=null&&!sid.equals("")){
	    	queryString.append(" and s.stId like '%" + sid+ "%'");
	    }
	    if(sname!=null&&!sname.equals("")){
	    	queryString.append(" and s.kuId in( select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')");
	  
	    }
	    queryString.append(" order by s.clId,s.stId");
	    return getHibernateTemplate().find(queryString.toString(), new Object[]{year,term,kdid});
	}

	public List findByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndNameFDY(
			String year, Short term, Long kdid, Long xyid, Long zyid,
			Integer grade, String cla, String sid, String sname, Long kuid) {
		StringBuffer queryString=new StringBuffer("from Student as s where s.stNormal=0 and s.clId in(select c.clId from XyClass as c where c.kdId in(select d.kdId from WkTDept as d where d.kdSchid="+kdid+" ) ) and s.clId in(select f.id.clId from XyFudao as f where f.id.kuId ="+kuid+" )and s.kuId not in(select cn.kuId from CqNstudent as cn where cn.xlId in(select c.id from JxCalendar as c where c.CYear=? and c.term=? and c.kdId=?)) ");
		if(xyid!=null&&xyid!=0L){
			queryString.append(" and s.kuId in(select u.kuId from WkTUser as u where u.kdId in(select d.kdId from WkTDept as d where d.kdPid="+xyid+"))");
		}
		if(zyid!=null&&zyid!=0L){
			queryString.append(" and s.clId in (select c.clId from XyClass as c where c.kdId="+zyid+")");
		}
		if(grade!=null&&grade!=0){
	    	queryString.append(" and s.stGrade="+grade+" ");
	    }
	    if(cla!=null&&!cla.equals("-ÇëÑ¡Ôñ-")&&!cla.equals("")){
	    	queryString.append(" and s.stClass = '"+cla+"' ");
	    }
	    if(sid!=null&&!sid.equals("")){
	    	queryString.append(" and s.stId like '%" + sid+ "%'");
	    }
	    if(sname!=null&&!sname.equals("")){
	    	queryString.append(" and s.kuId in( select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')");
	  
	    }
	    queryString.append(" order by s.clId,s.stId");
	    return getHibernateTemplate().find(queryString.toString(), new Object[]{year,term,kdid});
	}

	public List findHgStuInStulist(Long xlid,List stulist,Long small,Long big,Long small1,Long big1) {
		StringBuffer queryString=new StringBuffer("from Student as stu where stu.kuId in(");
		for(int i=0;i<stulist.size();i++){
			Student student=(Student)stulist.get(i);
			queryString.append(student.getKuId());
			if(i<stulist.size()-1){
				queryString.append(",");
			}
		}
		queryString.append(") and (select count(distinct cz.czTime) from CqZccq as cz where cz.kuId=stu.kuId and cz.xlId="+xlid+")>=? and (select count(distinct cz.czTime) from CqZccq as cz where cz.kuId=stu.kuId and cz.xlId="+xlid+")<=? and (select count(distinct ck.ckStart) from CqKyhdcq as ck where ck.kuId=stu.kuId and ck.xlId="+xlid+")>=? and (select count(distinct ck.ckStart) from CqKyhdcq as ck where ck.kuId=stu.kuId and ck.xlId="+xlid+")<=?");
		return getHibernateTemplate().find(queryString.toString(), new Object[]{small,big,small1,big1});
	}
	
	public List heyafindstu(Long  cid)
	{
		String queryString = "from Student as st where st.clId =?  order by st.clId,st.stId";
		return  getHibernateTemplate().find(queryString, new Object[] { cid});
		
		
	}

	public List findBysclassAndRzidAndTypeNotInKq(String sclass, Long rzid,Short type) {
		String queryString = "from Student as st where st.stClass =?  and st.stId not in (select kq.jkqSid from JxStukqjl as kq where kq.jkqJxrzid=? and kq.jkqRztype=?)";
		return  getHibernateTemplate().find(queryString, new Object[] { sclass,rzid,type});
		
		
	}

	public List findByclasslist(List classlist) {
		StringBuffer queryString = new StringBuffer("from Student as st where st.stClass in (");
		for (int i = 0; i < classlist.size(); i++) {
			queryString.append("'");
			queryString.append(classlist.get(i));
			queryString.append("'");
			if (i < classlist.size() - 1) {
				queryString.append(",");
			}

		}
		queryString.append(") and st.stNormal=0 order by st.clId,st.stId");
		
		return getHibernateTemplate().find(queryString.toString());
	}

	public List findByClassandNameandXno(List classlist, String xname,String xsno) {
		StringBuffer queryString = new StringBuffer("from Student as stu where stu.stId like '%" + xsno + "%'  and stu.kuId in (select u.kuId from WkTUser as u where " + "u.kuName like '%" + xname + "%' ) and stu.stClass in (");
		for (int i = 0; i < classlist.size(); i++) {
			queryString.append("'");
			queryString.append(classlist.get(i));
			queryString.append("'");
			if (i < classlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") order by stu.clId,stu.stId");
		
		return  getHibernateTemplate().find(queryString.toString());
	}

	public List findByClassandNameandXno(String classname, String xname,
			String xsno) {
		StringBuffer queryString = new StringBuffer("from Student as stu where stu.stId like '%" + xsno + "%'  and stu.kuId in (select u.kuId from WkTUser as u where " + "u.kuName like '%" + xname + "%' )and stu.stClass =?");
		queryString.append(") order by stu.clId,stu.stId");
		//System.out.println("----"+queryString);
		return  getHibernateTemplate().find(queryString.toString(), new Object[] { classname});
	}
	public List findByCidandSidandSname(Long clid,String sid,String sname){
		StringBuffer queryString = new StringBuffer("from Student as stu where stu.clId=?");
		if(!"".equals(sid)){
			queryString.append(" and stu.stId like '%" + sid + "%' ");	
		}
		if(!"".equals(sname)){
			queryString.append(" and stu.kuId in (select u.kuId from WkTUser as u where " + "u.kuName like '%" + sname + "%' ) ");	
		}
		queryString.append(" order by stu.clId,stu.stId");
		return  getHibernateTemplate().find(queryString.toString(), new Object[] { clid});
		
	}

	public List findBycaidAndClidAndTypeNotInXszc(Long clid, Long caid,
			Short type) {
		String queryString = "from Student as st where st.clId =?  and ((st.kuId in (select xszc.kuId from Xszc as xszc where xszc.caId=? and xszc.xsNormal=?)) or (st.kuId not in (select xszc.kuId from Xszc as xszc where xszc.caId=? ))) order by st.stId ";
		return  getHibernateTemplate().find(queryString, new Object[] { clid,caid,type,caid});
		
		
	}

	public List findXyAllStudent(Long kdid) {
		
		String queryString = "from Student as ss where ss.clId in (select xycla.clId from XyClass as xycla where xycla.kdId=? and xycla.clId in (select stu.clId from Student as stu)  or xycla.kdId in (select dept.kdId from WkTDept as dept where dept.kdPid=? or dept.kdPid in (select dept.kdId from WkTDept as dept where dept.kdPid=? )))";
		return getHibernateTemplate().find(queryString, new Object[] { kdid,kdid,kdid });
	}
	public List findByFDYKuidAndSid(Long kuid,Long sid,String sname,String sno){
		String queryString = " from Student as st where st.clId in( select xc.clId from XyClass as xc where xc.clId in(select xf.id.clId from XyFudao as xf where xf.id.kuId=? ) and xc.kdId in( select kdId from WkTDept as d where d.kdSchid=?))";
		  if(sname!=null ){
		    	queryString=queryString+" and st.kuId in( select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')";
		  
		    }
		if(sno!=null ){
			 queryString=queryString+" and st.stId like '%" + sno+ "%'";
		    }
		  
		return getHibernateTemplate().find(queryString, new Object[] { kuid,sid});
	}
	public List findByFDYKuidAndSidAndGrade(Long kuid,Long sid,Integer grade,String sname,String sno){
		String queryString = " from Student as st where st.clId in( select xc.clId from XyClass as xc where xc.clId in(select xf.id.clId from XyFudao as xf where xf.id.kuId=? ) and xc.kdId in( select kdId from WkTDept as d where d.kdSchid=?) and xc.clGrade=?) ";

		if(sname!=null ){
		    	queryString=queryString+" and st.kuId in( select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')";
		  
		    }
		if(sno!=null ){
			 queryString=queryString+" and st.stId like '%" + sno+ "%'";
		    }
		  
		return getHibernateTemplate().find(queryString, new Object[] { kuid,sid,grade});
	}

	public WkTDept finddeptByStudentno(String number) {
		String queryString = "from WkTDept as wd where wd.kdId in (select xc.kdId from XyClass as xc where xc.clId in ("
				+ "select st.clId from Student as st where st.stId=? ) )";
		return (WkTDept) getHibernateTemplate().find(queryString,
				new Object[] { number }).get(0);
	}
	
	   public List findPageByYearAndTermAndXyidAndZyidAndGradeAndClassAndSidAndNameFDY(
			String year, Short term, Long kdid, Long xyid, Long zyid,
			Integer grade, String cla, String sid, String sname, Long kuid,
			int rowNumber, int dataCount) {
		   
		   StringBuffer queryString=new StringBuffer("from Student as s where s.stNormal=0 and s.clId in(select c.clId from XyClass as c where c.kdId in(select d.kdId from WkTDept as d where d.kdSchid="+kdid+" ) ) and s.clId in(select f.id.clId from XyFudao as f where f.id.kuId ="+kuid+" )and s.kuId not in(select cn.kuId from CqNstudent as cn where cn.xlId in(select c.id from JxCalendar as c where c.CYear="+year+" and c.term="+term+" and c.kdId="+kdid+")) ");
			if(xyid!=null&&xyid!=0L){
				queryString.append(" and s.kuId in(select u.kuId from WkTUser as u where u.kdId in(select d.kdId from WkTDept as d where d.kdPid="+xyid+"))");
			}
			if(zyid!=null&&zyid!=0L){
				queryString.append(" and s.clId in (select c.clId from XyClass as c where c.kdId="+zyid+")");
			}
			if(grade!=null&&grade!=0){
		    	queryString.append(" and s.stGrade="+grade+" ");
		    }
		    if(cla!=null&&!cla.equals("-ÇëÑ¡Ôñ-")&&!cla.equals("")){
		    	queryString.append(" and s.stClass = '"+cla+"' ");
		    }
		    if(sid!=null&&!sid.equals("")){
		    	queryString.append(" and s.stId like '%" + sid+ "%'");
		    }
		    if(sname!=null&&!sname.equals("")){
		    	queryString.append(" and s.kuId in( select u.kuId from WkTUser as u where u.kuName like '%"+sname+"%')");
		  
		    }
		    queryString.append(" order by s.stGrade");
		    Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
		    s.beginTransaction();
	        Query q = s.createQuery(queryString.toString());
	        q.setFirstResult(rowNumber);
	        q.setMaxResults(dataCount);
	        List resUsers = q.list();
	        s.getTransaction().commit();
	        return resUsers;
	}
	   public List findNdsStuByYearAndTermAndSchidAndKdidAndGradeAndClassAndSidAndName(
				String year, Short term, Long schid,Long kdid, Integer grade, String cla,
				String sid, String sname ,Date dsdate) {
		    Long time=dsdate.getTime();
			
			StringBuffer queryString=new StringBuffer("from Student as s where s.stNormal=0 and s.kuId not in(select cn.kuId from CqDstudent as cn where cn.dsDate="+time+" and cn.xlId in(select c.id from JxCalendar as c where c.CYear=? and c.term=? and c.kdId=?))  and s.clId in (select c.clId from XyClass as c where (c.kdId=? or c.kdId in (select d.kdId from WkTDept as d where d.kdPid=? or d.kdSchid=?))");
		    if(grade!=0){
		    	queryString.append(" and c.clGrade="+grade+" )");
		    }else{
		    	queryString.append(" )");
		    }
		    if(cla!=null&&!cla.equals("-ÇëÑ¡Ôñ-")&&!cla.equals("")){
		    	queryString.append(" and s.stClass = '"+cla+"' ");
		    }
		    if(sid!=null&&!sid.equals("")){
		    	queryString.append(" and s.stId like '%" + sid+ "%'");
		    }
		    if(sname!=null&&!sname.equals("")){
		    	queryString.append(" and s.kuId in( select u.kuId from WkTUser as u where u.kuName like '"+sname+"')");
		  
		    }
		    return getHibernateTemplate().find(queryString.toString(), new Object[]{year,term,schid,kdid,kdid,kdid});
		}

	 
}
