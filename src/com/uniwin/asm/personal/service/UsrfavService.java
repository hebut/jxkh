package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface UsrfavService extends BaseService {
	public List getUsrfavList(Long kuid);// ���ݵ�¼�û���ø��û��ղ��б�

	public List getUsrfavTitleList(Long kuid);// ���ݵ�¼�û���ø��û������б�

	public List getUsrfavDiyList(Long kuid);// ���ݵ�¼�û���ø��û��Զ����б�
}
