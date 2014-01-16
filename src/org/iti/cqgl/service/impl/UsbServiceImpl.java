package org.iti.cqgl.service.impl;
import java.util.List;
import org.iti.cqgl.entity.CqUsb;
import org.iti.cqgl.service.UsbService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class UsbServiceImpl extends BaseServiceImpl implements UsbService {
	public CqUsb findByKuid(Long kuid) {
	   String queryString="from CqUsb as cu where cu.kuId=?";
	   List ulist=getHibernateTemplate().find(queryString, kuid);
	   if(ulist!=null&&ulist.size()>0){
		   return (CqUsb)ulist.get(0);
	   }else{
		   return null;
	   }
	  
	}

	public boolean checkIfExsit(Long kuid,String ip) {
		String queryString="from CqUsb as cu where cu.kuId=? and cu.uipId like '%"+ip+"%'";
		List ulist=getHibernateTemplate().find(queryString, new Object[]{kuid});
		  if(ulist!=null&&ulist.size()>0){
			  return true;
		  }else{
			  return false;
		  }
	}
	public List findBykuid(Long kuid) {
		// TODO Auto-generated method stub
		String queryString = "from CqUsb as cu where cu.kuId=? )";
		return getHibernateTemplate().find(queryString, new Object[] { kuid});
	}
	public List findByCuidNotcuid(String cuId, String cuid) {
		String queryString="from CqUsb as cz where cz.cuId=? and cz.cuId<>?";
		 return getHibernateTemplate().find(queryString,new Object[]{cuId,cuid});
	}

	public List findBykuIdAndIp(Long kuid, String ip) {
		
		String queryString="from CqUsb as cu where cu.kuId=? and cu.uipId like '%"+ip+"%' and cu.ceId is not null and cu.ceId<>'0' and cu.ceId<>''";
		return getHibernateTemplate().find(queryString, new Object[]{kuid});
	}

	public List findkuIdAndIp(Long kuid, String ip) {
		String queryString="from CqUsb as cu where cu.kuId=? and cu.uipId like '%"+ip+"%' ";
		return getHibernateTemplate().find(queryString, new Object[]{kuid});
	}
	
	
	
}
