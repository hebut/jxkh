package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface ShjzService extends BaseService {
//根据KUID找该老师的社会兼职实体
    public List  FindByKuid(long kuid);
}
