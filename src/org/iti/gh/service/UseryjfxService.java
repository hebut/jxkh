package org.iti.gh.service;



import java.util.List;

import org.iti.gh.entity.GhUseryjfx;


import com.uniwin.basehs.service.BaseService;

public interface UseryjfxService extends BaseService {

	public List<GhUseryjfx> findByKuid(Long kuId);
	public List<GhUseryjfx> findByGyid(Long gyId);
}
