package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;


/*
 * 存取上传附件的路径
 */
public interface GhFileService extends BaseService {
	/**
	 * 
	 * @param fxmId
	 * @param lx
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByFxmIdandFType(Long fxmId,Integer xmType);
}
