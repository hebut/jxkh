package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface SgcfService extends BaseService {
	//����KUID�Ҹ���ʦ���¹ʴ���ʵ��
    public List  FindByKuid(long kuid);
}
