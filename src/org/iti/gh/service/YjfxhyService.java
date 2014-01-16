package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface YjfxhyService extends BaseService {
	public List findByGyidAndTypeAndSfAndYear(Long gyid,Short type,Short sf,Integer year);
}
