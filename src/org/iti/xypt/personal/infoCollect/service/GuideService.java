package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTGuidereg;

import com.uniwin.basehs.service.BaseService;



public interface GuideService extends BaseService{

	public List findGuideListById(Long Id);
	public WkTGuidereg findById(Long sid,Long gid);
}
