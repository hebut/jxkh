package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface GhZyService extends BaseService {
	List findByChild(Long zyId);

}
