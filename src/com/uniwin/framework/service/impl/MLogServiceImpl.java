package com.uniwin.framework.service.impl;

import java.util.Date;
import java.util.List;

import com.uniwin.basehs.service.impl.BaseServiceImpl;
import com.uniwin.common.util.ConvertUtil;
import com.uniwin.framework.entity.WkTMlog;
import com.uniwin.framework.entity.WkTUser;
import com.uniwin.framework.service.MLogService;

public class MLogServiceImpl extends BaseServiceImpl implements MLogService {
	@SuppressWarnings("unchecked")
	public List<WkTMlog> findLogsByTimes(String btime, String etime) {
		String queryString = "from WkTMlog as l where l.kmlTime between ? and ? order by l.kmlId desc";
		return  getHibernateTemplate().find(queryString, new Object[] { btime, etime });
	}

	public void saveMLog(String func, String desc, WkTUser douser) {
		WkTMlog log = new WkTMlog();
		log.setKmlDesc(desc);
		log.setKmlFunc(func);
		log.setKmlIp(douser.getKuLastaddr());
		Date d = new Date();
		log.setKmlTime(ConvertUtil.convertDateAndTimeString(d));
		log.setKuId(douser.getKuId());
		log.setKuName(douser.getKuName());
		getHibernateTemplate().save(log);
	}
}
