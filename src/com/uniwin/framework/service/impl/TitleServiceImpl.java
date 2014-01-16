package com.uniwin.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.entity.WkTAuth;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTTitle;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.TitleService;

@SuppressWarnings("unchecked")
public class TitleServiceImpl extends BaseServiceImpl implements TitleService {
	
	public List<WkTTitle> getChildTitle(Long ptid) {
		String queryString = "from WkTTitle as t where t.ktPid=? order by t.ktOrdno";
		return getHibernateTemplate().find(queryString, new Object[] { ptid });
	}

	public List<WkTTitle> getChildTitleThree(Long ptid) {
		String queryString = "from WkTTitle as t where t.ktPid=3 order by t.ktOrdno";
		return getHibernateTemplate().find(queryString, new Object[] { ptid });
	}

	public List<WkTTitle> getChildTitle(Long ptid, Long tid) {
		String queryString = "from WkTTitle as t where t.ktPid=? and t.ktId!=? order by t.ktOrdno";
		return getHibernateTemplate().find(queryString, new Object[] { ptid, tid });
	}

	// 只显示二级标题
	public List<WkTTitle> getChildTitleTwo(Long ptid) {
		String queryString = "from WkTTitle as t where t.ktPid=? and t.ktPid<6 order by t.ktOrdno";
		return getHibernateTemplate().find(queryString, new Object[] { ptid });
	}

	public List<WkTTitle> getChildTitleTwo(Long ptid, Long tid) {
		String queryString = "from WkTTitle as t where t.ktPid=? and t.ktPid<6 and t.ktId!=? order by t.ktOrdno";
		return getHibernateTemplate().find(queryString, new Object[] { ptid, tid });
	}

	public List<WkTTitle> getTitlesOfUserAccess(WkTUser user) {
		// 部门id编号列表，为用户所在部门及上级部门直至顶级,表示用户具有的默认角色为本级部门或上级部门拥有的角色才可以
		List<Long> dlist = new ArrayList<Long>();
		Long kdid = user.getKdId();
		while (kdid.intValue() != 0) {
			dlist.add(kdid);
			WkTDept dept = (WkTDept) getHibernateTemplate().get(WkTDept.class, kdid);
			kdid = dept.getKdPid();
		}
		dlist.add(0L);
		// 用户登陆IP数组
		Long[] ips = IPUtil.getIPLong(user.getKuLastaddr());
		StringBuffer queryString = new StringBuffer("from WkTTitle as t where t.ktId in (" + "select auth.kaRid from WkTAuth as auth where auth.kaType=1 and auth.kaCode1=1 and (auth.kdId=0 or auth.kdId=?) and (auth.krId=0 or auth.krId in(" + "select u_r.id.krId from WkTUserole as u_r where u_r.id.kuId=?) or auth.krId in (select r.krId from WkTRole as r where r.krDefault=? and " + "r.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long l = (Long) dlist.get(i);
			queryString.append(l);
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append("))) and (auth.kaIp11 is null or auth.kaIp11=0 or (auth.kaIp11<=? and auth.kaIp12>=?))" + " and (auth.kaIp21 is null or auth.kaIp21=0 or (auth.kaIp21<=? and auth.kaIp22>=?))" + " and (auth.kaIp31 is null or auth.kaIp31=0 or (auth.kaIp31<=? and auth.kaIp32>=?))" + " and (auth.kaIp41 is null or auth.kaIp41=0 or (auth.kaIp41<=? and auth.kaIp42>=?))" + ") order by t.ktOrdno");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { user.getKdId(), user.getKuId(), "1", ips[0], ips[0], ips[1], ips[1], ips[2], ips[2], ips[3], ips[3] });
	}

	public List<WkTTitle> getTitlesOfUserAccess(WkTUser user, Long tpid) {
		// 部门id编号列表，为用户所在部门及上级部门直至顶级,表示用户具有的默认角色为本级部门或上级部门拥有的角色才可以
		List<Long> dlist = new ArrayList<Long>();
		Long kdid = user.getKdId();
		while (kdid.intValue() != 0) {
			dlist.add(kdid);
			WkTDept dept = (WkTDept) getHibernateTemplate().get(WkTDept.class, kdid);
			kdid = dept.getKdPid();
		}
		dlist.add(0L);
		// 用户登陆IP数组
		Long[] ips = IPUtil.getIPLong(user.getKuLastaddr());
		StringBuffer queryString = new StringBuffer("from WkTTitle as t where t.ktPid=? and t.ktId in (" + "select auth.kaRid from WkTAuth as auth where auth.kaType=1 and auth.kaCode1=1 and (auth.kdId=0 or auth.kdId=?) and (auth.krId=0 or auth.krId in(" + "select u_r.id.krId from WkTUserole as u_r where u_r.id.kuId=?) or auth.krId in (select r.krId from WkTRole as r where r.krDefault=? and " + "r.kdId in (");
		for (int i = 0; i < dlist.size(); i++) {
			Long l = (Long) dlist.get(i);
			queryString.append(l);
			if (i < dlist.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append("))) and (auth.kaIp11 is null or auth.kaIp11=0 or (auth.kaIp11<=? and auth.kaIp12>=?))" + " and (auth.kaIp21 is null or auth.kaIp21=0 or (auth.kaIp21<=? and auth.kaIp22>=?))" + " and (auth.kaIp31 is null or auth.kaIp31=0 or (auth.kaIp31<=? and auth.kaIp32>=?))" + " and (auth.kaIp41 is null or auth.kaIp41=0 or (auth.kaIp41<=? and auth.kaIp42>=?))" + ") order by t.ktOrdno");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { tpid, user.getKdId(), user.getKuId(), "1", ips[0], ips[0], ips[1], ips[1], ips[2], ips[2], ips[3], ips[3] });
	}

	public List<WkTTitle> getTitlesOfUserManager(WkTUser user, List<Long> deptList, List<WkTTitle> titleList) {
		// deptList 当前用户及下级部门列表,要求权限所在的部门及角色所在部门在此列表中
		// 用户登陆IP数组
		Long[] ips = IPUtil.getIPLong(user.getKuLastaddr());
		StringBuffer queryString = new StringBuffer("from WkTTitle as t where t.ktId in (" + "select auth.kaRid from WkTAuth as auth where auth.kaType=1 and auth.kaCode2=1 and (auth.kdId=0 or auth.kdId in (");
		for (int i = 0; i < deptList.size(); i++) {
			Long l = (Long) deptList.get(i);
			queryString.append(l);
			if (i < deptList.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(")) and (auth.krId=0 or auth.krId in (select r.krId from WkTRole as r where " + "r.kdId in (");
		for (int i = 0; i < deptList.size(); i++) {
			Long l = (Long) deptList.get(i);
			queryString.append(l);
			if (i < deptList.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append("))) and (auth.kaIp11 is null or auth.kaIp11=0 or (auth.kaIp11<=? and auth.kaIp12>=?))" + " and (auth.kaIp21 is null or auth.kaIp21=0 or (auth.kaIp21<=? and auth.kaIp22>=?))" + " and (auth.kaIp31 is null or auth.kaIp31=0 or (auth.kaIp31<=? and auth.kaIp32>=?))" + " and (auth.kaIp41 is null or auth.kaIp41=0 or (auth.kaIp41<=? and auth.kaIp42>=?))" + ") and t.ktId in(");
		for (int i = 0; i < titleList.size(); i++) {
			WkTTitle t = (WkTTitle) titleList.get(i);
			queryString.append(t.getKtId());
			if (i < titleList.size() - 1) {
				queryString.append(",");
			}
		}
		queryString.append(") order by t.ktOrdno");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { ips[0], ips[0], ips[1], ips[1], ips[2], ips[2], ips[3], ips[3] });
	}

	public void delete(WkTTitle title) {
		// 删除标题相关权限
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String deleteTitleAuth = "delete WkTAuth as au where au.kaType=? and au.kaRid=?";
		Query dTitleAuth = session.createQuery(deleteTitleAuth);
		dTitleAuth.setString(0, WkTAuth.TYPE_TITLE);
		dTitleAuth.setLong(1, title.getKtId());
		dTitleAuth.executeUpdate();
		session.getTransaction().commit();
		//session.clear();
		// 删除此标题
		getHibernateTemplate().delete(title);
	}

	public List<WkTTitle> getTitleOfRoleAccess(Long ptid, Long rid) {
		StringBuffer queryString = new StringBuffer("from WkTTitle as t where t.ktPid=? and t.ktId in (" + "select auth.kaRid from WkTAuth as auth where auth.kaType=1 and auth.kaCode1=1 and auth.krId=?) order by t.ktOrdno");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { ptid, rid });
	}

	public List<WkTTitle> getChildTitleInlist(Long pid, Long krid, String typeTitle, Short kacode) {
		String queryString = "from WkTTitle as t where t.ktPid=? and t.ktId in(" + "select auth.kaRid from WkTAuth as auth where auth.krId=? and auth.kaType=? " + "and auth.kaCode1=?) order by t.ktOrdno";
		return getHibernateTemplate().find(queryString.toString(), new Object[] { pid, krid, typeTitle, kacode });
	}
	public WkTTitle getTitle(Long tid) {
		String queryString = "from WkTTitle as t where t.ktId=? ";
		return (WkTTitle) getHibernateTemplate().find(queryString, new Object[] { tid }).get(0);
	}
}
