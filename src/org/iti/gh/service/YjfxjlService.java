package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface YjfxjlService extends BaseService {
	List findByGyidAndGyType(Long gyid,Short type);
	public List findTypeByGyid(Long gyid);
	public List findByGyidAndtypeAndDjAndJbAndYear(Long gyid,Short type,Short dj,Short jb,Integer year);
	public List findGjDjByGyidAndTypeAndGjjb(Long gyid,Short type,Short gjjb) ;
	public List findGjjbByGyidAndtype(Long gyid,Short type);
}
