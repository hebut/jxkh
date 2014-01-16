package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_VoteConfig;
import org.iti.jxkh.entity.JXKH_VoteResult;

import com.uniwin.basehs.service.AnnBaseService;
import com.uniwin.framework.entity.WkTDept;

public interface VoteConfigService extends AnnBaseService {
	/**
	 * 
	 * @return
	 */
	public List<WkTDept> findAllDept();
	/**
	 * 查找管理部门
	 * @author WXY
	 */
	public List<WkTDept> findManageDept();

	/**
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_VoteConfig> findConfigByYear(String year);
	
	public List<JXKH_VoteConfig> findConfig(Long vcId);

	/**
	 * 
	 * @param year
	 * @return
	 */
	public List<JXKH_VoteConfig> findConfigByKuId(String kuId, String year);
	/**
	 * 
	 * @param vcId
	 * @return
	 */
	public List<JXKH_VoteResult>findResultByVcId(Long vcId);
	
	
}
