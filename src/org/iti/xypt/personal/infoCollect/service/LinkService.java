package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;


public interface LinkService extends BaseService{
	
	public List findbyUrl(String url);
	public List findByKeId(Long id);
	
	//�жϳ�ȡ��ַ����������id��url����
	public List findByIdAndUrl(String url,Long id);
	
	//�ж��Ƿ��ȡ��ַ����������id���Ƿ���ȡ0,1
	public List findByIdAndStatus(Long id,Long sId);
}
