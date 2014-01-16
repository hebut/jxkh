package org.iti.gh.service;

import org.iti.gh.entity.TeaThought;

import com.uniwin.basehs.service.BaseService;

public interface ThoughtService extends BaseService {

	/**
	 * <li>功能描述：根据用户id和年度查询用户年度思想总结
	 * @param kuid
	 * @param year
	 * @return
	 */
	public TeaThought findByKuidAndYear(Long kuid,String year);
}
