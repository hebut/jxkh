package com.uniwin.asm.personal.service;

import java.util.List;

import com.uniwin.basehs.service.BaseService;

public interface QzMessageService extends BaseService {
	/**
	 * 根据主题id查询回复/查看该主题的消息，按照时间倒序
	 * 
	 * 1代表查看，2回复
	 */
	public List findBtSjid(Long sjid, Short type);

	/**
	 * 查询我回复过的内容
	 */
	public List findMyReply(Long kuid);
}
