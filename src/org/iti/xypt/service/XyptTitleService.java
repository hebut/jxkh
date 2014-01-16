package org.iti.xypt.service;

import java.util.List;

import org.iti.xypt.entity.Title;

import com.uniwin.basehs.service.BaseService;

public interface XyptTitleService extends BaseService {
	public Title findByTId(Integer TId );
	
	public List findFirstTitles();
	
	public List findByPtid(String pid);

	
	public List findByTname(String tname);

	public List findBytid(String tid);

}
