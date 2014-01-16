package org.iti.jxkh.service.impl;

import java.util.List;

import org.iti.jxkh.entity.Jxkh_Honour;
import org.iti.jxkh.entity.Jxkh_HonourFile;
import org.iti.jxkh.service.RychService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class RychServiceImpl extends BaseServiceImpl implements RychService {

	public List FindByKuid(long kuid) {
		String queryString="from Jxkh_Honour as ch where ch.kuId=?  order by ch.ryYear";
		return getHibernateTemplate().find(queryString,new Object[]{kuid});
	}

	
	public List<Jxkh_HonourFile> findFileByHonour(Jxkh_Honour honour) {
		String queryString = "from Jxkh_HonourFile as rf where rf.honour = ?";
		return getHibernateTemplate().find(queryString,new Object[]{honour});
	}


	@Override
	public List<Jxkh_HonourFile> findFileByFilename(String fileName) {
		String queryString = "from Jxkh_HonourFile as rf where rf.fileName = ?";
		return getHibernateTemplate().find(queryString,new Object[]{fileName});
	}


	@Override
	public List<Jxkh_Honour> findHonourByState(short state) {
		String queryString="from Jxkh_Honour as ch where ch.state=?  order by ch.ryYear";
		return getHibernateTemplate().find(queryString,new Object[]{state});
	}


	@Override
	public List<Jxkh_Honour> findAllHonour() {
		String queryString="from Jxkh_Honour as ch order by ch.state,ch.ryYear";
		return getHibernateTemplate().find(queryString,new Object[]{});
	}


	@Override
	public List<Jxkh_Honour> findHonourByCondition(String name, int index) {
		short state = 0;
		String queryString;
		switch(index) {
		case 1:
			state = Jxkh_Honour.NONE;
			break;
		case 2:
			state = Jxkh_Honour.BUSI_PASS;
			break;
		case 3:
			state = Jxkh_Honour.BUSI_OUT;
			break;
		case 4:
			state = Jxkh_Honour.RECORD_YES;
			break;
		}
		if(index == 0) {
			queryString="from Jxkh_Honour as ch where ch.ryName like '%"+name+"%' order by ch.state,ch.ryYear";
		}else {
			queryString="from Jxkh_Honour as ch where ch.ryName like '%"+name+"%' and ch.state = "+state+" order by ch.state,ch.ryYear";
		}		
		return getHibernateTemplate().find(queryString,new Object[]{});
	}
}
