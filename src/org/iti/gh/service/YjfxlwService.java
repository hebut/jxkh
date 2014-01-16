package org.iti.gh.service;

import java.util.List;


import com.uniwin.basehs.service.BaseService;

public interface YjfxlwService extends BaseService {
	List findTypeByGyid(Long gyid);
	Long countByGyidAndTypeAndYear(Long gyid,Short type,Integer year);
    List findByGyidAndTypeAndYear(Long gyid,Short type,Integer year);
}
