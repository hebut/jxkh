package org.iti.xypt.personal.infoCollect.service.Impl;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTChanel;
import org.iti.xypt.personal.infoCollect.entity.WkTDistribute;
import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTInfo;
import org.iti.xypt.personal.infoCollect.entity.WkTInfoscore;
import org.iti.xypt.personal.infoCollect.service.NewsService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.entity.WkTUserole;


public  class NewsServiceImpl extends BaseServiceImpl implements NewsService {

	public List getNewsOfAllChanelFB(){
		String queryString = "from WkTInfo as i where i.kiId in (select d.kiId from WkTDistribute as d where d.kbStatus is 0) order by i.kiCtime desc";
		return getHibernateTemplate().find(queryString);
		
	}
	
	public List getNewstop() {
		String queryString="from WkTDistribute as d where d.kbRight='0' order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString);
	}

	public List getChildNews(Long ptid) {
		String queryString="from WkTChanel as n where n.kcPid=? order by n.kcOrdno"; 
		return getHibernateTemplate().find(queryString, new Object[]{ptid});
	}
	
	public List getChildNews(Long ptid,Long kwid) {
		String queryString="from WkTChanel as n where n.kcPid=? and n.kwId=? order by n.kcOrdno"; 
		return getHibernateTemplate().find(queryString, new Object[]{ptid,kwid});
	}
	
	public List getChildNewsown(Long ptid,Long did) {
		String queryString=" from WkTChanel as c where c.kcId in (select a.kaRid from WkTAuth as a where a.kaType=2 and a.kaCode2=1 and a.krId in (select r.id.krId from WkTUserole as r where r.id.kuId=?) and a.kdId in (?,0))";
		return getHibernateTemplate().find(queryString, new Object[]{ptid,did});
	}
	public List getChildNewsaudit(Long ptid,Long did)
	{
		String queryString=" from WkTChanel as c where c.kcId in (select a.kaRid from WkTAuth as a where a.kaType=2 and a.kaCode3=1 and a.krId in (select r.id.krId from WkTUserole as r where r.id.kuId=?)and a.kdId in (?,0))";
		return getHibernateTemplate().find(queryString, new Object[]{ptid,did});
	}
	public List getNewsOfChanelZG(Long did,Long pid) {
		 String queryString="from WkTDistribute as d where  d.keId=?  and d.kbFlag is 0 and d.kbStatus is 1  and d.kiId in (select kiId from WkTInfo where kuId=?) order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});
	}
	public List getNewsOfChanelZG(Long did,Long pid, String key) {
		String queryString="from WkTDistribute as d where d.keId=? and d.kiId in (select i.kiId from WkTInfo as i where i.kuId=? and i.kiTitle like '%"+key+"%') and d.kbStatus is 1 and d.kbFlag is 0 " ;
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});	 
		
	}
	public List getNewsOfChanelTH(Long did,Long pid) {
		 String queryString="from WkTDistribute as d where d.keId=? and d.kbStatus is 4 and d.kbFlag is 0  and d.kiId in (select kiId from WkTInfo where kuId=?) order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});
	}
	public List getNewsOfChanelTH(Long did,Long pid, String key) {
		String queryString="from WkTDistribute as d where d.keId=? and d.kiId in (select i.kiId from WkTInfo as i where i.kuId=? and i.kiTitle like '%"+key+"%') and d.kbStatus is 4 and d.kbFlag is 0 " ;
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});	 
		
	}
	
	public List getNewsOfChanelSS(Long did,Long pid) {
		 String queryString="from WkTDistribute as d where d.keId=? and d.kbStatus is 2 and d.kiId in (select kiId from WkTInfo where kuId=?) order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});
	}
	public List getNewsOfChanelSS(Long did, Long pid,String key) {
		String queryString="from WkTDistribute as d where d.keId=? and d.kiId in (select i.kiId from WkTInfo as i where i.kuId=? and i.kiTitle like '%"+key+"%') and d.kbStatus is 2 and d.kbFlag is 0 " ;	
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});	 
		
	}
	public List getNewsOfChanelSS(Long did) {
		 String queryString="from WkTDistribute as d where d.keId=? and d.kbStatus is 2  order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getNewsOfChanelSS(Long did,String key) {
		String queryString="from WkTInfo as i where i.kiId in (select d.kiId from WkTDistribute as d where d.keId=? and d.kbStatus is 2) and i.kiTitle like '%"+key+"%'";
		return getHibernateTemplate().find(queryString,new Object[]{did});	 
		
	}
	public List getNewsOfChanelYY(Long did,Long pid) {
		 String queryString="from WkTDistribute as d where d.keId=? and d.kbStatus is 3 and d.kiId in (select kiId from WkTInfo where kuId=?) order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});
	}
	public List getNewsOfChanelYY(Long did, Long pid,String key) {
		String queryString="from WkTDistribute as d where d.keId=? and d.kiId in (select i.kiId from WkTInfo as i where i.kuId=? and i.kiTitle like '%"+key+"%') and d.kbStatus is 3 and d.kbFlag is 0 " ;	
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});	 
		
	}
	public List getNewsOfChanelYY(Long did) {
		 String queryString="from WkTDistribute as d where d.keId=? and d.kbStatus is 3 order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getNewsOfChanelYY(Long did,String key) {
		String queryString="from WkTInfo as i where i.kiId in (select d.kiId from WkTDistribute as d where d.keId=? and d.kbStatus is 3)  and i.kiTitle like '%"+key+"%'";
		return getHibernateTemplate().find(queryString,new Object[]{did});	 
		
	}
	public List getNewsOfChanelFB(Long did,Long pid) {
		 String queryString="from WkTDistribute as d where d.keId =? and d.kbStatus is 0 and d.kiId in (select kiId from WkTInfo where kuId=?) order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});
	}
	public List getNewsOfChanelFB(Long did,Long pid, String key) {
		String queryString="from WkTDistribute as d where d.keId=? and d.kiId in (select i.kiId from WkTInfo as i where i.kuId=? and i.kiTitle like '%"+key+"%') and d.kbStatus is 0 and d.kbFlag is 0 " ;	
		return getHibernateTemplate().find(queryString,new Object[]{did,pid});	 
		
	}
	public List getNewsOfChanelFB(Long did) {
		 String queryString="from WkTDistribute as d where d.keId =? and d.kbStatus is 0 order by d.kbDtime Desc";
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getNewsOfChanelFB(Long did, String key) {
		String queryString="from WkTInfo as i where i.kiId in (select d.kiId from WkTDistribute as d where d.keId=? and d.kbStatus is 0)  and i.kiTitle like '%"+key+"%'";
		return getHibernateTemplate().find(queryString,new Object[]{did});	 
		
	}
   public List  getChildNewsContent(Long qtid)
{
	String queryString="from WkTInfocnt as inf where inf.id.kiId=? order by inf.id.kiSubid"; 
	return getHibernateTemplate().find(queryString, new Object[]{qtid});
}
   public WkTChanel getNewsOfChanel(Long qtid)
{
	String queryString="from WkTChanel as c where c.kcId in (select d.kcId from WkTDistribute as d where d.kiId=?)"; 
	return  (WkTChanel)getHibernateTemplate().find(queryString, new Object[]{qtid}).get(0);
}
   public WkTChanel getNewsOfChanelId(String pid)
   {
	   String queryString="from WkTChanel as c where c.kcName=?";
	   return  (WkTChanel)getHibernateTemplate().find(queryString,new Object[]{pid}).get(0);

   }
   public WkTExtractask getChanelState(Long pid)
   {
	   String queryString="from WkTExtractask as c where c.keId=?";
	   return  (WkTExtractask)getHibernateTemplate().find(queryString,new Object[]{pid}).get(0);
   }
   
	public List getDistribute(Long pid)
	{
		 String queryString="from WkTDistribute as d  where d.kiId=? and d.kbRight is null";
		    return  getHibernateTemplate().find(queryString,new Object[]{pid});
	}
	public WkTDistribute getWktDistribute(Long pid)
	{
		String queryString="from WkTDistribute as d  where d.kbId=?";
	    return  (WkTDistribute)getHibernateTemplate().find(queryString,new Object[]{pid}).get(0);
	}
	public List getDistribute(Long pid,Long did)
	{
		 String queryString="from WkTDistribute as d  where d.kiId=? and d.keId=? and d.kbFlag=1";
		    return  getHibernateTemplate().find(queryString,new Object[]{pid,did});
	}
	public List getDistributeShare(Long pid)
	{
		 String queryString="from WkTDistribute as d  where d.kiId=? and d.kbFlag=1";
		    return  getHibernateTemplate().find(queryString,new Object[]{pid});
	}
		public List getDistributeList(Long pid)
	{
		 String queryString="from WkTDistribute as d  where d.kiId=? order by d.kbFlag";
		    return  getHibernateTemplate().find(queryString,new Object[]{pid});
	}
	
	public List getInfocnt(Long pid)
	{
		String queryString="from WkTInfocnt as i  where i.id.kiId=? order by i.id.kiSubid";
	    return   getHibernateTemplate().find(queryString,new Object[]{pid});
	}
	public List getNewsOfcomment(Long did)
	{
		String queryString="from WkTDocrmk as d  where d.kbId=? order by d.koId";
	    return  getHibernateTemplate().find(queryString,new Object[]{did});
	}

	public List getflog(Long did)
	{
		String queryString="from WkTFlog as f where f.kbId=? order by f.kflId desc"; 
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getAnnex(Long did)
	{
		String queryString="from WkTFile as f where f.id.kiId=?"; 
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getInfo()
	{
		String queryString="select distinct f.kiSource from WkTInfo as f where f.kiSource is not null"; 
		return getHibernateTemplate().find(queryString,new Object[]{});
	}
	public WkTInfo getWkTInfo(Long did)
	{
		String queryString="from WkTInfo as i where i.kiId=?"; 
		return (WkTInfo)getHibernateTemplate().find(queryString,new Object[]{did}).get(0);
	}
	public List getNewsOfShareChanel(Long did)
	{
		String queryString="from WkTDistribute as d where d.kiId=? and d.kbFlag is 1 and d.kbMail is not null"; 
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getNewsOfShare(Long did)
	{
		String queryString="from WkTDistribute as d where d.kiId=? and d.kbFlag is 1 "; 
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getNewsOfShareNew(Long did)
	{
		String queryString="from WkTDistribute as d where d.kiId=? and d.kbFlag is 1 and d.kbMail is null "; 
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getFile(Long did)
	{
		String queryString="from WkTFile as f where f.id.kiId=?"; 
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getOrifile(Long did)
	{
		String queryString="from WkTOrifile as f where f.id.koiId=?"; 
		return getHibernateTemplate().find(queryString,new Object[]{did});
	}
	public List getSiteFile(Long kbid)
	{
		String queryString="from WkTFile as f where f.id.kiId=(select d.kiId from WkTDistribute as d where d.kbId=?)"; 
		return getHibernateTemplate().find(queryString,new Object[]{kbid});
	}
		public List getCountInfor(Long kuid, String status,String db,String db1) {
		    
			String queryString="from WkTDistribute as d where d.kiId in (select i.kiId from WkTInfo as i where i.kuId=?)and d.kbStatus=? and d.kbDtime > ? and d.kbDtime< ? and d.kbFlag is 0 order by d.kbDtime"; 
			return getHibernateTemplate().find(queryString, new Object[]{kuid,status,db,db1});
		}
		
		public List getSumbyuid(Long kuid, String db, String db1) {
			
			String queryString="from WkTDistribute as d where d.kiId in (select i.kiId from WkTInfo as i where i.kuId=?) and d.kbDtime > ? and d.kbDtime < ? and d.kbFlag is 0 order by d.kbDtime"; 
			return getHibernateTemplate().find(queryString, new Object[]{kuid,db,db1});
		}
		public List getCountbywid(Long kwid,String status, String db, String db1) {
			String queryString="from WkTDistribute as d where d.kcId in (select c.kcId from WkTChanel as c where c.kwId=?) and d.kbStatus = ? and d.kiId in (select i.kiId from WkTInfo as i) and d.kbDtime > ? and d.kbDtime < ? and d.kbFlag is 0 order by d.kbDtime";
			return  getHibernateTemplate().find(queryString, new Object[]{kwid,status,db,db1});
		}
		public List getCountbycid(Long kcid,String status, String db, String db1) {
			String queryString="from WkTDistribute as d where d.kcId=? and d.kbStatus = ? and d.kiId in (select i.kiId from WkTInfo as i) and d.kbDtime > ? and d.kbDtime < ? and d.kbFlag is 0 order by d.kbDtime";
			return  getHibernateTemplate().find(queryString, new Object[]{kcid,status,db,db1});
		}
		public List getSumBywid(Long kwid, String db, String db1) {
			String queryString="from WkTDistribute as d where d.kcId in (select c.kcId from WkTChanel as c where c.kwId=?) and d.kiId in (select i.kiId from WkTInfo as i) and d.kbDtime > ? and d.kbDtime< ? and d.kbFlag is 0 order by d.kbDtime";
			return  getHibernateTemplate().find(queryString, new Object[]{kwid,db,db1});
		}
		public List getSumBycid(Long kcid, String db, String db1) {
			String queryString="from WkTDistribute as d where d.kcId=? and d.kiId in (select i.kiId from WkTInfo as i) and d.kbDtime > ? and d.kbDtime< ? and d.kbFlag is 0 order by d.kbDtime";
			return  getHibernateTemplate().find(queryString, new Object[]{kcid,db,db1});
		}
		public List getCountByDid(Long kdid,String rname, String status, String db,String db1) {
			String queryString="from WkTDistribute as d where d.kiId in ( select i.kiId from WkTInfo as i where i.kuId in (select u.kuId from WkTUser as u where u.kdId=? and u.kuId in(select ur.id.kuId from WkTUserole as ur where ur.id.krId in(select r.krId from WkTRole as r where r.krName=? )))) and d.kbStatus = ? and d.kbDtime > ? and d.kbDtime < ? and d.kbFlag is 0 order by d.kbDtime";
			return getHibernateTemplate().find(queryString, new Object[]{kdid,rname,status,db,db1});
		}
		public List getSumBydid(Long kdid, String rname,String db, String db1) {
			String queryString="from WkTDistribute as d where d.kiId in (select i.kiId from WkTInfo as i where i.kuId in (select u.kuId from WkTUser as u where u.kdId=? and u.kuId in(select ur.id.kuId from WkTUserole as ur where ur.id.krId in(select r.krId from WkTRole as r where r.krName=? )))) and d.kbDtime > ? and d.kbDtime < ? and d.kbFlag is 0 order by d.kbDtime";
			return getHibernateTemplate().find(queryString, new Object[]{kdid,rname,db,db1});
		}
	
		public List getDistriByCid(Long kcid) {
			String queryString="from WkTDistribute as d where d.kcId=? order by d.kbDtime Desc"; 
			return getHibernateTemplate().find(queryString, new Object[]{kcid});
		}
		
		public List getSumBywbid(Long kbid, String db, String db1) {
			String queryString="from WkTDocrmk as d where d.kbId in(select dd.kbId from WkTDistribute as dd where dd.kcId in (select c.kcId from WkTChanel as c where c.kwId=?)) and d.koTime > ? and d.koTime< ? order by d.koTime";
			return  getHibernateTemplate().find(queryString, new Object[]{kbid,db,db1});
		}
		public List getCountbywiid(Long kiid,Long status, String db, String db1) {
			String queryString="from WkTDocrmk as d where d.kbId in(select dd.kbId from WkTDistribute as dd where dd.kcId in (select c.kcId from WkTChanel as c where c.kwId=?)) and d.koStatus = ?  and d.koTime > ? and d.koTime < ? order by d.koTime";
			return  getHibernateTemplate().find(queryString, new Object[]{kiid,status,db,db1});
		}
		public List getCountbyciid(Long kcid,Long status, String db, String db1) {
			String queryString="from WkTDocrmk as d where d.kbId in(select dd.kbId from WkTDistribute as dd where dd.kcId=?) and d.koStatus = ?  and d.koTime > ? and d.koTime < ? order by d.koTime";
			return  getHibernateTemplate().find(queryString, new Object[]{kcid,status,db,db1});
		}
		public List getSumByciid(Long kcid, String db, String db1) {
			String queryString="from WkTDocrmk as d where d.kbId in(select dd.kbId from WkTDistribute as dd where dd.kcId=?) and d.koTime > ? and d.koTime< ? order by d.koTime";
			return  getHibernateTemplate().find(queryString, new Object[]{kcid,db,db1});
		}
		public List getInfoByCid(Long kcid) {
			String queryString="from WkTDistribute as d where d.kcId=? and d.kbStatus is 0 and d.kbFlag is 0  and d.kbSite is 1 and kbRight is 0 and d.kiId in(select kiId from WkTInfo as i where i.kiType is not 4) order by d.kbDtime Desc"; 
			return getHibernateTemplate().find(queryString, new Object[]{kcid});
		}
		public WkTDistribute getDistri(Long kiid,Long kcid)
		{
			String queryString="from WkTDistribute as d where d.kiId=? and d.keId=?";
			return (WkTDistribute)getHibernateTemplate().find(queryString, new Object[]{kiid,kcid}).get(0);
		}
		public WkTDistribute getDistriBybid(Long kbid)
		{
			String queryString="from WkTDistribute as d where d.kbId=?";
			return (WkTDistribute)getHibernateTemplate().find(queryString, new Object[]{kbid}).get(0);
		}
		public List getChanelOfUserManage(WkTUser user,List deptList)
		{

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
					")");
		//queryString.append(")"); 
		return getHibernateTemplate().find(queryString.toString(), new Object[]{ips[0],ips[0],ips[1],ips[1],ips[2],ips[2],ips[3],ips[3]});

		}
		public List getChanelSite(Long kcid)
		{
			String queryString="from WkTCsite as c where c.id.kscId=?"; 
			return getHibernateTemplate().find(queryString, new Object[]{kcid});
		}
		public List getCsDistribute(Long kcid,Long kiid)
		{
			String queryString="from WkTDistribute as d where d.kcId=? and d.kiId=? and d.kbFlag is 2"; 
			return  getHibernateTemplate().find(queryString, new Object[]{kcid,kiid});
		}
		public List getInfoBycid(Long kcid)
		{
			String queryString="from WkTInfo as i where i.kiId in (select d.kiId from WkTDistribute as d where d.kcId=? and d.kbStatus is 0)"; 
			return  getHibernateTemplate().find(queryString, new Object[]{kcid});
		}
		public WkTInfoscore getScore(String kitype)
		{
			String queryString="from WkTInfoscore as i where i.kiType=?"; 
			return  (WkTInfoscore)getHibernateTemplate().find(queryString, new Object[]{kitype}).get(0);
		}

		public List getInfodep(Long kdid)
		{
			String queryString="from WkTUser as u where u.kuId in(select r.id.kuId from WkTUserole as r where r.id.krId is 14) and  u.kdId=?"; 
			return  getHibernateTemplate().find(queryString, new Object[]{kdid});
		}
		 public List getInfoSiteTime(String kbid)
		 {

				String queryString="from WkTInfo as i where i.kiAddress=?";
				return  getHibernateTemplate().find(queryString, new Object[]{kbid});
		 }
		 public WkTChanel getChanel(Long kcid)
		 {
			 String queryString="from WkTChanel as c where c.kcId=?"; 
				return (WkTChanel)getHibernateTemplate().find(queryString, new Object[]{kcid}).get(0);
		 }
		 public List getOrderedNewsOfChanelFB(Long cid, String orderType) {
				String queryString = "from WkTInfo as i where i.kiId in (select d.kiId from WkTDistribute as d where d.kcId=? and d.kbStatus is 0) order by i.kiCtime "+orderType;
				return getHibernateTemplate().find(queryString,new Object[]{cid});
			}
		 public List getRMKByKiid(Long kiid) {
				String queryString = "from WkTDocrmk as r where r.kbId in (select d.kbId from WkTDistribute as d where d.kiId=?) order by r.koTime";
				return getHibernateTemplate().find(queryString,new Object[]{kiid});
			}
		 public WkTInfo getInfobyBid(Long bid)
		 {
			 String queryString = "from WkTInfo as i where i.kiId =(select d.kiId from WkTDistribute as d where d.kbId=?)";
				return (WkTInfo) getHibernateTemplate().find(queryString,new Object[]{bid}).get(0);
		 }
			public List getChanelOfManage(WkTUser user,List deptList,List rlist)
			{

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
			queryString.append(")) and (auth.krId=0 or auth.krId in (");
			for(int i=0;i<rlist.size();i++){
				WkTUserole role=(WkTUserole) rlist.get(i);
				Long l=role.getId().getKrId();
				queryString.append(l);
				if(i<rlist.size()-1){
					queryString.append(",");
				}
			}
			queryString.append("))and (auth.kaIp11 is null or auth.kaIp11=0 or (auth.kaIp11<=? and auth.kaIp12>=?))"+
						" and (auth.kaIp21 is null or auth.kaIp21=0 or (auth.kaIp21<=? and auth.kaIp22>=?))"+
						" and (auth.kaIp31 is null or auth.kaIp31=0 or (auth.kaIp31<=? and auth.kaIp32>=?))"+
						" and (auth.kaIp41 is null or auth.kaIp41=0 or (auth.kaIp41<=? and auth.kaIp42>=?))"+
						")");
			//queryString.append(")"); 
			return getHibernateTemplate().find(queryString.toString(), new Object[]{ips[0],ips[0],ips[1],ips[1],ips[2],ips[2],ips[3],ips[3]});

			}
			public List getChanelOfAudit(WkTUser user,List deptList,List rlist)
			{

				Long[] ips=IPUtil.getIPLong(user.getKuLastaddr());
				StringBuffer queryString=new StringBuffer("from WkTChanel as c where c.kcId in ("+
				"select auth.kaRid from WkTAuth as auth where auth.kaType=2 and auth.kaCode3=1 and (auth.kdId=0 or auth.kdId in (");
			  for(int i=0;i<deptList.size();i++){
				Long l=(Long)deptList.get(i);
				queryString.append(l);
				if(i<deptList.size()-1){
					queryString.append(",");
				}
			}
			queryString.append(")) and (auth.krId=0 or auth.krId in (");
			for(int i=0;i<rlist.size();i++){
				WkTUserole role=(WkTUserole) rlist.get(i);
				Long l=role.getId().getKrId();
				queryString.append(l);
				if(i<rlist.size()-1){
					queryString.append(",");
				}
			}
			queryString.append("))and (auth.kaIp11 is null or auth.kaIp11=0 or (auth.kaIp11<=? and auth.kaIp12>=?))"+
						" and (auth.kaIp21 is null or auth.kaIp21=0 or (auth.kaIp21<=? and auth.kaIp22>=?))"+
						" and (auth.kaIp31 is null or auth.kaIp31=0 or (auth.kaIp31<=? and auth.kaIp32>=?))"+
						" and (auth.kaIp41 is null or auth.kaIp41=0 or (auth.kaIp41<=? and auth.kaIp42>=?))"+
						")");
			//queryString.append(")"); 
			return getHibernateTemplate().find(queryString.toString(), new Object[]{ips[0],ips[0],ips[1],ips[1],ips[2],ips[2],ips[3],ips[3]});

			}
			public List getNewsOfinfo(Long ktaid)
			{
				 String queryString = "from WkTDistribute as d where d.kbStatus is 0 and d.keId in (select e.keId from WkTExtractask as e where e.ktaId=?) order by d.kbDtime desc";
					return  getHibernateTemplate().find(queryString,new Object[]{ktaid});
			}
		public List NewsSearch(Long keid,Long status,String bt,String et,String flag,String k,String s)
		{
			StringBuffer queryString=new StringBuffer("from WkTDistribute as d where d.keId=? ");
			queryString.append(" and d.kiId in( select distinct i.kiId from  WkTInfo as i where i.kiCtime>=? and i.kiCtime<=?)");
			if(k.length()!=0)
			{
				if(flag.equals("1"))
				{
					queryString.append(" and d.kiId in( select distinct ic.id.kiId from  WkTInfocnt as ic where ic.kiContent like '%"+k+"%')");
				}
				else if(flag.equals("2"))
				{
					queryString.append(" and d.kbTitle  like '%"+k+"%'");
				}
			}
			if(s.length()!=0)
			{
				queryString.append(" and d.kiId in(select i.kiId from WkTInfo as i where i.kiSource  like '%"+s+"%')");
			}
			queryString.append(" and d.kbStatus="+status);
			return getHibernateTemplate().find(queryString.toString(), new Object[]{keid,bt,et});

		}
		public List OriNewsSearch(Long keid,String bt,String et,String flag,String k,String s)
		{
			StringBuffer queryString=new StringBuffer("from WkTOrinfo as d where d.keId=?  ");
			queryString.append(" and d.koiId in( select  i.koiId from  WkTOrinfo as i where i.koiCtime>=? and i.koiCtime<=?)");
			if(k.length()!=0)
			{
				if(flag.equals("1"))
				{
					queryString.append(" and d.koiId in( select distinct ic.id.koiId from  WkTOrinfocnt as ic where ic.koiContent like '%"+k+"%')");
				}
				else if(flag.equals("2"))
				{
					queryString.append(" and d.koiTitle  like '%"+k+"%'");
				}
			}
			if(s.length()!=0)
			{
				queryString.append(" and d.koiId in(select i.koiId from WkTOrinfo as i where i.koiSource  like '%"+s+"%')");
			}
			return getHibernateTemplate().find(queryString.toString(), new Object[]{keid,bt,et});
		}
		
		public List findPubInfoList(Long ktaId)
		{
			String queryString="from WkTDistribute as d where d.keId=?  and d.kbStatus is 0  order by d.kbDtime desc"; 
			return  getHibernateTemplate().find(queryString, new Object[]{ktaId});
		}
		public List findRead(Long kbid,Long kuid)
		{
			String queryString="from WkTNewsRead as d where d.id.kbId=?  and d.id.kuId=?"; 
			return  getHibernateTemplate().find(queryString, new Object[]{kbid,kuid});
		}
}
