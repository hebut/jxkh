package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface SubjectService extends BaseService {
	/**
	 * <li>功能描述：根据群组id查询最新发的两个主题
	 */
	public List findByQzid(Long qzid);

	/**
	 * <li>功能描述：根据主题创建者用户id查询其发的所有主题
	 */
	public List findByKuid(Long kuid);

	/**
	 * <li>功能描述：根据主题创建者用户id查询其发的所有主题
	 */
	public List findSubject(Long qzid);
}
