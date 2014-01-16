package org.iti.xypt.personal.infoCollect.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTExtractask;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;
import org.iti.xypt.personal.infoCollect.service.TaskService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.IPUtil;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.entity.WkTUserole;




public class TaskServiceImpl extends BaseServiceImpl implements TaskService 
{
	public List getChildType(Long ktid)
	{
		String queryString="from WkTTasktype as t where t.ktaPid=? order by t.ktaOrdno"; 
		return getHibernateTemplate().find(queryString, new Object[]{ktid});
	}
	public List getTaskOfManage(WkTUser user,List deptList,List rlist)
	{

		Long[] ips=IPUtil.getIPLong(user.getKuLastaddr());
		StringBuffer queryString=new StringBuffer("from WkTExtractask as w where w.keId in ("+
		"select auth.kaRid from WkTAuth as auth where auth.kaType=3 and auth.kaCode2=1 and (auth.kdId=0 or auth.kdId in (");
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
	public List getTaskOfAuditManage(WkTUser user,List deptList,List rlist)
	{

		Long[] ips=IPUtil.getIPLong(user.getKuLastaddr());
		StringBuffer queryString=new StringBuffer("from WkTExtractask as w where w.keId in ("+
		"select auth.kaRid from WkTAuth as auth where auth.kaType=3 and auth.kaCode3=1 and (auth.kdId=0 or auth.kdId in (");
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
	
	public List getTaskOfAllManage()
	{
	StringBuffer queryString=new StringBuffer("from WkTExtractask as w where w.keId in (select auth.kaRid from WkTAuth as auth where auth.kaType=3)");
	return getHibernateTemplate().find(queryString.toString(), new Object[]{});

}
	public List getChildTask(Long ktaid)
	{
		String queryString="from WkTExtractask as t where t.ktaId=? "; 
		return getHibernateTemplate().find(queryString, new Object[]{ktaid});
	}
	
	public List getChildTasktype(Long pid,Long taid)
	{
		String queryString="from WkTTasktype as w where w.ktaPid=? and w.ktaId!=? ";
		return getHibernateTemplate().find(queryString, new Object[]{pid,taid});
	}
	
	public WkTTasktype getTpyeById(Long ktaid)
	{
		String queryString="from WkTTasktype as w where  w.ktaId=? ";
		return (WkTTasktype) getHibernateTemplate().find(queryString, new Object[]{ktaid}).get(0);
	}
	public  List getTypeAuth(Long ktaid)
	{
		String queryString="from WkTAuth as a where  a.kaRid=? and a.kaType is 3";
		return getHibernateTemplate().find(queryString, new Object[]{ktaid});
	}
	public List  getTaskByKtaid(Long ktaid)
	{
		String queryString="from WkTExtractask as t where  t.ktaId=?";
		return getHibernateTemplate().find(queryString, new Object[]{ktaid});
	}
	public List getInfoBykeid(Long keid)
	{
		String queryString="from WkTInfo as t where  t.keId=?";
		return getHibernateTemplate().find(queryString, new Object[]{keid});
	}
	public List getTaskBykeId(Long keid)
	{
		String queryString="from WkTExtractask as t where  t.keId=?";
		return getHibernateTemplate().find(queryString, new Object[]{keid});
	}
	
	/* 抽取task的方法*/
	public List findAllTask() {
		String query="from WkTExtractask";
		return getHibernateTemplate().find(query);
	}

	public List findByFolderId(Long folderId) {
		String query="from WkTExtractask as u where u.ktaId=?";
		return getHibernateTemplate().find(query,new Object[]{folderId});
	}

	public WkTTasktype findByFolderID(Long id) {
		String query="from WkTTasktype as model where model.ktaId=?";
		List<WkTTasktype> rList=getHibernateTemplate().find(query,new Object[]{id});
		return rList.get(0);
	}
	
	public WkTExtractask findById(Long id) {
		String query="from WkTExtractask as model where model.keId=?";
		return (WkTExtractask) getHibernateTemplate().find(query).get(0);
	}
	public List getUserSort(Long kusid)
	{
		String query="from WkTUsersort as model where model.kusPid=?";
		return getHibernateTemplate().find(query,new Object[]{kusid});
	}
	@Override
	public List findTaskAndExtract() {
		
		List allList=new ArrayList();
		String query="from WkTTasktype as model where model.ktaPid!="+Long.parseLong("0");
		List typeList=getHibernateTemplate().find(query);
		if(typeList!=null){
			WkTTasktype tasktype;
			for(int i=0;i<typeList.size();i++){
				tasktype=(WkTTasktype)typeList.get(i);
				allList.add(tasktype);
				String query2="from WkTExtractask as model where model.ktaId="+tasktype.getKtaId();
				List eList=getHibernateTemplate().find(query2);
				if(eList!=null){
					allList.addAll(eList);
				}
			}
		}
		
		
		return allList;
	}

}