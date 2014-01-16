/**
 * 
 */
package org.iti.bysj.service.impl;

import java.util.List;
import org.iti.bysj.service.GprocesService;
import org.iti.xypt.entity.Student;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

/**
 *
 * @author DaLei
 * @version $Id: GprocesServiceImpl.java,v 1.1 2011/08/31 07:03:06 ljb Exp $
 */
public class GprocesServiceImpl extends BaseServiceImpl implements
		GprocesService {

	/**
	 * 只要用户具有该学校的角色，就说明能够参与此毕设过程
	 */
	public List findByUser(Long kuid) {
		Student stu=(Student) get(Student.class, kuid);
		String queryString=null;
		if(stu==null){
			queryString="from BsGproces as gp where gp.kdId in (select r.kdId from WkTRole as r where r.krId"+
			" in(select u_r.id.krId from XyUserrole as u_r where u_r.id.kuId=?) and r.krId in (select krId from WkTAuth where kaRid in (select ktId from WkTTitle where ktName='毕业设计'))) order by gp.bsGrade desc,gp.kdId ";
		}else{
			queryString="from BsGproces as gp where gp.bgId in (select bu.bgId from BsGpunit as bu,BsStudent as bs where bs.kuId=? and bu.buId=bs.buId) order by gp.bsGrade desc,gp.kdId ";
		}
		
		return getHibernateTemplate().find(queryString, new Object[]{kuid});
	}

	public List findByKdId(Long kdId) {
		String queryString="from BsGproces as gp where gp.kdId=?";
		return getHibernateTemplate().find(queryString, new Object[]{kdId});
	}

	public List findByKdIdAndSgrade(Long kdId, Integer sgrade) {
		String queryString="from BsGproces as gp where gp.kdId=? and gp.bsGrade=?";
		return getHibernateTemplate().find(queryString, new Object[]{kdId,sgrade});
	}

	

}
