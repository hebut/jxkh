package org.iti.bysj.service.impl;

import java.util.List;

import org.iti.bysj.entity.BsGpunit;
import org.iti.bysj.service.CheckService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class CheckServiceImpl extends BaseServiceImpl implements CheckService{

	public List findByKuidAndKridAndBsidAndBcpid(Long kuid, Long krid, Long bsid,Long bcpid) {
		String queryString = "from BsChecksin as ck where ck.kuId =? and ck.krId = ? " +
		"and ck.bsId = ? and ck.bcpId=?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, krid , bsid, bcpid });
	}

	public List findByBcsid(Long bcsid) {
		String queryString = "from BsCrange as bc where bc.bcsId = ?";
		return getHibernateTemplate().find(queryString, new Object[] {  bcsid });
	}

	public List findByKuidAndKridAndBcpid(Long kuid, Long krid, Long bcpid) {
		String queryString = "from BsChecksin as ck where ck.kuId =? and ck.krId = ? " +
		"and ck.bcpId = ? and ck.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, krid , bcpid });
	}

	public List findByBcsidAndKrid(Long bcsid, Long krid) {
		String queryString = "from BsCrange as bc where bc.bcsId = ? and bc.krId = ?";
		return getHibernateTemplate().find(queryString, new Object[] {  bcsid,krid });
	}

	public List findByKridAndBsid(Long krid, Long bsid) {
		String queryString = "from BsCrange as bc where bc.krId = ? and bc.bcsId in(" +
		" select ck.bcsId from BsChecksin as ck where ck.bsId = ?)";
		return getHibernateTemplate().find(queryString, new Object[] {  krid , bsid });
	}

	public List findbyBcsidFromCS(Long bcsid) {
		String queryString = "from BsChecksin as ck where ck.bcsId =?" ;
		return getHibernateTemplate().find(queryString, new Object[] { bcsid });
	}

	public List findByBtidAndKrid(Long btid, Long krid) {
		String queryString = "from BsCrange as bc where bc.krId = ? and bc.bcsId in(" +
		" select ck.bcsId from BsChecksin as ck where ck.bsId in(" +
		" select db.bsId from BsDbchoose as db where db.btId = ?))";
		return getHibernateTemplate().find(queryString, new Object[] {  krid, btid});
	}

	public List findByKridAndBuid(Long krid, Long buid) {
		String queryString = "from BsCrange as bc where bc.krId = ? and bc.bcsId in(" +
		" select ck.bcsId from BsChecksin as ck where ck.buId = ? )";
		return getHibernateTemplate().find(queryString, new Object[] {  krid , buid });
	}



	public List findByKridAndBuidAndKuidAndIfck(Long krid, Long buid,
			Long kuid, Long ifck) {
		String queryString = "from BsChecksin as ck where ck.krId =? and ck.buId = ? " +
		"and ck.kuId = ? and ck.bcsIfcomcheck=?";
		return getHibernateTemplate().find(queryString, new Object[] {  krid ,buid,kuid, ifck });
	}

	public List findByKdidAndKuidAndBgidAndKrid(Long kdid, Long kuid, Long bgid ,Long krid) {
		String queryString = "from BsCheckcom as bc where bc.kdId=? and bc.kuId=? and bc.bgId=? and bc.krId=?";
		return getHibernateTemplate().find(queryString,new Object[]{kdid,kuid,bgid,krid});
	}

	public List findByKridAndKuidAndBcpidAndIfck(Long krid, Long kuid,
			Long bcpid, Short ifck) {
		String queryString = "from BsChecksin as ck where ck.kuId =? and ck.krId = ? "+
		"and ck.bcpId = ? and ck.bcsIfcomcheck = ?";
		return getHibernateTemplate().find(queryString, new Object[] { kuid, krid , bcpid, ifck });
	}

	public List findByKuidAndBgidAndKrid(Long kuid, Long bgid,Long krid) {
		String queryString = "from BsCheckcom as bc where bc.kuId=? and bc.bgId=? and bc.krId=?";
		return getHibernateTemplate().find(queryString,new Object[]{kuid,bgid,krid});
	}

	public List findByKdidAndKridAndBgid(Long kdid, Long krid, Long bgid) {
		String queryString = "from BsCheckcom as bc where bc.kdId=? and bc.bgId=? and bc.bccId in(" +
				"select cr.bccId from BsCrange as cr where cr.krId=? )";
		return getHibernateTemplate().find(queryString,new Object[]{kdid , bgid , krid});
	}


	public Long countByKuidAndKrid(Long kuid, Long krid) {
		String queryString = "from BsChecksin as ck where ck.kuId=? and ck.krId=? and ck.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		List list = getHibernateTemplate().find(queryString, new Object[]{ kuid,krid });
		return Long.parseLong(list.size()+"");
	}

	public List findByKuid(Long kuid) {
		String queryString = "from BsChecksin as ck where ck.kuId =? and ck.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		return getHibernateTemplate().find(queryString, new Object[] { kuid });
	}

	public List findKdidByKuidAndKdid(Long kuid , Long kdid) {
		String queryString = "from BsUserDudao as ud where ud.id.kuId=? and ud.id.kdId in(" +
				"select dp.kdId from WkTDept as dp where dp.kdPid=?)";
		return getHibernateTemplate().find(queryString, new Object[]{ kuid , kdid});
	}

	public List findByKdid(Long kdid) {
		String queryString = "from BsUserDudao as ud where ud.id.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[]{ kdid});
	}

	public Long countByBuidAndKrid(Long buid , Long krid) {
		String queryString = "from BsChecksin as ck where ck.buId=? and ck.krId=? and ck.bsId not in (select bs.bsId from BsStudent as bs where bs.stNormal!=0)";
		List list = getHibernateTemplate().find(queryString, new Object[]{ buid ,krid});
		return Long.parseLong(list.size()+"");
	}

	public Long countByKdidAndKridAndBgid(Long kdid, Long krid, Long bgid) {
		String queryString = "from BsCheckcom as ck where ck.kdId=? and ck.krId=? and ck.bgId = ?";
		List list = getHibernateTemplate().find(queryString, new Object[]{ kdid ,krid,bgid});
		return Long.parseLong(list.size()+"");
	}

	public Long countComByKuidAndKrid(Long kuid, Long krid) {
		String queryString = "from BsCheckcom as ck where ck.kuId=? and ck.krId=?";
		List list = getHibernateTemplate().find(queryString, new Object[]{ kuid,krid });
		return Long.parseLong(list.size()+"");
	}


	public List findByBcpidAndBsid(Long bcpid, Long bsid) {
		String queryString = "from BsChecksin as ck where ck.bcpId=? and ck.bsId=?";		
		return getHibernateTemplate().find(queryString, new Object[]{ bcpid ,bsid});
	}


	public List findByKuidAndKrid(Long kuId, Long krId) {
		String queryString = "from BsChecksin as ck where ck.kuId=? and ck.krId=?";
		return getHibernateTemplate().find(queryString, new Object[]{ kuId,krId });
	}

	public List findByBccid(Long bccid) {
		String queryString = "from BsCnumber as num where num.id.bccId = ?";
		return getHibernateTemplate().find(queryString,new Object[]{bccid});
	}

	public List findByKuidAndKridAndKdid(Long kuId, Long krid, List deplist) {
		StringBuffer queryString = new StringBuffer("from BsChecksin as ck where ck.kuId=? and ck.krId=? and ck.bsId not in " +
				"(select bs.bsId from BsStudent as bs where bs.stNormal!=0) and ck.buId in(") ;
		for(int i=0;i<deplist.size();i++){
			BsGpunit gpunit = (BsGpunit) deplist.get(i);
			queryString.append(gpunit.getBuId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")");
		return getHibernateTemplate().find(queryString.toString(), new Object[]{ kuId,krid});
	}

	public List findComByKuidAndKridAndKdidAndBgid(Long kuId, Long krid, List deplist,Long bgid) {
		StringBuffer queryString = new StringBuffer("from BsCheckcom as ck where ck.kuId=? and ck.krId=? and ck.bgId=? and ck.kdId in(") ;
		for(int i=0;i<deplist.size();i++){
			BsGpunit gpunit = (BsGpunit) deplist.get(i);
			queryString.append(gpunit.getKdId());
			if (i < deplist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")");
		return getHibernateTemplate().find(queryString.toString(), new Object[]{ kuId,krid,bgid});
	}

}
