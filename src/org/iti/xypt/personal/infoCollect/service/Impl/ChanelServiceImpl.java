package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.service.ChanelService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.entity.WkTDept;
import com.uniwin.framework.entity.WkTUser;



public class ChanelServiceImpl extends BaseServiceImpl implements ChanelService {

	public WkTChanel findBykcid(Long kcid) {
		String queryString="from WkTChanel as c where c.kcId=?"; 
		return (WkTChanel)getHibernateTemplate().find(queryString, new Object[]{kcid}).get(0);
	}
	public List getChildChanel(Long ptid) {
		String queryString="from WkTChanel as c where c.kcPid=? order by c.kcOrdno"; 
		return getHibernateTemplate().find(queryString, new Object[]{ptid});
	}

	public List findByChanelname(String chanelname){
		String queryString="from WkTChanel as c where c.kcName=? order by c.kcOrdno";
		return getHibernateTemplate().find(queryString, new Object[]{chanelname});
	}
	public List findByKcPid(Long pid) {
		String queryString="from WkTChanel as c where c.kcId=? order by c.kcOrdno";
		return getHibernateTemplate().find(queryString, new Object[]{pid});
	}
	public List getChildChanel(Long pcid,Long cid){
		String queryString="from WkTChanel as c where c.kcPid=? and c.kcId!=? order by c.kcOrdno";
		return getHibernateTemplate().find(queryString, new Object[]{pcid,cid});
	}
	
	public List getChanelsOfUserManage(WkTUser user,List deptList,List chanelList) {
		//deptList 当前用户及下级部门列表,要求权限所在的部门及角色所在部门在此列表中
		//用户登陆IP数组
		Long[] ips=IPUtil.getIPLong(user.getKuLastaddr());
		StringBuffer queryString=new StringBuffer("from WkTChanel as c where c.kcId in ("+
		"select auth.kaRid from WkTAuth as auth where auth.kaType=2 and auth.kaCode2=1 and (auth.kdId=0 or auth.kdId in (");
	  for(int i=0;i<deptList.size();i++){
		Long l=(Long)deptList.get(i);
		queryString.append(l);
		if(i<deptList.size()-1){
			queryString.append(",");
		}
	}
	queryString.append(")) and (auth.krId=0 or auth.krId in (select r.krId from WkTRole as r where "+
		"r.kdId in (");
	for(int i=0;i<deptList.size();i++){
		Long l=(Long)deptList.get(i);
		queryString.append(l);
		if(i<deptList.size()-1){
			queryString.append(",");
		}
	}
	queryString.append(")))and (auth.kaIp11 is null or auth.kaIp11=0 or (auth.kaIp11<=? and auth.kaIp12>=?))"+
				" and (auth.kaIp21 is null or auth.kaIp21=0 or (auth.kaIp21<=? and auth.kaIp22>=?))"+
				" and (auth.kaIp31 is null or auth.kaIp31=0 or (auth.kaIp31<=? and auth.kaIp32>=?))"+
				" and (auth.kaIp41 is null or auth.kaIp41=0 or (auth.kaIp41<=? and auth.kaIp42>=?))"+
				") and c.kcId in(");
	for(int i=0;i<chanelList.size();i++){
		WkTChanel c=(WkTChanel)chanelList.get(i);
		queryString.append(c.getKcId());
		if(i<chanelList.size()-1){
			queryString.append(",");
		}
	}
	queryString.append(") order by c.kwId "); 
	return getHibernateTemplate().find(queryString.toString(), new Object[]{ips[0],ips[0],ips[1],ips[1],ips[2],ips[2],ips[3],ips[3]});
	}
	public List getChanelsOfUserAccess(WkTUser user,Long kcpid) {
		//部门id编号列表，为用户所在部门及上级部门直至顶级,表示用户具有的默认角色为本级部门或上级部门拥有的角色才可以
		List dlist=new ArrayList();
		Long kdid=user.getKdId();
		
		while(kdid.intValue()!=0){
			dlist.add(kdid);
			WkTDept dept=(WkTDept)getHibernateTemplate().get(WkTDept.class, kdid);
			kdid=dept.getKdPid();
		}
		 dlist.add(0L);
		//用户登陆IP数组
		 Long[] ips=IPUtil.getIPLong(user.getKuLastaddr());
		 
		StringBuffer queryString=new StringBuffer("from WkTChanel as c where c.kcPid=? and c.kcId in ("+
			"select auth.kaRid from WkTAuth as auth where auth.kaType=2 and auth.kaCode1=1 and (auth.kdId=0 or auth.kdId=?) and (auth.krId=0 or auth.krId in("+
			"select u_r.id.krId from WkTUserole as u_r where u_r.id.kuId=?) or auth.krId in (select r.krId from WkTRole as r where r.krDefault=? and "+
			"r.kdId in (");
		for(int i=0;i<dlist.size();i++){
			Long l=(Long)dlist.get(i);
			queryString.append(l);
			if(i<dlist.size()-1){
				queryString.append(",");
			}
		}
		queryString.append("))) and (auth.kaIp11 is null or auth.kaIp11=0 or (auth.kaIp11<=? and auth.kaIp12>=?))"+
				" and (auth.kaIp21 is null or auth.kaIp21=0 or (auth.kaIp21<=? and auth.kaIp22>=?))"+
				" and (auth.kaIp31 is null or auth.kaIp31=0 or (auth.kaIp31<=? and auth.kaIp32>=?))"+
				" and (auth.kaIp41 is null or auth.kaIp41=0 or (auth.kaIp41<=? and auth.kaIp42>=?))"+
				") order by c.kcOrdno"); 
		return getHibernateTemplate().find(queryString.toString(), new Object[]{kcpid,user.getKdId(),user.getKuId(),"1",ips[0],ips[0],ips[1],ips[1],ips[2],ips[2],ips[3],ips[3]});
	}
	public List getChanelByKwid(Long kwid){
		String queryString="from WkTChanel as c where c.kwId=? order by c.kcOrdno";
		return getHibernateTemplate().find(queryString, new Object[]{kwid});
	}
	public List getChanel(Long kwid){
		String queryString="from WkTChanel as c where c.kwId=? and kcPid is 0 order by c.kcOrdno";
		return getHibernateTemplate().find(queryString, new Object[]{kwid});
	}
	
	public List getChanelByKwid(Long pid,Long kwid)
	{
		String queryString="from WkTChanel as c where c.kcPid=? and c.kwId=? order by c.kcOrdno";
		return getHibernateTemplate().find(queryString, new Object[]{pid,kwid});
	}
	public List getSiteChanel(Long kcid)
	{
		String queryString="from WkTCsite as c where c.id.kcId=?";
		return getHibernateTemplate().find(queryString, new Object[]{kcid});
	}
	public List getSite(Long kcid)
	{
		String queryString="from WkTCsite as c where c.id.kscId=?";
		return getHibernateTemplate().find(queryString, new Object[]{kcid});
	}
	public List getNoPcid()
	{
		String queryString="from WkTChanel as c where c.kcPid not in (select cc.kcId from WkTChanel as cc) and c.kcPid!=0";
		return getHibernateTemplate().find(queryString, new Object[]{});
	}
	public List getSchanel(Long kiid)
	{
		String queryString="from WkTChanel as c where c.kcId  in (select d.kcId from WkTDistribute as d where d.kiId=? and d.kbFlag is 1)";
		return getHibernateTemplate().find(queryString, new Object[]{kiid});
	}
	public List getSiteCha(Long cid)
	{
		String queryString="from WkTChanel as c where c.kcId  in (select d.id.kscId from WkTCsite as d where d.id.kcId=? )";
		return getHibernateTemplate().find(queryString, new Object[]{cid});
	}
}
