package org.iti.xypt.personal.infoCollect.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;


public interface LinkService extends BaseService{
	
	public List findbyUrl(String url);
	public List findByKeId(Long id);
	
	//判断抽取网址，依据任务id和url本身
	public List findByIdAndUrl(String url,Long id);
	
	//判断是否抽取网址，依据任务id和是否提取0,1
	public List findByIdAndStatus(Long id,Long sId);
}
