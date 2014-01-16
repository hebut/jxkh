package org.iti.cqgl.service;

import java.util.Date;
import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface DstudentService extends BaseService {
	public List findByYearAndTermAndgradeAndXy(String year, Short term, Long schid, Long xykdid,
			Integer grade,String tname,String tno,Date start,Date end);
	public List findByYearAndTermAndclass(String year, Short term, Long schid, String cla ,Date start,Date end);
}
