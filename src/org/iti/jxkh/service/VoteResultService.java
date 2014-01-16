package org.iti.jxkh.service;

import java.util.List;

import org.iti.jxkh.entity.JXKH_VoteResult;

import com.uniwin.basehs.service.AnnBaseService;
import com.uniwin.framework.entity.WkTRole;

public interface VoteResultService extends AnnBaseService {
	public List<JXKH_VoteResult> findResultByVcId(Long vcId);

	public List<JXKH_VoteResult> findResultByYear(String year);

	public List<WkTRole> findMyRole(Long kuId);
	
	public List<JXKH_VoteResult> findByYearAndKuId(String year,Long kuId);
}
