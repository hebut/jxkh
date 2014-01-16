package org.iti.gh.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface ZsService extends BaseService {
	/**
	 * 根据单位部门编号查找该单位部门教师招生情况
	 * @param kdid
	 * @return
	 */
	public List findByKdId(long kdid);
	/**
	 * 根据用户查找该教师的招生情况
	 * @param kuid
	 * @return
	 */
	public List findByKuid(long kuid);
	/**
	 * 根据用户和年份查找该教师的招生情况
	 * @param kuid，year
	 * @return
	 */
	public List findByKuidAndYear(long kuid,String year);
}
