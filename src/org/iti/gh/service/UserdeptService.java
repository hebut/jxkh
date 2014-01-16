package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhUserdept;

import com.uniwin.basehs.service.BaseService;

public interface UserdeptService extends BaseService {
	public List<GhUserdept> findByKdId(Long kdid);
	public List<GhUserdept> findByKuId(Long kuid);
}
