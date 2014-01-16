package org.iti.bysj.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.iti.bysj.entity.BsFscore;
import org.iti.bysj.entity.BsReplypacket;
import org.iti.bysj.entity.BsStudent;
import org.iti.bysj.service.BsStudentService;
import org.iti.xypt.entity.Student;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class BsStudentServiceImpl extends BaseServiceImpl implements BsStudentService {

	public List findBbIdNotInDbChoose(Long bbid) {
		String queryString = "from BsStudent as stu where stu.bbId=? and stu.bsId not in(select cho.bsId from BsDbchoose as cho where cho.bdbIfaccept=1) and stu.kuId not in (select bp.bprUid from BsProject as bp where bp.bprState=2) and stu.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { bbid });
	}

	public List findBuIdNotInDbChoose(Long buid) {
		String queryString = "from BsStudent as stu where stu.buId=? and stu.bsId not in(select cho.bsId from BsDbchoose as cho where cho.bdbIfaccept=1) and stu.kuId not in (select bp.bprUid from BsProject as bp where bp.bprState=2) and stu.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findByBbId(Long BbId) {
		String queryString = "from BsStudent as stu where stu.bbId=? and stu.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { BbId });
	}

	public List findByBsidAndBstispasstwo(Long bsid, short ispasstwo) {
		String queryString = "from BsStudent as stu where stu.buId=? and stu.bstIspasstwo=? and stu.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { bsid, ispasstwo });
	}

	public List findByBuid(Long buid) {
		String queryString = " select stu from BsStudent as stu ,Student as stunew where stu.buId=? and stu.kuId=stunew.kuId and stunew.stNormal=0 order by stunew.stClass,stunew.stId";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findByBuidAndBstispassone(Long buid, short ispassone) {
		String queryString = "from BsStudent as bs where bs.buId=? and bs.bstIspassone=? and bs.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ispassone });
	}

	public List findByBuidAndBstispasstwo(Long buid, short ispasstwo) {
		String queryString = "from BsStudent as bs where bs.buId=? and bs.bstIspasstwo=? and bs.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ispasstwo });
	}

	public List findByBrpid(Long brpid) {
		String queryString = "from BsStudent as bs where bs.bsId in (select bss.bsId from BsSgroupId as bss where bss.brpId=?) and bs.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { brpid });
	}

	public BsStudent findByKuidAndBuid(Long kuid, Long buid) {
		String queryString = "from BsStudent as bs where bs.kuId=? and bs.buId=? and bs.stNormal=0";
		List bslist = getHibernateTemplate().find(queryString, new Object[] { kuid, buid });
		if (bslist.size() > 0) {
			return (BsStudent) bslist.get(0);
		} else {
			return null;
		}
	}

	public BsStudent findByKuidAndBbid(Long kuid, Long bbid) {
		String queryString = "from BsStudent as bs where bs.kuId=? and bs.bbId=? and bs.stNormal=0";
		List bslist = getHibernateTemplate().find(queryString, new Object[] { kuid, bbid });
		if (bslist.size() > 0) {
			return (BsStudent) bslist.get(0);
		} else {
			return null;
		}
	}

	public List findByBuidAndNameAndstid(Long buid, String name, String tno) {
		String queryString = "select bt from BsStudent as bt,Student as stu,WkTUser as wuser where stu.kuId=bt.kuId and wuser.kuId=bt.kuId and bt.buId=?  and bt.stNormal=0";
		Object[] arg;
		if (name != null && name.length() > 0) {
			queryString = queryString + "and bt.kuId in(select u.kuId from WkTUser as u where u.kuName like '%" + name + "%')";
		}

		if (tno != null && tno.length() > 0) {
			queryString = queryString + " and bt.kuId in(select t.kuId from Student as t where t.stId like '%" + tno + "%') ";

		}
		queryString=queryString+" order by wuser.kdId,stu.stClass,stu.stId";
		arg = new Object[] { buid };

		return getHibernateTemplate().find(queryString, arg);
	}

	public BsStudent findByBsid(Long bsid) {
		String queryString = "from BsStudent as bs where bs.bsId=?";
		List bslist = getHibernateTemplate().find(queryString, new Object[] { bsid });
		if (bslist.size() > 0) {
			return (BsStudent) bslist.get(0);
		} else {
			return null;
		}
	}

	public List findNoBatchByBuId(Long buId) {
		String queryString = "from BsStudent as stu where stu.buId=? and stu.bbId=0  and stu.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buId });
	}

	public List findByBbidAndNameAndStidNotInDbchoose(Long bbid, String name, String stid) {
		String queryString = "from BsStudent as stu where stu.bbId=? and stu.bsId not in(select cho.bsId from BsDbchoose as cho  where cho.bdbIfaccept=1) and stu.kuId not in (select bp.bprUid from BsProject as bp where bp.bprState=2)  and stu.stNormal=0";
		Object[] arg;
		if (name != null && name.length() > 0) {
			queryString = queryString + "and stu.kuId in(select u.kuId from WkTUser as u where u.kuName like '%" + name + "%')";
		}

		if (stid != null && stid.length() > 0) {
			queryString = queryString + " and stu.kuId in(select t.kuId from Student as t where t.stId =" + stid + ") ";

		}

		arg = new Object[] { bbid };

		return getHibernateTemplate().find(queryString, arg);
	}

	public List findByBuidAndNameAndStidNotInDbchoose(Long buid, String name, String stid) {
		String queryString = "from BsStudent as stu where stu.buId=? and stu.bsId not in(select cho.bsId from BsDbchoose as cho  where cho.bdbIfaccept=1) and stu.kuId not in (select bp.bprUid from BsProject as bp where bp.bprState=2)  and stu.stNormal=0";
		Object[] arg;
		if (name != null && name.length() > 0) {
			queryString = queryString + "and stu.kuId in(select u.kuId from WkTUser as u where u.kuName like '%" + name + "%')";
		}

		if (stid != null && stid.length() > 0) {
			queryString = queryString + " and stu.kuId in(select t.kuId from Student as t where t.stId = " + stid + ") ";
		}
		arg = new Object[] { buid };
		return getHibernateTemplate().find(queryString, arg);
	}

	public List findByBuidAndNameAndStidNotInDb(Long buid, String name, String stid) {
		String queryString = "select stu from BsStudent as stu,Student as stunew  where stu.kuId=stunew.kuId and stu.buId=? and stu.bsId not in(select cho.bsId from BsDbchoose as cho ) and stu.kuId not in (select bp.bprUid from BsProject as bp where bp.bprState=2)  and stu.stNormal=0 ";
		Object[] arg;
		if (name != null && name.length() > 0) {
			queryString = queryString + "and stu.kuId in(select u.kuId from WkTUser as u where u.kuName like '%" + name + "%')";
		}

		if (stid != null && stid.length() > 0) {
			queryString = queryString + " and stu.kuId in(select t.kuId from Student as t where t.stId = " + stid + ")order by stunew.stClass,stunew.stId ";
		}else{
			queryString=queryString+" order by stunew.stClass,stunew.stId";
		}
		arg = new Object[] { buid };
		return getHibernateTemplate().find(queryString, arg);
	}

	public List findByBbidAndNameAndStidNotInDb(Long bbid, String name, String stid) {
		String queryString = "select stu from BsStudent as stu ,Student as stunew where stunew.kuId=stu.kuId and stu.bbId=? and stu.bsId not in(select cho.bsId from BsDbchoose as cho ) and stu.kuId not in (select bp.bprUid from BsProject as bp where bp.bprState=2)  and stu.stNormal=0";
		Object[] arg;
		if (name != null && name.length() > 0) {
			queryString = queryString + "and stu.kuId in(select u.kuId from WkTUser as u where u.kuName like '%" + name + "%')";
		}

		if (stid != null && stid.length() > 0) {
			queryString = queryString + " and stu.kuId in(select t.kuId from Student as t where t.stId =" + stid + ") ";

		}
		queryString=queryString+" order by stunew.stClass,stunew.stId";
		arg = new Object[] { bbid };

		return getHibernateTemplate().find(queryString, arg);
	}

	public void updateStuPassone(long buid) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String update = "update BsStudent as bs set bs.bstIspassone=1 where bs.buId=? and bs.bsId in (select db.bsId from BsDbchoose as db where db.bdbIfaccept=1 and db.bdbId not in (select bd.bdbId from BsDelayreply as bd where bd.bdrDbcs=1))  and bs.stNormal=0";
		Query eupdate = session.createQuery(update);
		eupdate.setLong(0, buid);
		eupdate.executeUpdate();
	}

	public List findBuIdNotInSgroup(Long buid, Short ispasstwo) {
		String queryString = "from BsStudent as bs where bs.buId=? and bs.bstIspasstwo=? and bs.bsId not in (select sg.id.bsId from BsSgroup as sg where sg.id.brpId in (select rp.brpId from BsReplypacket as rp where rp.buId=? and rp.brpRbatch="
				+ BsReplypacket.BRP_RBATCH_TWO + "))  and bs.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ispasstwo, buid });
	}

	public List findBuIdNotDbcj(Long buid) {
		String queryString = "from BsStudent as bs where bs.buId=? and bs.bstIspassone =" + BsStudent.PASSONE_JXMS + "  and bs.bsId not in (select fs.bsId from BsFscore as fs where fs.bfsDbedit=" + BsFscore.DBEDIT_TJ + ")  and bs.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findBuIdNotTwoDbcj(Long buid) {
		String queryString = "from BsStudent as bs where bs.buId=? and bs.bstIspasstwo =" + BsStudent.PASSTWO_JXMS + "  and bs.bsId not in (select fs.bsId from BsFscore as fs where fs.bfsTwodbedit=" + BsFscore.TWODBEDIT_TJ + ")  and bs.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findBuIdDbcj(Long buid) {
		String queryString = "from BsStudent as bs where bs.buId=? and bs.bstIspassone =" + BsStudent.PASSONE_JXMS + "  and bs.bsId  in (select fs.bsId from BsFscore as fs where fs.bfsDbedit=" + BsFscore.DBEDIT_TJ + ")  and bs.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findBuIdTwoDbcj(Long buid) {
		String queryString = "from BsStudent as bs where bs.buId=? and bs.bstIspassone=" + BsStudent.PASSONE_NONE + " and bs.bstIspasstwo =" + BsStudent.PASSTWO_JXMS + "  and bs.bsId  in (select fs.bsId from BsFscore as fs where fs.bfsTwodbedit="
				+ BsFscore.TWODBEDIT_TJ + ")  and bs.stNormal=0";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findBySidOrName(String sid, String name, Long buId) {
		StringBuffer queryString = new StringBuffer("from BsStudent as bs where bs.kuId in (select stu.kuId from Student as stu where ");
		if (sid.equals("") || sid.equals(null))
			queryString.append("stu.stId<>?) and bs.kuId in (select u.kuId from WkTUser as u where u.kuName like '%");
		else
			queryString.append("stu.stId=?) and bs.kuId in (select u.kuId from WkTUser as u where u.kuName like '");
		if (name.equals("") || name.equals(null))
			queryString.append("%%') and bs.buId=? and bs.bbId=0 and bs.stNormal=0");
		else
			queryString.append(name + "%') and bs.buId=? and bs.bbId=0 and bs.stNormal=0");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { sid, buId });
	}

	public List findByKuid(Long kuid) {
		String queryString = "from BsStudent as bs where bs.kuId = ?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public List findBySidorName(String sid, String name, Long buId, Short ispassone) {
		StringBuffer queryString = new StringBuffer("from BsStudent as bs where bs.buId=? and bs.bstIspassone=? and bs.kuId in (select stu.kuId from Student as stu where ");
		if (sid.equals("") || sid.equals(null))
			queryString.append("stu.stId<>?) and bs.kuId in (select u.kuId from WkTUser as u where u.kuName like '%");
		else
			queryString.append("stu.stId=?) and bs.kuId in (select u.kuId from WkTUser as u where u.kuName like '");
		if (name.equals("") || name.equals(null))
			queryString.append("%%') and bs.stNormal=0 and bs.bsId not in (select sg.id.bsId from BsSgroup as sg where sg.id.brpId in (select reply.brpId from BsReplypacket as reply where reply.buId=? and reply.brpRbatch=1))");
		else
			queryString.append(name + "%') and bs.stNormal=0 and bs.bsId not in (select sg.id.bsId from BsSgroup as sg where sg.id.brpId in (select reply.brpId from BsReplypacket as reply where reply.buId=? and reply.brpRbatch=1))");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { buId, ispassone, sid, buId });
	}

	public List findBysidorname(String sid, String name, Long buId, Short ispassone) {
		StringBuffer queryString = new StringBuffer("from BsStudent as bs where bs.buId=? and bs.bstIspassone=? and bs.kuId in (select stu.kuId from Student as stu where ");
		if (sid.equals("") || sid.equals(null))
			queryString.append("stu.stId<>?) and bs.kuId in (select u.kuId from WkTUser as u where u.kuName like '%");
		else
			queryString.append("stu.stId=?) and bs.kuId in (select u.kuId from WkTUser as u where u.kuName like '");
		if (name.equals("") || name.equals(null))
			queryString.append("%%') and bs.stNormal=0");
		else
			queryString.append(name + "%') and bs.stNormal=0");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { buId, ispassone, sid });
	}

	public List findBysidorname2(String sid, String name, Long buId, Short ispasstwo) {
		StringBuffer queryString = new StringBuffer("from BsStudent as bs where bs.buId=? and bs.bstIspasstwo=? and bs.kuId in (select stu.kuId from Student as stu where ");
		if (sid.equals("") || sid.equals(null))
			queryString.append("stu.stId<>?) and bs.kuId in (select u.kuId from WkTUser as u where u.kuName like '%");
		else
			queryString.append("stu.stId=?) and bs.kuId in (select u.kuId from WkTUser as u where u.kuName like '");
		if (name.equals("") || name.equals(null))
			queryString.append("%%') and bs.stNormal=0");
		else
			queryString.append(name + "%') and bs.stNormal=0");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { buId, ispasstwo, sid });
	}

	public List findByBuidINPassoneOrInPasstwo(Long buId) {
		String queryString = "from BsStudent as bs where bs.buId=? and ( bs.bstIspassone="+BsStudent.PASSONE_JXMS+" or bs.bstIspasstwo="+BsStudent.PASSTWO_JXMS+")  and bs.stNormal=0";
	return getHibernateTemplate().find(queryString, new Object[] { buId });
	}
}
