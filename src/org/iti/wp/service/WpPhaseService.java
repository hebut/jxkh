package org.iti.wp.service;


import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface WpPhaseService extends BaseService {
 
	 public List findbywtid(Long wtid); 
	public List findphasebykdid(Long kdId) ; 
	public List findphasebykuid(Long kuId) ; 
	//ͨ��ѧУ������ר��������׶�
	public List findphasebykdidAndkuid(Long kdid,Long kuid);
}
