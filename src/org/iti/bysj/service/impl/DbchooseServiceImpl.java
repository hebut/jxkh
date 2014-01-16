package org.iti.bysj.service.impl;

import java.util.List;

import org.iti.bysj.entity.BsDbchoose;
import org.iti.bysj.entity.BsFscore;
import org.iti.bysj.entity.BsStudent;
import org.iti.bysj.service.DbchooseService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class DbchooseServiceImpl extends BaseServiceImpl implements DbchooseService {

	public BsDbchoose findByBdbid(long bdbid) {

		return (BsDbchoose) getHibernateTemplate().get(BsDbchoose.class, bdbid);
	}

	public List findByBbidAndIfaccept(long bbid, short ifaccept) {
		String queryString = "select bc from BsDbchoose as bc, BsStudent as bsstu, Student as stu where bc.bsId=bsstu.bsId and bsstu.kuId=stu.kuId and bc.bbId=? and bc.bdbIfaccept=? and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) and bc.bsId in( select b.bsId from BsStudent as b where b.kuId in( select u.kuId from WkTUser as u ))  order by stu.stClass,stu.stId";
		return getHibernateTemplate().find(queryString, new Object[] { bbid, ifaccept });
	}

	public List findByBprid(long bprid) {
		String queryString = "from BsDbchoose as bc where bc.bprId=? and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { bprid });
	}

	public List findByBpridAndIfaccept(long bprid, short ifaccept) {
		String queryString = "select bc from BsDbchoose as bc ,BsStudent as bsstu, Student as stu where bc.bsId=bsstu.bsId and bsstu.kuId=stu.kuId and bc.bprId=? and bc.bdbIfaccept=?  and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by stu.stClass,stu.stId";
		return getHibernateTemplate().find(queryString, new Object[] { bprid, ifaccept });
	}

	public List findByBsid(long bsid) {
		String queryString = "from BsDbchoose as bc where bc.bsId=? ";
		return getHibernateTemplate().find(queryString, new Object[] { bsid });
	}

	public List findByBsidAndIfaccept(long bsid, short ifaccept) {
		String queryString = "from BsDbchoose as bc where bc.bsId=? and bc.bdbIfaccept=?";
		return getHibernateTemplate().find(queryString, new Object[] { bsid, ifaccept });
	}

	public List findByBtidAndIfaccept(long btid, short ifaccept) {
		String queryString = "select bc from BsDbchoose as bc,BsStudent as bsstu, Student as stu where bc.bsId=bsstu.bsId and bsstu.kuId=stu.kuId and bc.btId=? and bc.bdbIfaccept=?  and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by stu.stClass,stu.stId";
		return getHibernateTemplate().find(queryString, new Object[] { btid, ifaccept });
	}

	public List findByBuidAndIfaccept(long buid, short ifaccept) {
		String queryString = "select bc from BsDbchoose as bc,BsTeacher as tea,WkTUser as wuser where  bc.btId=tea.btId and tea.kuId=wuser.kuId and bc.buId=? and bc.bdbIfaccept=? and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) and bc.bsId in( select b.bsId from BsStudent as b where b.kuId in( select u.kuId from WkTUser as u )) order by wuser.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept });
	}
	public List findByBuidAndIfacceptks(long buid, short ifaccept) {
		String queryString = "select bp.bprName,suser.kuName,stu.stId,stu.stClass,wuser.kuName,bs.bsId ,bc.bdbId from BsDbchoose as bc,BsProject as bp,BsTeacher as tea,WkTUser as wuser,WkTUser as suser,BsStudent as bs,Student as stu where stu.kuId=suser.kuId and bs.bsId=bc.bsId and bs.kuId=suser.kuId and bc.bprId=bp.bprId and  bc.btId=tea.btId and tea.kuId=wuser.kuId and bc.buId=? and bc.bdbIfaccept=? and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) and bc.bsId in( select b.bsId from BsStudent as b where b.kuId in( select u.kuId from WkTUser as u )) order by wuser.kuName,stu.stId";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept });
	}
//
//	public List findByBuidAndKunameAndIfaccept(long buid, String kuname, short ifaccept) {
//		String queryString = "from BsDbchoose as bc where bc.buId=? and bc.btId in (select bt.btId from BsTeacher as bt,WkTUser as user where bt.kuId=user.kuId and user.kuName like '%" + kuname
//				+ "%') and bc.bdbIfaccept=?  and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
//		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept });
//	}

	public List findByBtidAndBbidAndIfaccept(long btid, long bbid, short ifaccept) {
		String queryString = "select bc from BsDbchoose as bc,BsStudent as bsstu,Student as stu  where bc.bsId=bsstu.bsId and bsstu.kuId=stu.kuId and bc.btId=? and bc.bdbIfaccept=? and bc.bbId=?  and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by stu.stClass,stu.stId";
		return getHibernateTemplate().find(queryString, new Object[] { btid, ifaccept, bbid });
	}

	public List findByBtid(long btid) {
		String queryString = "select bc from BsDbchoose as bc,BsStudent as bsstu,Student as stu  where bc.bsId=bsstu.bsId and bsstu.kuId=stu.kuId and bc.btId=?  and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by stu.stClass,stu.stId";
		return getHibernateTemplate().find(queryString, new Object[] { btid });
	}

	public List findByBbid(long bbid) {
		String queryString = "from BsDbchoose as bc where bc.bbId=? and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { bbid });
	}

	public List findByBuid(long buid) {
		String queryString = "from BsDbchoose as bc where bc.buId=? and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findByBbidAndBtid(long bbid, long btid) {
		String queryString = "select bc from BsDbchoose as bc ,BsStudent as bsstu,Student as stu where bc.bsId=bsstu.bsId and bsstu.kuId=stu.kuId and bc.bbId=? and bc.btId=?  and bc.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)order by stu.stClass,stu.stId";
		return getHibernateTemplate().find(queryString, new Object[] { bbid, btid });
	}
	public List findOnebatchByBuid(Long buid) {
		String queryString = "from BsDbchoose as db where db.buId=? and db.bsId in (select bs.bsId from BsStudent as bs where bs.bstIspassone>0 and bs.stNormal=0) and db.bdbIfaccept=1";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}

	public List findByBuidAndIfacceptAndBprtype(Long buid, short ifaccept, short bprtype) {
		String queryString = "from BsDbchoose as db where db.buId=? and db.bdbIfaccept=? and db.bprId in (select bpr.bprId from BsProject as bpr where bpr.bprType=?)  and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept, bprtype });
	}
	public List findNotDb2ByBuid(Long buid) {
		String queryString = "from BsDbchoose as db where db.buId=? and db.bdbIfaccept=1 and db.bsId not in (select bf.bsId from BsFscore as bf ) and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, buid);
	}
	public List findByIfacceptAndBcpidAndStateInPcontrol(short ifaccept, Long bcpid, short state) {
		String queryString = "select db from BsDbchoose as db,BsTeacher as bsteacher,WkTUser as user where db.btId=bsteacher.btId and bsteacher.kuId=user.kuId and db.bdbIfaccept=? and db.bsId in (select cp.bsId from BsPcontrol as cp where cp.bcpId=? and cp.bpcState =?) and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { ifaccept, bcpid, state });
	}

	public List findByBuidAndIfacceptAndBcpidAndStateNotInPcontrol(Long buid, short ifaccept, Long bcpid, short state) {
		String queryString = "select db from BsDbchoose as db,BsTeacher as bt,WkTUser as user  where db.btId=bt.btId and bt.kuId=user.kuId and db.buId=? and  db.bdbIfaccept=? and db.bsId not in (select cp.bsId from BsPcontrol as cp where cp.bcpId=? and cp.bpcState =?)  and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept, bcpid, state });
	}

	public List findByBbidAndIfacceptAndBcpidAndStateNotInPcontrol(Long bbid, short ifaccept, Long bcpid, short state) {
		String queryString = "select db from BsDbchoose as db,BsTeacher as bt,WkTUser as user  where db.btId=bt.btId and bt.kuId=user.kuId and db.bbId=? and  db.bdbIfaccept=? and db.bsId not in (select cp.bsId from BsPcontrol as cp where cp.bcpId=? and cp.bpcState =?)  and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { bbid, ifaccept, bcpid, state });
	}
	public List findByIfacceptAndBcpidAndStateAndIfcqInPcontrol(short ifaccept, Long bcpid, short state, short ifcq) {
		String queryString = "from BsDbchoose as db where db.bdbIfaccept=? and db.bsId in (select cp.bsId from BsPcontrol as cp where cp.bcpId=? and cp.bpcState =? and cp.bpcIfcq=?) and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { ifaccept, bcpid, state, ifcq });
	}

	public List findByBuidAndIfacceptHaveReplyOrTworeply(Long buid, short ifaccept) {
		String queryString = "from BsDbchoose as db where db.buId=? and db.bdbIfaccept=? and db.bsId in (select fs.bsId from BsFscore as fs where fs.bfsTedit=" + BsFscore.TEDIT_TJ
				+ ") and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept });
	}

	public List findByBuidAndIfacceptHaveReview(Long buid, short ifaccept) {
		String queryString = "from BsDbchoose as db where db.buId=? and db.bdbIfaccept=? and db.bsId in (select fs.bsId from BsFscore as fs where fs.bfsReview<>" + BsFscore.CjNone
				+ ") and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept });
	}

	public List findByBuidAndIfacceptHaveTScore(Long buid, short ifaccept) {
		String queryString = "from BsDbchoose as db where db.buId=? and db.bdbIfaccept=? and db.bsId in (select fs.bsId from BsFscore as fs where fs.bfsDbedit=" + BsFscore.DBEDIT_TJ + " or fs.bfsTwodbedit=" + BsFscore.TWODBEDIT_TJ
				+ ") and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept });
	}

	public List findSameNature(Long buid, Long bnsid) {
		String queryString = "from BsDbchoose as db where db.buId=? and db.bprId in (select pro.bprId from BsProject as pro where pro.bprNature=?) and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0 or bs.bstIspassone!=?)";
		return getHibernateTemplate().find(queryString, new Object[] { buid, bnsid,BsStudent.PASSONE_JXMS });
	}

	public List findByBtidAndIfacceptAndIfpass(long btid, short ifaccept, short ispassone) {
		String queryString = "from BsDbchoose as bc where bc.btId=? and bc.bdbIfaccept=? and bc.bsId in (select stu.bsId from BsStudent as stu where stu.bstIspassone=? and stu.stNormal=0)";
		return getHibernateTemplate().find(queryString, new Object[] { btid, ifaccept, ispassone });
	}

	public List findBybbid(long bbid) {
		String queryString = "from BsDbchoose as bc where bc.bbId=?";
		return getHibernateTemplate().find(queryString, new Object[] { bbid });
	}
	public List findByIfacceptAndBcpidInPcontrol(short ifaccept, Long bcpid) {
		String queryString = " select db from BsDbchoose as db,BsTeacher as bsteacher,WkTUser as user  where db.btId=bsteacher.btId and bsteacher.kuId=user.kuId and db.bdbIfaccept=? and db.bsId in (select cp.bsId from BsPcontrol as cp where cp.bcpId=?) and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { ifaccept, bcpid});
	}

	public List findByBuidAndIfacceptAndBcpidNotInPcontrol(Long buid, short ifaccept, Long bcpid) {
		String queryString = "select db from BsDbchoose as db,BsTeacher as bt,WkTUser as user where db.btId=bt.btId and bt.kuId=user.kuId and db.buId=? and  db.bdbIfaccept=? and db.bsId not in (select cp.bsId from BsPcontrol as cp where cp.bcpId=?)  and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by user.kuName ";
		return getHibernateTemplate().find(queryString, new Object[] { buid, ifaccept, bcpid});
	}

	public List findByBbidAndIfacceptAndBcpidNotInPcontrol(Long bbid, short ifaccept, Long bcpid) {
		String queryString = "select db from BsDbchoose as db,BsTeacher as bt,WkTUser as user where db.btId=bt.btId and bt.kuId=user.kuId and db.buId=? and  where db.bbId=? and  db.bdbIfaccept=? and db.bsId not in (select cp.bsId from BsPcontrol as cp where cp.bcpId=?)  and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0) order by user.kuName";
		return getHibernateTemplate().find(queryString, new Object[] { bbid, ifaccept, bcpid});
	}
	public List findByIfacceptAndBcpidAndIfcqInPcontrol(short ifaccept, Long bcpid, short ifcq) {
		String queryString = "from BsDbchoose as db where db.bdbIfaccept=? and db.bsId in (select cp.bsId from BsPcontrol as cp where cp.bcpId=? and cp.bpcIfcq=?) and db.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { ifaccept, bcpid, ifcq });
	}
	public List findByBuidAndIfacceptAndStidOrName(long buid, short ifaccept,String stid, String name) {
		String queryString = "from BsDbchoose as db where db.buId=? and db.bdbIfaccept=?";
		if (!stid.equals("") &&!stid.equals(null)&& stid.trim().length()!=0)
			queryString= queryString+" and  db.bsId in (select bsstu.bsId from BsStudent as bsstu where bsstu.stNormal=0 and  bsstu.kuId in (select stu.kuId from Student as stu where stu.stId like '%"+stid+"%'))";
		if (!name.equals("") &&!name.equals(null)&& name.trim().length()!=0)
			queryString= queryString+"and db.bsId in (select bsstu.bsId from BsStudent as bsstu where  bsstu.stNormal=0 and bsstu.kuId in (select user.kuId from WkTUser as user where  user.kuName like '%"+name+"%'))";
			
		return getHibernateTemplate().find(queryString.toString(), new Object[] { buid,ifaccept });
	}

	public List findByBuidAndKunameAndIfaccept(long buid, String kuname,
			short ifaccept) {
		return null;
	}
	public List findBuIdNotDbcj(Long buid) {
		String queryString = "from BsDbchoose as db where db.bsId in (select bs.bsId from BsStudent as bs where bs.buId=? and bs.bstIspassone =" + BsStudent.PASSONE_JXMS + "  and bs.stNormal=0) and db.bsId not in (select fs.bsId from BsFscore as fs where fs.bfsDbedit=" + BsFscore.DBEDIT_TJ + ") ";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}
	public List findBuIdNotTwoDbcj(Long buid) {
		String queryString = "from BsDbchoose as db where  db.bsId in (select bs.bsId from BsStudent as bs where bs.buId=? and  bs.bstIspasstwo =" + BsStudent.PASSTWO_JXMS + "  and bs.stNormal=0) and db.bsId not in (select fs.bsId from BsFscore as fs where fs.bfsTwodbedit=" + BsFscore.TWODBEDIT_TJ + ")";
		return getHibernateTemplate().find(queryString, new Object[] { buid });
	}
}
