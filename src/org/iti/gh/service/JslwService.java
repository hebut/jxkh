package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GhJslw;

import com.uniwin.basehs.service.BaseService;

public interface JslwService extends BaseService {

	/**
	 * <li>功能描述：根据项目id，用户id和论文种类（如会议论文，期刊论文等）
	 * @param kuid
	 * @param lwid
	 * @param type
	 * @return
	 */
	public GhJslw findByKuidAndLwidAndType(Long kuid,Long lwid,Short type);
	
	/**
	 *  <li>功能描述：根据项目id，论文种类（如会议论文，期刊论文等）
	 * @param lwid
	 * @param type
	 * @return
	 */
	public List findByLwidAndType(Long lwid,Short type);
}
