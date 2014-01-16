package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTTotal;
import org.iti.xypt.personal.infoCollect.entity.WkTWebsite;
import org.iti.xypt.personal.infoCollect.service.WebsiteService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.entity.WkTSite;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.entity.WkTUserole;



public class WebsiteServiceImpl extends BaseServiceImpl implements WebsiteService {

	public List getChildWebsite(Long ptid)
	{
			String queryString="from WkTWebsite as w where w.kwPid=?"; 
			return getHibernateTemplate().find(queryString, new Object[]{ptid});
	}
	public List getChildUsersort(Long ptid)
	{
			String queryString="from WkTUsersort as s where s.kusPid=?"; 
			return getHibernateTemplate().find(queryString, new Object[]{ptid});
	}
	public List getChildWebsite(Long ptid,Long did)
	{
		String queryString="from WkTWebsite as w where w.kwPid=? and w.kwId!=? ";
		return getHibernateTemplate().find(queryString, new Object[]{ptid,did});
	}
	public WkTWebsite findBykwid(Long kwid)
	{
		String queryString="from WkTWebsite as w where w.kwId=?"; 
		return (WkTWebsite)getHibernateTemplate().find(queryString, new Object[]{kwid}).get(0);
	}
	public WkTSite findByKsid(Long ksid){
		String queryString="from WkTSite as c where c.ksId=?"; 
		return (WkTSite)getHibernateTemplate().find(queryString, new Object[]{ksid}).get(0);
	
	}
public List findAuthOfWebsite(Long kwid) {
		
		String queryString="from WkTAuth as au where au.kaType=15 and au.kaRid=? order by au.kaRid";		
		return getHibernateTemplate().find(queryString, new Object[]{kwid});
	}
public List getDistriByWid(Long kwid) {
	String queryString="from WkTDistribute as d where d.kcId in (select c.kcId from WkTChanel as c where c.kwId=?) "; 
	return getHibernateTemplate().find(queryString, new Object[]{kwid});
}
public List findByKwPid(Long pid)
{
	String queryString="from WkTWebsite as w where w.kwPid=? ";
	return getHibernateTemplate().find(queryString, new Object[]{pid});
}

public List getWebsiteOfUserManage(WkTUser user,List deptList,List websiteList)
{

	//deptList 当前用户及下级部门列表,要求权限所在的部门及角色所在部门在此列表中
	//用户登陆IP数组
	Long[] ips=IPUtil.getIPLong(user.getKuLastaddr());
	StringBuffer queryString=new StringBuffer("from WkTWebsite as w where w.kwId in ("+
	"select auth.kaRid from WkTAuth as auth where auth.kaType=15 and auth.kaCode2=1 and (auth.kdId=0 or auth.kdId in (");
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
			") and w.kwId in(");
for(int i=0;i<websiteList.size();i++){
	WkTWebsite w=(WkTWebsite)websiteList.get(i);
	queryString.append(w.getKwId());
	if(i<websiteList.size()-1){
		queryString.append(",");
	}
}
queryString.append(") and w.kwStatus is 0"); 
return getHibernateTemplate().find(queryString.toString(), new Object[]{ips[0],ips[0],ips[1],ips[1],ips[2],ips[2],ips[3],ips[3]});

}

public List getWebsiteOfUserManage(WkTUser user,List deptList)
{

	Long[] ips=IPUtil.getIPLong(user.getKuLastaddr());
	StringBuffer queryString=new StringBuffer("from WkTWebsite as w where w.kwId in ("+
	"select auth.kaRid from WkTAuth as auth where auth.kaType=15 and auth.kaCode2=1 and (auth.kdId=0 or auth.kdId in (");
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
			") and w.kwStatus is 0");
//queryString.append(")"); 
return getHibernateTemplate().find(queryString.toString(), new Object[]{ips[0],ips[0],ips[1],ips[1],ips[2],ips[2],ips[3],ips[3]});

}
public List getAuth(Long kwid)
{
	String queryString="from WkTAuth as a where a.kaRid=? and a.kaType is 15";
	return getHibernateTemplate().find(queryString, new Object[]{kwid});
}
public List getRole(Long kuid)
{
	String queryString="from WkTUserole as a where a.id.kuId=? ";
	return getHibernateTemplate().find(queryString, new Object[]{kuid});
}
public WkTTotal getTotal(Long kwid)
{
	String queryString="from WkTTotal as a where a.id.kwId=?";
	return (WkTTotal)getHibernateTemplate().find(queryString, new Object[]{kwid}).get(0);
	
}
public List getWebsiteOfManage(WkTUser user,List deptList,List rlist)
{

	Long[] ips=IPUtil.getIPLong(user.getKuLastaddr());
	StringBuffer queryString=new StringBuffer("from WkTWebsite as w where w.kwId in ("+
	"select auth.kaRid from WkTAuth as auth where auth.kaType=15 and auth.kaCode2=1 and (auth.kdId=0 or auth.kdId in (");
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
			") and w.kwStatus is 0");
//queryString.append(")"); 
return getHibernateTemplate().find(queryString.toString(), new Object[]{ips[0],ips[0],ips[1],ips[1],ips[2],ips[2],ips[3],ips[3]});

}
public List getChildNum(Long kusid)
{
	String queryString="from WkTUsersort as a where a.kusPid=?";
	return getHibernateTemplate().find(queryString, new Object[]{kusid});
}
}
