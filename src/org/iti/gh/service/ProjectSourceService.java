package org.iti.gh.service;

import java.util.List;

import org.iti.gh.entity.GH_PROJECTSOURCE;

import com.uniwin.basehs.service.BaseService;

public interface ProjectSourceService extends BaseService {

	/**
	 * 根据来源名称查找项目来源列表
	 * @param name
	 * @return
	 */
	public List<GH_PROJECTSOURCE> findByName(String name);
	
	/**
	 * 获得符合条件的记录数
	 * @param name
	 * @return
	 */
	public List<Long> getCountByName(String name);
	
	/**
	 * 根据来源名称分页查询项目来源列表
	 * @param name
	 * @return
	 */
	public List<GH_PROJECTSOURCE> findByNameAndPaging(String name,int pageNum,int pageSize);
	
	/**
	 * 判断当前项目来源是否已存在
	 * @param name
	 * @return true:存在重复的记录
	 * 		   false:不存在重复的记录
	 */
	public boolean isNameRepeat(String name);
}
