package org.iti.wp.service;


import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface WpPhaseService extends BaseService {
 
	 public List findbywtid(Long wtid); 
	public List findphasebykdid(Long kdId) ; 
	public List findphasebykuid(Long kuId) ; 
	//通过学校和评审专家找评审阶段
	public List findphasebykdidAndkuid(Long kdid,Long kuid);
}
