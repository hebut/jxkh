package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.asm.personal.entity.QzGroup;
import com.uniwin.basehs.service.BaseService;

public interface GroupService extends BaseService {
	/**
	 * 查询用户最近建立的群组
	 */
	public QzGroup findNewCreateGroup(Long kuid);

	/**
	 * 查询某一用户创建或加入的群组
	 * 
	 * @param kuid
	 * @return
	 */
	public List findgroupBykuid(Long kuid);

	/**
	 * 根据类型或名称查询群组
	 */
	public List findGroupByTypeOrName(Integer type, String name);

	/**
	 * 查询自己创建的所有群组
	 */
	public List<QzGroup> findMyGroup(Long kuId);
}
