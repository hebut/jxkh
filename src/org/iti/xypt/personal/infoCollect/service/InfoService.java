package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import org.iti.xypt.personal.infoCollect.entity.WkTOrinfo;
import org.iti.xypt.personal.infoCollect.entity.WkTTasktype;

import com.uniwin.basehs.service.BaseService;


/* –≈œ¢±Ì */
public interface InfoService extends BaseService{

	public void save(WkTOrinfo wkTOrinfo);
	public List findBySite(Long id);
	public List findAllPubNews();
	
}
