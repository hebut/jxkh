package org.iti.kyjf.service.impl;

import java.util.List;

import org.iti.kyjf.entity.Zbteacher;
import org.iti.kyjf.service.ZbteacherService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ZbteacherServiceImpl extends BaseServiceImpl implements
		ZbteacherService {
	public List<Zbteacher> findByYear(Integer year, Long kdid,String tno,String tname) {
		String queryString = " from Zbteacher as zt where zt.ztYear=? and zt.kdId=? and zt.kuId in(select u.kuId from WkTUser as u where " + "u.kuName like '%" + tname + "%' )  and zt.kuId in(select tea.kuId from Teacher as tea where tea.thId like '%" + tno + "%' )";
		return getHibernateTemplate().find(queryString, new Object[]{year,kdid});

	}

	public List findByKuid(Long kuid) {
		String queryString = " from Zbteacher as zt where zt.kuId =? order by zt.ztYear";
		return getHibernateTemplate().find(queryString, new Object[]{kuid});

	}

}
