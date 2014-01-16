package org.iti.bysj.service.impl;

import java.util.List;

import org.iti.bysj.entity.BsTeacher;
import org.iti.bysj.service.BsTeacherService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class BsTeacherServiceImpl extends BaseServiceImpl implements BsTeacherService {

	public List findByBuid(Long buid) {
		String queryString = "select bs from BsTeacher as bs,WkTUser as wuser where bs.buId=? and wuser.kuId=bs.kuId order by wuser.kdId,wuser.kuName";
		return getHibernateTemplate().find(queryString, buid);
	}

	public List findByBrpid(Long brpid) {
		String queryString = "from BsTeacher as bt where bt.btId in(select btt.btId from BsTgroupId as btt where btt.btpId=?)";
		return getHibernateTemplate().find(queryString, brpid);
	}

	public List findByTitleAndBuid(int title, Long buid) {
		String queryString = "from BsTeacher as bt where bt.buId=? and bt.kuId in (select tea.kuId from Teacher as tea where tea.thTitle like '__" + title + "')";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findByKuidAndGpid(Long kuid, Long gpid) {
		String queryString = "from BsTeacher as bt where bt.kuId=? and bt.buId in(select gu.buId from BsGpunit as gu where gu.bgId=?)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, gpid });
	}

	// 本单位非本学生的同行评阅人列表
	public List findByKuidAndBsid(Long Kuid, Long bsid) {
		String queryString = "from BsTeacher as bt where bt.btId not in(select bp.btId from BsPeerview as bp where bp.kuId =? and bsId=?) and  bt.buId in (select s.buId from BsStudent as s where s.bsId=?) ";
		return getHibernateTemplate().find(queryString, new Object[] { Kuid, bsid, bsid });
	}

	public List findByBuidAndNameAndTno(Long buid, String name, String tno, Long kdid) {
		String queryString = "select bt from BsTeacher as bt,WkTUser as wuser where bt.kuId=wuser.kuId and bt.buId=?";
		Object[] arg;
		if (name != null && name.length() > 0) {
			queryString = queryString + "and bt.kuId in(select u.kuId from WkTUser as u where u.kuName like '%" + name + "%')";
		}

		if (tno != null && tno.length() > 0) {
			queryString = queryString + " and bt.kuId in(select t.kuId from Teacher as t where t.thId like '%" + tno + "%')  order by wuser.kuName";
			arg = new Object[] { buid };
		} else {
			queryString=queryString+" order by wuser.kdId, wuser.kuName";
			arg = new Object[] { buid };
		}

		return getHibernateTemplate().find(queryString, arg);
	}

	public BsTeacher findBybtid(Long btId) {
		String queryString = "select bs from BsTeacher as bs,WkTUser as wuser where bs.btId=? and wuser.kuId=bs.kuId order by wuser.kuName";
		List btlist = getHibernateTemplate().find(queryString, new Object[] { btId });
		if (btlist.size() > 0) {
			return (BsTeacher) btlist.get(0);
		} else {
			return null;
		}
	}

	public BsTeacher findByKuidAndBuid(long kuid, long buid) {
		String queryString = "from BsTeacher as bt where bt.kuId=? and bt.buId=?)";
		List btlist = getHibernateTemplate().find(queryString, new Object[] { kuid, buid });
		if (btlist.size() > 0) {
			return (BsTeacher) btlist.get(0);
		} else {
			return null;
		}
	}

	public List findBsTeacher(Long kuid, Long kdid) {
		String queryString = "from BsTeacher as bt where bt.kuId=? and bt.buId in(select gu.buId from BsGpunit as gu where gu.bgId in (" + "select gp.bgId from BsGproces as gp where gp.kdId=?))";
		return find(queryString, new Object[] { kuid, kdid });
	}

	public List findKuidByBgidAndkdpid(Long bgid, Long kdpid) {
		String queryString="select distinct(bt.kuId) from BsTeacher as bt where bt.btId not in(select s.btId from BsTbstudent as s where s.btbNum=0) and bt.buId  in(select bp.buId from BsGpunit as bp where bp.kdId in(select dept.kdId from WkTDept as dept where kdPid=?) and bp.bgId=?) order by bt.kuId";
		return getHibernateTemplate().find(queryString, new Object[]{kdpid,bgid});
	}

	public List findBybgidandkdidandkuid(Long bgid, Long kdpid, Long kuid) {
		String queryString="from BsTeacher as bt where bt.buId  in(select bp.buId from BsGpunit as bp where bp.kdId in(select dept.kdId from WkTDept as dept where kdPid=?) and bp.bgId=?)and kuId=? order by bt.kuId";
		return getHibernateTemplate().find(queryString, new Object[]{kdpid,bgid,kuid});
	}
	public List findByKuid(Long kuid){
		String queryString = "from BsTeacher as bt where bt.kuId=? ";
		return find(queryString, new Object[] { kuid });
	}
	
	


}
