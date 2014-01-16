package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhFxfz;

import com.uniwin.basehs.service.BaseService;

public interface UserfxfzService extends BaseService{
	public List<GhFxfz> findByGyid(Long gyId);
	public List<GhFxfz> findFxfzByGyid(Long sch,Long kuid);
	public List<GhFxfz> findByKuid(Long kuid);
}
