package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface SgcfService extends BaseService {
	//根据KUID找该老师的事故处分实体
    public List  FindByKuid(long kuid);
}
